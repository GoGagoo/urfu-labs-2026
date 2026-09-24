#!/usr/bin/env sh

# Подключает JDK, даже если установленный Homebrew OpenJDK не добавлен в PATH.
if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/java" ]; then
    PATH="$JAVA_HOME/bin:$PATH"
    export PATH
elif [ -x /usr/libexec/java_home ] && JAVA_HOME=$(/usr/libexec/java_home 2>/dev/null); then
    export JAVA_HOME
    PATH="$JAVA_HOME/bin:$PATH"
    export PATH
elif [ -x /opt/homebrew/opt/openjdk@21/bin/java ]; then
    JAVA_HOME=/opt/homebrew/opt/openjdk@21
    export JAVA_HOME
    PATH="$JAVA_HOME/bin:$PATH"
    export PATH
elif [ -x /usr/local/opt/openjdk@21/bin/java ]; then
    JAVA_HOME=/usr/local/opt/openjdk@21
    export JAVA_HOME
    PATH="$JAVA_HOME/bin:$PATH"
    export PATH
elif command -v java >/dev/null 2>&1 && java -version >/dev/null 2>&1; then
    :
else
    echo "JDK не найден. Установите JDK 17+ (например: brew install openjdk@21)." >&2
    exit 1
fi
