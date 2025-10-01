#!/bin/bash
# Decompile StanfordJS.jar using CFR
# Make sure cfr-0.152.jar and StanfordJS.jar are in the same folder.

set -e

OUT_DIR="StanfordJS_src"
mkdir -p "$OUT_DIR"

echo "Running CFR decompiler..."
java -jar cfr-0.152.jar StanfordJS.jar --outputdir "$OUT_DIR"

echo "Decompilation complete. Sources are in $OUT_DIR/"
