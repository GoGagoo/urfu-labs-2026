#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

echo "=== Задание 3: анализ библиотеки Colt ==="
"$PROJECT_DIR/run-colt-analysis.sh"
