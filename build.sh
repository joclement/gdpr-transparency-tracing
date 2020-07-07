#!/bin/bash

echo "Performing a clean Maven build"
mvn clean package -DskipTests=true
