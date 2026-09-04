package training.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

final class OrderBatchService {
    private final AtomicBoolean onlyVirtualThreads = new AtomicBoolean(true);

    List<String> loadAll(List<String> orderIds) {
        onlyVirtualThreads.set(true);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();
            for (String orderId : orderIds) {
                futures.add(executor.submit(() -> loadOne(orderId)));
            }

            List<String> loaded = new ArrayList<>();
            for (Future<String> future : futures) {
                loaded.add(future.get());
            }
            return List.copyOf(loaded);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Batch interrupted", exception);
        } catch (Exception exception) {
            throw new IllegalStateException("Batch failed", exception);
        }
    }

    boolean usedOnlyVirtualThreads() {
        return onlyVirtualThreads.get();
    }

    private String loadOne(String orderId) throws InterruptedException {
        onlyVirtualThreads.compareAndSet(true, Thread.currentThread().isVirtual());
        Thread.sleep(10);
        return "loaded:" + orderId;
    }
}

