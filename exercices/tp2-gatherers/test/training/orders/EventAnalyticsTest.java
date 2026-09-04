package training.orders;

import java.util.List;

public final class EventAnalyticsTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        var analytics = new EventAnalytics();
        var events = List.of(
                new OrderEvent("A-1", "CREATED"),
                new OrderEvent("A-2", "CREATED"),
                new OrderEvent("A-1", "PAID"),
                new OrderEvent("A-3", "CREATED"),
                new OrderEvent("A-2", "SHIPPED")
        );

        verifier("Le gatherer doit filtrer selon orderId",
                analytics.firstEventPerOrder(events).equals(List.of(
                        new OrderEvent("A-1", "CREATED"),
                        new OrderEvent("A-2", "CREATED"),
                        new OrderEvent("A-3", "CREATED")
                )));

        verifier("Découper en fenêtres fixes de deux éléments",
                analytics.windows(List.of(1, 2, 3, 4, 5), 2)
                        .equals(List.of(List.of(1, 2), List.of(3, 4), List.of(5))));

        conclure("TP 2");
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
