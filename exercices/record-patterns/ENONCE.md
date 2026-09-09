# Record patterns

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Record patterns ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`Segment` contient deux `Point`. `SegmentDescriber.describe` qualifie un segment à partir de ses coordonnées, en redescendant aujourd'hui accesseur par accesseur.

## Lancer le test

```
javac -d out src/training/geometry/*.java test/training/geometry/*.java
```

```
java -cp out training.geometry.SegmentDescriberTest
```

Aucune option de lancement. Le test affiche une ligne par vérification et nomme ce qui reste à faire.
