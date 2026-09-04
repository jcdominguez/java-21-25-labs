# Contexte et objectif

La migration vise d'abord la maintenabilité et la maîtrise des traitements bloquants. Les gains de performance ne sont retenus qu'après mesure sur une charge représentative.

# Décisions

| Composant | Décision | Fonctionnalité et statut | Impact attendu | Validation |
|---|---|---|---|---|
| `order-api` | Adopter | Threads virtuels, final depuis Java 21 ; absence de pinning sur `synchronized` depuis Java 24 | Simplifier la concurrence des appels bloquants | Test de charge avec débit, latence et consommation mémoire |
| `audit-worker` | Adopter | `ScopedValue`, final dans Java 25 | Retirer le contexte mutable partagé par `ThreadLocal` | Tests de propagation, d'échec et d'absence de fuite entre requêtes |
| `pricing-batch` | Conserver | Aucun bénéfice démontré des threads virtuels pour un calcul dominé par le CPU | Éviter une migration sans hypothèse mesurable | Profil CPU et comparaison avec le parallélisme existant |
| `legacy-export` | Étudier séparément | Foreign Function & Memory API, finale depuis Java 22 | Préparer la sortie de JNI | Prototype isolé, tests de compatibilité et mesures de débit |
| tous | Expérimenter hors production | Structured Concurrency, preview dans Java 25 | Évaluer la lisibilité de l'orchestration | Branche de laboratoire compilée avec les options preview, sans engagement d'API |

# Risques et dépendances

- vérifier la compatibilité du build, de l'IDE, de l'analyse statique et des agents d'observabilité avec le JDK 25 ;
- établir une mesure de référence avant toute optimisation JVM ;
- ne pas mélanger dans le même lot la montée de version et la réécriture de l'accès natif ;
- conserver les previews dans une expérimentation supprimable.

# Ordre de migration

1. Mettre à jour la chaîne de build et exécuter la suite de tests sur JDK 25 sans changement fonctionnel.
2. Migrer `order-api` vers un exécuteur à un thread virtuel par tâche et mesurer.
3. Remplacer le contexte de `audit-worker` par `ScopedValue` et tester les chemins d'erreur.
4. Prototyper FFM pour `legacy-export` dans un lot distinct.
5. Évaluer les réglages JVM uniquement à partir de mesures reproductibles.

# Retour arrière

Conserver un artefact compatible avec le comportement Java 21 et déployer chaque composant indépendamment. Une régression de compatibilité ou de mesure interrompt le lot concerné sans empêcher le retour à l'artefact précédent.

