#!/bin/bash

VOLUME=prometheus-config-gallery-in-java

tar cf $VOLUME.tar prometheus.yml

podman volume exists $VOLUME

if [ $? != 0 ]; then
    podman volume create $VOLUME
    podman volume import $VOLUME $VOLUME.tar
else
    echo "Volume $VOLUME already exists"
fi

rm $VOLUME.tar

podman volume exists $VOLUME

if [ $? = 0 ]; then
    echo "Volume $VOLUME exists"
fi