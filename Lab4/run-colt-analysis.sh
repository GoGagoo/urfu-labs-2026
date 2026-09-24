#!/usr/bin/env sh
set -eu

PROJECT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
. "$PROJECT_DIR/scripts/java-env.sh"

SPOTBUGS_VERSION=4.10.4
TOOLS_DIR="$PROJECT_DIR/.tools"
SPOTBUGS_HOME="$TOOLS_DIR/spotbugs-$SPOTBUGS_VERSION"
REPORT_DIR="$PROJECT_DIR/reports"
COLT_JAR="$PROJECT_DIR/lib/colt.jar"
COLT_ANALYSIS_JAR="$TOOLS_DIR/colt.jar"
CONCURRENT_JAR="$PROJECT_DIR/lib/concurrent.jar"
CONCURRENT_ANALYSIS_JAR="$TOOLS_DIR/concurrent.jar"

if [ ! -f "$COLT_JAR" ]; then
    echo "Не найден исходный colt.jar: $COLT_JAR" >&2
    exit 1
fi
if [ ! -f "$CONCURRENT_JAR" ]; then
    echo "Не найден вспомогательный concurrent.jar: $CONCURRENT_JAR" >&2
    exit 1
fi

mkdir -p "$TOOLS_DIR" "$REPORT_DIR"
if [ ! -x "$SPOTBUGS_HOME/bin/spotbugs" ]; then
    ARCHIVE="$TOOLS_DIR/spotbugs-$SPOTBUGS_VERSION.tgz"
    echo "Первый запуск: загрузка SpotBugs $SPOTBUGS_VERSION..."
    curl -fL "https://github.com/spotbugs/spotbugs/releases/download/$SPOTBUGS_VERSION/spotbugs-$SPOTBUGS_VERSION.tgz" \
        -o "$ARCHIVE"
    tar -xzf "$ARCHIVE" -C "$TOOLS_DIR"
fi

# CLI SpotBugs не всегда корректно открывает JAR по пути с кириллицей на macOS.
# Анализируем тот же файл через короткую символическую ссылку с ASCII-именем.
ln -sfn "$COLT_JAR" "$COLT_ANALYSIS_JAR"
ln -sfn "$CONCURRENT_JAR" "$CONCURRENT_ANALYSIS_JAR"

"$SPOTBUGS_HOME/bin/spotbugs" -textui -effort:max -low \
    -auxclasspath "$CONCURRENT_ANALYSIS_JAR" \
    -xml:withMessages -output "$REPORT_DIR/colt-all-warnings.xml" "$COLT_ANALYSIS_JAR"

# Для показа оставляем десять групп проблем, которые описаны в готовом отчёте.
"$SPOTBUGS_HOME/bin/spotbugs" -textui -effort:max -low \
    -auxclasspath "$CONCURRENT_ANALYSIS_JAR" \
    -include "$PROJECT_DIR/config/colt-critical.xml" \
    -xml:withMessages -output "$REPORT_DIR/colt-spotbugs.xml" "$COLT_ANALYSIS_JAR"
"$SPOTBUGS_HOME/bin/spotbugs" -textui -effort:max -low \
    -auxclasspath "$CONCURRENT_ANALYSIS_JAR" \
    -include "$PROJECT_DIR/config/colt-critical.xml" \
    -html:fancy-hist.xsl -output "$REPORT_DIR/colt-spotbugs.html" "$COLT_ANALYSIS_JAR"

echo "Отчёты Colt созданы: критичные — colt-spotbugs.html, полный — colt-all-warnings.xml"
