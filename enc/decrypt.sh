#!/bin/bash

set -e

if [[ -n "$PLAYGROUND_ENCRYPT_KEY" ]]; then
  echo "> Decrypting files"
  openssl aes-256-cbc -d -md sha512 -salt -pbkdf2 -iter 10000 -in enc/release.keystore.aes -out signing/release.keystore -k $PLAYGROUND_ENCRYPT_KEY
  openssl aes-256-cbc -d -md sha512 -salt -pbkdf2 -iter 10000 -in enc/private_key.pepk.aes -out signing/private_key.pepk -k $PLAYGROUND_ENCRYPT_KEY
  openssl aes-256-cbc -d -md sha512 -salt -pbkdf2 -iter 10000 -in enc/enc.properties.aes -out enc.properties -k $PLAYGROUND_ENCRYPT_KEY
  echo "> Files decrypted"
  echo "> Finishing up"
else
  echo "> PLAYGROUND_ENCRYPT_KEY is undefined, exiting..."
fi