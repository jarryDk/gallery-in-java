#!/bin/bash

source ./config.conf

NETWORK=$PODMAN_NETWORK

podman network exists $NETWORK

if [ $? != 0 ]; then
    echo "The network $NETWORK did not exists"
    podman network create $NETWORK
else
    echo "We already have $PODMAN_NETWORK as a network"
fi
