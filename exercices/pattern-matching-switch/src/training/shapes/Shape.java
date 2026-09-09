package training.shapes;

/// Les formes reconnues par l'application.
///
/// La liste est arrêtée ici : `sealed` interdit à toute autre classe
/// d'implémenter `Shape`.
sealed interface Shape permits Circle, Square, Rectangle, Triangle {
}

record Circle(double radius) implements Shape {
}

record Square(double side) implements Shape {
}

record Rectangle(double width, double height) implements Shape {
}

record Triangle(double base, double height) implements Shape {
}
