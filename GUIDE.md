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

## Les trois commandes Git dont vous aurez besoin

Cette formation porte sur Java, pas sur Git. Trois commandes suffisent, les voici toutes ensemble une fois pour toutes.

Récupérer le code, une seule fois :

```
git clone https://github.com/jcdominguez/java-21-25-labs.git
```

Mettre votre travail de côté, pour le retrouver ensuite avec `git stash pop` :

```
git stash
```

Repartir de l'état de référence, en **jetant** ce que vous avez tapé depuis :

```
git restore .
```

> [!WARNING]
> **`git restore .` ne demande pas confirmation**
> Cette commande écrase vos fichiers avec la version enregistrée. Tout ce que vous avez modifié sans l'avoir mis de côté est perdu, sans corbeille et sans retour possible. Utilisez `git stash` si vous voulez garder votre travail.

Vous n'aurez pas besoin de changer de branche : chaque sujet a **son propre dossier**, et son corrigé vous attend dans `solutions/`.

## Le rituel des exercices

Chaque dossier d'exercice contient `src/` et `test/`. On compile les deux ensemble, puis on lance le test.

Placez-vous dans le dossier du sujet, par exemple :

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
> Ne cherchez pas la consigne ailleurs. Chaque ligne `ÉCHEC` nomme ce qui reste à faire, et le sujet est terminé quand la dernière ligne affiche `exercice validé`. Relancez le test aussi souvent que vous voulez, il ne coûte rien.

Deux sujets n'ont pas de test, et c'est normal : `imports-de-modules`, dont le juge est le **compilateur**, et `javadoc-markdown`, dont le juge est la **page produite**.

## Ouvrir le projet dans IntelliJ

Le dépôt n'a ni Maven ni Gradle. IntelliJ n'a donc aucun fichier de configuration à lire, et ne devine que partiellement.

**Ouvrez le dossier de l'exercice, pas la racine du dépôt.** Chaque sujet a sa copie du code dans `exercices/` et dans `solutions/` : en ouvrant la racine, IntelliJ voit des classes en double et ne sait plus laquelle exécuter.

Trois réglages à l'ouverture :

1. `Project Structure` (Cmd+;), onglet `Project` : `SDK` sur le JDK 25 et `Language level` sur 25. Sans cela, les record patterns sont soulignés en rouge alors que le code est correct.
2. Clic droit sur `src`, `Mark Directory as` → `Sources Root`.
3. Clic droit sur `test`, `Mark Directory as` → `Test Sources Root`.

> [!WARNING]
> **Aucune flèche verte sur les classes de `src`, et c'est voulu**
> Les classes de `src` sont déclarées `final class`, sans `public` et sans `main`. Le seul point d'entrée est la classe de test. Ce n'est pas un défaut de configuration.

Configuration d'exécution : `Application`, classe principale celle du test, aucune option VM.

## Ce que vous saurez faire

À l'issue des deux jours, vous pourrez adopter les fonctionnalités finales pertinentes, moderniser la concurrence, les collections et les pipelines, isoler les previews et les incubateurs, et argumenter chaque choix avec une validation observable.

## Lire le statut avant la syntaxe

Une nouveauté Java n'arrive pas d'un coup dans le langage. Elle traverse des étapes, et son étape décide de ce que vous avez le droit d'en faire.

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

C'est la seule preview en cinq ans de process à ne jamais être devenue finale. Sources : [JEP 430](https://openjdk.org/jeps/430), [JEP 459](https://openjdk.org/jeps/459), [JEP 465, withdrawn](https://openjdk.org/jeps/465).

> [!NOTE]
> **Question : Pourquoi le programme l'annonce-t-il alors ?**
> Parce qu'un programme de formation se construit à partir des annonces de preview, et que celle-ci a été retirée après. C'est exactement le risque que la grille des statuts sert à éviter : une preview n'est pas une promesse. Une fonctionnalité annoncée en preview peut disparaître sans jamais atteindre une version finale, et c'est ce qui s'est produit ici.

## Interpoler aujourd'hui

Trois écritures suffisent. Copiez ce bloc dans un fichier de test si vous voulez les voir tourner :

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

La règle de choix : le `+` par défaut, `String.format` quand le même gabarit se réutilise à plusieurs endroits, `StringBuilder` quand vous assemblez la chaîne morceau par morceau dans une boucle.

> [!NOTE]
> **Question : Le `+` est-il lent ?**
> Non, pas dans le cas courant. Réécrire une concaténation simple en `StringBuilder` « pour la performance » n'apporte rien et rend le code moins lisible. Le `StringBuilder` se justifie quand le nombre de morceaux n'est pas connu à l'écriture, typiquement une boucle.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le statut d'une fonctionnalité se lit avant sa syntaxe : final, preview, incubateur.
> - Une preview peut être retirée, les String Templates en sont le seul exemple à ce jour.
> - Pour interpoler en Java 25 : `+` par défaut, `String.format` pour un gabarit réutilisé, `StringBuilder` pour un assemblage en boucle.

# Langage, collections et documentation

Le premier exercice est détaillé pas à pas. Les suivants gardent le même rituel : comprendre le problème, prédire le résultat, modifier le minimum nécessaire, puis relancer le juge indiqué.

## Pattern matching pour `switch`

Ce sujet déroule le rituel en entier. Les exercices suivants se travaillent de la même façon ; leurs explications, leurs questions de blocage et leurs corrigés restent dans ce Guide pour permettre de les rejouer seul.

Dossier : `exercices/pattern-matching-switch`.

### Étape 1, lire le test avant le code

Compilez et lancez, comme montré plus haut. Vous obtenez ceci :

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

`sealed` arrête la liste : aucune autre classe ne peut implémenter `Shape`. Retenez ce mot, c'est lui qui rend possible tout ce qui suit.

> [!NOTE]
> **`sealed` n'est pas au programme**
> Il date de Java 17, et sert ici de **terrain**. Le sujet des deux jours, c'est ce que le `switch` sait en faire depuis Java 21.

### Étape 3, du `if instanceof` au `switch`

Ouvrez `ShapeDescriber.java`. Le code de départ est une chaîne de tests de type qui se termine par un repli :

```java
if (shape instanceof Circle circle) {
    return "cercle de rayon " + circle.radius();
}
// ... et à la fin :
return "forme inconnue";
```

C'est ce repli le défaut : le triangle est oublié, et personne n'est prévenu. Le test le constate, la compilation non.

Réécrivez avec un `switch` :

```java
return switch (shape) {
    case Circle circle -> "cercle de rayon " + circle.radius();
    case Square square -> "carré de côté " + square.side();
    case Rectangle rectangle ->
            "rectangle " + rectangle.width() + " x " + rectangle.height();
    case Triangle triangle -> "triangle de base " + triangle.base();
};
```

Relancez : quatre lignes vertes.

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

C'est le comportement recherché. Maintenant ajoutez un `default -> "forme inconnue"` : la compilation repasse au vert, et vous venez d'éteindre le filet. Le prochain oubli ne se verra plus qu'à l'exécution.

Retirez le `default` et `Ellipse`, ou repartez de l'état de référence :

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

`Segment` contient deux `Point`, et `SegmentDescriber.describe` qualifie un segment à partir de ses coordonnées. Deux records, une classe, rien d'autre à connaître.

Dossier : `exercices/record-patterns`.

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

Récrivez les deux branches du corrigé, recompilez et vérifiez que les résultats restent identiques. Cette manipulation se juge à la compilation et à la relecture.

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

Le point à vérifier est la conversion exacte. Une valeur `int` peut être représentée exactement par un `double`, tandis qu'un `long` suffisamment grand peut perdre de la précision. Un pattern ne doit réussir que lorsque la conversion conserve la valeur.

```java
static String classifier(double valeur) {
    return switch (valeur) {
        case int entier -> "entier exact : " + entier;
        default -> "valeur décimale";
    };
}
```

Ce code nécessite les options de preview correspondant au JDK 25. La formation le lit comme une perspective et ne l'intègre pas aux exercices sans option.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le pattern teste qu'une conversion primitive est exacte avant de l'appliquer.
> - La fonctionnalité est en preview dans Java 25.
> - Son statut impose de l'isoler d'un socle de production.

Source : [JEP 507, Primitive Types in Patterns](https://openjdk.org/jeps/507).

## Collections séquencées

`WaitingLine` est une file d'attente de tickets. Les tickets sont uniques et conservés dans leur ordre d'arrivée, d'où le `LinkedHashSet`. C'est tout ce qu'il y a à savoir pour commencer.

Dossier : `exercices/collections-sequencees`.

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

Une indication, et elle porte tout l'exercice : le champ est déclaré `Set<String>`, et `Set` ne porte aucune notion d'ordre de rencontre. Cherchez la méthode sur le bon type avant de l'écrire à la main.

Deux méthodes existantes, `first()` et `last()`, montrent ce qu'il fallait écrire avant Java 21. Les simplifier est un bonus, que le test ne vérifie pas.

**Algorithme en français** : exposer le champ comme une collection séquencée, ajouter en tête avec l'opération dédiée, retirer le premier élément, puis copier la vue inversée dans une liste stable.

> [!TIP]
> **Ce qu'il faut retenir**
> - Le type déclaré décide des opérations visibles.
> - Les interfaces séquencées unifient premier élément, dernier élément et ordre inverse.
> - `reversed()` rend une vue ; une copie est utile lorsque le résultat doit rester indépendant.

Source : [JEP 431, Sequenced Collections](https://openjdk.org/jeps/431).

## Javadoc Markdown

`Temperature` est documentée en Javadoc classique : `<p>`, `<ul>`, `<li>`, `<a href>` et `{@code}`. La récrire en commentaires `///` et en Markdown.

Dossier : `exercices/javadoc-markdown`. Générez la documentation **avant** de toucher au fichier :

```
javadoc -d apidocs src/training/docs/Temperature.java
```

Ouvrez `apidocs/training/docs/Temperature.html`, puis récrivez les commentaires et régénérez.

> [!WARNING]
> **Ici le résultat ne change pas, et c'est la leçon**
> La page produite est rigoureusement identique avant et après : mêmes `<ul>`, mêmes `<li>`, même `<code>`. Ce qui change est la **lisibilité de la source**, pas la sortie. Aucun test ne peut donc juger cet exercice, seule la relecture le peut.

Une fois la conversion faite, essayez le piège : remettez une liste Markdown dans un commentaire `/** */`, régénérez, et regardez le HTML.

**Algorithme en français** : produire d'abord la référence HTML, convertir chaque commentaire `/** */` en `///`, remplacer les balises HTML par leur équivalent Markdown, régénérer, puis comparer les deux pages.

> [!TIP]
> **Ce qu'il faut retenir**
> - `///` active le Markdown dans les commentaires de documentation.
> - Les tags Javadoc comme `@param` et `@return` restent disponibles.
> - Le bénéfice se mesure dans la source ; le HTML produit doit conserver le sens.

Source : [JEP 467, Markdown Documentation Comments](https://openjdk.org/jeps/467).

## Fichiers source compacts

`Bienvenue.java` ne contient qu'un commentaire. Écrivez le programme qui affiche exactement `Bonjour depuis un fichier source compact`, sous trois contraintes : aucune classe déclarée, pas de `main` statique ni de paramètre `String[] args`, aucun `import`.

Dossier : `exercices/fichiers-source-compacts`. Il se lance directement, sans compilation :

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

`Inventaire` utilise `List`, `ArrayList`, `Map` et `HashMap`, et ses imports ont été retirés. Rétablissez-les en **une seule déclaration**, sans nommer aucun type.

Dossier : `exercices/imports-de-modules`.

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

C'est tout l'intérêt de cet exercice : le juge n'est pas un test, c'est le **compilateur**. Une fois la déclaration écrite, lancez :

```
java -cp out training.modules.InventaireTest
```

**Algorithme en français** : placer `import module java.base;` après le `package`, recompiler, puis exécuter le test. Si un nom devient ambigu, ajouter un import de type explicite pour trancher.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un import de module rend visibles les types publics de tous les packages exportés par ce module.
> - Il porte sur un module, pas sur un package.
> - Le gain de concision doit être comparé au risque d'ambiguïté des noms.

Source : [JEP 511, Module Import Declarations](https://openjdk.org/jeps/511).

## Corps de constructeurs flexibles

`Capteur` est une classe abstraite dont le constructeur appelle `etiquette()`, redéfinie par ses sous-classes : il observe donc un objet encore en construction. `CapteurNomme` appelle `super()` en première instruction, avec deux conséquences.

Dossier : `exercices/constructeurs-flexibles`.

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

**Algorithme en français** : refuser d'abord le nom invalide, affecter ensuite le champ `final`, puis appeler `super()`. Relancer le test pour vérifier la valeur observée par la base et l'absence de construction en cas d'erreur.

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

## Stream Gatherers

`SerieDeReleves` traite une liste de relevés, chacun portant le nom d'un capteur et une valeur. C'est tout le domaine.

Dossier : `exercices/stream-gatherers`.

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

La ligne verte n'est pas un cadeau, c'est un filet : elle utilise `windowFixed`, fourni par le JDK, et garantit que votre modification ne casse pas le reste du pipeline. Elle montre au passage que la dernière fenêtre incomplète est conservée, et non jetée.

**Question de débriefing** : pourquoi ce gatherer est-il construit avec `ofSequential`, et que faudrait-il fournir de plus pour qu'il supporte un flux parallèle ?

**Algorithme en français** : créer un ensemble de clés pour une évaluation du pipeline ; pour chaque relevé, transmettre le premier portant une clé nouvelle et ignorer les suivants ; continuer jusqu'à la fin du flux.

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

Une boucle scalaire doit traiter la fin du tableau lorsque sa taille n'est pas un multiple du nombre de lanes. L'espèce préférée dépend de la machine ; ne figez donc pas une largeur supposée.

> [!NOTE]
> **Question : Pourquoi ne pas laisser uniquement le JIT vectoriser une boucle ordinaire ?**
> L'auto-vectorisation reste utile, mais elle n'offre pas toujours un contrôle prévisible sur les opérations produites. L'API explicite le calcul vectoriel ; seule une mesure sur la charge réelle justifie son adoption.

> [!TIP]
> **Ce qu'il faut retenir**
> - Un vecteur traite plusieurs valeurs de même type en une opération.
> - La largeur préférée dépend du processeur.
> - La Vector API reste en incubation dans Java 25.
> - Une mesure et une boucle scalaire de fin restent nécessaires.

Source : [JEP 508, Vector API](https://openjdk.org/jeps/508).

# JVM : performances et observabilité

## ZGC générationnel

La plupart des objets meurent jeunes. Le ZGC générationnel sépare les objets récents des objets plus anciens afin de concentrer plus souvent le travail sur la jeune génération. Il est devenu le mode par défaut de ZGC, puis le mode non générationnel a été retiré.

La décision se prend en observant les pauses, le débit et la mémoire de l'application avant et après, avec la même charge.

Vérifiez que le JDK sélectionne ZGC :

```
java -XX:+UseZGC -Xlog:gc -version
```

La sortie doit contenir `Using The Z Garbage Collector`. Sous Java 25, aucune option supplémentaire ne sélectionne le mode générationnel.

> [!TIP]
> **Ce qu'il faut retenir**
> - ZGC vise des pauses très faibles sur des tas importants.
> - Le mode générationnel exploite la mortalité rapide de nombreux objets.
> - Une migration de GC se valide avec les objectifs de service et des mesures comparables.

Sources : [JEP 439](https://openjdk.org/jeps/439), [JEP 474](https://openjdk.org/jeps/474) et [JEP 490](https://openjdk.org/jeps/490).

## AOT HotSpot

HotSpot apprend normalement au démarrage quelles classes charger et quelles méthodes optimiser. Les évolutions AOT de Java 25 permettent d'enregistrer une partie de cet apprentissage lors d'un lancement d'entraînement, puis de la réutiliser au démarrage suivant.

Le profil d'entraînement doit représenter le démarrage réel. Un cache construit sur un parcours incomplet peut apporter peu ; il ne remplace ni les tests ni la mesure du temps de démarrage.

Le trajet est : `entraînement` → `cache AOT produit par HotSpot` → `chargement lors des lancements suivants` → `JIT toujours actif`. Les commandes suivantes montrent la forme du mécanisme avec une application exemple ; adaptez le classpath et la classe principale à l'application mesurée.

Produire le cache après un lancement d'entraînement représentatif :

```
java -XX:AOTCacheOutput=app.aot -cp app.jar com.example.App
```

Charger ce cache :

```
java -XX:AOTCache=app.aot -cp app.jar com.example.App
```

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

> [!TIP]
> **Ce qu'il faut retenir**
> - Le gain porte sur le coût fixe de chaque objet.
> - L'impact cumulé devient visible quand les petits objets sont très nombreux.
> - Java 25 finalise cette évolution, mais son activation et son intérêt se vérifient sur l'environnement cible.

Source : [JEP 519, Compact Object Headers](https://openjdk.org/jeps/519).

## Java Flight Recorder

Java Flight Recorder enregistre des événements de la JVM et de l'application avec un coût conçu pour l'observation en production. Java 25 enrichit notamment le profilage du temps CPU, l'échantillonnage coopératif et le traçage ciblé de méthodes.

Partez d'une question : où part le temps CPU, quelle méthode dépasse une durée, ou quel blocage explique une latence ? Enregistrez ensuite les événements adaptés et confrontez l'hypothèse aux données.

Démarrer un enregistrement de 30 secondes :

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

> [!TIP]
> **Ce qu'il faut retenir**
> - JFR sert à enregistrer avant d'expliquer.
> - Un événement n'est utile que relié à une question de diagnostic.
> - Les nouveaux événements améliorent l'observation ; ils ne remplacent pas un protocole de mesure.

Sources : [JEP 509](https://openjdk.org/jeps/509), [JEP 518](https://openjdk.org/jeps/518) et [JEP 520](https://openjdk.org/jeps/520).

# Concurrence

## Threads virtuels

`LotDeTaches` reçoit une liste d'identifiants et exécute une tâche par identifiant. Chaque tâche attend 10 millisecondes, comme le ferait un appel réseau, puis rend son résultat.

Dossier : `exercices/threads-virtuels`.

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

Le point à remarquer avant d'écrire quoi que ce soit : **rien dans `LotDeTaches` ne dit quel type de thread a servi**. Aucun compteur, aucun `isVirtual()`, aucune instrumentation. Le juge est la durée mesurée par le test autour de l'appel. Un pool de quatre threads sérialise 1000 attentes de 10 ms en quatre files, soit environ 2,5 secondes de plancher ; mille threads virtuels attendent tous en même temps.

Un seul geste est demandé, et il ne touche ni à la méthode qui attend, ni au corps des tâches, ni au test.

**Question de débriefing** : la même bascule appliquée à 1000 calculs de 10 millisecondes de CPU, et non d'attente, ne gagnerait rien. Pourquoi ?

**Algorithme en français** : remplacer le pool fixe par un exécuteur créant un thread virtuel par tâche, conserver la collecte des `Future` dans le même ordre, puis comparer la durée mesurée.

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

Les porteurs sont peu nombreux (par défaut, autant que de CPU). Quelques dizaines de tâches épinglées suffisaient à saturer le pool : plus aucune requête n'était servie, alors que les tâches ne faisaient qu'attendre.

La JEP 491 s'ouvre sur un exemple de ce type, que voici détaillé :

```java
synchronized byte[] getData() throws IOException {
    byte[] buf = new byte[2048];
    int n = socket.getInputStream().read(buf);  // attend le réseau, verrou tenu
    return Arrays.copyOf(buf, n);
}
```

Le verrou protège un état partagé, la lecture attend une réponse distante. Avant Java 24, ce porteur gelait pendant l'attente. Depuis Java 24 ([JEP 491](https://openjdk.org/jeps/491)), le thread virtuel se détache comme pour n'importe quel autre blocage, et le porteur sert une autre requête. `Object.wait()` se détache aussi. Le pinning qui reste se produit quand le code se bloque sous une frame native (JNI, FFM) ; l'événement JFR `jdk.VirtualThreadPinned` le signale.

> [!NOTE]
> **Question : Faut-il remplacer tous les `synchronized` par `ReentrantLock` avant de migrer ?**
> Non. C'était la recommandation d'avant Java 24, quand le `synchronized` épinglait ; elle est obsolète. Le but affiché de JEP 491 est que le code existant passe aux threads virtuels **sans** renoncer au `synchronized`. Le code déjà réécrit en `ReentrantLock` se garde, pas de retour en arrière.

> [!NOTE]
> **Question : Le `synchronized` devient-il gratuit ?**
> Non. La contention reste : deux tâches qui disputent le même verrou se mettent en file, et l'attente se paie pareil. Ce qui change, c'est que cette file ne bloque plus les porteurs, donc plus tout le service. La règle de conception ne change pas : ne pas tenir un verrou pendant une attente longue.

> [!TIP]
> **Ce qu'il faut retenir**
> - Avant Java 24, un thread virtuel bloqué dans un `synchronized` épinglait son porteur ; quelques dizaines de tâches suffisaient à geler le service.
> - Depuis Java 24, il se détache comme pour n'importe quel autre blocage, et le code `synchronized` existant se migre tel quel.
> - Le pinning subsiste sous frame native (JNI, FFM) ; JFR le signale avec `jdk.VirtualThreadPinned`.
> - Pinning et contention sont deux problèmes distincts : réécrire un `synchronized` ne supprime pas une contention.

## Scoped Values

### Comprendre avant de passer aux tâches concurrentes

Une requête porte l’identifiant `R-42`. Son traitement appelle plusieurs méthodes : `traiter()` → `enregistrer()` → `tracer()`. La dernière doit écrire `R-42:enregistrement`, pour qu’on retrouve les traces de cette requête.

On peut passer l’identifiant en paramètre à chaque méthode : c’est une bonne solution quand le trajet reste simple. Avec de nombreux intermédiaires, cela leur impose parfois de recevoir et retransmettre une donnée qu’ils n’utilisent pas. Cette information qui accompagne l’opération s’appelle ici le **contexte**.

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

Enregistrer dans `DemoScopedValues.java`, puis exécuter avec Java 25 :

```
java DemoScopedValues.java
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

Les paramètres explicites restent adaptés aux dépendances simples. `ScopedValue` est utile pour un contexte lu à travers plusieurs appels, dont la disponibilité doit se terminer avec l’opération. Un `ThreadLocal` permet aussi une lecture sans paramètre, mais demande d’organiser explicitement la durée de présence de la valeur, avec `set()` et `remove()`.

Source : [API ScopedValue, Java SE 25](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/lang/ScopedValue.html).

### Appliquer le concept à l’exercice

`TraitementTrace` reçoit un contexte de requête, réduit à un identifiant, et lance une tâche par étape. Chaque étape doit préfixer sa trace par l'identifiant de la requête, sans le recevoir en paramètre.

Le changement par rapport au premier exemple est le passage à d’autres threads. Une tâche soumise à un exécuteur ne reçoit pas automatiquement la liaison du thread appelant. La lambda transporte l’objet `contexte`, puis la tâche établit sa propre liaison avec `ScopedValue.where(CONTEXTE, contexte).call(() -> tracerUne(etape))`. `call()` renvoie le résultat, là où `run()` ne renvoie rien.

Dossier : `exercices/scoped-values`.

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

**Question de débriefing** : qu'est-ce qu'un `ScopedValue` interdit, qu'un `ThreadLocal` autorisait ?

**Algorithme en français** : remplacer la clé mutable par une `ScopedValue`, capturer le contexte dans la lambda de chaque tâche, établir la liaison dans cette tâche autour de l'appel qui doit la lire, puis supprimer le nettoyage manuel devenu inutile.

> [!TIP]
> **Ce qu'il faut retenir**
> - Une liaison rend une valeur accessible aux appels descendants pendant un bloc borné.
> - La clé ne fournit pas de `set()` ; une portée imbriquée peut toutefois établir une liaison temporaire différente.
> - L'objet transporté n'est pas rendu immuable par l'API.
> - Une tâche d'exécuteur ordinaire doit établir explicitement sa liaison.

Source : [JEP 506, Scoped Values](https://openjdk.org/jeps/506).

## Stable Values

Une Stable Value vise l'initialisation différée d'une donnée qui ne changera plus après sa première création. Le problème est différent de Scoped Values : il ne s'agit pas de propager un contexte pendant une opération, mais de publier une valeur stable créée au premier besoin.

La fonctionnalité reste en preview dans Java 25. Comparez-la à une initialisation immédiate, à un holder statique ou à une synchronisation explicite avant de l'expérimenter.

> [!TIP]
> **Ce qu'il faut retenir**
> - Stable Values traite l'initialisation différée et la stabilité après création.
> - Scoped Values traite la transmission descendante d'un contexte borné.
> - Le statut preview impose une expérimentation isolée et supprimable.

Source : [JEP 502, Stable Values](https://openjdk.org/jeps/502).

## Choisir une primitive de concurrence

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

> [!TIP]
> **Ce qu'il faut retenir**
> - Une KDF sépare les clés selon leur usage à partir d'un même secret.
> - Le protocole fixe l'algorithme, le sel, le contexte et la longueur.
> - Une API cryptographique ne remplace ni le protocole ni les vecteurs de test.

Source : [JEP 510, Key Derivation Function API](https://openjdk.org/jeps/510).

# Quiz

Répondez sans regarder, les réponses sont en fin de Guide.

1. Vous lisez qu'une fonctionnalité est en preview dans la version que vous utilisez. Qu'avez-vous le droit d'en conclure pour un projet en production ?
2. Pourquoi le `switch` de l'étape 3 compile-t-il sans `default` ?
3. Un collègue ajoute un `default -> throw new IllegalStateException()` à un `switch` sur une hiérarchie `sealed`, « par sécurité ». Que perdez-vous ?
4. Pourquoi le cas `Square square when square.side() == 0` doit-il précéder le cas `Square square` ?
5. Quelle différence sépare une vue `reversed()` d'une copie réalisée avec `List.copyOf` ?
6. Pourquoi les threads virtuels n'accélèrent-ils pas un calcul qui occupe continuellement le CPU ?
7. Que devient une liaison Scoped Values à la sortie de `run()` ou `call()` ?
8. Quelle différence de problème sépare Scoped Values et Stable Values ?
9. Pourquoi la Vector API conserve-t-elle une boucle scalaire de fin ?
10. Que reste-t-il à décider autour d'une API KEM ou KDF ?

# Corrigés

## Réponses du quiz

1. Rien de définitif. Une preview peut changer de syntaxe à la version suivante, et elle peut être retirée sans jamais devenir finale, comme les String Templates. Vous pouvez expérimenter, pas engager une production.
2. Parce que `Shape` est `sealed`. Le compilateur connaît la liste complète des types autorisés par le `permits`, il vérifie que le `switch` les traite tous, et il ne réclame donc pas de cas par défaut.
3. Vous perdez l'erreur de compilation lors du prochain ajout de forme. Le `switch` restera valide aux yeux du compilateur, le cas nouveau tombera dans le `default`, et l'anomalie se déplacera de la compilation vers l'exécution.
4. Parce que les `case` sont évalués de haut en bas et que `Square square` recouvre déjà le carré de côté nul. Placé après, le cas gardé ne serait jamais atteint.
5. `reversed()` est une vue reliée à la collection d'origine ; `List.copyOf` produit un résultat indépendant et non modifiable.
6. Un thread virtuel qui calcule ne se détache pas de son porteur. La capacité reste bornée par les cœurs disponibles.
7. La liaison précédente est restaurée ; en l'absence de liaison extérieure, la clé redevient non liée, y compris après une exception.
8. Scoped Values propage un contexte pendant une opération bornée ; Stable Values initialise une donnée au premier besoin puis la garde stable.
9. La longueur du tableau n'est pas toujours un multiple de la largeur vectorielle préférée. La boucle scalaire traite les éléments restants.
10. Le protocole doit encore fixer l'algorithme, les paramètres, la séparation des usages, le fournisseur et les vecteurs de test.

## Pattern matching pour `switch`


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

Les branches `horizontal` et `vertical` déclarent quatre coordonnées mais n'en utilisent que deux. Le sujet suivant, les variables et patterns anonymes, permet d'ignorer les coordonnées inutilisées avec `_`.

## Collections séquencées

Le geste décisif est le premier : changer le type déclaré du champ. `LinkedHashSet` implémente `SequencedSet` depuis Java 21, mais tant que le champ est vu comme un `Set`, aucune de ces méthodes n'est visible.

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

## Javadoc Markdown

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
```

Les balises `@param` et `@return` ne changent pas : le Markdown remplace le HTML, pas les tags Javadoc.

Et le piège : une liste Markdown dans un commentaire `/** */` n'est pas interprétée, elle sort telle quelle dans le HTML.

## Fichiers source compacts

```java
void main() {
    IO.println("Bonjour depuis un fichier source compact");
}
```

Trois lignes, et aucune ne serait valide avant Java 25 : la classe est implicite, `main` est une méthode d'instance sans argument, et `IO` est visible sans import puisqu'elle vit dans `java.lang`.

## Imports de modules

Une ligne, à placer après le `package` :

```java
import module java.base;
```

`java.base` exporte `java.util`, `java.io`, `java.nio.file`, `java.time` et une trentaine d'autres packages : tous deviennent visibles d'un coup.

`import module M` importe **tous les packages exportés** par le module, pas un package : c'est un cran au-dessus de l'import à l'étoile. Le confort a une contrepartie, un nom de type devient ambigu s'il existe dans deux modules importés, par exemple `List` de `java.base` et `java.awt.List`. Le compilateur le signale, et un import explicite du type voulu tranche.

## Corps de constructeurs flexibles

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

L'état est créé par l'initializer, et il appartient à **une évaluation du pipeline**, jamais au code appelant. C'est ce qui rend le gatherer réutilisable sans partager d'état entre deux appels.

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

Une ligne :

```java
try (var executeur = Executors.newVirtualThreadPerTaskExecutor()) {
```

à la place de :

```java
try (var executeur = Executors.newFixedThreadPool(4)) {
```

La durée mesurée passe d'environ 3000 ms à environ 20 ms. L'ordre des résultats ne change pas, parce qu'il vient de l'ordre de lecture des `Future`, pas de l'ordre d'exécution.

**Réponse au débriefing** : les mêmes 1000 tâches occupées à **calculer** pendant 10 ms ne gagneraient rien. Un thread virtuel qui calcule ne libère pas son thread porteur, puisqu'il ne se bloque jamais. Le plancher devient le nombre de cœurs disponibles, et créer 1000 threads virtuels ne fait qu'ajouter de l'ordonnancement par-dessus. La bascule ne rend rien plus rapide, elle **cesse d'immobiliser un thread OS pendant une attente**.

## Scoped Values

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
