#!/bin/bash

docker run -it --rm \
  -p 8080:8080 \
  --name wiremock \
  -v $PWD/wiremock:/home/wiremock \
  wiremock/wiremock:2.33.2
