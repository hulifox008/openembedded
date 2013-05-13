DESCRIPTION = "U-boot bootloader tools"
LICENSE = "GPLv2"
SECTION = "bootloader"

SRCREV = "3b3cb912783a51cae12b09a5c0f42c076c555c91"
SRC_URI = "git://github.com/linux-sunxi/u-boot-sunxi.git;protocol=git;branch=sunxi-current \
          "
inherit kernel-arch

PR = "r1"

S = "${WORKDIR}/${PV}"

EXTRA_OEMAKE = "CROSS_COMPILE=${HOST_PREFIX}"

TARGET_CC_ARCH += "${LDFLAGS}"

do_compile () {
    unset LDFLAGS
	oe_runmake cubieboard_config 
#	sed -i -e 's:img2srec$(SFX) mkimage$(SFX) envcrc$(SFX) ubsha1$(SFX) gen_eth_addr$(SFX) bmp_logo$(SFX):mkimage$(SFX):' tools/Makefile
	oe_runmake HOSTCC=gcc
}

do_install () {
	install -d ${STAGING_BINDIR_NATIVE}
	install -m 0755 tools/mkimage ${STAGING_BINDIR_NATIVE}/uboot-mkimage
	ln -sf uboot-mkimage ${STAGING_BINDIR_NATIVE}/mkimage
    install -m 0755 tools/mkenvimage ${STAGING_BINDIR_NATIVE}/mkenvimage

    install -d ${DEPLOY_DIR_IMAGE}
    install -m 0644 spl/sunxi-spl.bin ${DEPLOY_DIR_IMAGE}/sunxi-spl.bin
    install -m 0644 u-boot.bin ${DEPLOY_DIR_IMAGE}/u-boot.bin
}
