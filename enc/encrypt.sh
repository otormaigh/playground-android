#!/bin/bash

set -e

if [[ -n "$PLAYGROUND_ENCRYPT_KEY" ]]; then
  echo "> Encrypting files"
  openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in signing/release.keystore -out enc/release.keystore.aes -k $PLAYGROUND_ENCRYPT_KEY
  openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in signing/private_key.pepk -out enc/private_key.pepk.aes -k $PLAYGROUND_ENCRYPT_KEY
  openssl aes-256-cbc -md sha512 -salt -pbkdf2 -iter 10000 -in enc.properties -out enc/enc.properties.aes -k $PLAYGROUND_ENCRYPT_KEY
  echo "> Files encrypted"
  echo "> Finishing up"
else
  echo "> PLAYGROUND_ENCRYPT_KEY is undefined, exiting..."
fi