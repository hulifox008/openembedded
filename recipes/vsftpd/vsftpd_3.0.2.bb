DESCRIPTION = "Secure ftp daemon"
SECTION = "console/network"
LICENSE = "GPL"
PR = "r4"

DEPENDS = "libcap"

SRC_URI = "https://security.appspot.com/downloads/vsftpd-3.0.2.tar.gz \
           file://init \
           file://vsftpd.conf"

inherit update-rc.d

CONFFILES_${PN} = "${sysconfdir}/vsftpd.conf"

do_compile() {
        oe_runmake "LIBS=-L${STAGING_LIBDIR} -lcrypt -lcap -lpam"
}

do_install() {
        install -d ${D}${sbindir}
        install -d ${D}${sysconfdir}/init.d/
        install -m 0755 ${S}/vsftpd ${D}${sbindir}/vsftpd
        install -m 0755 ${WORKDIR}/vsftpd.conf ${D}${sysconfdir}/vsftpd.conf
        install -m 755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/vsftpd
}

pkg_postinst() {
        if [ "x$D" != "x" ]; then
                exit 1
        fi
        addgroup ftp
        adduser --system --home /var/lib/ftp --no-create-home --ingroup ftp --disabled-password -s /bin/false ftp
        mkdir -p ${localstatedir}/share/empty
}

INITSCRIPT_NAME = "vsftpd"

INITSCRIPT_PARAMS = "defaults"
SRC_URI[md5sum] = "8b00c749719089401315bd3c44dddbb2"
SRC_URI[sha256sum] = "be46f0e2c5528fe021fafc8dab1ecfea0c1f183063a06977f8537fcd0b195e56"

