# Ateliers Java 21 à 25

Ce dépôt accompagne la formation « Nouveautés Java 21 à 25 ». La formation compile des concepts indépendants : **chaque sujet a son dossier, son code de départ, son test et sa solution**, et se comprend sans les autres.

Le [Guide stagiaire](GUIDE.md) présente les notions dans l'ordre de la formation, donne les consignes des exercices et regroupe les corrigés en fin de document.

## Un sujet, un exercice

Un dossier d'exercice contient un domaine réduit à une phrase, une classe à compléter et un test. Aucun personnage à mémoriser, aucune classe d'un autre exercice à connaître.

Rien ne tourne en dehors des tests : pas de framework, pas de base de données, pas de réseau, aucune dépendance à télécharger. Un test se lance sans option et affiche une ligne `OK` ou `ÉCHEC` par vérification, puis `<sujet> validé` quand tout passe. Il est à la fois l'énoncé et le correcteur.

Quelques sujets se montrent au lieu de se pratiquer : ils vivent dans `demos/`.

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
data/        inventaire synthétique de l'atelier de décision
solutions/   résultats de référence, à ne pas ouvrir avant la correction
demos/       exemples courts utilisés par le formateur
scripts/     compilation et vérification sans dépendance externe
```

Les fichiers compilés vont dans `out/`, ignoré par Git. Rien de généré n'est versionné.

## Un dossier par sujet, pas de branche

La branche `main` porte tout le dépôt, et c'est la seule. Chaque sujet a **son dossier** dans `exercices/`, avec son code de départ, et son corrigé dans `solutions/` sous le même nom.

Il n'y a donc aucune branche d'étape à connaître : pour repartir de l'état de référence, `git restore .` suffit.

## Les exercices, dans l'ordre du déroulé

| Dossier | Sujet | Ce qui juge |
|---|---|---|
| `pattern-matching-switch` | Pattern matching pour `switch` | Test de comportement |
| `record-patterns` | Record patterns | Test de comportement |
| `collections-sequencees` | Collections séquencées | Test de comportement |
| `javadoc-markdown` | Commentaires Markdown en Javadoc | Rendu HTML de `javadoc` |
| `fichiers-source-compacts` | Fichier source compact | L'exécution elle-même |
| `imports-de-modules` | Import de module | Le compilateur |
| `constructeurs-flexibles` | Corps de constructeur flexible | Test de comportement |
| `stream-gatherers` | Stream Gatherers | Test de comportement |
| `threads-virtuels` | Threads virtuels | La durée mesurée sur 1000 tâches |
| `scoped-values` | Scoped Values | Test de comportement |
| `audit-migration` | Décision de migration | Restitution écrite, pas de test |

`audit-migration` est le seul à ne pas porter de code : c'est un atelier de décision, dont le livrable est un document.

## Validation formateur

Depuis la racine du dépôt :

```bash
./scripts/verify-all.sh
```

Le script compile les solutions et les démonstrations avec `--release 25`, puis exécute chaque exercice dans ses deux états : le code de départ, dont l'échec attendu est vérifié, et la solution, qui doit passer au vert.

## Garder ou jeter son travail

Avant de changer d'étape, mettre son travail de côté :

```bash
git stash
```

Le récupérer plus tard :

```bash
git stash pop
```

Pour repartir de l'état de référence et **jeter** ce qui n'a pas été commité, sans confirmation :

```bash
git restore .
```
