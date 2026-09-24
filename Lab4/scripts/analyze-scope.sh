#!/usr/bin/env sh
set -eu

if [ "$#" -ne 2 ]; then
    echo "Использование: analyze-scope.sh <filter.xml> <report-name>" >&2
    exit 2
fi

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
. "$PROJECT_DIR/scripts/java-env.sh"

FILTER_FILE="$1"
REPORT_NAME="$2"

cd "$PROJECT_DIR"
./mvnw -q clean package spotbugs:spotbugs \
    -Dspotbugs.filter="$FILTER_FILE"

mkdir -p reports
cp target/spotbugsXml.xml "reports/$REPORT_NAME.xml"
if [ -f target/spotbugs.html ]; then
    cp target/spotbugs.html "reports/$REPORT_NAME.html"
elif [ -f target/site/spotbugs.html ]; then
    cp target/site/spotbugs.html "reports/$REPORT_NAME.html"
else
    echo "SpotBugs не создал HTML-отчёт." >&2
    exit 1
fi

echo "Отчёт создан: reports/$REPORT_NAME.html"
