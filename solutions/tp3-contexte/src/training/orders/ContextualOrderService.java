package training.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class ContextualOrderService {
    private static final ScopedValue<RequestContext> CONTEXT = ScopedValue.newInstance();

    List<String> process(RequestContext context, List<String> orderIds) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();
            for (String orderId : orderIds) {
                futures.add(executor.submit(() ->
                        ScopedValue.where(CONTEXT, context).call(() -> processOne(orderId))));
            }

            try {
                List<String> results = new ArrayList<>();
                for (Future<String> future : futures) {
                    results.add(future.get());
                }
                return List.copyOf(results);
            } catch (InterruptedException exception) {
                futures.forEach(future -> future.cancel(true));
                Thread.currentThread().interrupt();
                throw new IllegalStateException("Processing interrupted", exception);
            } catch (ExecutionException exception) {
                futures.forEach(future -> future.cancel(true));
                throw new IllegalStateException("Processing failed", exception.getCause());
            }
        }
    }

    boolean contextBound() {
        return CONTEXT.isBound();
    }

    private String processOne(String orderId) {
        RequestContext context = CONTEXT.get();
        return context.tenant() + ":" + context.traceId() + ":" + orderId;
    }
}

