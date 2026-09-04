package training.orders;

import java.util.List;

public final class EventAnalyticsTest {
    public static void main(String[] args) {
        var analytics = new EventAnalytics();
        var events = List.of(
                new OrderEvent("A-1", "CREATED"),
                new OrderEvent("A-2", "CREATED"),
                new OrderEvent("A-1", "PAID"),
                new OrderEvent("A-3", "CREATED"),
                new OrderEvent("A-2", "SHIPPED")
        );

        assert analytics.firstEventPerOrder(events).equals(List.of(
                new OrderEvent("A-1", "CREATED"),
                new OrderEvent("A-2", "CREATED"),
                new OrderEvent("A-3", "CREATED")
        ));

        assert analytics.windows(List.of(1, 2, 3, 4, 5), 2)
                .equals(List.of(List.of(1, 2), List.of(3, 4), List.of(5)));

        System.out.println("TP 2 validé");
    }
}
