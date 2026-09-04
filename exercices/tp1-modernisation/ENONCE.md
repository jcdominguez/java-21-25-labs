# TP 1 - Moderniser un service Java 21

## Objectif

Moderniser le code sans modifier son comportement observable.

## Travail demandé

1. Remplacer la chaîne de tests de type de `CommandSummary` par un `switch` exhaustif utilisant des record patterns.
2. Remplacer les accès par index de `OrderQueue` par l'API des collections séquencées.
3. Remplacer le pool fixe de `OrderBatchService` par un exécuteur adapté aux opérations bloquantes.
4. Expliquer pourquoi ce choix ne doit pas être appliqué automatiquement à un calcul dominé par le CPU.

## Validation

Compiler puis exécuter `OrderModernizationTest` avec les assertions activées. Le test final échoue tant que les traitements n'utilisent pas uniquement des threads virtuels.

