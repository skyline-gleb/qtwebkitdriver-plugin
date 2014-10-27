#!/usr/bin/env bash
SELENIUM_SERVER_STANDALONE=$1

if [ -z "$SELENIUM_SERVER_STANDALONE" ]; then
    echo >&2 "Add path to selenium-server-standalone as argument"
    exit 1
fi

javac -cp .:$SELENIUM_SERVER_STANDALONE qtwebkitdriver/*.java
jar cf qtwebkitdriver-plugin.jar qtwebkitdriver/*.class META-INF/*
