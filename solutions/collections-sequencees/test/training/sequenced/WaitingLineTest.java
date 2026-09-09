package training.sequenced;

import java.util.List;

public final class WaitingLineTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        WaitingLine ligne = trois();
        ligne.pushToFront("T-0");
        verifier("Placer un ticket prioritaire en tête de file",
                "T-0".equals(ligne.first()));

        ligne = trois();
        verifier("Retirer le premier ticket et le retourner",
                "T-1".equals(ligne.serveNext()) && "T-2".equals(ligne.first()));

        ligne = trois();
        verifier("Rendre la file du plus récent au plus ancien",
                List.of("T-3", "T-2", "T-1").equals(ligne.newestFirst()));

        conclure("Collections séquencées");
    }

    private static WaitingLine trois() {
        WaitingLine ligne = new WaitingLine();
        ligne.add("T-1");
        ligne.add("T-2");
        ligne.add("T-3");
        return ligne;
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
