@echo off
REM Decompile StanfordJS.jar using CFR and zip the sources
REM Place this script in the same folder as cfr-0.152.jar and StanfordJS.jar

set OUT_DIR=StanfordJS_src
set ZIP_FILE=StanfordJS_src.zip

REM clean old output
if exist %OUT_DIR% rmdir /s /q %OUT_DIR%
if exist %ZIP_FILE% del %ZIP_FILE%
mkdir %OUT_DIR%

echo Running CFR decompiler...
java -jar cfr-0.152.jar StanfordJS.jar --outputdir %OUT_DIR%

echo Zipping sources...
powershell -command "Compress-Archive -Path %OUT_DIR% -DestinationPath %ZIP_FILE%"

echo Done! Decompiled sources in %OUT_DIR%\ and also in %ZIP_FILE%
pause
