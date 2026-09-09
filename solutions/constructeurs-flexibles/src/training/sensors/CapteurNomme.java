package training.sensors;

/// Java 25 : le prologue du constructeur s'exécute avant `super()`.
///
/// La validation refuse donc l'argument avant que la base ne soit
/// construite, et le champ est affecté avant que la base ne le lise.
final class CapteurNomme extends Capteur {

    private final String nom;

    CapteurNomme(String nom) {
        if (nom == null || nom.isBlank()) {
            throw new IllegalArgumentException("nom obligatoire");
        }
        this.nom = nom;
        super();
    }

    @Override
    String etiquette() {
        return "capteur:" + nom;
    }
}
