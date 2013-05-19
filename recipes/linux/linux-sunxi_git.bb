DESCRIPTION = "Linux Kernel for cubieboard"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r1"


SRCREV = "c96ea5293f163d16f99822fdc78f902184628a2c"
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
