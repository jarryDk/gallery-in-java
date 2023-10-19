#!/bin/bash


echo
echo "🚮 Housekeeping"
./hard-housekeeping.sh

echo
echo "🖧 Create network - if needed"
./create_network.sh

echo
echo "🖫 Create volume"
./create_volume_prometheus-config-gallery-in-java.sh

echo
echo "⛴️ Start containers"
./start_postgres-gallery-in-java.sh
./start_gallery-server-jvm-gallery-in-java.sh
./start_jaeger-gallery-in-java.sh
./start_prometheus-gallery-in-java.sh
./start_grafana-gallery-in-java.sh
