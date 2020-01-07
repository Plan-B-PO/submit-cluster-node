#!/bin/bash

TOKEN="e8037093-a8f3-4b08-8bf2-e540f3a4be01"

#cpu
CPU=$(grep ^cpu\\scores /proc/cpuinfo | uniq |  awk '{print $4}')

#gpu
GPU=$(glxinfo | grep 'Video memory' | awk '{print $3/1024}')

#memory
MEM=$(awk '/MemTotal/ {printf "%.3f",$2/1024/1024}' /proc/meminfo)

#local storage
LOCAL=$(df --output=avail -h "$PWD" | sed '1d;s/[^0-9]//g')

JSON=$(echo "{\"token\": \"${TOKEN}\", \"cpu\": ${CPU}, \"gpu\": ${GPU}, \"memory\": ${MEM}, \"local_storage\": ${LOCAL}}")
echo ${JSON}
curl -d "$JSON" -H "Content-Type: application/json" -X PUT http://192.168.43.117:8080/verificator