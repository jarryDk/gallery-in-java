#!/bin/bash

source ./config.conf

podman container exists $JAEGER_NAME
if [ $? = 0 ]; then
  podman stop $JAEGER_NAME
fi

podman run -d \
  	--rm=true \
	--name $JAEGER_NAME \
  	--network=$PODMAN_NETWORK \
  	-e COLLECTOR_OTLP_ENABLED=true \
	-p 4317:4317 \
	-p 4318:4318 \
	-p 5775:5775/udp \
	-p 6831:6831/udp \
	-p 6832:6832/udp \
	-p 5778:5778 \
	-p 14268:14268 \
	-p 14250:14250 \
	-p 16686:16686 \
	jaegertracing/all-in-one:1.50