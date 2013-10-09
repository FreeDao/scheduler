#!/bin/sh
cd ..
mvn clean install -Pprd -Dmaven.test.skip=true
cp /home/zhy/ruoogle/workspace/scheduler/target/scheduler.war /home/zhy/jboss-as-7.1.1.Final/standalone/deployments/

