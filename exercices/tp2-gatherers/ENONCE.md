# TP 2 - Transformer un pipeline avec un Gatherer

## Objectif

Créer une opération intermédiaire réutilisable qui conserve le premier événement rencontré pour chaque commande.

## Travail demandé

1. Compléter `DistinctByKeyGatherer.distinctBy` avec un état privé contenant les clés déjà rencontrées.
2. N'émettre un élément que lorsque sa clé apparaît pour la première fois.
3. Préserver l'ordre du flux.
4. Vérifier le comportement du gatherer intégré `windowFixed` sur une dernière fenêtre incomplète.
5. Expliquer pourquoi le gatherer personnalisé est séquentiel.

## Validation

Compiler puis exécuter `EventAnalyticsTest` avec les assertions activées. Le code de départ compile, mais le premier test échoue tant que le gatherer laisse passer les doublons.

