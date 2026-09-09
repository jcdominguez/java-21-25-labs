package training.releves;

import java.util.List;

public final class SerieDeRelevesTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var serie = new SerieDeReleves();
        var releves = List.of(
                new Releve("nord", 12),
                new Releve("sud", 19),
                new Releve("nord", 14),
                new Releve("est", 8),
                new Releve("sud", 21)
        );

        verifier("Ne garder que le premier relevé de chaque capteur",
                serie.premierParCapteur(releves).equals(List.of(
                        new Releve("nord", 12),
                        new Releve("sud", 19),
                        new Releve("est", 8)
                )));

        verifier("Conserver l'ordre de rencontre des capteurs",
                serie.premierParCapteur(releves).stream().map(Releve::capteur).toList()
                        .equals(List.of("nord", "sud", "est")));

        verifier("Découper en fenêtres fixes de deux valeurs",
                serie.fenetres(List.of(1, 2, 3, 4, 5), 2)
                        .equals(List.of(List.of(1, 2), List.of(3, 4), List.of(5))));

        conclure("Stream Gatherers");
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
