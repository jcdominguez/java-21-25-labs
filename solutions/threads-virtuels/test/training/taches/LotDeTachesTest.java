package training.taches;

import java.util.List;
import java.util.stream.IntStream;

public final class LotDeTachesTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var lot = new LotDeTaches();
        List<String> identifiants = IntStream.range(0, 1000)
                .mapToObj(numero -> "t-" + numero)
                .toList();

        long debut = System.nanoTime();
        List<String> resultats = lot.executer(identifiants);
        long dureeMs = (System.nanoTime() - debut) / 1_000_000;

        System.out.println("Durée mesurée : " + dureeMs + " ms pour 1000 tâches de 10 ms.");

        verifier("Rendre les 1000 résultats dans l'ordre des identifiants",
                resultats.equals(identifiants.stream().map(identifiant -> "fait:" + identifiant).toList()));
        verifier("Exécuter les 1000 tâches de 10 ms en moins d'une seconde",
                dureeMs < 1000);

        conclure("Threads virtuels");
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
