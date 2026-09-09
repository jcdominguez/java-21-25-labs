# Ateliers Java 21 à 25

Ce dépôt accompagne la formation « Nouveautés Java 21 à 25 ». Il contient les exercices, leurs solutions et les démonstrations exécutables avec un JDK 25, sans framework ni dépendance externe.

## Commencer ici

Le [Guide stagiaire](GUIDE.md) constitue le parcours principal. Il présente les notions dans l'ordre de la formation, donne les consignes détaillées et regroupe les corrigés à la fin.

Cloner le dépôt :

```bash
git clone https://github.com/jcdominguez/java-21-25-labs.git
```

Entrer dans le dossier :

```bash
cd java-21-25-labs
```

Ouvrir ensuite [le Guide](GUIDE.md) et commencer par sa section « Avant de commencer ».

## Prérequis

- JDK 25 disponible dans le terminal ;
- Git ;
- un éditeur ou un IDE capable d'ouvrir des sources Java.

Vérifier le runtime :

```bash
java -version
```

Vérifier le compilateur :

```bash
javac -version
```

Les deux commandes doivent annoncer une version 25.

## Organisation du dépôt

```text
GUIDE.md    parcours autonome, énoncés et corrigés
exercices/  code de départ, organisé par sujet
solutions/  résultats de référence
demos/      exemples courts exécutables
data/       inventaire de l'atelier de décision
scripts/    validation complète du dépôt
```

Chaque exercice est indépendant. Il n'existe ni application globale, ni base de données, ni service réseau à démarrer. Ouvrir le dossier du sujet évite que l'IDE mélange le code de départ et la solution.

## Exercices

| Dossier | Sujet | Vérification |
|---|---|---|
| `pattern-matching-switch` | Pattern matching pour `switch` | Test de comportement |
| `record-patterns` | Record patterns | Test de comportement et relecture |
| `collections-sequencees` | Collections séquencées | Test de comportement |
| `javadoc-markdown` | Commentaires Markdown en Javadoc | Page HTML produite |
| `fichiers-source-compacts` | Fichiers source compacts | Exécution du fichier |
| `imports-de-modules` | Imports de modules | Compilateur puis test |
| `constructeurs-flexibles` | Corps de constructeurs flexibles | Test de comportement |
| `stream-gatherers` | Stream Gatherers | Test de comportement |
| `threads-virtuels` | Threads virtuels | Résultat et durée mesurée |
| `scoped-values` | Scoped Values | Test de comportement |

Le Guide donne, pour chaque dossier, la commande exacte, le résultat initial attendu, l'algorithme en français et le corrigé.

## Atelier de décision complémentaire

Le dossier `exercices/audit-migration/` propose un audit sans code. À partir de `data/migration-inventory.csv`, il faut produire une recommandation de migration Java 21 vers Java 25 avec, pour chaque décision, un statut, un effet observable, une méthode de validation, un risque et un retour arrière.

Le livrable à compléter est `exercices/audit-migration/recommandation.md`. Une référence est disponible dans `solutions/audit-migration/`.

## Démonstrations

Le dossier `demos/` complète les exercices avec des exemples sur :

- les variables anonymes ;
- la Javadoc Markdown ;
- les fichiers source compacts ;
- les imports de modules ;
- les constructeurs flexibles ;
- les Stream Gatherers ;
- la Foreign Function & Memory API ;
- la Class-File API ;
- KEM et KDF.

Les commandes et les résultats attendus sont indiqués dans le Guide.

## Travailler sans perdre ses modifications

Mettre le travail en cours de côté :

```bash
git stash
```

Le récupérer :

```bash
git stash pop
```

Repartir de la version du dépôt :

```bash
git restore .
```

> **Attention**
> `git restore .` supprime les modifications non enregistrées sans demander confirmation. Utiliser `git stash` pour les conserver.

## Solutions

Les solutions sont disponibles dans `solutions/` sous le même nom que les exercices. Pendant la formation, ne les ouvrir qu'au moment indiqué par le formateur.

## Validation complète

Depuis la racine du dépôt :

```bash
./scripts/verify-all.sh
```

Le script vérifie les échecs pédagogiques attendus dans le code de départ, puis compile et exécute les solutions et les démonstrations.
