package training.shapes;

/// Traduit une forme en une ligne lisible.
///
/// Un `switch` sur le type, sans `default` : la hiérarchie est `sealed`,
/// le compilateur connaît donc la liste complète et refuse un cas manquant.
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
