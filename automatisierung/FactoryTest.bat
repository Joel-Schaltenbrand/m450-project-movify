@echo off
cd ../backend/movify
mvn clean test -Dtest=MovieFactoryTest -Dsurefire.useFile=false -DredirectTestOutputToFile=true -DtrimStackTrace=false > ../../automatisierung/output/FactoryTest.txt
