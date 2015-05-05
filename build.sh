#!/usr/bin/env bash
DRIVER_NAME=$1
SELENIUM_SERVER_STANDALONE=$2

if [ -z "$SELENIUM_SERVER_STANDALONE" ] || [ -z "$SELENIUM_SERVER_STANDALONE" ]; then
    echo >&2 "Add driver name as first argument and path to selenium-server-standalone as second argument"
    exit 1
fi

nameInLower=$(echo $DRIVER_NAME | tr '[:upper:]' '[:lower:]')
nameInUpper=$(echo $DRIVER_NAME | tr '[:lower:]' '[:upper:]')
packageName="${nameInLower}driver"

mkdir -p META-INF/services
mkdir -p $packageName
echo "${nameInLower}driver.${DRIVER_NAME}DriverProvider" > ./META-INF/services/org.openqa.selenium.remote.server.DriverProvider

for file in "Driver.java" "DriverProvider.java" "DriverService.java"
do
    sed -e "s/_name_/$DRIVER_NAME/g;s/_nameInLower_/$nameInLower/g;s/_nameInUpper_/$nameInUpper/g" < plugin_template/$file > $packageName/$DRIVER_NAME$file
done

javac -cp .:$SELENIUM_SERVER_STANDALONE $packageName/*.java
jar cf $packageName-plugin.jar $packageName/*.class META-INF/*
