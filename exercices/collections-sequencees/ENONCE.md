# Collections séquencées

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Collections séquencées ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`WaitingLine` est une file d'attente de tickets uniques, conservés dans leur ordre d'arrivée. Trois méthodes restent à écrire : `pushToFront`, `serveNext` et `newestFirst`.

## Lancer le test

```
javac -d out src/training/sequenced/*.java test/training/sequenced/*.java
```

```
java -cp out training.sequenced.WaitingLineTest
```

Aucune option de lancement. Le test affiche une ligne par vérification et nomme ce qui reste à faire.
