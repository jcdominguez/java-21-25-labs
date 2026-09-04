package training.orders;

sealed interface OrderCommand permits CreateOrder, CancelOrder, PrioritizeOrder {
}

record CreateOrder(String orderId, int itemCount) implements OrderCommand {
}

record CancelOrder(String orderId, String reason) implements OrderCommand {
}

record PrioritizeOrder(String orderId) implements OrderCommand {
}

