#!/bin/bash

source ./config.conf

function removeContainer(){
    CONTAINER=$1
    echo "Container to remove : $CONTAINER"
    podman container exists $CONTAINER
    if [ $? = 0 ]; then
        echo "We will stop and remove the pod $CONTAINER"
        podman stop $CONTAINER
        podman rm $CONTAINER
    else
        echo "The container $CONTAINER does not exists"
    fi
    echo "----"
}

function removeVolume(){
    VOLUME=$1
    echo "Volume to remove : $VOLUME"
    podman volume exists $VOLUME
    if [ $? = 0 ]; then
        echo "We will remove the volume $VOLUME"
        podman volume rm $VOLUME
    else
         echo "The volume $VOLUME does not exist"
    fi
    echo "----"
}

removeContainer $GRAFANA_NAME
removeContainer $PROMERHEUS_NAME
removeContainer $JAEGER_NAME
removeContainer $GALLERY_SERVER_NAME
removeContainer $POSTGRES_NAME

removeVolume prometheus-config-gallery-in-java
