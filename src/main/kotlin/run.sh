#!/bin/bash

JAYTC_PATH="/Users/lisandrodimeo/Documents/Me/College/BehavioralTypes/java-typestate-checker/dist"

java -jar $JAYTC_PATH/checker/checker.jar -classpath $JAYTC_PATH/jatyc.jar -processor jatyc.JavaTypestateChecker *.java
