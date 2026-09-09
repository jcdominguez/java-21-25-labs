package training.sensors;

/// Écrit avant Java 25 : `super()` devait être la première instruction.
///
/// Deux conséquences, toutes deux visibles au test :
///   - la base lit `nom` avant qu'il soit affecté, donc `null` ;
///   - un nom invalide n'est refusé qu'après que la base a été construite.
final class CapteurNomme extends Capteur {

    private final String nom;

    CapteurNomme(String nom) {
        super();
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("nom obligatoire");
        }
        this.nom = nom;
    }

    @Override
    String etiquette() {
        return "capteur:" + nom;
    }
}
