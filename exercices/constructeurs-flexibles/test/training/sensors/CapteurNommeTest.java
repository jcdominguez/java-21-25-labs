package training.sensors;

public final class CapteurNommeTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        new CapteurNomme("temp-1");
        verifier("La base lit le nom déjà affecté",
                "capteur:temp-1".equals(Capteur.vuALaConstruction));

        int avant = Capteur.constructions;
        String message = "";
        try {
            new CapteurNomme("   ");
        } catch (IllegalArgumentException refus) {
            message = refus.getMessage();
        }

        verifier("Refuser un nom vide",
                "nom obligatoire".equals(message));

        verifier("Refuser avant de construire la base",
                Capteur.constructions == avant);

        conclure("Constructeurs flexibles");
    }

    private static void verifier(String intitule, boolean reussi) {
        System.out.println((reussi ? "OK    " : "ÉCHEC ") + intitule);
        if (!reussi) {
            echecs++;
        }
    }

    private static void conclure(String sujet) {
        if (echecs == 0) {
            System.out.println(sujet + " : exercice validé");
        } else {
            System.out.println(echecs + " vérification(s) en échec");
            System.exit(1);
        }
    }
}
