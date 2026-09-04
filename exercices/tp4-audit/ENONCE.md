# TP 4 - Auditer une migration Java 21 vers Java 25

## Mission

Le service de commandes fonctionne actuellement sur Java 21. À partir de `../../data/migration-inventory.csv` et des résultats des TP précédents, produire une recommandation de migration exploitable par une équipe technique.

## Travail demandé

1. Identifier les composants qui peuvent bénéficier d'une évolution finale de Java 22 à 25.
2. Distinguer adoption immédiate, expérimentation isolée et absence de bénéfice démontré.
3. Associer chaque proposition à un impact observable et à une méthode de validation.
4. Décrire les risques de compatibilité, d'outillage et d'exploitation.
5. Proposer un ordre de migration et un retour arrière.

## Livrable

Compléter `recommandation.md`. La recommandation doit pouvoir être expliquée oralement en cinq minutes.

## Critères de réussite

- chaque décision cite le statut de la fonctionnalité ;
- chaque bénéfice annoncé possède une mesure ou un test associé ;
- les fonctionnalités expérimentales sont isolées des choix de production ;
- le plan contient un point d'arrêt et une stratégie de retour arrière.

