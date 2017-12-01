#!/bin/sh -e

#remove folders
rm -r /etc/nginx/conf.d

#copy new files
cp -r /nginx_entrypoint/conf.d/ /etc/nginx/conf.d
cp -r /nginx_entrypoint/www/ /var/www

#entrypoint override
nginx -g 'daemon off;'