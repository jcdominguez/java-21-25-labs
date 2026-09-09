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

# Exercices par sujet du chapitre « Langage et collections »

compile_starter "$REPO_DIR/exercices/pattern-matching-switch" \
  "training.shapes.ShapeDescriberTest" \
  "Décrire un triangle"
compile_starter "$REPO_DIR/exercices/record-patterns" \
  "training.geometry.SegmentDescriberTest" \
  "Reconnaître un segment réduit à un point"
compile_starter "$REPO_DIR/exercices/collections-sequencees" \
  "training.sequenced.WaitingLineTest" \
  "Placer un ticket prioritaire en tête de file"
compile_starter "$REPO_DIR/exercices/constructeurs-flexibles" \
  "training.sensors.CapteurNommeTest" \
  "La base lit le nom déjà affecté"

compile_and_run "$REPO_DIR/solutions/pattern-matching-switch" "training.shapes.ShapeDescriberTest"
compile_and_run "$REPO_DIR/solutions/record-patterns" "training.geometry.SegmentDescriberTest"
compile_and_run "$REPO_DIR/solutions/collections-sequencees" "training.sequenced.WaitingLineTest"
compile_and_run "$REPO_DIR/solutions/constructeurs-flexibles" "training.sensors.CapteurNommeTest"

# Exercices par sujet des chapitres « Pipelines de données » et « Concurrence »

compile_starter "$REPO_DIR/exercices/stream-gatherers" \
  "training.releves.SerieDeRelevesTest" \
  "Ne garder que le premier relevé de chaque capteur"
compile_starter "$REPO_DIR/exercices/threads-virtuels" \
  "training.taches.LotDeTachesTest" \
  "Exécuter les 1000 tâches de 10 ms en moins d'une seconde"
compile_starter "$REPO_DIR/exercices/scoped-values" \
  "training.contexte.TraitementTraceTest" \
  "Lire l'identifiant de la requête dans chaque tâche"

compile_and_run "$REPO_DIR/solutions/stream-gatherers" "training.releves.SerieDeRelevesTest"
compile_and_run "$REPO_DIR/solutions/threads-virtuels" "training.taches.LotDeTachesTest"
compile_and_run "$REPO_DIR/solutions/scoped-values" "training.contexte.TraitementTraceTest"

# Trois exercices dont le juge n'est pas un test

starter_ne_compile_pas() {
  local module_dir="$1"
  local output_dir="$BUILD_DIR/nocompile-$(basename "$module_dir")"
  mkdir -p "$output_dir"

  if find "$module_dir/src" "$module_dir/test" -name '*.java' -print0 \
      | xargs -0 javac --release 25 -d "$output_dir" 2>/dev/null; then
    echo "Le code de départ $(basename "$module_dir") compile alors qu'il ne devrait pas." >&2
    return 1
  fi
  echo "Code de départ $(basename "$module_dir") non compilable, comme attendu."
}

starter_ne_compile_pas "$REPO_DIR/exercices/imports-de-modules"
compile_and_run "$REPO_DIR/solutions/imports-de-modules" "training.modules.InventaireTest"

if java "$REPO_DIR/exercices/fichiers-source-compacts/Bienvenue.java" >/dev/null 2>&1; then
  echo "Le fichier de départ fichiers-source-compacts s'exécute alors qu'il est vide de code." >&2
  exit 1
fi
echo "Code de départ fichiers-source-compacts non exécutable, comme attendu."
java "$REPO_DIR/solutions/fichiers-source-compacts/Bienvenue.java"

JAVADOC_MD="$BUILD_DIR/javadoc-markdown"
javadoc -quiet -d "$JAVADOC_MD" \
  "$REPO_DIR/solutions/javadoc-markdown/src/training/docs/Temperature.java" >/dev/null 2>&1
grep -q "<li>Celsius</li>" "$JAVADOC_MD/training/docs/Temperature.html"
echo "Javadoc Markdown de la solution rendue en HTML, comme attendu."

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
