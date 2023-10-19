#!/bin/bash

source ./config.conf

podman container exists $PROMERHEUS_NAME
if [ $? = 0 ]; then
  podman stop $PROMERHEUS_NAME
fi

podman run -d \
  --rm=true \
	--name $PROMERHEUS_NAME \
  --network=$PODMAN_NETWORK \
	-p 9090:9090 \
	-v prometheus-config-gallery-in-java:/etc/prometheus \
	prom/prometheus:v2.47.2 --config.file=/etc/prometheus/prometheus.yml
