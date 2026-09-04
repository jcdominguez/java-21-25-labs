# Ateliers Java 21 à 25

Ce dépôt accompagne la formation « Nouveautés Java 21 à 25 ». Le fil rouge est un petit service de traitement de commandes, volontairement indépendant d'un framework.

## Prérequis

- JDK 25 disponible dans le terminal ;
- un éditeur ou un IDE capable d'ouvrir des sources Java ;
- aucun accès réseau requis pendant les TP.

Vérifier l'environnement :

```bash
java -version
```

Puis :

```bash
javac -version
```

## Organisation

```text
exercices/   énoncés et code de départ
data/        inventaire synthétique du TP d'audit
solutions/   résultats de référence, à publier après les corrections
demos/       exemples courts utilisés par le formateur
scripts/     compilation et vérification sans dépendance externe
```

## Progression

1. `tp1-modernisation` : patterns, collections séquencées et threads virtuels ;
2. `tp2-gatherers` : opération intermédiaire personnalisée et fenêtres fixes ;
3. `tp3-contexte` : propagation explicite d'un contexte avec `ScopedValue` ;
4. `tp4-audit` : recommandation argumentée de migration.

## Validation formateur

Depuis la racine du dépôt :

```bash
./scripts/verify-all.sh
```

Le script compile les solutions et les démonstrations avec `--release 25`, puis exécute les tests autonomes avec les assertions activées.

## Remise à zéro

Les exercices et les solutions sont dans des dossiers distincts. Pour recommencer un TP, rouvrir le dossier correspondant sous `exercices/` ; aucun fichier généré n'est écrit dans ces dossiers.

