#!/bin/sh

mount -o remount,rw /

resize2fs -f /dev/mmcblk0p2
rm -fr /etc/rcS.d/S99resize_fs.sh
