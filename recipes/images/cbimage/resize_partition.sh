#!/bin/sh

echo "Resizing partition ..."
mount -o remount,rw /
sfdisk -uS -f -L /dev/mmcblk0 << EOF
16384,,L
EOF
rm -fr /etc/rcS.d/S98resize_partition.sh
sleep 5;
reboot -f
