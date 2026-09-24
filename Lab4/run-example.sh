#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
. "$PROJECT_DIR/scripts/java-env.sh"
cd "$PROJECT_DIR"

if [ "$#" -eq 0 ]; then
    set -- all
fi

./mvnw -q -DskipTests package
java -jar target/lab4-static-analysis-1.0.0.jar "$@"
