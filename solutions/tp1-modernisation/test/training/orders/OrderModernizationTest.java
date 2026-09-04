package training.orders;

import java.util.List;

public final class OrderModernizationTest {
    public static void main(String[] args) {
        var summary = new CommandSummary();
        assert summary.describe(new CreateOrder("A-1", 3)).equals("create A-1 (3 items)");
        assert summary.describe(new CancelOrder("A-2", "duplicate")).equals("cancel A-2: duplicate");
        assert summary.describe(new PrioritizeOrder("A-3")).equals("prioritize A-3");

        var queue = new OrderQueue();
        queue.add("A-1");
        queue.add("A-2");
        assert queue.first().equals("A-1");
        assert queue.last().equals("A-2");

        var batch = new OrderBatchService();
        assert batch.loadAll(List.of("A-1", "A-2", "A-3"))
                .equals(List.of("loaded:A-1", "loaded:A-2", "loaded:A-3"));
        assert batch.usedOnlyVirtualThreads();

        System.out.println("TP 1 validé");
    }
}
