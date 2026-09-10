> [!NOTE]
> **Guide stagiaire - Nouveautés Java 21 à 25**
> Guide à suivre pendant la séance ou à rejouer seul ensuite. Il présente les mécanismes dans l'ordre de la formation, porte les manipulations et regroupe les corrigés en fin de document.
>
> - [JDK 21, OpenJDK](https://openjdk.org/projects/jdk/21/)
> - [JDK 25, OpenJDK](https://openjdk.org/projects/jdk/25/)

> [!NOTE]
> **Comment ce Guide est organisé**
> La formation rassemble des sujets **indépendants**. Chaque exercice a son dossier, ses sources et son moyen de vérification. Les démonstrations et perspectives disposent de leur propre procédure ou question de décision.
>
> Les énoncés de référence vivent ici. Le dépôt en garde la copie technique nécessaire à l'exécution. Les corrigés sont regroupés à la fin pour permettre une première recherche autonome.

**Navigation** : [Préparer le poste](#avant-de-commencer) · [Langage et collections](#langage-collections-et-documentation) · [Streams et calcul vectoriel](#traitement-des-données--streams-et-calcul-vectoriel) · [JVM](#jvm--performances-et-observabilité) · [Concurrence](#concurrence) · [API spécialisées](#api-spécialisées) · [Quiz](#quiz) · [Corrigés](#corrigés).

Les repères **À observer** invitent à prédire un résultat ; **À essayer** indiquent une manipulation ; **Avant de continuer** permet de vérifier la compréhension. Les perspectives restent des lectures et des décisions argumentées, sauf manipulation explicitement proposée.

# Avant de commencer

## Ce dont vous avez besoin

Un JDK 25 installé, un éditeur capable d'ouvrir des sources Java, et Git.

Vérifiez votre version :

```
java -version
```

Puis :

```
javac -version
```

Les deux doivent afficher une version 25. Si `javac` répond autre chose que `java`, c'est que votre terminal utilise deux JDK différents : corrigez cela avant d'aller plus loin, sinon les erreurs de compilation des prochaines étapes ne seront pas celles décrites ici.

## Récupérer le code

Tout le code de la formation est dans un dépôt public :

```
git clone https://github.com/jcdominguez/java-21-25-labs.git
```

Puis placez-vous dedans :

```
cd java-21-25-labs
```

Une fois le clone fait, aucun accès réseau n'est nécessaire pendant les exercices.

Les commandes ci-dessous se lancent dans un terminal de type Bash ou zsh, ou Git Bash sous Windows. Le terminal fait foi si l'éditeur signale une erreur différente.

**Dossier courant** : chaque chemin `exercices/...`, `solutions/...` ou `demos/...` part de la racine `java-21-25-labs`. Avant de changer de sujet, revenez à cette racine. Depuis un dossier `exercices/<sujet>` ou `solutions/<sujet>` :

```
cd ../..
```

Pour les petits fichiers de démonstration à saisir dans le Guide, créez depuis la racine un dossier de travail distinct :

```
mkdir -p essais-guide
```

Les classes de ces fichiers sont complètes quand il est demandé de les exécuter ; les autres blocs sont signalés comme fragments ou exemples à lire.

## Les trois commandes Git dont vous aurez besoin

Cette formation porte sur Java, pas sur Git. Trois commandes suffisent, les voici toutes ensemble une fois pour toutes.

Récupérer le code, une seule fois :

```
git clone https://github.com/jcdominguez/java-21-25-labs.git
```

Depuis la racine du dépôt, mettre les modifications des fichiers suivis de côté, pour les retrouver ensuite avec `git stash pop` :

```
git stash
```

Cette commande ne sauvegarde pas les nouveaux fichiers non suivis, notamment ceux créés dans `essais-guide/`. Conservez-les séparément si nécessaire. Le stash porte sur le dépôt, même lancé depuis un sous-dossier.

Repartir de l'état enregistré dans l'index, en **jetant** les modifications non indexées des fichiers suivis sous le dossier courant (sans `git add` pendant ces exercices, c'est le code de départ) :

```
git restore .
```

> [!WARNING]
> **`git restore .` ne demande pas confirmation**
> Cette commande écrase vos fichiers avec la version enregistrée. Tout ce que vous avez modifié sans l'avoir mis de côté est perdu, sans corbeille et sans retour possible. Utilisez `git stash` si vous voulez garder votre travail.

Depuis la racine, `git restore .` touche tous les exercices ; depuis `exercices/<sujet>`, seulement ce sujet. Les nouveaux fichiers non suivis ne sont pas supprimés.

Vous n'aurez pas besoin de changer de branche : chaque sujet a **son propre dossier**, et son corrigé vous attend dans `solutions/`.

## Le rituel des exercices

Les exercices avec test contiennent `src/` et `test/`. On compile les deux ensemble, puis on lance la classe de test. `javadoc-markdown` contient seulement `src/` ; `fichiers-source-compacts` contient directement `Bienvenue.java`.

Depuis la racine du dépôt, placez-vous dans le dossier du sujet, par exemple :

```
cd exercices/pattern-matching-switch
```

Compilez :

```
javac -d out src/training/shapes/*.java test/training/shapes/*.java
```

Lancez, **sans aucune option** :

```
java -cp out training.shapes.ShapeDescriberTest
```

Le test affiche une ligne par vérification :

```
OK    Décrire un cercle
ÉCHEC Décrire un triangle
1 vérification(s) en échec
```

> [!TIP]
> **Le test est votre énoncé**
> Chaque ligne `ÉCHEC` nomme un comportement à obtenir. La consigne détaillée reste dans la séquence du Guide : certains choix de syntaxe se vérifient à la relecture. La dernière ligne annonce `exercice validé` ou `validé` selon le sujet. Relancez le test après chaque modification.

Deux sujets n'ont pas de test Java : `javadoc-markdown` se vérifie sur la documentation produite et sa source ; `fichiers-source-compacts` se vérifie par l'exécution et l'affichage. `imports-de-modules` possède bien un test : il faut d'abord réparer ses imports pour pouvoir le compiler.

**Avant de continuer** : après une compilation en erreur, ne lancez pas `java -cp out ...` ; le dossier `out` peut contenir d'anciennes classes. Corrigez, puis recompilez avec succès avant de relancer.

## Ouvrir le projet dans IntelliJ

Le dépôt n'a ni Maven ni Gradle. IntelliJ n'a donc aucun fichier de configuration à lire, et ne devine que partiellement.

**Ouvrez le dossier de l'exercice, pas la racine du dépôt.** Chaque sujet a sa copie du code dans `exercices/` et dans `solutions/` : en ouvrant la racine, IntelliJ voit des classes en double et ne sait plus laquelle exécuter.

Trois réglages à l'ouverture :

1. `Project Structure` (Cmd+;), onglet `Project` : `SDK` sur le JDK 25 et `Language level` sur 25. Sans cela, les record patterns sont soulignés en rouge alors que le code est correct.
2. Quand `src` existe, clic droit sur `src`, `Mark Directory as` → `Sources Root`.
3. Quand `test` existe, clic droit sur `test`, `Mark Directory as` → `Test Sources Root`.

> [!WARNING]
> **Aucune flèche verte sur les classes de `src`, et c'est voulu**
> Dans les exercices avec test, les classes métier de `src` n'ont pas de `main`. Le point d'entrée est la classe de test. Pour la Javadoc et le fichier source compact, utilisez leur commande dédiée ci-dessous.

Configuration d'exécution : `Application`, classe principale celle du test, aucune option VM.

### Dépannage

| Symptôme | Cause à vérifier | Action |
|---|---|---|
| `java` et `javac` annoncent des versions différentes | Outils issus de JDK différents | Sélectionner le même JDK 25 pour les deux, puis rouvrir le terminal |
| Le compilateur ne trouve pas `src/training/...` | Mauvais dossier courant | Revenir à la racine puis entrer dans le dossier indiqué par la séquence |
| L'IDE voit des classes en double | Racine du dépôt ouverte avec exercices et solutions | Ouvrir seulement le dossier du sujet |
| Imports de modules : `cannot find symbol` au départ | Imports volontairement retirés | Ajouter l'import demandé, recompiler, puis lancer le test |
| Le test semble ignorer une modification après une erreur de compilation | Anciennes classes encore présentes dans `out` | Obtenir une compilation réussie avant de relancer |

## Ce que vous saurez faire

À l'issue des deux jours, vous pourrez adopter les fonctionnalités finales pertinentes, moderniser la concurrence, les collections et les pipelines, isoler les previews et les incubateurs, et argumenter chaque choix avec une validation observable.

## Lire le statut avant la syntaxe

Les nouveautés du langage et des API peuvent traverser des phases d'évaluation. Leur statut indique le niveau de stabilité sur lequel vous pouvez compter ; toutes ne passent pas obligatoirement par les trois statuts ci-dessous.

| Statut | Ce que cela autorise |
|---|---|
| Final | Évaluer une adoption en production |
| Preview | Expérimenter, en acceptant que la syntaxe change encore |
| Incubateur | Tester une API qui n'est pas encore standardisée |

Le geste à prendre : devant une nouveauté, cherchez son statut avant sa syntaxe. Le numéro de version ne suffit pas à décider. Une fonctionnalité peut apparaître en Java 21, rester en preview jusqu'en Java 25, et ne jamais devenir finale.

## Le cas des String Templates

Le programme de la formation annonce les « modèles de chaînes », ou String Templates. Ils n'existent pas en Java 25, et ce Guide ne les enseignera pas. Voici pourquoi, parce que c'est votre première occasion d'appliquer la grille ci-dessus.

Ils devaient permettre d'interpoler des valeurs dans une chaîne sans concaténation ni `%s` :

```java
String name = "Harry";
String greeting = STR."Hello, \{name}!";
// Hello, Harry!
```

| Version | Statut |
|---|---|
| Java 21 | Preview (JEP 430) |
| Java 22 | Deuxième preview (JEP 459) |
| Java 23 à 25 | Retirée (JEP 465, withdrawn) |

La troisième preview proposée par la JEP 465 a été retirée ; les String Templates ne sont pas disponibles en Java 25. Le fragment ci-dessus est historique et ne doit pas être compilé avec ce JDK. Sources : [JEP 430](https://openjdk.org/jeps/430), [JEP 459](https://openjdk.org/jeps/459), [JEP 465, withdrawn](https://openjdk.org/jeps/465).

> [!NOTE]
> **Question : Pourquoi le programme l'annonce-t-il alors ?**
> L'intitulé annoncé ne correspond plus à une fonctionnalité disponible dans le JDK 25. Cela illustre le risque que la grille des statuts sert à éviter : une preview n'est pas une promesse. Elle peut disparaître sans atteindre une version finale.

## Interpoler aujourd'hui

Trois écritures suffisent. **À essayer** : depuis la racine du dépôt, lancez `jshell`, puis saisissez ces instructions dans cette console Java interactive (elles ne constituent pas seules un fichier Java complet) :

```
jshell
```

```java
String name = "Harry";

// Concaténation : lisible, une seule expression
String a = "Hello, " + name + "!";

// Formatage : gabarit réutilisable, valeurs séparées
String b = String.format("Hello, %s!", name);

// Construction incrémentale : assemblage en boucle
var sb = new StringBuilder("Hello, ");
sb.append(name).append("!");
String c = sb.toString();
```

Les variables `a`, `b` et `c` valent toutes `Hello, Harry!`. Quittez JShell avec `/exit` avant de reprendre les commandes du terminal.

La règle de choix : le `+` par défaut, `String.format` quand le même gabarit se réutilise à plusieurs endroits, `StringBuilder` quand vous assemblez la chaîne morceau par morceau dans une boucle.

> [!NOTE]
> **Question : Le `+` est-il lent ?**
> Une concaténation simple ne justifie pas à elle seule une réécriture pour la performance : choisissez d'abord une forme lisible, puis mesurez si ce code devient un point chaud. `StringBuilder` évite notamment de reconstruire un résultat intermédiaire à chaque tour d'un assemblage en boucle.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le statut d'une fonctionnalité se lit avant sa syntaxe : final, preview, incubateur.
> - Une preview peut être retirée ; les String Templates l'illustrent.
> - Pour interpoler en Java 25 : `+` par défaut, `String.format` pour un gabarit réutilisé, `StringBuilder` pour un assemblage en boucle.

# Langage, collections et documentation

**Point d'étape précédent** : répondre à la [question 1 du quiz](#quiz), sur les statuts.

Le premier exercice est détaillé pas à pas. Les suivants gardent le même rituel : comprendre le problème, prédire le résultat, modifier le minimum nécessaire, puis relancer le juge indiqué.

## Pattern matching pour `switch`

<a id="sequence-pattern-matching-switch"></a>

[Aller au corrigé](#corrige-pattern-matching-switch)

Ce sujet déroule le rituel en entier. Les exercices suivants se travaillent de la même façon ; leurs explications, leurs questions de blocage et leurs corrigés restent dans ce Guide pour permettre de les rejouer seul.

Depuis la racine du dépôt :

```
cd exercices/pattern-matching-switch
```

**Fichier à modifier** : `src/training/shapes/ShapeDescriber.java`.

### Étape 1, lire le test avant le code

Dans ce dossier, compilez :

```
javac -d out src/training/shapes/*.java test/training/shapes/*.java
```

Puis lancez :

```
java -cp out training.shapes.ShapeDescriberTest
```

Vous obtenez ceci :

```
OK    Décrire un cercle
OK    Décrire un carré
OK    Décrire un rectangle
ÉCHEC Décrire un triangle
ÉCHEC Signaler un carré de côté nul
2 vérification(s) en échec
```

Trois lignes passent déjà. Elles ne sont pas un cadeau : elles garantissent que votre réécriture ne cassera pas ce qui marchait.

### Étape 2, la hiérarchie fermée

Ouvrez `src/training/shapes/Shape.java` :

```java
sealed interface Shape permits Circle, Square, Rectangle, Triangle {
}

record Circle(double radius) implements Shape {
}
```

`sealed` borne les implémentations directes à la liste `permits`. Ici, toutes sont des records, donc finales : aucune autre forme ne peut se glisser dans la hiérarchie. Retenez cette fermeture, c'est elle qui rend possible tout ce qui suit.

> [!NOTE]
> **`sealed` n'est pas au programme**
> Il date de Java 17, et sert ici de **terrain**. Le sujet des deux jours, c'est ce que le `switch` sait en faire depuis Java 21.

### Étape 3, du `if instanceof` au `switch`

Ouvrez `src/training/shapes/ShapeDescriber.java`. Le code de départ est une chaîne de tests de type qui se termine par un repli :

```java
if (shape instanceof Circle circle) {
    return "cercle de rayon " + circle.radius();
}
// ... et à la fin :
return "forme inconnue";
```

C'est ce repli le défaut : le triangle est oublié, et personne n'est prévenu. Le test le constate, la compilation non.

Remplacez le corps de `describe(Shape shape)` par ce `switch` ; conservez la classe, la signature de méthode et le `package` :

```java
return switch (shape) {
    case Circle circle -> "cercle de rayon " + circle.radius();
    case Square square -> "carré de côté " + square.side();
    case Rectangle rectangle ->
            "rectangle " + rectangle.width() + " x " + rectangle.height();
    case Triangle triangle -> "triangle de base " + triangle.base();
};
```

Recompilez puis relancez : quatre lignes vertes, et toujours un échec intentionnel pour le carré de côté nul.

> [!NOTE]
> **Question : Pourquoi ça compile sans `default` ?**
> Parce que `Shape` est `sealed`. Le compilateur connaît la liste complète des types autorisés par le `permits`, vérifie que le `switch` les traite tous, et ne réclame donc aucun cas par défaut.

### Étape 4, un cas particulier avant le cas général

Il reste une ligne rouge : un carré de côté nul doit répondre `carré dégénéré`. Ajoutez une garde avec `when`, **avant** le cas général :

```java
case Square square when square.side() == 0 -> "carré dégénéré";
case Circle circle -> "cercle de rayon " + circle.radius();
case Square square -> "carré de côté " + square.side();
```

L'ordre décide : les `case` sont évalués de haut en bas, et un cas particulier placé après le cas général qui le recouvre ne serait jamais atteint. Le compilateur vous le dirait.

Relancez. `Pattern matching pour switch : exercice validé`.

### Étape 5, constater ce que le filet protège

Une dernière manipulation, à faire et à défaire. Ajoutez une cinquième forme dans `Shape.java` :

```java
sealed interface Shape permits Circle, Square, Rectangle, Triangle, Ellipse {
}

record Ellipse(double a, double b) implements Shape {
}
```

Recompilez. **La compilation échoue**, à l'endroit exact où une décision manque :

```
error: the switch expression does not cover all possible input values
```

C'est le comportement recherché. Maintenant ajoutez `default -> "forme inconnue";` comme dernière branche, après les `case` : la compilation repasse au vert, et vous venez d'éteindre le filet. Le prochain oubli ne se verra plus qu'à l'exécution.

Retirez uniquement la branche `default`, `Ellipse` du `permits` et la déclaration du record `Ellipse`. Conservez les quatre records existants et votre `switch` corrigé ; recompilez et relancez pour retrouver la validation.

Pour recommencer volontairement tout le sujet depuis son état initial, la commande suivante est une autre possibilité, mais elle efface aussi votre solution. Lancez-la seulement depuis `exercices/pattern-matching-switch` :

```
git restore .
```

> [!TIP]
> **Ce qu'il faut retenir**
> - `sealed` arrête la liste des types possibles, et c'est cette liste qui rend le `switch` vérifiable.
> - Sur une hiérarchie fermée, un `switch` complet se passe de `default`, et l'ajout d'un type casse la compilation à l'endroit exact où une décision manque.
> - Ajouter un `default` sur une hiérarchie fermée éteint ce filet : le cas oublié devient un comportement silencieux à l'exécution.
> - Dans un `switch`, l'ordre des `case` décide : le cas particulier passe avant le cas général qui le recouvre.

## Record patterns

<a id="sequence-record-patterns"></a>

[Aller au corrigé](#corrige-record-patterns)

`Segment` contient deux `Point`, et `SegmentDescriber.describe` qualifie un segment à partir de ses coordonnées. Deux records, une classe, rien d'autre à connaître.

Un pattern de type comme `case Segment segment` donne accès à l'objet entier. Pour tester ses composants, il faut encore appeler `segment.start().x()`, etc. Un record pattern relie directement des noms aux composants déclarés dans le record : `Point(int x, int y)` extrait les deux coordonnées. En imbriquant deux patterns `Point` dans `Segment(...)`, on extrait les deux extrémités dans une seule branche.

**À observer** : `Segment(Point(0, 3), Point(5, 3))` est horizontal parce que ses ordonnées sont égales. Si les deux coordonnées coïncident, le segment est un point ; ce cas doit être reconnu avant le cas horizontal.

Depuis la racine du dépôt :

```
cd exercices/record-patterns
```

**Fichier à modifier** : `src/training/geometry/SegmentDescriber.java`.

```
javac -d out src/training/geometry/*.java test/training/geometry/*.java
```

```
java -cp out training.geometry.SegmentDescriberTest
```

Au premier lancement :

```
OK    Reconnaître un segment horizontal
OK    Reconnaître un segment vertical
ÉCHEC Reconnaître un segment réduit à un point
ÉCHEC Reconnaître un segment oblique
OK    Refuser ce qui n'est pas un segment
```

Le travail : récrire `describe` avec des record patterns imbriqués, en ajoutant les deux cas manquants. Un segment dont les deux extrémités coïncident est un `point unique`, un segment ni horizontal ni vertical est `oblique`.

> [!WARNING]
> **Ici la nouveauté n'est pas nécessaire, elle est plus lisible**
> Tout cet exercice se résout sans record pattern, à coups d'accesseurs. Le test vérifie donc le **comportement**, pas la forme. C'est la relecture qui juge la forme, et c'est le sujet du débriefing.

**Algorithme en français** : reconnaître d'abord un segment réduit à un point, puis un segment horizontal, vertical ou oblique ; traiter enfin ce qui n'est pas un segment.

**Avant de continuer** : la dernière ligne doit afficher `Record patterns : exercice validé`. La branche du point unique doit précéder celles qui ne comparent qu'une coordonnée.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un record pattern reconnaît le type et extrait ses composants en une construction.
> - Les patterns peuvent s'imbriquer pour parcourir plusieurs records.
> - Les cas les plus précis doivent précéder ceux qui les recouvrent.
> - Le test vérifie ici le résultat ; la relecture vérifie l'usage réel des record patterns.

Source : [JEP 440, Record Patterns](https://openjdk.org/jeps/440).

## Variables et patterns anonymes

Après le corrigé des record patterns, certaines branches déclarent quatre coordonnées alors qu'elles n'en utilisent que deux. Le caractère `_` indique qu'une valeur est volontairement ignorée : le lecteur ne cherche plus un usage qui n'existe pas et le compilateur interdit toute lecture accidentelle.

```java
case Segment(Point(int x1, _), Point(int x2, _)) when x1 == x2 -> "vertical";
case Segment(Point(_, int y1), Point(_, int y2)) when y1 == y2 -> "horizontal";
```

**À essayer** : restez dans `exercices/record-patterns`, dans `src/training/geometry/SegmentDescriber.java`, après avoir obtenu la solution précédente. Remplacez seulement les branches `horizontal` et `vertical` par ces fragments, en gardant leur ordre et la branche `point unique` avant elles. Recompilez et relancez `SegmentDescriberTest` avec les commandes précédentes : toutes les lignes restent vertes. Les fragments ci-dessus constituent aussi le corrigé de cette manipulation.

> [!NOTE]
> **Question : `_` est-il une variable au nom très court ?**
> Non. Il marque une valeur ignorée et ne peut pas être lu dans la suite du code.

> [!TIP]
> **Ce qu'il faut retenir**
> - `_` documente une valeur reçue mais volontairement inutilisée.
> - Il réduit le bruit sans changer le comportement.
> - Il ne doit pas masquer une donnée dont le traitement est réellement nécessaire.

Source : [JEP 456, Unnamed Variables & Patterns](https://openjdk.org/jeps/456).

## Patterns sur les types primitifs

Les patterns primitifs étendent les conversions contrôlées dans `instanceof` et `switch`. En Java 25, cette fonctionnalité reste en preview : elle sert à expérimenter, pas à engager une migration de production.

Le point à vérifier est la conversion exacte. Une valeur `int` peut être représentée exactement par un `double`, tandis qu'un `long` suffisamment grand peut perdre de la précision. Dans une conversion primitive testée par un pattern, celui-ci ne réussit que si la conversion est exacte.

Le fragment suivant est une méthode à lire, pas un fichier à lancer :

```java
static String classifier(double valeur) {
    return switch (valeur) {
        case int entier -> "entier exact : " + entier;
        default -> "valeur décimale";
    };
}
```

Ce code nécessite les options de preview correspondant au JDK 25. La formation le lit comme une perspective et ne l'intègre pas aux exercices sans option.

**Avant de continuer** : prédisez `classifier(12.0)` puis `classifier(12.5)`. Réponses : `entier exact : 12`, puis `valeur décimale`. Le libellé du `default` est simplifié : un nombre entier hors de la plage de `int`, ou `NaN`, y passe aussi.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le pattern teste qu'une conversion primitive est exacte avant de l'appliquer.
> - La fonctionnalité est en preview dans Java 25.
> - Son statut impose de l'isoler d'un socle de production.

Source : [JEP 507, Primitive Types in Patterns](https://openjdk.org/jeps/507).

## Collections séquencées

<a id="sequence-collections-sequencees"></a>

[Aller au corrigé](#corrige-collections-sequencees)

`WaitingLine` est une file d'attente de tickets. Les tickets sont uniques et conservés dans leur ordre d'arrivée, d'où le `LinkedHashSet`. C'est tout ce qu'il y a à savoir pour commencer.

Depuis la racine du dépôt :

```
cd exercices/collections-sequencees
```

**Fichier à modifier** : `src/training/sequenced/WaitingLine.java`.

```
javac -d out src/training/sequenced/*.java test/training/sequenced/*.java
```

```
java -cp out training.sequenced.WaitingLineTest
```

Au premier lancement :

```
ÉCHEC Placer un ticket prioritaire en tête de file
ÉCHEC Retirer le premier ticket et le retourner
ÉCHEC Rendre la file du plus récent au plus ancien
3 vérification(s) en échec
```

Trois méthodes sont à écrire : `pushToFront`, `serveNext` et `newestFirst`.

Ajoutez l'import `java.util.SequencedSet` en remplaçant l'import devenu inutile de `java.util.Set`. Gardez `LinkedHashSet` comme implémentation ; le changement porte sur le type déclaré du champ et les corps des méthodes.

Une indication, et elle porte tout l'exercice : le champ est déclaré `Set<String>`, et `Set` ne porte aucune notion d'ordre de rencontre. Cherchez la méthode sur le bon type avant de l'écrire à la main.

Deux méthodes existantes, `first()` et `last()`, montrent ce qu'il fallait écrire avant Java 21. Les simplifier est un bonus, que le test ne vérifie pas.

**Algorithme en français** : exposer le champ comme une collection séquencée, ajouter en tête avec l'opération dédiée, retirer le premier élément, puis copier la vue inversée dans une liste stable.

**Avant de continuer** : la dernière ligne doit afficher `Collections séquencées : exercice validé`. Expliquez pourquoi le type déclaré du champ donne accès à ces méthodes et pourquoi la sortie est copiée.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le type déclaré décide des opérations visibles.
> - Les interfaces séquencées unifient premier élément, dernier élément et ordre inverse.
> - `reversed()` rend une vue ; une copie est utile lorsque le résultat doit rester indépendant.

Source : [JEP 431, Sequenced Collections](https://openjdk.org/jeps/431).

## Javadoc Markdown

<a id="sequence-javadoc-markdown"></a>

[Aller au corrigé](#corrige-javadoc-markdown)

`Temperature` est documentée en Javadoc classique : `<p>`, `<ul>`, `<li>`, `<a href>` et `{@code}`. La récrire en commentaires `///` et en Markdown.

Depuis la racine du dépôt :

```
cd exercices/javadoc-markdown
```

**Fichier à modifier** : `src/training/docs/Temperature.java`.

Générez la documentation **avant** de toucher au fichier :

```
javadoc -d apidocs src/training/docs/Temperature.java
```

Ouvrez `apidocs/training/docs/Temperature.html`, puis récrivez les commentaires et régénérez.

> [!WARNING]
> **Ici le résultat ne change pas, et c'est la leçon**
> La documentation doit conserver le même sens : liste des deux échelles, lien, mots en code et descriptions des paramètres. Le HTML n'est pas garanti identique octet par octet. La relecture juge la lisibilité de la source ; la page produite et des contrôles de ses éléments vérifient le résultat.

Une fois la conversion faite, essayez le piège : remettez une liste Markdown dans un commentaire `/** */`, régénérez, et regardez le HTML.

**À observer** : les tirets de cette liste ne produisent plus une liste HTML structurée. Remettez ensuite les commentaires `///` et régénérez pour conserver la version correcte. Préservez la méthode de conversion, ses accolades et sa formule : seul le commentaire change.

**Algorithme en français** : produire d'abord la référence HTML, convertir chaque commentaire `/** */` en `///`, remplacer les balises HTML par leur équivalent Markdown, régénérer, puis comparer les deux pages.

> [!TIP]
> **Ce qu'il faut retenir**
> - `///` active le Markdown dans les commentaires de documentation.
> - Les tags Javadoc comme `@param` et `@return` restent disponibles.
> - Le bénéfice se mesure dans la source ; le HTML produit doit conserver le sens.

Source : [JEP 467, Markdown Documentation Comments](https://openjdk.org/jeps/467).

## Fichiers source compacts

<a id="sequence-fichiers-source-compacts"></a>

[Aller au corrigé](#corrige-fichiers-source-compacts)

`Bienvenue.java` ne contient qu'un commentaire. Écrivez le programme qui affiche exactement `Bonjour depuis un fichier source compact`, sous trois contraintes : aucune classe déclarée, pas de `main` statique ni de paramètre `String[] args`, aucun `import`.

Depuis la racine du dépôt :

```
cd exercices/fichiers-source-compacts
```

**Fichier à modifier** : `Bienvenue.java`.

Le lanceur compile le fichier en mémoire puis l'exécute, sans étape `javac` séparée :

```
java Bienvenue.java
```

En l'état, le fichier répond :

```
error: no class declared in source file
```

**Algorithme en français** : déclarer une méthode `main` d'instance sans argument, puis afficher le message avec `IO.println`. Relancer directement le fichier source.

> [!TIP]
> **Ce qu'il faut retenir**
> - Java 25 permet un petit programme sans déclaration explicite de classe.
> - La méthode `main` peut être une méthode d'instance sans argument.
> - Cette forme vise l'apprentissage et les programmes compacts ; elle n'oblige pas à récrire les applications existantes.

Source : [JEP 512, Compact Source Files and Instance Main Methods](https://openjdk.org/jeps/512).

## Imports de modules

<a id="sequence-imports-de-modules"></a>

[Aller au corrigé](#corrige-imports-de-modules)

`Inventaire` utilise `List`, `ArrayList`, `Map` et `HashMap`, et ses imports ont été retirés. Rétablissez-les en **une seule déclaration**, sans nommer aucun type.

Depuis la racine du dépôt :

```
cd exercices/imports-de-modules
```

**Fichier à modifier** : `src/training/modules/Inventaire.java`.

```
javac -d out src/training/modules/*.java test/training/modules/*.java
```

En l'état, la compilation s'arrête :

```
src/training/modules/Inventaire.java:10: error: cannot find symbol
        List<String> articles = new ArrayList<>();
        ^
  symbol:   class List
```

C'est le premier juge de cet exercice : le **compilateur** vérifie la visibilité des types. Ajoutez la déclaration dans `Inventaire.java`, après le `package`, puis relancez la compilation précédente. Une fois celle-ci réussie, le test vérifie le comportement :

```
java -cp out training.modules.InventaireTest
```

Résultat attendu : `OK    Résumer l'inventaire`, puis `Imports de modules : exercice validé`.

Un package regroupe des types (`java.util`) ; un module regroupe des packages et précise ceux qu'il exporte (`java.base`). L'import de module permet d'employer les noms simples des types publics exportés accessibles au code appelant. Il ne copie pas les classes, ne les charge pas toutes et ne remplace pas la déclaration des dépendances d'une application modulaire.

**Algorithme en français** : placer `import module java.base;` après le `package`, recompiler, puis exécuter le test. Si un nom devient ambigu, ajouter un import de type explicite pour trancher.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un import de module rend visibles les types publics de tous les packages exportés par ce module.
> - Il porte sur un module, pas sur un package.
> - Le gain de concision doit être comparé au risque d'ambiguïté des noms.

Source : [JEP 511, Module Import Declarations](https://openjdk.org/jeps/511).

## Corps de constructeurs flexibles

<a id="sequence-constructeurs-flexibles"></a>

[Aller au corrigé](#corrige-constructeurs-flexibles)

`Capteur` est une classe abstraite dont le constructeur appelle `etiquette()`, redéfinie par ses sous-classes : il observe donc un objet encore en construction. `CapteurNomme` appelle `super()` en première instruction, avec deux conséquences.

Depuis la racine du dépôt :

```
cd exercices/constructeurs-flexibles
```

**Fichier à modifier** : `src/training/sensors/CapteurNomme.java`.

```
javac -d out src/training/sensors/*.java test/training/sensors/*.java
```

```
java -cp out training.sensors.CapteurNommeTest
```

Au premier lancement :

```
ÉCHEC La base lit le nom déjà affecté
OK    Refuser un nom vide
ÉCHEC Refuser avant de construire la base
```

Les deux échecs disent la même chose sous deux angles : la base est construite trop tôt. Elle lit `nom` avant son affectation, donc `null`, et un nom invalide n'est refusé qu'une fois la base déjà construite.

Le travail : déplacer la validation et l'affectation **avant** `super()`.

Avant Java 25 sans preview, l'appel explicite à `super()` devait ouvrir le constructeur. Java 25 permet de préparer l'état dans un prologue, avec des restrictions : on peut ici affecter le champ déclaré par `CapteurNomme`, mais pas lire librement l'objet en construction ni appeler ses méthodes d'instance. Cet exemple montre le mécanisme ; appeler une méthode redéfinissable depuis un constructeur reste délicat à concevoir.

**Algorithme en français** : refuser d'abord le nom invalide, affecter ensuite le champ `final`, puis appeler `super()`. Relancer le test pour vérifier la valeur observée par la base et l'absence de construction en cas d'erreur.

**Avant de continuer** : la dernière ligne doit afficher `Constructeurs flexibles : exercice validé`. Le nom est validé et affecté avant que la base appelle `etiquette()`.

> [!NOTE]
> **Question : Peut-on utiliser `this` librement avant `super()` ?**
> Non. Java 25 autorise un prologue limité avant l'appel explicite au constructeur parent ; l'objet ne doit pas s'échapper et certaines utilisations de `this` restent interdites.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le prologue permet de valider et préparer les arguments avant de construire la base.
> - Un champ de l'objet peut être affecté lorsque les règles d'initialisation le permettent.
> - Le but est d'empêcher le constructeur parent d'observer un état invalide.

Source : [JEP 513, Flexible Constructor Bodies](https://openjdk.org/jeps/513).

# Traitement des données : Streams et calcul vectoriel

**Avant de continuer** : répondre aux [questions 2 à 5 du quiz](#quiz), sur le `switch` et les collections. Les questions de concurrence, de Vector API et de cryptographie viennent après leurs séquences.

## Stream Gatherers

<a id="sequence-stream-gatherers"></a>

[Aller au corrigé](#corrige-stream-gatherers)

### Regrouper sans quitter le Stream

Un Stream décrit un traitement : `map` transforme chaque élément, `filter` décide de le garder. Supposons maintenant qu'on veuille regrouper `1, 2, 3, 4, 5` deux par deux, puis continuer à traiter ces lots. Il faut retenir un premier nombre jusqu'à l'arrivée du suivant. Ce besoin était programmable auparavant ; Java 24 apporte une manière standard d'ajouter cette opération au milieu du pipeline.

**À essayer** : revenez à la racine du dépôt et lancez JShell :

```
jshell
```

Saisissez les imports, puis le pipeline :

```java
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Gatherers;
Stream.of(1, 2, 3, 4, 5).gather(Gatherers.windowFixed(2)).toList();
```

**À observer** : le résultat est `[[1, 2], [3, 4], [5]]`. Le dernier lot incomplet est conservé. Trois noms désignent trois rôles : `gather(...)` applique l'opération au Stream, un `Gatherer` décrit cette opération et `Gatherers` fournit les fabriques du JDK. On peut donc utiliser un Gatherer sans en écrire un.

Prédisez maintenant ce qui entre dans `map`. Collez cette expression sur une seule ligne dans JShell :

```java
Stream.of(1, 2, 3, 4, 5).gather(Gatherers.windowFixed(2)).map(List::size).toList();
```

Résultat : `[2, 2, 1]`. Après `gather`, les éléments sont des listes ; `map` reçoit chaque lot. `gather` et `map` sont intermédiaires : ils renvoient un Stream. `toList()` termine et déclenche l'évaluation. `collect(...)` est également terminale : elle utilise un `Collector` pour construire le résultat final. Une opération intermédiaire peut retenir plusieurs entrées avant d'émettre.

### Utiliser les opérations fournies

Sur les mêmes nombres, voici ce que produisent les fabriques du JDK lorsqu'on termine par `toList()` :

| Opération dans `gather(...)` | Résultat ou rôle |
|---|---|
| `Gatherers.windowFixed(2)` | `[[1, 2], [3, 4], [5]]`, lots sans recouvrement |
| `Gatherers.windowSliding(2)` | `[[1, 2], [2, 3], [3, 4], [4, 5]]`, fenêtres glissantes |
| `Gatherers.scan(() -> 0, Integer::sum)` | `[1, 3, 6, 10, 15]`, totaux successifs |
| `Gatherers.fold(() -> 0, Integer::sum)` | `[15]`, un total émis dans le Stream |
| `Gatherers.mapConcurrent(2, n -> n * 2)` | `[2, 4, 6, 8, 10]`, fonction appliquée avec concurrence bornée, ordre conservé |

Remplacez l'opération dans le premier pipeline pour vérifier ces prédictions. `fold` reste ici une opération intermédiaire malgré son résultat unique. `mapConcurrent` utilise des threads virtuels, abordés au chapitre Concurrence ; retenez pour l'instant son rôle, différent du regroupement en lots.

Quittez JShell avec `/exit`. Source : [API Gatherers, Java SE 25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Gatherers.html).

### Garder le premier relevé de chaque capteur

`SerieDeReleves` traite une liste de relevés, chacun portant le nom d'un capteur et une valeur. C'est tout le domaine.

Pour `A:20, B:17, A:21, C:19`, on veut `A:20, B:17, C:19`. La clé est le capteur, pas le relevé entier : `distinct()` conserverait les deux relevés de A, car leurs valeurs diffèrent. Il faut mémoriser les clés déjà vues et transmettre seulement leur premier relevé.

| Entrée | Clés vues avant | Décision | Clés vues après |
|---|---|---|---|
| `A:20` | aucune | Transmettre | A |
| `B:17` | A | Transmettre | A, B |
| `A:21` | A, B | Ne rien émettre, continuer | A, B |
| `C:19` | A, B | Transmettre | A, B, C |

Le `Set` ne sert pas à construire la sortie : il répond « déjà vu ? ». Les relevés sont transmis dans l'ordre de lecture, donc l'ensemble n'a pas besoin d'être ordonné.

Depuis la racine du dépôt :

```
cd exercices/stream-gatherers
```

**Fichier à modifier** : `src/training/releves/SerieDeReleves.java`.

```
javac -d out src/training/releves/*.java test/training/releves/*.java
```

```
java -cp out training.releves.SerieDeRelevesTest
```

Au premier lancement :

```
ÉCHEC Ne garder que le premier relevé de chaque capteur
ÉCHEC Conserver l'ordre de rencontre des capteurs
OK    Découper en fenêtres fixes de deux valeurs
2 vérification(s) en échec
```

Le gatherer `premierPar(cle)` pousse aujourd'hui tout ce qu'il reçoit. Il lui manque un **état privé** : l'ensemble des clés déjà rencontrées.

Dans `premierPar`, `T` désigne le type de l'élément et `K` celui de sa clé ; `cle.apply(element)` extrait ici le capteur. Ajoutez les imports `java.util.Set` et `java.util.HashSet`, puis remplacez le corps de cette méthode. Conservez les méthodes de pipeline et les tests.

Les rôles se rattachent à l'algorithme :

| Rôle | Ce qu'il fait | Dans cet exercice |
|---|---|---|
| Initializer | Crée l'état d'une évaluation | `Etat::new` crée un ensemble vide |
| Integrator | Reçoit état, élément et aval, la suite du pipeline | Ajoute la clé, transmet si elle est nouvelle |
| Finisher | Peut émettre à la fin de l'entrée | Aucun élément retenu à vider ici |
| Combiner | Combine des états partiels pour une stratégie parallèle | Pas de combinaison pour cette version séquentielle |

`Set.add` renvoie `true` si la clé est nouvelle. Dans ce cas, `aval.push(element)` transmet le relevé et renvoie si l'aval accepte encore des éléments ; retourner ce booléen respecte sa demande d'arrêt. Pour un doublon, n'appelez pas `push`, mais retournez `true` pour poursuivre jusqu'au capteur suivant. Un `false` signifierait « arrêter », pas « ignorer ce relevé ».

L'état doit être créé dans l'initializer, pas capturé depuis un ensemble extérieur. Ainsi, réutiliser le même Gatherer pour une seconde évaluation séquentielle repart sans capteur mémorisé. Les fabriques permettent d'omettre les rôles inutiles : ici `ofSequential` reçoit seulement l'initializer et l'integrator.

La ligne verte n'est pas un cadeau, c'est un filet : elle utilise `windowFixed`, fourni par le JDK, et garantit que votre modification ne casse pas le reste du pipeline. Elle montre au passage que la dernière fenêtre incomplète est conservée, et non jetée.

**Question de débriefing** : pourquoi ce gatherer est-il construit avec `ofSequential`, et que faudrait-il fournir de plus pour qu'il supporte un flux parallèle ?

**Algorithme en français** : créer un ensemble de clés pour une évaluation du pipeline ; pour chaque relevé, transmettre le premier portant une clé nouvelle et ignorer les suivants ; continuer jusqu'à la fin du flux.

**Avant de continuer** : la dernière ligne doit afficher `Stream Gatherers validé`. Expliquez pourquoi un doublon ne doit rien émettre mais doit laisser continuer le flux.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un Gatherer étend un pipeline par une opération intermédiaire personnalisée.
> - Son état appartient à une évaluation du pipeline.
> - Ne rien émettre et arrêter le traitement sont deux décisions différentes.
> - Une implémentation parallèle demande une stratégie de combinaison correcte.

Source : [API Gatherer, Java SE 25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Gatherer.html).

## Vector API

Une boucle scalaire traite une valeur à la fois. La Vector API décrit explicitement un même calcul sur un bloc de valeurs, afin que le JIT puisse l'abaisser vers les instructions vectorielles adaptées au processeur.

```java
var espece = FloatVector.SPECIES_PREFERRED;
for (; i < espece.loopBound(a.length); i += espece.length()) {
    var va = FloatVector.fromArray(espece, a, i);
    var vb = FloatVector.fromArray(espece, b, i);
    va.mul(vb).intoArray(resultat, i);
}
```

Ce fragment se lit à l'intérieur d'une méthode où `i` vaut initialement 0 et où `a`, `b` et `resultat` sont des tableaux `float[]` de même longueur. Il ne constitue pas un fichier exécutable. Dans cette version sans masque, une boucle scalaire traite ensuite les éléments restants :

```java
for (; i < a.length; i++) {
    resultat[i] = a[i] * b[i];
}
```

L'espèce préférée dépend de la machine ; ne figez donc pas une largeur supposée. Une autre implémentation peut utiliser des opérations masquées pour la fin du tableau : la boucle scalaire n'est pas une obligation universelle de l'API.

> [!NOTE]
> **Question : Pourquoi ne pas laisser uniquement le JIT vectoriser une boucle ordinaire ?**
> L'auto-vectorisation reste utile, mais elle n'offre pas toujours un contrôle prévisible sur les opérations produites. L'API explicite le calcul vectoriel ; seule une mesure sur la charge réelle justifie son adoption.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un vecteur traite plusieurs valeurs de même type en une opération.
> - La largeur préférée dépend du processeur.
> - La Vector API reste en incubation dans Java 25.
> - Une mesure justifie l'adoption ; il faut traiter la fin du tableau, ici par une boucle scalaire.

Source : [JEP 508, Vector API](https://openjdk.org/jeps/508).

# JVM : performances et observabilité

**Point d'étape précédent** : répondre à la [question 9 du quiz](#quiz), sur la fin d'un calcul vectoriel. Pour les Gatherers, expliquer pourquoi `gather` laisse continuer le pipeline alors que `collect` le termine, puis justifier l'état privé et le choix séquentiel ; [correction](#corrige-stream-gatherers).

## ZGC générationnel

La plupart des objets meurent jeunes. Le ZGC générationnel sépare les objets récents des objets plus anciens afin de concentrer plus souvent le travail sur la jeune génération. Il est devenu le mode par défaut de ZGC, puis le mode non générationnel a été retiré.

La décision se prend en observant les pauses, le débit et la mémoire de l'application avant et après, avec la même charge.

Depuis n'importe quel dossier, vérifiez que le JDK sélectionne ZGC :

```
java -XX:+UseZGC -Xlog:gc -version
```

La sortie doit contenir `Using The Z Garbage Collector`. Sous Java 25, aucune option supplémentaire ne sélectionne le mode générationnel.

**Avant de continuer** : cette commande prouve-t-elle une baisse des pauses sur votre service ? Non : elle vérifie le GC sélectionné. Il faut encore mesurer l'application sous une charge comparable. « Par défaut » désigne ici le mode de ZGC, pas le choix de ZGC comme GC par défaut de toute JVM.

> [!TIP]
> **Ce qu'il faut retenir**
> - ZGC vise des pauses très faibles sur des tas importants.
> - Le mode générationnel exploite la mortalité rapide de nombreux objets.
> - Une migration de GC se valide avec les objectifs de service et des mesures comparables.

Sources : [JEP 439](https://openjdk.org/jeps/439), [JEP 474](https://openjdk.org/jeps/474) et [JEP 490](https://openjdk.org/jeps/490).

## AOT HotSpot

HotSpot apprend normalement au démarrage quelles classes charger et quelles méthodes optimiser. Les évolutions AOT de Java 25 permettent d'enregistrer une partie de cet apprentissage lors d'un lancement d'entraînement, puis de la réutiliser au démarrage suivant.

Le profil d'entraînement doit représenter le démarrage réel. Un cache construit sur un parcours incomplet peut apporter peu ; il ne remplace ni les tests ni la mesure du temps de démarrage.

Le trajet est : `entraînement` → `cache AOT produit par HotSpot` → `chargement lors des lancements suivants` → `JIT toujours actif`. AOT signifie *Ahead Of Time*, en avance de phase ; JIT signifie *Just In Time*, pendant l'exécution. Le cache prépare du chargement, de la liaison de classes et des profils utiles aux optimisations ; il ne transforme pas l'application en exécutable natif autonome.

**À observer, sans exécuter dans le dépôt** : les commandes suivantes sont des modèles. `app.jar` et `com.example.App` n'existent pas dans les exercices. Pour les appliquer à une vraie application, placez-vous dans le dossier de son JAR et remplacez ces deux noms par son classpath et sa classe principale ; le cache est écrit dans ce dossier courant.

Produire le cache après un lancement d'entraînement représentatif :

```
java -XX:AOTCacheOutput=app.aot -cp app.jar com.example.App
```

Charger ce cache :

```
java -XX:AOTCache=app.aot -cp app.jar com.example.App
```

**Avant de continuer** : un entraînement qui ne parcourt pas le démarrage utile garantit-il un gain ? Non. Il faut reproduire ce parcours, vérifier l'utilisation du cache et mesurer le temps jusqu'au service prêt ; le JIT continue à optimiser pendant l'exécution.

> [!TIP]
> **Ce qu'il faut retenir**
> - L'AOT déplace une partie du travail de démarrage vers une phase d'entraînement.
> - Le bénéfice attendu concerne surtout le démarrage et la montée en régime.
> - Le cache dépend de l'application et de son environnement ; il doit être régénéré après les changements pertinents.

Sources : [JEP 483](https://openjdk.org/jeps/483), [JEP 514](https://openjdk.org/jeps/514) et [JEP 515](https://openjdk.org/jeps/515).

## En-têtes d'objet compacts

Chaque objet porte un en-tête utilisé par la JVM. Les en-têtes compacts réduisent cette surcharge, ce qui peut diminuer l'empreinte mémoire des applications créant beaucoup de petits objets.

L'effet dépend du profil d'allocation. Mesurez la taille du tas et le comportement du GC sur une charge représentative ; une application dominée par de gros tableaux verra un gain relatif plus faible.

Dans Java 25, l'option produit reste désactivée par défaut et s'active avec `-XX:+UseCompactObjectHeaders`. Comparez l'application avec et sans cette option, sans changer la charge ni les autres réglages.

**Avant de continuer** : pourquoi de nombreux petits objets sont-ils un meilleur candidat qu'un unique gros tableau ? L'en-tête est un coût par objet ; sa réduction se répète avec le nombre d'objets. Le gain réel dépend aussi de leur disposition et de leur alignement.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le gain porte sur le coût fixe de chaque objet.
> - L'impact cumulé devient visible quand les petits objets sont très nombreux.
> - Java 25 finalise cette évolution, mais son activation et son intérêt se vérifient sur l'environnement cible.

Source : [JEP 519, Compact Object Headers](https://openjdk.org/jeps/519).

## Java Flight Recorder

Java Flight Recorder enregistre des événements de la JVM et de l'application avec un coût conçu pour l'observation en production. Java 25 enrichit notamment le profilage du temps CPU, l'échantillonnage coopératif et le traçage ciblé de méthodes.

Partez d'une question : où part le temps CPU, quelle méthode dépasse une durée, ou quel blocage explique une latence ? Enregistrez ensuite les événements adaptés et confrontez l'hypothèse aux données.

Les commandes suivantes sont des modèles pour une application déjà compilée : `MonApplication` n'est pas fourni dans le dépôt. Depuis le dossier contenant `MonApplication.class`, démarrer un enregistrement de 30 secondes (il s'arrête plus tôt si la JVM termine) :

```
java -XX:StartFlightRecording:duration=30s,filename=recording.jfr MonApplication
```

Afficher les événements GC et de charge CPU :

```
jfr print --categories GC --events CPULoad recording.jfr
```

Exemple Java 25, tracer uniquement les appels à `HashMap.resize` :

```
java -XX:StartFlightRecording:jdk.MethodTrace#filter=java.util.HashMap::resize,filename=recording.jfr MonApplication
```

Le fichier `recording.jfr` se trouve dans le dossier du lancement ; exécutez `jfr print` depuis ce même dossier après sa production. Ce filtre affiche les événements enregistrés, sans garantir qu'une application courte produise une pause GC ou un échantillon CPU.

**À essayer**, pour observer le mécanisme avec un fichier existant : revenez à la racine du dépôt et lancez :

```
java -XX:StartFlightRecording:jdk.MethodTrace#filter=java.util.HashMap::resize,filename=essais-guide/recording.jfr demos/ModuleImportDemo.java
```

Puis lisez les événements ciblés :

```
jfr print --events jdk.MethodTrace essais-guide/recording.jfr
```

Le programme affiche `25`, puis le fichier permet de lire des événements `jdk.MethodTrace` visant `HashMap.resize`, notamment pendant le lancement. Cela vérifie l'enregistrement et le filtrage ; ce n'est pas une mesure de performance représentative.

`jdk.CPUTimeSample` est expérimental et propre à Linux dans Java 25. Le timing et le tracing ciblés peuvent coûter cher si leur filtre est trop large. **Avant de continuer** : pour trouver qui déclenche `HashMap.resize`, choisissez le tracing et ses piles d'appels ; pour situer la consommation CPU, choisissez l'échantillonnage adapté à la plateforme.

> [!TIP]
> **Ce qu'il faut retenir**
> - JFR sert à enregistrer avant d'expliquer.
> - Un événement n'est utile que relié à une question de diagnostic.
> - Les nouveaux événements améliorent l'observation ; ils ne remplacent pas un protocole de mesure.

Sources : [JEP 509](https://openjdk.org/jeps/509), [JEP 518](https://openjdk.org/jeps/518) et [JEP 520](https://openjdk.org/jeps/520).

# Concurrence

## Threads virtuels

<a id="sequence-threads-virtuels"></a>

[Aller au corrigé](#corrige-threads-virtuels)

`LotDeTaches` reçoit une liste d'identifiants et exécute une tâche par identifiant. Chaque tâche attend 10 millisecondes, comme le ferait un appel réseau, puis rend son résultat.

Depuis la racine du dépôt :

```
cd exercices/threads-virtuels
```

**Fichier à modifier** : `src/training/taches/LotDeTaches.java`.

```
javac -d out src/training/taches/*.java test/training/taches/*.java
```

```
java -cp out training.taches.LotDeTachesTest
```

Au premier lancement :

```
Durée mesurée : 3052 ms pour 1000 tâches de 10 ms.
OK    Rendre les 1000 résultats dans l'ordre des identifiants
ÉCHEC Exécuter les 1000 tâches de 10 ms en moins d'une seconde
1 vérification(s) en échec
```

La durée est une mesure : votre chiffre sera différent, autour de trois secondes. C'est l'ordre de grandeur qui compte, pas la valeur.

Le point à remarquer avant d'écrire quoi que ce soit : **le test n'inspecte pas le type des threads**. Aucun compteur ni `isVirtual()` ; il mesure la durée autour de l'appel. Un pool de quatre threads répartit 1000 attentes de 10 ms en quatre files, soit un plancher théorique proche de 2,5 secondes. Un exécuteur créant un thread virtuel par tâche permet à beaucoup de ces attentes de se chevaucher. Leur démarrage n'est pas simultané, et la durée dépend du poste.

Un thread de plateforme mobilise un thread du système d'exploitation. Un thread virtuel est géré par la JVM et s'exécute sur un thread de plateforme appelé **porteur**. Lors d'une attente prise en charge, il peut se détacher ; le porteur exécute alors un autre thread virtuel. Ce mécanisme augmente le nombre d'opérations en attente que le service peut supporter, sans ajouter de capacité CPU.

Un seul geste est demandé, et il ne touche ni à la méthode qui attend, ni au corps des tâches, ni au test.

**Question de débriefing** : la même bascule appliquée à 1000 calculs de 10 millisecondes de CPU, et non d'attente, ne gagnerait rien. Pourquoi ?

**Algorithme en français** : remplacer le pool fixe par un exécuteur créant un thread virtuel par tâche, conserver la collecte des `Future` dans le même ordre, puis comparer la durée mesurée.

**Avant de continuer** : la dernière ligne doit afficher `Threads virtuels validé`. Les résultats gardent l'ordre demandé et la durée passe sous le seuil pédagogique du test.

> [!TIP]
> **Ce qu'il faut retenir**
> - Les threads virtuels augmentent la capacité des traitements qui passent du temps à attendre.
> - Ils ne rendent pas un calcul CPU plus rapide.
> - Le style reste un traitement séquentiel lisible dans chaque thread ; la JVM gère son montage sur les porteurs.

Source : [JEP 444, Virtual Threads](https://openjdk.org/jeps/444).

## Pinning et `synchronized`

Cette section n'a pas d'exercice : ce que Java 24 change se constate sur du code que vous n'avez pas à écrire. Elle répond à la question qui vient naturellement après l'exercice précédent : votre projet contient des `synchronized` partout, faut-il les réécrire avant de passer aux threads virtuels ?

Reprenons le mécanisme normal. Un thread virtuel qui se bloque sur une entrée-sortie est suspendu, et son thread porteur part servir une autre tâche. C'est ce détachement qui fait le gain de capacité.

Avant Java 24, ce détachement était impossible dans un cas précis : à l'intérieur d'un bloc `synchronized`. La JVM sait alors quel thread de plateforme détient le moniteur, pas quel thread virtuel ; si le thread virtuel se détachait, un autre monté sur le même porteur apparaîtrait faussement comme détenteur du verrou. La JVM interdisait donc le détachement dans un `synchronized` : un thread virtuel qui attendait à l'intérieur d'un verrou **épinglait** son porteur, qui gelait avec lui.

Le parallélisme cible du scheduler correspond par défaut au nombre de processeurs disponibles. Si tous les porteurs disponibles sont immobilisés par des attentes longues, les autres threads virtuels peuvent ne plus progresser ; le seuil dépend du poste et de la charge.

La JEP 491 s'ouvre sur un exemple de ce type, que voici détaillé :

```java
synchronized byte[] getData() throws IOException {
    byte[] buf = new byte[2048];
    int n = socket.getInputStream().read(buf);  // attend le réseau, verrou tenu
    return Arrays.copyOf(buf, n);
}
```

Cet extrait conceptuel suppose les imports et un champ `socket` déjà définis ; ce n'est pas un programme à copier. Il illustre une attente réseau sous verrou. Avant Java 24, ce porteur restait immobilisé pendant l'attente. Depuis Java 24 ([JEP 491](https://openjdk.org/jeps/491)), tenir ou attendre un moniteur n'empêche plus à lui seul le détachement. `Object.wait()` peut aussi libérer le porteur. Des cas subsistent, notamment lorsqu'un appel natif revient dans du Java qui bloque, ainsi que certains cas de chargement ou d'initialisation de classes. L'événement JFR `jdk.VirtualThreadPinned` aide au diagnostic ; il ne couvre pas tous les cas résiduels.

> [!NOTE]
> **Question : Faut-il remplacer tous les `synchronized` par `ReentrantLock` avant de migrer ?**
> Non. La recommandation antérieure visait les blocages fréquents et longs sous `synchronized`, pas tous les verrous. La JEP 491 supprime la nécessité de cette migration pour éviter ce pinning. Un `ReentrantLock` peut toujours répondre à d'autres besoins ; il n'est pas nécessaire de revenir sur le code déjà migré.

> [!NOTE]
> **Question : Le `synchronized` devient-il gratuit ?**
> Non. La contention reste : deux tâches qui disputent le même verrou se mettent en file, et l'attente se paie pareil. Ce qui change, c'est que cette file ne bloque plus les porteurs, donc plus tout le service. La règle de conception ne change pas : ne pas tenir un verrou pendant une attente longue.

> [!TIP]
> **Ce qu'il faut retenir**
> - Avant Java 24, une attente sous `synchronized` pouvait immobiliser le porteur et nuire à la montée en charge.
> - Depuis Java 24, le moniteur n'empêche plus à lui seul le détachement.
> - Des cas résiduels existent côté natif et lors du chargement ou de l'initialisation de classes ; JFR aide à les diagnostiquer.
> - Pinning et contention sont deux problèmes distincts : réécrire un `synchronized` ne supprime pas une contention.

## Scoped Values

<a id="sequence-scoped-values"></a>

[Aller au corrigé](#corrige-scoped-values)

### Comprendre avant de passer aux tâches concurrentes

Une requête porte l’identifiant `R-42`. Son traitement appelle plusieurs méthodes : `traiter()` → `enregistrer()` → `tracer()`. La dernière doit écrire `R-42:enregistrement`, pour qu’on retrouve les traces de cette requête.

On peut passer l’identifiant en paramètre à chaque méthode : c’est une bonne solution quand le trajet reste simple. Avec de nombreux intermédiaires, cela leur impose parfois de recevoir et retransmettre une donnée qu’ils n’utilisent pas. Cette information qui accompagne l’opération s’appelle ici le **contexte**.

Dans une application de commandes, les étapes vérifient le stock, calculent le prix et enregistrent la commande. Chaque message doit reprendre le bon identifiant. Un champ partagé contenant directement `R-42` pourrait être écrasé par une autre requête. Le besoin est que chaque traitement retrouve son information, puis cesse de l'exposer quand il se termine.

### Solution 1 : ThreadLocal, enregistrer puis retirer

`ThreadLocal` offre un emplacement propre à chaque thread. Si le thread A y écrit `R-42` et le thread B `R-99`, chacun retrouve sa valeur avec `get()`, même en utilisant la même clé. Cette isolation ne transmet pas une valeur vers un autre thread et ne sécurise pas automatiquement un objet mutable partagé.

**À essayer** : depuis la racine du dépôt, créez `essais-guide/DemoThreadLocal.java` avec ce fichier complet :

```java
public class DemoThreadLocal {
    private static final ThreadLocal<String> REQUETE = new ThreadLocal<>();

    public static void main(String[] args) {
        REQUETE.set("R-42");
        try {
            traiter();
            System.out.println(REQUETE.get()); // le traitement a fini
        } finally {
            REQUETE.remove();
        }
        System.out.println(REQUETE.get());
    }

    static void traiter() { enregistrer(); }
    static void enregistrer() { tracer(); }
    static void tracer() {
        System.out.println(REQUETE.get() + ":enregistrement");
    }
}
```

```
java essais-guide/DemoThreadLocal.java
```

**À observer** : la sortie est `R-42:enregistrement`, puis `R-42`, puis `null`. Le retour de `traiter()` n'efface pas la valeur. Si le thread est réutilisé pour une autre demande sans remplacement ni retrait, l'ancien identifiant peut être relu. Pour ce contexte temporaire, `remove()` dans un `finally` organise le nettoyage même si le traitement échoue. Ici, `get()` après retrait rend la valeur initiale `null` du `ThreadLocal` créé sans initializer.

Source : [API ThreadLocal, Java SE 25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/ThreadLocal.html).

### Solution 2 : Scoped Values, limiter la liaison à l'appel

`ScopedValue` permet d’associer ce contexte à l’exécution d’un appel. Les méthodes appelées qui ont accès à la clé peuvent alors lire la donnée, sans paramètre supplémentaire. Ce premier exemple utilise un seul thread : les threads virtuels ne sont pas nécessaires.

```java
public class DemoScopedValues {
    private static final ScopedValue<String> REQUETE = ScopedValue.newInstance();

    public static void main(String[] args) {
        System.out.println(REQUETE.isBound());
        ScopedValue.where(REQUETE, "R-42").run(() -> traiter());
        System.out.println(REQUETE.isBound());
    }

    static void traiter() { enregistrer(); }
    static void enregistrer() { tracer(); }
    static void tracer() {
        System.out.println(REQUETE.get() + ":enregistrement");
    }
}
```

Enregistrez ce fichier complet dans `essais-guide/DemoScopedValues.java`, puis exécutez depuis la racine du dépôt avec Java 25 :

```
java essais-guide/DemoScopedValues.java
```

Prédire la sortie avant de lancer :

```
false
R-42:enregistrement
false
```

`REQUETE` est la clé d’accès ; `R-42` est la donnée. Leur association s’appelle une **liaison**. `where(...).run(...)` exécute `traiter()` avec cette liaison dans le thread courant. Elle reste disponible pendant ses appels à `enregistrer()` puis `tracer()` : c’est la **portée** de la liaison. `run()` ne crée aucun thread.

Après le retour de `run()`, `isBound()` vaut `false` dans cet exemple. Appeler `get()` à cet endroit lèverait `NoSuchElementException`. La liaison se termine aussi si une exception remonte : aucun `remove()` à placer dans un `finally`. Si une liaison extérieure existait, elle serait restaurée.

> [!NOTE]
> **Pourquoi une clé `static` ne mélange-t-elle pas les requêtes ?**
> Les threads peuvent partager la même clé tout en ayant des liaisons différentes. Deux threads qui lient respectivement `R-42` et `R-99` lisent chacun leur identifiant. Un champ partagé contenant directement l’identifiant n’offrirait pas cette séparation.

> [!WARNING]
> **L’objet transporté n’est pas rendu immuable**
> Une `String` est immuable ; une liste mutable reste modifiable même si elle est transportée par `ScopedValue`. Préférer un contexte immuable pour cet usage. Il n’y a pas de `set()` pour remplacer la liaison en place, mais un appel imbriqué peut établir une autre liaison temporaire, puis retrouver la précédente à son retour.

### Comparer le même besoin

| Question | ThreadLocal | Scoped Values |
|---|---|---|
| Lire l'identifiant sans paramètre intermédiaire ? | Oui, dans le thread courant | Oui, dans la portée active |
| Rendre la valeur disponible ? | `set(valeur)` | `where(CLE, valeur).run(...)` ou `call(...)` |
| Retrouver la valeur ? | `get()` | `get()` |
| Au retour du traitement ? | La valeur peut rester attachée au thread | La liaison de l'appel se termine, la précédente est restaurée |
| Responsabilité pour ce contexte temporaire ? | Organiser le retrait dans `finally` | Délimiter l'appel qui a besoin du contexte |

**Avant de continuer** : avec quelle solution faut-il programmer le retrait ? Avec `ThreadLocal`. La fin d'une liaison Scoped Values termine une association, elle ne détruit pas l'objet transporté.

**À essayer** : dans `DemoScopedValues.java`, remplacez temporairement `traiter()` par `static void traiter() { throw new IllegalStateException("essai"); }`. Entourez seulement l'appel à `run()` d'un `try/catch (IllegalStateException e)` qui affiche `e.getMessage()`, en conservant le dernier `isBound()` après le `catch`. La sortie attendue est `false`, `essai`, `false` : le retour exceptionnel termine aussi la liaison. Remettez ensuite le fichier initial.

Les paramètres explicites restent adaptés aux dépendances simples. `ScopedValue` est utile pour un contexte lu à travers plusieurs appels, dont la disponibilité doit se terminer avec l’opération. Un `ThreadLocal` permet aussi une lecture sans paramètre, mais demande d’organiser explicitement la durée de présence de la valeur, avec `set()` et `remove()`.

Source : [API ScopedValue, Java SE 25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/ScopedValue.html).

### Appliquer le concept à l’exercice

`TraitementTrace` reçoit un contexte de requête, réduit à un identifiant, et lance une tâche par étape. Chaque étape doit préfixer sa trace par l'identifiant de la requête, sans le recevoir en paramètre.

Le changement par rapport au premier exemple est le passage à d’autres threads. Une tâche soumise à un exécuteur ne reçoit pas automatiquement la liaison du thread appelant. La lambda transporte l’objet `contexte`, puis la tâche établit sa propre liaison avec `ScopedValue.where(CONTEXTE, contexte).call(() -> tracerUne(etape))`. `call()` renvoie le résultat, là où `run()` ne renvoie rien.

Depuis la racine du dépôt :

```
cd exercices/scoped-values
```

**Fichier à modifier** : `src/training/contexte/TraitementTrace.java`.

```
javac -d out src/training/contexte/*.java test/training/contexte/*.java
```

```
java -cp out training.contexte.TraitementTraceTest
```

Au premier lancement :

```
ÉCHEC Lire l'identifiant de la requête dans chaque tâche
OK    Ne laisser aucune liaison après le traitement
1 vérification(s) en échec
```

Chaque tâche rend `absent:...`. La raison mérite d'être comprise avant de coder.

> [!NOTE]
> **Pourquoi le code de départ échoue**
> Une valeur posée dans un `ThreadLocal` appartient au thread qui l'a posée, ici le thread appelant. Les tâches s'exécutent sur d'autres threads, qui ne la voient pas.
>
> **Ce n'est pas propre aux threads virtuels.** Un pool fixe de threads de plateforme produit le même échec avec ce `ThreadLocal` : le contexte est absent parce que le thread est différent, pas parce qu'il est virtuel.
>
> `ScopedValue` ne se propage pas davantage tout seul vers les tâches d'un exécuteur : la liaison est explicite, tâche par tâche. C'est justement ce que l'exercice fait écrire.

La ligne verte est un filet : le code de départ retire son `ThreadLocal` dans un `finally`, et la vérification garantit que la nouvelle version ne laisse pas fuir davantage.

Ce test observe l'absence de contexte dans le thread appelant après un traitement réussi ; il ne teste pas toutes les sorties exceptionnelles ni les tâches filles. L'exemple précédent et le contrat de l'API expliquent la fin de liaison en cas d'exception.

Dans `TraitementTrace.java`, modifiez le champ `CONTEXTE`, l'établissement de la liaison dans la lambda soumise, `contexteLie()` et `tracerUne()`. Retirez `CONTEXTE.set(...)` et le `finally` de nettoyage, en conservant la fermeture de l'exécuteur et les `catch`. La lambda capture la donnée `contexte` ; le `where` exécuté **dans la tâche** crée sa liaison. `call()` renvoie la trace, contrairement à `run()` qui ne renvoie pas de résultat.

**Question de débriefing** : qu'est-ce qu'un `ScopedValue` interdit, qu'un `ThreadLocal` autorisait ?

**Algorithme en français** : remplacer la clé mutable par une `ScopedValue`, capturer le contexte dans la lambda de chaque tâche, établir la liaison dans cette tâche autour de l'appel qui doit la lire, puis supprimer le nettoyage manuel devenu inutile.

**Avant de continuer** : la dernière ligne doit afficher `Scoped Values validé`. Chaque trace retrouve son identifiant, et le thread appelant ne conserve pas de contexte après l'appel.

> [!TIP]
> **Ce qu'il faut retenir**
> - Une liaison rend une valeur accessible aux appels descendants pendant un bloc borné.
> - La clé ne fournit pas de `set()` ; une portée imbriquée peut toutefois établir une liaison temporaire différente.
> - L'objet transporté n'est pas rendu immuable par l'API.
> - Une tâche d'exécuteur ordinaire doit établir explicitement sa liaison.

Source : [JEP 506, Scoped Values](https://openjdk.org/jeps/506).

## Stable Values

**Point d'étape précédent** : répondre aux [questions 6 et 7 du quiz](#quiz), sur les threads virtuels et la fin d'une liaison Scoped Values.

Une Stable Value vise l'initialisation différée d'une donnée qui ne changera plus après sa première création. Le problème est différent de Scoped Values : il ne s'agit pas de propager un contexte pendant une opération, mais de publier une valeur stable créée au premier besoin.

La fonctionnalité reste en preview dans Java 25. Comparez-la à une initialisation immédiate, à un holder statique ou à une synchronisation explicite avant de l'expérimenter.

**À observer** : un service n'a besoin d'un parseur qu'au premier document reçu. Une Stable Value vise à le créer à ce premier usage et à partager ensuite cette valeur initialisée, y compris face à des premiers accès concurrents. Le mécanisme ne rend pas le parseur lui-même immuable. **Avant de continuer** : s'il est toujours nécessaire dès le démarrage, une initialisation immédiate peut être plus simple ; l'expérimentation doit démontrer un bénéfice de l'initialisation différée.

> [!TIP]
> **Ce qu'il faut retenir**
> - Stable Values traite l'initialisation différée et la stabilité après création.
> - Scoped Values traite la transmission descendante d'un contexte borné.
> - Le statut preview impose une expérimentation isolée et supprimable.

Source : [JEP 502, Stable Values](https://openjdk.org/jeps/502).

## Choisir une primitive de concurrence

**Point d'étape précédent** : répondre à la [question 8 du quiz](#quiz), qui distingue Scoped Values et Stable Values.

Associez le problème à la primitive avant de choisir une API : beaucoup d'attentes indépendantes orientent vers les threads virtuels ; un contexte descendant borné vers Scoped Values ; une valeur créée au premier besoin vers Stable Values ; un groupe de tâches filles dont les vies doivent rester liées vers la concurrence structurée.

> [!NOTE]
> **Question : Peut-on combiner ces primitives ?**
> Oui. Elles répondent à des dimensions différentes. Une opération peut utiliser des threads virtuels, partager un contexte par Scoped Values et organiser ses tâches filles dans une portée structurée.

> [!TIP]
> **Ce qu'il faut retenir**
> - Choisir d'abord d'après le problème : attente, contexte, initialisation ou durée de vie d'un groupe de tâches.
> - Plusieurs primitives peuvent coopérer dans une même opération.
> - Leur proximité dans le JDK ne signifie pas qu'elles sont interchangeables.

## Concurrence structurée

La concurrence structurée traite plusieurs tâches filles comme une seule opération : leur création, leur attente, leur échec et leur annulation restent dans une portée commune. Elle évite de laisser des tâches continuer sans propriétaire après l'abandon de l'opération appelante.

Dans Java 25, l'API reste en preview. La formation compare son modèle à l'orchestration manuelle par `Future`, sans en faire une dépendance du socle pratique.

**À observer** : une opération demande en parallèle un prix et un stock. Si le prix échoue et rend la réponse impossible, que devient la recherche de stock ? Avec une orchestration manuelle, il faut décider et coder attente, propagation d'erreur et annulation. Une portée structurée rattache les tâches à l'opération, selon la politique choisie. **Avant de continuer** : l'annulation reste coopérative ; une tâche doit répondre à l'interruption pour terminer rapidement. Ce modèle mérite une expérimentation isolée si la gestion manuelle du groupe devient difficile à fiabiliser.

> [!TIP]
> **Ce qu'il faut retenir**
> - La portée structure la durée de vie des tâches filles.
> - L'échec et l'annulation deviennent des décisions de l'opération globale.
> - Les liaisons de Scoped Values sont héritées dans ce cadre structuré.
> - Le statut preview demande de garder l'expérimentation isolée.

Source : [JEP 505, Structured Concurrency](https://openjdk.org/jeps/505).

# API spécialisées

## Foreign Function & Memory API

FFM permet d'appeler du code natif et de manipuler une mémoire extérieure au tas Java sans écrire de couche JNI pour chaque fonction. Un segment mémoire porte des bornes et une durée de vie ; un descripteur décrit précisément la signature native.

L'API sécurise l'accès mémoire par sa structure, mais elle ne rend pas une bibliothèque native sûre. La signature, la durée de vie, l'alignement et les conventions de la plateforme restent à vérifier.

Depuis la racine du dépôt, lancez d'abord la démonstration sans autorisation explicite pour lire l'avertissement :

```
java demos/FfmStrlenDemo.java
```

Puis relancez avec l'accès natif autorisé :

```
java --enable-native-access=ALL-UNNAMED demos/FfmStrlenDemo.java
```

Dans les deux cas, le résultat attendu est `FFM validée : strlen("Java 25") = 7` ; le premier lancement affiche auparavant les avertissements d'accès natif.

**À observer** : ouvrez `demos/FfmStrlenDemo.java`. L'arène crée la chaîne native, le symbole `strlen` désigne la fonction et le descripteur fixe son contrat d'appel. Le message confirme la longueur de cette chaîne, pas la sûreté de tout code natif. **Avant de continuer** : rendre l'accès natif explicite supprime-t-il le besoin de vérifier signature et durée de vie ? Non.

> [!TIP]
> **Ce qu'il faut retenir**
> - FFM couvre l'appel natif et la mémoire hors tas avec des abstractions Java.
> - Une arène borne la durée de vie des segments qu'elle gère.
> - Le contrat de la bibliothèque native reste une responsabilité de l'intégration.

Source : [JEP 454, Foreign Function & Memory API](https://openjdk.org/jeps/454).

## Class-File API

La Class-File API lit, transforme et écrit les fichiers `.class` avec un modèle standard qui évolue avec le format de la JVM. Elle vise les outils de bytecode, agents, frameworks et analyseurs qui dépendaient auparavant de bibliothèques tierces ou d'API internes.

Elle ne décide pas quelle transformation métier est correcte. Toute réécriture doit être validée sur les classes produites et sur les versions de bytecode ciblées.

Depuis la racine du dépôt :

```
java demos/ClassFileDemo.java
```

Résultat attendu : `Class-File API validée : ClassFileDemo`.

**À observer** : lisez `demos/ClassFileDemo.java` : la démonstration lit les octets de sa propre classe compilée, les analyse et vérifie le nom retrouvé. **Avant de continuer** : cela prouve-t-il qu'une transformation quelconque préserve le comportement ? Non, il faut tester le code transformé et les versions ciblées.

> [!TIP]
> **Ce qu'il faut retenir**
> - L'API standardise la manipulation du format de classe.
> - Son modèle suit l'évolution du class-file.
> - La validité structurelle du bytecode ne prouve pas la validité de la transformation.

Source : [JEP 484, Class-File API](https://openjdk.org/jeps/484).

## API KEM

Une KEM permet à deux parties d'établir un secret partagé : l'une encapsule un secret avec une clé publique, l'autre le décapsule avec la clé privée correspondante. L'API fournit le mécanisme ; le protocole fixe l'algorithme, les paramètres et l'usage du secret.

Depuis la racine du dépôt :

```
java demos/KemDemo.java
```

Résultat attendu : `KEM validé : même secret, message d'encapsulation distinct`.

**À observer** : dans `demos/KemDemo.java`, repérez la paire de clés du destinataire, l'encapsulation avec sa clé publique et la décapsulation avec sa clé privée. Le message d'encapsulation peut être transmis ; le secret partagé est retrouvé séparément. **Avant de continuer** : cela authentifie-t-il à lui seul le destinataire ? Non, l'authentification appartient au protocole.

> [!TIP]
> **Ce qu'il faut retenir**
> - KEM encapsule puis décapsule un secret partagé.
> - L'API ne conçoit pas le protocole cryptographique.
> - Le fournisseur et des vecteurs de test connus doivent être vérifiés.

Source : [JEP 452, Key Encapsulation Mechanism API](https://openjdk.org/jeps/452).

## API KDF

Une KDF dérive des clés distinctes depuis un secret initial, un sel et un contexte d'usage. Deux parties possédant les mêmes entrées reproduisent le même résultat ; changer le contexte doit produire une autre clé.

Depuis la racine du dépôt, exécutez la démonstration :

```
java demos/KdfDemo.java
```

Résultat attendu : `KDF validée : résultat reproductible et séparé par contexte`.

**À observer** : dans `demos/KdfDemo.java`, les mêmes entrées reproduisent la même clé ; changer le contexte d'usage produit une autre dérivation. **Avant de continuer** : la réussite de cet exemple suffit-elle à valider un protocole ? Non. Répondez maintenant à la [question 10 du quiz](#quiz).

> [!TIP]
> **Ce qu'il faut retenir**
> - Une KDF sépare les clés selon leur usage à partir d'un même secret.
> - Le protocole fixe l'algorithme, le sel, le contexte et la longueur.
> - Une API cryptographique ne remplace ni le protocole ni les vecteurs de test.

Source : [JEP 510, Key Derivation Function API](https://openjdk.org/jeps/510).

# Quiz

Répondez sans regarder les [réponses du quiz](#réponses-du-quiz).

| Moment du parcours | Questions à utiliser |
|---|---|
| Après les statuts et les String Templates | 1 |
| Après langage, collections et documentation | 2 à 5 |
| Après Vector API | 9 |
| Après threads virtuels et Scoped Values | 6 et 7 |
| Après Stable Values | 8 |
| Après KEM et KDF | 10 |

1. Vous lisez qu'une fonctionnalité est en preview dans la version que vous utilisez. Qu'avez-vous le droit d'en conclure pour un projet en production ?
2. Pourquoi le `switch` de l'étape 3 compile-t-il sans `default` ?
3. Un collègue ajoute un `default -> throw new IllegalStateException()` à un `switch` sur une hiérarchie `sealed`, « par sécurité ». Que perdez-vous ?
4. Pourquoi le cas `Square square when square.side() == 0` doit-il précéder le cas `Square square` ?
5. Quelle différence sépare une vue `reversed()` d'une copie réalisée avec `List.copyOf` ?
6. Pourquoi les threads virtuels n'accélèrent-ils pas un calcul qui occupe continuellement le CPU ?
7. Que devient une liaison Scoped Values à la sortie de `run()` ou `call()` ?
8. Quelle différence de problème sépare Scoped Values et Stable Values ?
9. Pourquoi l'exemple de Vector API conserve-t-il une boucle scalaire de fin ?
10. Que reste-t-il à décider autour d'une API KEM ou KDF ?

# Corrigés

## Réponses du quiz

[Retour aux questions](#quiz)

1. Son API ou sa syntaxe peut encore changer, voire être retirée. Le statut ne garantit pas une stabilité pour la production. Dans ce parcours, on garde donc l'expérimentation isolée ; ce choix n'est pas une interdiction technique d'exécuter une preview en production.
2. Parce que `Shape` est `sealed`. Le compilateur connaît la liste complète des types autorisés par le `permits`, il vérifie que le `switch` les traite tous, et il ne réclame donc pas de cas par défaut.
3. Vous perdez l'erreur de compilation lors du prochain ajout de forme. Le `switch` restera valide aux yeux du compilateur, le cas nouveau tombera dans le `default`, et l'anomalie se déplacera de la compilation vers l'exécution.
4. Parce que les `case` sont évalués de haut en bas et que `Square square` recouvre déjà le carré de côté nul. Placé après, le cas gardé ne serait jamais atteint.
5. `reversed()` est une vue reliée à la collection d'origine ; `List.copyOf` produit une liste non modifiable dont le contenu ne suit plus les changements de cette collection. Les objets éléments ne sont pas copiés en profondeur.
6. Un thread virtuel qui calcule ne se détache pas de son porteur. La capacité reste bornée par les cœurs disponibles.
7. La liaison précédente est restaurée ; en l'absence de liaison extérieure, la clé redevient non liée, y compris après une exception.
8. Scoped Values propage un contexte pendant une opération bornée ; Stable Values initialise une donnée au premier besoin puis la garde stable.
9. La longueur du tableau n'est pas toujours un multiple de la largeur vectorielle préférée. Dans cet exemple sans masque, la boucle scalaire traite les éléments restants. Une autre version pourrait utiliser des opérations masquées.
10. Le protocole doit encore fixer l'algorithme, les paramètres, la séparation des usages, le fournisseur et les vecteurs de test.

## Pattern matching pour `switch`

<a id="corrige-pattern-matching-switch"></a>

[Retour à la séquence](#sequence-pattern-matching-switch) · [Solution complète dans le dépôt](solutions/pattern-matching-switch/)

**Emplacement** : `src/training/shapes/ShapeDescriber.java`, depuis `exercices/pattern-matching-switch`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/pattern-matching-switch`, puis utilisez les mêmes commandes.


```java
package training.shapes;

final class ShapeDescriber {

    String describe(Shape shape) {
        return switch (shape) {
            case Square square when square.side() == 0 -> "carré dégénéré";
            case Circle circle -> "cercle de rayon " + circle.radius();
            case Square square -> "carré de côté " + square.side();
            case Rectangle rectangle ->
                    "rectangle " + rectangle.width() + " x " + rectangle.height();
            case Triangle triangle -> "triangle de base " + triangle.base();
        };
    }
}
```

Ce corrigé est aussi dans `solutions/pattern-matching-switch/`, avec le reste des solutions.

## Record patterns

<a id="corrige-record-patterns"></a>

[Retour à la séquence](#sequence-record-patterns) · [Solution complète dans le dépôt](solutions/record-patterns/)

**Emplacement** : `src/training/geometry/SegmentDescriber.java`, depuis `exercices/record-patterns`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/record-patterns`, puis utilisez les mêmes commandes.

L'ordre des `case` est le piège : un segment réduit à un point est aussi horizontal **et** vertical. Son cas doit venir en premier, sinon il est capté avant d'être testé.

```java
String describe(Object value) {
    return switch (value) {
        case Segment(Point(int x1, int y1), Point(int x2, int y2))
                when x1 == x2 && y1 == y2 -> "point unique";
        case Segment(Point(int x1, int y1), Point(int x2, int y2))
                when y1 == y2 -> "horizontal";
        case Segment(Point(int x1, int y1), Point(int x2, int y2))
                when x1 == x2 -> "vertical";
        case Segment segment -> "oblique";
        default -> "pas un segment";
    };
}
```

`default` couvre ici les objets qui ne sont pas des segments. Une branche générale est nécessaire à la couverture de `Object`. Sans `case null`, une valeur `null` provoque une `NullPointerException`.

Le code de départ rendait `pas un segment` pour `null` ; le test fourni ne couvre pas cette entrée. Pour conserver aussi ce comportement, ajoutez `case null -> "pas un segment";` avant les autres branches. Le corrigé du dépôt illustre le traitement des entrées non nulles testées.

Les branches `horizontal` et `vertical` déclarent quatre coordonnées mais n'en utilisent que deux. Le sujet suivant, les variables et patterns anonymes, permet d'ignorer les coordonnées inutilisées avec `_`.

## Collections séquencées

<a id="corrige-collections-sequencees"></a>

[Retour à la séquence](#sequence-collections-sequencees) · [Solution complète dans le dépôt](solutions/collections-sequencees/)

**Emplacement** : `src/training/sequenced/WaitingLine.java`, depuis `exercices/collections-sequencees`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/collections-sequencees`, puis utilisez les mêmes commandes.

Le geste décisif est le premier : changer le type déclaré du champ. `LinkedHashSet` implémente `SequencedSet` depuis Java 21, mais tant que le champ est vu comme un `Set`, aucune de ces méthodes n'est visible.

Remplacez aussi `import java.util.Set;` par `import java.util.SequencedSet;`. Le bloc suivant remplace le champ et les trois méthodes demandées dans `WaitingLine`.

```java
private final SequencedSet<String> tickets = new LinkedHashSet<>();

void pushToFront(String ticket) {
    tickets.addFirst(ticket);
}

String serveNext() {
    return tickets.removeFirst();
}

List<String> newestFirst() {
    return List.copyOf(tickets.reversed());
}
```

Et le bonus, deux boucles qui disparaissent :

```java
String first() {
    return tickets.getFirst();
}

String last() {
    return tickets.getLast();
}
```

`List.copyOf` est là pour figer le résultat : `reversed()` rend une vue, qui suivrait les modifications ultérieures de la file.

Le bonus change aussi l'exception sur une file vide : `getFirst()` et `getLast()` lèvent `NoSuchElementException`, alors que les méthodes initiales levaient `IllegalStateException`. Le test fourni ne couvre pas ce contrat. Si vous devez conserver l'exception d'origine, ajoutez une vérification `tickets.isEmpty()` qui lève `IllegalStateException("File vide")` avant l'accès.

## Javadoc Markdown

<a id="corrige-javadoc-markdown"></a>

[Retour à la séquence](#sequence-javadoc-markdown) · [Solution complète dans le dépôt](solutions/javadoc-markdown/)

**Emplacement** : `src/training/docs/Temperature.java`, depuis `exercices/javadoc-markdown`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/javadoc-markdown`, puis utilisez les mêmes commandes.

```java
/// Convertit des températures.
///
/// Les échelles gérées sont les suivantes :
///
/// - Celsius
/// - Fahrenheit
///
/// Voir [la page Wikipedia](https://fr.wikipedia.org/wiki/Degr%C3%A9_Celsius)
/// pour les définitions.
public final class Temperature {

    /// Convertit des degrés `Celsius` en `Fahrenheit`.
    ///
    /// @param celsius la température en degrés Celsius
    /// @return la température en degrés Fahrenheit
    public double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
```

Les balises `@param` et `@return` ne changent pas : le Markdown remplace le HTML, pas les tags Javadoc.

Et le piège : une liste Markdown dans un commentaire `/** */` n'est pas interprétée, elle sort telle quelle dans le HTML.

## Fichiers source compacts

<a id="corrige-fichiers-source-compacts"></a>

[Retour à la séquence](#sequence-fichiers-source-compacts) · [Solution complète dans le dépôt](solutions/fichiers-source-compacts/)

**Emplacement** : `Bienvenue.java`, depuis `exercices/fichiers-source-compacts`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/fichiers-source-compacts`, puis utilisez les mêmes commandes.

```java
void main() {
    IO.println("Bonjour depuis un fichier source compact");
}
```

Cette forme est finale en Java 25 ; des formes antérieures ont existé en preview. La classe est implicite et `main` est une méthode d'instance sans argument. `IO` appartient au package `java.lang`, importé implicitement dans tout fichier source Java. Les fichiers compacts importent en plus implicitement le module `java.base` ; cela rend par exemple `List` utilisable sans import explicite. Les méthodes de `IO` ne sont pas importées statiquement : on écrit bien `IO.println(...)`.

## Imports de modules

<a id="corrige-imports-de-modules"></a>

[Retour à la séquence](#sequence-imports-de-modules) · [Solution complète dans le dépôt](solutions/imports-de-modules/)

**Emplacement** : `src/training/modules/Inventaire.java`, depuis `exercices/imports-de-modules`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/imports-de-modules`, puis utilisez les mêmes commandes.

Une ligne, à placer après le `package` :

```java
import module java.base;
```

`java.base` exporte notamment `java.util`, `java.io`, `java.nio.file` et `java.time`. Les types publics des packages exportés accessibles au code appelant deviennent utilisables par leur nom simple.

`import module M` est un import à la demande des types publics exportés accessibles, incluant ceux des modules lus transitivement via M. Le confort a une contrepartie : si l'on importe `java.base` et `java.desktop`, le nom simple `List` devient ambigu entre `java.util.List` et `java.awt.List`. Un import explicite `import java.util.List;` tranche. Dans cet exercice, gardez seulement `import module java.base;`.

## Corps de constructeurs flexibles

<a id="corrige-constructeurs-flexibles"></a>

[Retour à la séquence](#sequence-constructeurs-flexibles) · [Solution complète dans le dépôt](solutions/constructeurs-flexibles/)

**Emplacement** : `src/training/sensors/CapteurNomme.java`, depuis `exercices/constructeurs-flexibles`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/constructeurs-flexibles`, puis utilisez les mêmes commandes.

```java
CapteurNomme(String nom) {
    if (nom == null || nom.isBlank()) {
        throw new IllegalArgumentException("nom obligatoire");
    }
    this.nom = nom;
    super();
}
```

Le champ est `final`, et il est pourtant affecté avant `super()` : c'est permis, et c'est ce qui garantit que la base ne verra jamais sa valeur par défaut.

## Stream Gatherers

<a id="corrige-stream-gatherers"></a>

[Retour à la séquence](#sequence-stream-gatherers) · [Solution complète dans le dépôt](solutions/stream-gatherers/)

**Emplacement** : `src/training/releves/SerieDeReleves.java`, depuis `exercices/stream-gatherers`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/stream-gatherers`, puis utilisez les mêmes commandes.

L'état est créé par l'initializer, et il appartient à **une évaluation du pipeline**, jamais au code appelant. C'est ce qui rend le gatherer réutilisable sans partager d'état entre deux appels.

Ajoutez `import java.util.Set;` et `import java.util.HashSet;` aux imports existants. Remplacez uniquement la méthode `premierPar` par le bloc suivant.

```java
static <T, K> Gatherer<T, ?, T> premierPar(Function<? super T, ? extends K> cle) {
    final class Etat {
        private final Set<K> vues = new HashSet<>();
    }

    return Gatherer.ofSequential(
            Etat::new,
            (etat, element, aval) -> {
                if (etat.vues.add(cle.apply(element))) {
                    return aval.push(element);
                }
                return true;
            }
    );
}
```

Le `return true` du cas dupliqué ne veut pas dire « émis », il veut dire « continue » : l'intégrateur rend `false` uniquement pour demander l'arrêt anticipé du flux.

**Réponse au débriefing** : `ofSequential` convient à cette implémentation, qui mémorise les clés et transmet les relevés dans l'ordre de lecture. Une stratégie parallèle demanderait un état et un **combiner** adaptés à la règle du premier relevé ; fusionner simplement les ensembles de clés ne suffit pas à corriger des relevés déjà transmis. Préserver l'ordre n'interdit pas en soi le parallélisme. Voir le [contrat de Gatherer](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/stream/Gatherer.html).

## Threads virtuels

<a id="corrige-threads-virtuels"></a>

[Retour à la séquence](#sequence-threads-virtuels) · [Solution complète dans le dépôt](solutions/threads-virtuels/)

**Emplacement** : `src/training/taches/LotDeTaches.java`, depuis `exercices/threads-virtuels`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/threads-virtuels`, puis utilisez les mêmes commandes.

Une ligne :

```java
try (var executeur = Executors.newVirtualThreadPerTaskExecutor()) {
```

à la place de :

```java
try (var executeur = Executors.newFixedThreadPool(4)) {
```

Le test doit désormais rendre tous les résultats et passer sous son seuil d'une seconde. La durée exacte dépend du poste et de sa charge ; ce seuil pédagogique n'est pas un benchmark de production. L'ordre des résultats vient de l'ordre de lecture des `Future`, pas de l'ordre d'exécution.

**Réponse au débriefing** : les mêmes 1000 tâches occupées à **calculer** pendant 10 ms ne gagneraient rien. Un thread virtuel qui calcule ne libère pas son thread porteur, puisqu'il ne se bloque jamais. Le plancher devient le nombre de cœurs disponibles, et créer 1000 threads virtuels ne fait qu'ajouter de l'ordonnancement par-dessus. La bascule ne rend rien plus rapide, elle **cesse d'immobiliser un thread OS pendant une attente**.

## Scoped Values

<a id="corrige-scoped-values"></a>

[Retour à la séquence](#sequence-scoped-values) · [Solution complète dans le dépôt](solutions/scoped-values/)

**Emplacement** : `src/training/contexte/TraitementTrace.java`, depuis `exercices/scoped-values`. Les fragments ci-dessous remplacent les éléments nommés ; conservez le reste du fichier. Pour relancer la vérification, utilisez les commandes de la séquence. Pour tester directement la solution fournie, revenez à la racine, entrez dans `solutions/scoped-values`, puis utilisez les mêmes commandes.

Le champ change de nature, et la liaison devient explicite autour de chaque tâche :

```java
private static final ScopedValue<ContexteRequete> CONTEXTE = ScopedValue.newInstance();

encours.add(executeur.submit(() ->
        ScopedValue.where(CONTEXTE, contexte).call(() -> tracerUne(etape))));
```

Le `finally` qui appelait `CONTEXTE.remove()` disparaît : il n'y a plus rien à nettoyer, la liaison meurt avec le bloc `call()`, y compris si celui-ci lève.

La sonde de fuite change de méthode :

```java
boolean contexteLie() {
    return CONTEXTE.isBound();
}
```

Et `tracerUne` n'a plus de cas d'absence à traiter, puisqu'un `get()` hors liaison lève au lieu de rendre `null` :

```java
private String tracerUne(String etape) {
    ContexteRequete contexte = CONTEXTE.get();
    return contexte.identifiant() + ":" + etape;
}
```

**Réponse au débriefing** : le code ayant accès au `ThreadLocal` pouvait remplacer sa valeur par `set()`, et devait prévoir son retrait par `remove()`. `ScopedValue` ne propose pas de `set()` : il établit une liaison pour la durée de `call()` ou `run()`, puis restaure automatiquement l’état précédent. Une portée imbriquée peut établir une autre liaison temporaire ; l’objet transporté, lui, n’est pas rendu immuable. Le test d’absence de liaison reste utile pour vérifier le contrat du traitement.
