package training.shapes;

/// Traduit une forme en une ligne lisible.
///
/// Écrit avant Java 21 : une chaîne de `if instanceof`, avec un cast
/// implicite par branche et une valeur de repli en dernier recours.
///
/// Cette valeur de repli est le défaut à observer : une forme oubliée
/// ne se signale pas, elle produit une chaîne fausse à l'exécution.
final class ShapeDescriber {

    String describe(Shape shape) {
        if (shape instanceof Circle circle) {
            return "cercle de rayon " + circle.radius();
        }
        if (shape instanceof Square square) {
            return "carré de côté " + square.side();
        }
        if (shape instanceof Rectangle rectangle) {
            return "rectangle " + rectangle.width() + " x " + rectangle.height();
        }
        return "forme inconnue";
    }
}
