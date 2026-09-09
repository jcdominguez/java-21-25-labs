package training.modules;

public final class InventaireTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        Inventaire inventaire = new Inventaire();

        verifier("Résumer l'inventaire",
                "2 références, 4 unités".equals(inventaire.resume()));

        conclure("Imports de modules");
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
