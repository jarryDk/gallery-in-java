#!/bin/bash

source ./config.conf

podman container exists $POSTGRES_NAME
if [ $? = 0 ]; then
  podman stop $POSTGRES_NAME
fi

podman run -d \
  --rm=true \
  --name $POSTGRES_NAME \
  --network=$PODMAN_NETWORK \
  -e POSTGRES_USER=$POSTGRES_DATABASE_USER \
  -e POSTGRES_PASSWORD=$POSTGRES_DATABASE_PASSWORD \
  -e POSTGRES_DB=$POSTGRES_DATABASE_NAME \
  -p 5432:5432 \
  postgres:16
