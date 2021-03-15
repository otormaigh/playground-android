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

echo "> Encrypting files"
openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in signing/release.keystore -out enc/release.keystore.aes -k PLAYGROUND_ENCRYPT_KEY
openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in signing/private_key.pepk -out enc/private_key.pepk.aes -k PLAYGROUND_ENCRYPT_KEY
openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in enc.properties -out enc/enc.properties.aes -k PLAYGROUND_ENCRYPT_KEY
echo "> Files encrypted"

echo "> Finishing up"