#!/usr/bin/env bash
set -euo pipefail

REPO_DIR="$(cd "$(dirname "$0")/.." && pwd)"
BUILD_DIR="$(mktemp -d "${TMPDIR:-/tmp}/java-21-25-labs.XXXXXX")"
trap 'rm -rf "$BUILD_DIR"' EXIT

compile_and_run() {
  local module_dir="$1"
  local main_class="$2"
  local output_dir="$BUILD_DIR/$(basename "$module_dir")"

  mkdir -p "$output_dir"
  find "$module_dir/src" "$module_dir/test" -name '*.java' -print0 \
    | xargs -0 javac --release 25 -d "$output_dir"
  java -cp "$output_dir" "$main_class"
}

compile_starter() {
  local module_dir="$1"
  local main_class="$2"
  local expected_message="$3"
  local output_dir="$BUILD_DIR/starter-$(basename "$module_dir")"
  local test_output

  mkdir -p "$output_dir"
  find "$module_dir/src" "$module_dir/test" -name '*.java' -print0 \
    | xargs -0 javac --release 25 -d "$output_dir"

  if test_output="$(java -cp "$output_dir" "$main_class" 2>&1)"; then
    echo "Le code de départ $(basename "$module_dir") ne contient plus l'échec pédagogique attendu." >&2
    return 1
  fi

  if [[ "$test_output" != *"$expected_message"* ]]; then
    echo "Échec inattendu dans $(basename "$module_dir"):" >&2
    echo "$test_output" >&2
    return 1
  fi

  echo "Code de départ $(basename "$module_dir") compilable, échec pédagogique confirmé."
}

compile_starter "$REPO_DIR/exercices/tp1-modernisation" \
  "training.orders.OrderModernizationTest" \
  "Remplacer le pool fixe"
compile_starter "$REPO_DIR/exercices/tp2-gatherers" \
  "training.orders.EventAnalyticsTest" \
  "Le gatherer doit filtrer"
compile_starter "$REPO_DIR/exercices/tp3-contexte" \
  "training.orders.ContextualOrderServiceTest" \
  "Le contexte doit être lié"

compile_and_run "$REPO_DIR/solutions/tp1-modernisation" "training.orders.OrderModernizationTest"
compile_and_run "$REPO_DIR/solutions/tp2-gatherers" "training.orders.EventAnalyticsTest"
compile_and_run "$REPO_DIR/solutions/tp3-contexte" "training.orders.ContextualOrderServiceTest"

DEMO_OUTPUT="$BUILD_DIR/demos"
mkdir -p "$DEMO_OUTPUT"
find "$REPO_DIR/demos" -name '*.java' -print0 \
  | xargs -0 javac --release 25 -d "$DEMO_OUTPUT"

java -cp "$DEMO_OUTPUT" CompactHello
java -cp "$DEMO_OUTPUT" UnnamedVariablesDemo
java -cp "$DEMO_OUTPUT" GatherersDemo
java -cp "$DEMO_OUTPUT" ModuleImportDemo
java -cp "$DEMO_OUTPUT" FlexibleConstructorDemo
java -cp "$DEMO_OUTPUT" KemDemo
java -cp "$DEMO_OUTPUT" KdfDemo
java --enable-native-access=ALL-UNNAMED -cp "$DEMO_OUTPUT" FfmStrlenDemo
java -cp "$DEMO_OUTPUT" ClassFileDemo

JAVADOC_OUTPUT="$BUILD_DIR/javadoc"
javadoc -quiet -d "$JAVADOC_OUTPUT" "$REPO_DIR/demos/MarkdownDocsDemo.java"
test -f "$JAVADOC_OUTPUT/MarkdownDocsDemo.html"

echo "Toutes les solutions et démonstrations Java ont été validées."
