#!/bin/bash

DOMAIN=localhost
SUBJECT="/c=SE/ST=Stockholm/L=Kista/O=Veroveli/OU=Org/CN=${DOMAIN}"
DAYS=365

## -nodes - no password for key.pem
## -subj  - key for localdomain
openssl req -x509 -newkey rsa:2048 -keyout key.pem -out cert.pem -days $DAYS -subj $SUBJECT -nodes
