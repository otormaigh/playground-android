#!/bin/bash

set -e

# Checks if an env value is present and not empty
checkEnv() {
  echo "> Checking"
  env_value=$(printf '%s\n' "${!1}")
  if [ -z ${env_value} ]; then
    echo "> $1 is undefined, exiting..."
    exit 1
  else
    echo "> Found value for $1"
  fi
}

checkEnv PLAYGROUND_ENCRYPT_KEY

echo "> Decrypting files"
openssl aes-256-cbc -a -d -md sha256 -in enc/release.keystore.aes -out signing/release.keystore -k PLAYGROUND_ENCRYPT_KEY
openssl aes-256-cbc -a -d -md sha256 -in enc/enc.properties.aes -out enc.properties -k PLAYGROUND_ENCRYPT_KEY
echo "> Files decrypted"

echo "> Finishing up"