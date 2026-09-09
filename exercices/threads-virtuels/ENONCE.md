# Threads virtuels - attendre sans bloquer

## Le domaine, en une phrase

`LotDeTaches` reçoit une liste d'identifiants et exécute une tâche par identifiant. Chaque tâche attend 10 millisecondes, comme le ferait un appel réseau, puis rend son résultat.

## Travail demandé

Faire passer le lot de 1000 tâches sous la seconde, sans toucher ni à `executerUne`, ni au corps des tâches, ni au test.

Un seul geste suffit. Le code de départ soumet les tâches à un pool fixe de quatre threads de plateforme.

## Lancer le test

Compiler :

```
javac -d out src/training/taches/*.java test/training/taches/*.java
```

Puis lancer, sans aucune option :

```
java -cp out training.taches.LotDeTachesTest
```

Le test chronomètre l'appel et affiche la durée mesurée avant ses vérifications. Rien dans `LotDeTaches` ne dit quel type de thread a servi : **c'est la durée qui juge**, pas une inspection du code.

Au premier lancement, l'ordre des résultats est déjà correct. Cette vérification qui passe est là pour garantir que le changement d'exécuteur ne désordonne pas la sortie.

## Question de débriefing

La même bascule appliquée à 1000 calculs de 10 millisecondes de **CPU**, et non d'attente, ne gagnerait rien. Pourquoi ?
