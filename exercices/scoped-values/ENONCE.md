# Scoped Values - propager un contexte sans le passer en paramètre

## Le domaine, en une phrase

`TraitementTrace` reçoit un contexte de requête, réduit à un identifiant, et lance une tâche par étape. Chaque étape doit préfixer sa trace par l'identifiant de la requête, sans le recevoir en paramètre.

## Travail demandé

Remplacer le `ThreadLocal` par un `ScopedValue<ContexteRequete>`, et lier explicitement le contexte autour de chaque tâche soumise à l'exécuteur.

## Lancer le test

Compiler :

```
javac -d out src/training/contexte/*.java test/training/contexte/*.java
```

Puis lancer, sans aucune option :

```
java -cp out training.contexte.TraitementTraceTest
```

Au premier lancement, la première vérification échoue : chaque tâche rend `absent:...`. La seconde passe déjà, parce que le code de départ retire le `ThreadLocal` dans un bloc `finally` ; elle est là pour garantir que la nouvelle version ne laisse pas fuir davantage.

## Pourquoi le code de départ échoue

Une valeur posée dans un `ThreadLocal` appartient au thread qui l'a posée, ici le thread appelant. Les tâches s'exécutent sur d'autres threads, qui ne la voient pas.

**Ce n'est pas propre aux threads virtuels.** Un pool fixe de threads de plateforme produit exactement le même échec : le contexte est absent parce que le thread est différent, pas parce qu'il est virtuel. Un `InheritableThreadLocal` ne sauve pas le cas non plus, puisque les threads d'un exécuteur ne sont pas créés au moment de la soumission.

`ScopedValue` ne se propage pas davantage tout seul vers les tâches d'un exécuteur : la liaison est explicite, tâche par tâche. C'est justement ce que l'exercice fait écrire.

## Question de débriefing

Qu'est-ce qu'un `ScopedValue` interdit, qu'un `ThreadLocal` autorisait ?
