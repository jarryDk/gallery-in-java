#!/bin/bash

source ./config.conf

podman container exists $GALLERY_SERVER_NAME
if [ $? = 0 ]; then
  podman stop $GALLERY_SERVER_NAME
fi

podman run -d \
  --rm=true \
	--name $GALLERY_SERVER_NAME \
  --network=$PODMAN_NETWORK \
  -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://$POSTGRES_NAME:5432/$POSTGRES_DATABASE_NAME \
  -e QUARKUS_DATASOURCE_USERNAME=$POSTGRES_DATABASE_USER \
  -e QUARKUS_DATASOURCE_PASSWORD=$POSTGRES_DATABASE_PASSWORD \
  -e QUARKUS_OTEL_EXPORTER_OTLP_TRACES_ENDPOINT=http://$JAEGER_NAME:4317 \
  -p 8080:8080 \
  -p 8443:8443 \
  jarrydk/gallery-server-jvm

podman logs -t -f $GALLERY_SERVER_NAME