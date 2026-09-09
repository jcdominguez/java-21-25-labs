# Constructeurs flexibles

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Corps de constructeurs flexibles ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`CapteurNomme` appelle `super()` en première instruction. La base lit donc `nom` avant qu'il soit affecté, et un nom invalide n'est refusé qu'une fois la base construite.

## Lancer le test

```
javac -d out src/training/sensors/*.java test/training/sensors/*.java
```

```
java -cp out training.sensors.CapteurNommeTest
```

Aucune option de lancement. Le test affiche une ligne par vérification et nomme ce qui reste à faire.
