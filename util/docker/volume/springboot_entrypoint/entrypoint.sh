#!/usr/bin/env bash

#Instalacao de fonts
REPO="deb http://http.us.debian.org/debian stable main contrib non-free"
REPO_FILE="/etc/apt/sources.list"
if ! grep -q "${REPO}" ${REPO_FILE}; then
	echo "${REPO}" | tee -a ${REPO_FILE} > /dev/null
	apt-get update
	apt-get install -y ttf-mscorefonts-installer
fi

#java awt fix
COMMENT_ME="assistive_technologies"
JDK_ASSISTIVE_FILES="/etc/java-8-openjdk/accessibility.properties"
if ! grep -q "\#${COMMENT_ME}" ${JDK_ASSISTIVE_FILES}; then
	sed -i "s/^${COMMENT_ME}/#${COMMENT_ME}/" ${JDK_ASSISTIVE_FILES}
fi

#healthcheck - postgres
i=0;
max_retrys=100
sleep_time=5
while ! [ "$(</dev/tcp/postgres/5432 && echo $?)" = '0' ];
do
	if [ $i == $max_retrys ]; then
		exit
	fi
	i=$((i+1))
	echo "Esperando 'postgres' ficar disponivel... (Tentativa: ${i}/${max_retrys}; Nova tentativa em ${sleep_time}s)"
	sleep ${sleep_time}
done

#entrypoint
java ${JAVA_OPTS} \
	-Djava.security.egd=file:/dev/./urandom \
	-Dserver.port=${SERVER_PORT} \
	-Djava.awt.headless=true \
	-XX:MaxRAM=$(cat /sys/fs/cgroup/memory/memory.limit_in_bytes) \
	-jar /springboot_entrypoint/jar/${JAR_FILE}