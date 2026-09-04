package training.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class ContextualOrderService {
    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();

    List<String> process(RequestContext context, List<String> orderIds) {
        CONTEXT.set(context);
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> futures = new ArrayList<>();
            for (String orderId : orderIds) {
                futures.add(executor.submit(() -> processOne(orderId)));
            }

            List<String> results = new ArrayList<>();
            for (Future<String> future : futures) {
                results.add(future.get());
            }
            return List.copyOf(results);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Processing interrupted", exception);
        } catch (Exception exception) {
            throw new IllegalStateException("Processing failed", exception);
        } finally {
            CONTEXT.remove();
        }
    }

    boolean contextBound() {
        return CONTEXT.get() != null;
    }

    private String processOne(String orderId) {
        RequestContext context = CONTEXT.get();
        if (context == null) {
            return "missing:" + orderId;
        }
        return context.tenant() + ":" + context.traceId() + ":" + orderId;
    }
}

