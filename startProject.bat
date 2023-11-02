@echo off
start cmd /c "cd backend\movify & mvn spring-boot:run"
start cmd /c "cd frontend\movify & npm install --force & ng serve"
exit
