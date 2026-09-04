package training.orders;

import java.util.List;

public final class ContextualOrderServiceTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var service = new ContextualOrderService();
        var context = new RequestContext("trace-42", "north");

        verifier("Le contexte doit être lié dans chaque tâche",
                service.process(context, List.of("A-1", "A-2"))
                        .equals(List.of("north:trace-42:A-1", "north:trace-42:A-2")));
        verifier("Le contexte ne doit pas fuir après le traitement",
                !service.contextBound());

        conclure("TP 3");
    }

    private static void verifier(String intitule, boolean condition) {
        System.out.println((condition ? "OK    " : "ÉCHEC ") + intitule);
        if (!condition) {
            echecs++;
        }
    }

    private static void conclure(String tp) {
        if (echecs == 0) {
            System.out.println(tp + " validé");
        } else {
            System.out.println(echecs + " vérification(s) en échec");
            System.exit(1);
        }
    }
}
