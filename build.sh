#!/bin/bash

echo "Performing a clean Maven build"
mvn clean package -DskipTests=true

echo "Building the Gateway"
cd gateway
docker build --tag gateway .
cd ..
