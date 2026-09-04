package training.orders;

import java.util.ArrayList;
import java.util.SequencedCollection;

final class OrderQueue {
    private final SequencedCollection<String> orderIds = new ArrayList<>();

    void add(String orderId) {
        orderIds.add(orderId);
    }

    String first() {
        return orderIds.getFirst();
    }

    String last() {
        return orderIds.getLast();
    }
}

