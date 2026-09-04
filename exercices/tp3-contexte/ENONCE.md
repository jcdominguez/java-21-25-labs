# TP 3 - Propager un contexte avec des threads virtuels

## Objectif

Propager un contexte immuable vers plusieurs traitements concurrents sans conserver d'état mutable partagé entre les requêtes.

## Travail demandé

1. Remplacer le `ThreadLocal` par un `ScopedValue<RequestContext>`.
2. Lier explicitement le contexte autour de chaque tâche soumise à l'exécuteur.
3. Conserver l'ordre des résultats.
4. Annuler les tâches restantes lorsqu'une tâche échoue.
5. Restaurer le statut d'interruption lorsqu'une attente est interrompue.
6. Vérifier que le contexte n'est plus lié après le traitement.

## Validation

Compiler puis exécuter `ContextualOrderServiceTest` avec les assertions activées. Le code de départ retourne un contexte manquant, car un `ThreadLocal` ordinaire n'est pas propagé vers les threads virtuels de l'exécuteur.

