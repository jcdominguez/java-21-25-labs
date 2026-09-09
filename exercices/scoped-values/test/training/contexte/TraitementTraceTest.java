package training.contexte;

import java.util.List;

public final class TraitementTraceTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var traitement = new TraitementTrace();
        var contexte = new ContexteRequete("req-42");

        verifier("Lire l'identifiant de la requête dans chaque tâche",
                traitement.tracer(contexte, List.of("lecture", "calcul", "écriture"))
                        .equals(List.of("req-42:lecture", "req-42:calcul", "req-42:écriture")));
        verifier("Ne laisser aucune liaison après le traitement",
                !traitement.contexteLie());

        conclure("Scoped Values");
    }

    private static void verifier(String intitule, boolean condition) {
        System.out.println((condition ? "OK    " : "ÉCHEC ") + intitule);
        if (!condition) {
            echecs++;
        }
    }

    private static void conclure(String sujet) {
        if (echecs == 0) {
            System.out.println(sujet + " validé");
        } else {
            System.out.println(echecs + " vérification(s) en échec");
            System.exit(1);
        }
    }
}
