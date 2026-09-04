package training.orders;

import java.util.List;

public final class OrderModernizationTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var summary = new CommandSummary();
        verifier("Décrire une création de commande",
                summary.describe(new CreateOrder("A-1", 3)).equals("create A-1 (3 items)"));
        verifier("Décrire une annulation",
                summary.describe(new CancelOrder("A-2", "duplicate")).equals("cancel A-2: duplicate"));
        verifier("Décrire une priorisation",
                summary.describe(new PrioritizeOrder("A-3")).equals("prioritize A-3"));

        var queue = new OrderQueue();
        queue.add("A-1");
        queue.add("A-2");
        verifier("Premier élément de la file", queue.first().equals("A-1"));
        verifier("Dernier élément de la file", queue.last().equals("A-2"));

        var batch = new OrderBatchService();
        var loaded = batch.loadAll(List.of("A-1", "A-2", "A-3"));
        verifier("Charger les commandes dans l'ordre",
                loaded.equals(List.of("loaded:A-1", "loaded:A-2", "loaded:A-3")));
        verifier("Remplacer le pool fixe par des threads virtuels",
                batch.usedOnlyVirtualThreads());

        conclure("TP 1");
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
