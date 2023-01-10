#!/usr/bin/env sh

echo "Starting to wait for Service at host '${1}'"
while ! nc -z -v -w30 ${1} ${2};
do
  echo "Waiting for '${1}' to be ready";
  sleep 5;
done;