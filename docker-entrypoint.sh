#!/bin/sh
set -e


exec java $@ -jar -noverify /app.jar -Dfile.encoding=UTF-8 -XX:MaxRAMPercentage=80.0 --spring.config.location=classpath:/application.yaml
