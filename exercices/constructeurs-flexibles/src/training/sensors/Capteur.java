package training.sensors;

/// Un capteur, dont la construction est journalisée.
///
/// Le constructeur de base appelle `etiquette()`, redéfinie par les
/// sous-classes : il observe donc l'objet en cours de construction.
abstract class Capteur {

    /// Nombre de constructions de base réellement exécutées.
    static int constructions = 0;

    /// Ce que la base a lu au moment de sa propre construction.
    static String vuALaConstruction = "";

    Capteur() {
        constructions++;
        vuALaConstruction = etiquette();
    }

    abstract String etiquette();
}
