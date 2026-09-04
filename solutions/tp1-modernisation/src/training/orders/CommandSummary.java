package training.orders;

final class CommandSummary {
    String describe(OrderCommand command) {
        return switch (command) {
            case CreateOrder(var orderId, var itemCount) ->
                    "create " + orderId + " (" + itemCount + " items)";
            case CancelOrder(var orderId, var reason) ->
                    "cancel " + orderId + ": " + reason;
            case PrioritizeOrder(var orderId) ->
                    "prioritize " + orderId;
        };
    }
}

