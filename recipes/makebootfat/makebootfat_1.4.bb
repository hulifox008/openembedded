DESCRIPTION = "makebootfat"
INC_PR ="r0"


BBCLASSEXTEND = "native sdk"

SRC_URI = "ftp://ftp.mirrorservice.org/sites/distfiles.gentoo.org/distfiles/makebootfat-1.4.tar.gz"

inherit autotools

do_install_append(){
    install -m 0755 -d ${DEPLOY_DIR_IMAGE}
    install -m 0644 ${S}/mbrfat.bin ${DEPLOY_DIR_IMAGE}/mbrfat.bin
}

SRC_URI[md5sum] = "8ae9144e2bec8b8498361a25fdf76741"
SRC_URI[sha256sum] = "0287daafc04da2ae70676f0cf6b6c7fbd8742183ce82d005afd078d0550f0f6c"

