package training.orders;

import java.util.List;
import java.util.stream.Gatherers;

final class EventAnalytics {
    List<OrderEvent> firstEventPerOrder(List<OrderEvent> events) {
        return events.stream()
                .gather(DistinctByKeyGatherer.distinctBy(OrderEvent::orderId))
                .toList();
    }

    List<List<Integer>> windows(List<Integer> values, int size) {
        return values.stream()
                .gather(Gatherers.windowFixed(size))
                .toList();
    }
}

