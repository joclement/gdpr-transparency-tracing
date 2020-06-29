#!/bin/bash

echo "Performing a clean Maven build"
mvn clean package -DskipTests=true

echo "Building the UAA"
cd uaa
docker build --tag scg-demo-uaa .
cd ..

echo "Building the Gateway"
cd security-gateway
docker build --tag scg-demo-security-gateway .
cd ..
