@echo off
cd ../backend/movify
mvn clean test -Dtest=MovieFactoryTest > ../../automatisierung/output/FactoryTest.txt
