#!/bin/sh

echo "Resizing partition ..."
mount -o remount,rw /
sfdisk -uS -f -L /dev/mmcblk0 << EOF
2048,16384,b
18432,,L
EOF
rm -fr /etc/rcS.d/S98resize_partition.sh
reboot -f
