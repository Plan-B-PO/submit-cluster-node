#!/bin/bash

TOKEN="c808563e-3eb8-49a9-901b-f4098f59d44a"

#cpu
CPU=$(grep ^cpu\\scores /proc/cpuinfo | uniq |  awk '{print $4}')

#gpu
GPU=$(glxinfo | grep 'Video memory' | awk '{print $3/1024}')

#memory
MEM=$(awk '/MemTotal/ {printf "%.3f",$2/1024/1024}' /proc/meminfo)

#local storage
LOCAL=$(df --output=avail -h "$PWD" | sed '1d;s/[^0-9]//g')

JSON=$(echo "{token: ${TOKEN}, cpu: ${CPU}, gpu: ${GPU}, memory: ${MEM}, local_storage: ${LOCAL}}")

curl -d "$JSON" -H "Content-Type: application/json" -X PUT http://localhost:8080/verificator