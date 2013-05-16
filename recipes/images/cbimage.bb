# micro-image
#
# Image configuration for the OE Micro Linux Distribuion (micro, micro-uclibc)
#
# This image basically consists of: libc, busybox, udev, sysv init, and a few
# init scripts for running up the system.
#
# It is truely tiny and should build with most architectures/machines.
#
# The image is tested to build and run succesfully with the following machines:
#
#	* at91sam9263ek (jffs2 image size ~ 1 MB, uclibc)
#	* x86
#
# Maintainer: Martin Lund <mgl@doredevelopment.dk>
#

SRC_URI += "file://sunxi_env \
            file://resize_partition.sh \
            file://resize_fs.sh \
            "
DEPENDS += "u-boot-sunxi"

# Install basic files only
IMAGE_INSTALL = "\
kernel \
kernel-modules \
base-files \
base-passwd \
initscripts \
bash \
vim \
openssh \
apache2 \
vsftpd \
e2fsprogs \
e2fsprogs-tune2fs \
procps \
hdparm \
smartmontools \
coreutils \
"
IMAGE_LINGUAS = ""

# Use busybox as login manager
IMAGE_LOGIN_MANAGER = "busybox"

# Include minimum init and init scripts
IMAGE_DEV_MANAGER = "udev"
IMAGE_INIT_MANAGER = "sysvinit sysvinit-pidof"
IMAGE_INITSCRIPTS = ""

IMAGE_FSTYPES = "ext4"

inherit image

rootfs_postproc() {
    install -m 0755 ${WORKDIR}/resize_partition.sh ${IMAGE_ROOTFS}/etc/rcS.d/S98resize_partition.sh
    install -m 0755 ${WORKDIR}/resize_fs.sh ${IMAGE_ROOTFS}/etc/rcS.d/S99resize_fs.sh
}

image_postproc() {
#Assemble final image that can be written to SD card.
OUTPUT=cubieboard_sd.img
 
pushd ${DEPLOY_DIR_IMAGE}
 
dd if=/dev/zero of=${OUTPUT} bs=1M count=1
dd if=uImage-cubieboard.bin of=${OUTPUT} oflag=append conv=notrunc
truncate -s 8M ${OUTPUT}
dd if=${IMAGE_LINK_NAME}.ext4 of=${OUTPUT} oflag=append conv=notrunc
dd if=sunxi-spl.bin of=${OUTPUT} bs=1024 seek=8 conv=notrunc
dd if=u-boot.bin of=${OUTPUT} bs=1024 seek=32 conv=notrunc

printf "envsize=0x%x" `ls -sL --block-size=512 uImage-cubieboard.bin | cut -d ' ' -f 1` >> ${WORKDIR}/sunxi_env
 
mkenvimage -s 131072 -o sunxi_env.bin ${WORKDIR}/sunxi_env
dd if=sunxi_env.bin of=${OUTPUT} bs=1024 seek=544 conv=notrunc

sfdisk -f -uS -L ${OUTPUT} << EOF
16384,,L
EOF
 
popd
}

IMAGE_POSTPROCESS_COMMAND = "image_postproc"
ROOTFS_POSTPROCESS_COMMAND = "rootfs_postproc"
