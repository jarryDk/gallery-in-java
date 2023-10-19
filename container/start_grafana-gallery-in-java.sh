#!/bin/bash

source ./config.conf

podman container exists $GRAFANA_NAME
if [ $? = 0 ]; then
  podman stop $GRAFANA_NAME
fi

podman run -d \
  --rm=true \
  --name $GRAFANA_NAME \
  --network=$PODMAN_NETWORK \
  -p 3000:3000 \
  grafana/grafana:10.1.5