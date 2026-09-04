package training.orders;

final class CommandSummary {
    String describe(OrderCommand command) {
        if (command instanceof CreateOrder create) {
            return "create " + create.orderId() + " (" + create.itemCount() + " items)";
        }
        if (command instanceof CancelOrder cancel) {
            return "cancel " + cancel.orderId() + ": " + cancel.reason();
        }
        if (command instanceof PrioritizeOrder prioritize) {
            return "prioritize " + prioritize.orderId();
        }
        throw new IllegalArgumentException("Unsupported command: " + command);
    }
}

