DESCRIPTION = "Linux Kernel for cubieboard"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r1"


SRCREV = "9c408f59515ec06a0c5f38d9d507352c5aa6ee6f"
PV = "3.4.43+gitr${SRCREV}"
SRC_URI = "git://github.com/hulifox008/linux-sunxi.git;branch=fox-3.4;protocol=git \
           file://defconfig_cubieboard"

S = "${WORKDIR}/git"

KERNEL_IMAGETYPE = "uImage"
inherit kernel

COMPATIBLE_HOST = "arm.*-linux"
COMPATIBLE_MACHINE = "cubieboard"

do_configure() {
	install ${WORKDIR}/defconfig_cubieboard ${S}/.config
	oe_runmake oldconfig
}

KERNEL_RELEASE = "3.4.43"
