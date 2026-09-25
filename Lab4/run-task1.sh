#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

echo "=== Задание 1: восемь учебных примеров ==="
"$PROJECT_DIR/run-example.sh" all

echo "=== Статический анализ примеров задания 1 ==="
"$PROJECT_DIR/scripts/analyze-scope.sh" \
    "$PROJECT_DIR/config/task1-include.xml" task1-examples
