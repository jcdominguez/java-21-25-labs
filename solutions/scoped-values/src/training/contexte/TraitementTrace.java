package training.contexte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class TraitementTrace {
    private static final ScopedValue<ContexteRequete> CONTEXTE = ScopedValue.newInstance();

    List<String> tracer(ContexteRequete contexte, List<String> etapes) {
        try (var executeur = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> encours = new ArrayList<>();
            for (String etape : etapes) {
                encours.add(executeur.submit(() ->
                        ScopedValue.where(CONTEXTE, contexte).call(() -> tracerUne(etape))));
            }

            List<String> traces = new ArrayList<>();
            for (Future<String> future : encours) {
                traces.add(future.get());
            }
            return List.copyOf(traces);
        } catch (InterruptedException interruption) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Traitement interrompu", interruption);
        } catch (Exception echec) {
            throw new IllegalStateException("Traitement en échec", echec);
        }
    }

    boolean contexteLie() {
        return CONTEXTE.isBound();
    }

    private String tracerUne(String etape) {
        ContexteRequete contexte = CONTEXTE.get();
        return contexte.identifiant() + ":" + etape;
    }
}
