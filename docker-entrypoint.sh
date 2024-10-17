#!/bin/sh
set -e


exec java $@ -jar /app.jar -Dfile.encoding=UTF-8
