# Stream Gatherers - construire une opération intermédiaire

## Le domaine, en une phrase

`SerieDeReleves` traite une liste de relevés, chacun portant le nom d'un capteur et une valeur. C'est tout ce qu'il y a à savoir pour commencer.

## Travail demandé

Compléter `premierPar(cle)` pour qu'elle n'émette un élément que la première fois que sa clé est rencontrée, sans changer l'ordre du flux.

Le gatherer de départ pousse tout ce qu'il reçoit. Il lui manque un état privé : l'ensemble des clés déjà vues.

## Lancer le test

Compiler :

```
javac -d out src/training/releves/*.java test/training/releves/*.java
```

Puis lancer, sans aucune option :

```
java -cp out training.releves.SerieDeRelevesTest
```

Au premier lancement, deux vérifications échouent et une passe. Celle qui passe déjà, le découpage en fenêtres fixes, utilise le gatherer `windowFixed` fourni par le JDK : elle est là pour garantir que votre modification ne casse pas le reste du pipeline.

## Question de débriefing

Pourquoi ce gatherer est-il construit avec `ofSequential`, et que faudrait-il fournir de plus pour qu'il supporte un flux parallèle ?
