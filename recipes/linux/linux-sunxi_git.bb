DESCRIPTION = "Linux Kernel for cubieboard"
SECTION = "kernel"
LICENSE = "GPLv2"
PR = "r1"


SRCREV = "274a66a7bfcbaabb88d63e4eba161965383cc416"
PV = "3.4.43+gitr${SRCREV}"
SRC_URI = "git://github.com/hulifox008/linux-sunxi.git;branch=fox-3.4;protocol=git \
           file://defconfig_cubieboard"

S = "${WORKDIR}/git"

inherit kernel

COMPATIBLE_HOST = "arm.*-linux"
COMPATIBLE_MACHINE = "cubieboard"

do_configure() {
	install ${WORKDIR}/defconfig_cubieboard ${S}/.config
	oe_runmake oldconfig
}

KERNEL_RELEASE = "3.4.43"
