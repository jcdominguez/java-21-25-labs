# Pattern matching pour switch

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Un sujet de bout en bout : pattern matching ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`Shape` est une hiérarchie scellée de quatre formes. `ShapeDescriber.describe` les traduit en texte, par une chaîne de `if instanceof` qui en oublie une, sans le signaler.

## Lancer le test

```
javac -d out src/training/shapes/*.java test/training/shapes/*.java
```

```
java -cp out training.shapes.ShapeDescriberTest
```

Aucune option de lancement. Le test affiche une ligne par vérification et nomme ce qui reste à faire.
