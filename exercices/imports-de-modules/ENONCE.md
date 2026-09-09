# Imports de modules

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Imports de modules ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`Inventaire` utilise `List`, `ArrayList`, `Map` et `HashMap`, mais ses imports ont été retirés. Les rétablir en **une seule déclaration**, sans nommer aucun type.

## Lancer le test

```
javac -d out src/training/modules/*.java test/training/modules/*.java
```

```
java -cp out training.modules.InventaireTest
```

En l'état, la compilation échoue sur `cannot find symbol: class List`. C'est le point de l'exercice.
