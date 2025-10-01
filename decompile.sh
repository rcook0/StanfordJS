#!/bin/bash
# Decompile StanfordJS.jar using CFR and zip the sources
# Place this script in the same folder as cfr-0.152.jar and StanfordJS.jar

set -e

OUT_DIR="StanfordJS_src"
ZIP_FILE="StanfordJS_src.zip"

# clean old output
rm -rf "$OUT_DIR" "$ZIP_FILE"
mkdir -p "$OUT_DIR"

echo "Running CFR decompiler..."
java -jar cfr-0.152.jar StanfordJS.jar --outputdir "$OUT_DIR"

echo "Zipping sources..."
zip -r "$ZIP_FILE" "$OUT_DIR" > /dev/null

echo "Done! Decompiled sources in $OUT_DIR/ and also in $ZIP_FILE"
