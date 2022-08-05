DESCRIPTION = "My own quilt recipe"
SECTION = "MyTask"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
SRC_URI = "https://download.savannah.gnu.org/releases/quilt/quilt-0.66.tar.gz \
	file://run-ptest \
        file://Makefile \
        file://test.sh \
        file://0001-tests-Allow-different-output-from-mv.patch \
"

SRC_URI[sha256sum] = "314b319a6feb13bf9d0f9ffa7ce6683b06919e734a41275087ea457cc9dc6e07"

SRC_URI:append:class-target = " file://gnu_patch_test_fix_target.patch"
inherit autotools
inherit autotools-brokensep
inherit pkgconfig
EXTRA_OECONF = "--with-perl='${USRBINPATH}/env perl' --with-patch=patch --without-sendmail"
EXTRA_OECONF:append:class-native = " --disable-nls"
EXTRA_AUTORECONF += "--exclude=aclocal"
REBAR3_RELEASE = "quilt_0.66"
DEPENDS +=  "pkgconfig"
PROVIDES += "aetisam"
PATCHTOOL:class-native = "patch"
CLEANBROKEN = "1"
CACHED_CONFIGUREVARS += "ac_cv_path_BASH=/bin/bash ac_cv_path_COLUMN=column"
do_configure:prepend () {
        find ${S} -name "*.in" -exec sed -i -e "1s,^#\!.*@PERL@ -w$,#\! @PERL@\nuse warnings;," {} \;
}


do_configure:append () {
        sed -e 's,^COMPAT_SYMLINKS.*:=.*,COMPAT_SYMLINKS        :=,' -i ${S}/Makefile
}

do_install () {
	oe_runmake 'BUILD_ROOT=${D}' install
	# cleanup unpackaged files
	rm -rf ${D}/${datadir}/emacs
}
do_display_banner() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*  I am display coming from 0.68 quilt        *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}
RDEPENDS:${PN} = "bash patch diffstat bzip2 util-linux less"
                                                                                                                                                                           

