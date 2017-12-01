#!/bin/sh -e

#install temporal tables
#apk --update add --virtual build-dependencies gcc musl-dev py-pip make
#pip install --trusted-host pypi.python.org pgxnclient
#TODO dando erro de certificaod inválido
#pgxnclient install --mirror pypi.python.org temporal_tables=1.2.0
#apk del build-dependencies

#entrypoint override
/usr/local/bin/docker-entrypoint.sh postgres