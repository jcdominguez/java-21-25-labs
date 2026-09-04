package training.orders;

import java.util.ArrayList;
import java.util.List;

final class OrderQueue {
    private final List<String> orderIds = new ArrayList<>();

    void add(String orderId) {
        orderIds.add(orderId);
    }

    String first() {
        return orderIds.get(0);
    }

    String last() {
        return orderIds.get(orderIds.size() - 1);
    }
}

