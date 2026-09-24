#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

echo "=== Задание 2: анализ проекта library ==="
"$PROJECT_DIR/scripts/analyze-scope.sh" \
    "$PROJECT_DIR/config/task2-include.xml" task2-library
