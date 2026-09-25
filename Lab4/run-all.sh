#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)

"$PROJECT_DIR/run-task1.sh"
"$PROJECT_DIR/run-task2.sh"
"$PROJECT_DIR/run-task3.sh"

echo "=== Все три задания успешно выполнены ==="
