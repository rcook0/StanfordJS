@echo off
REM Decompile StanfordJS.jar using CFR
REM Make sure cfr-0.152.jar and StanfordJS.jar are in the same folder.

set OUT_DIR=StanfordJS_src
if not exist %OUT_DIR% mkdir %OUT_DIR%

echo Running CFR decompiler...
java -jar cfr-0.152.jar StanfordJS.jar --outputdir %OUT_DIR%

echo Decompilation complete. Sources are in %OUT_DIR%\
pause
