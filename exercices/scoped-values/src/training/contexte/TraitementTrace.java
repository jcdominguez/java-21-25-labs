package training.contexte;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class TraitementTrace {
    private static final ThreadLocal<ContexteRequete> CONTEXTE = new ThreadLocal<>();

    List<String> tracer(ContexteRequete contexte, List<String> etapes) {
        CONTEXTE.set(contexte);
        try (var executeur = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Future<String>> encours = new ArrayList<>();
            for (String etape : etapes) {
                encours.add(executeur.submit(() -> tracerUne(etape)));
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
        } finally {
            CONTEXTE.remove();
        }
    }

    boolean contexteLie() {
        return CONTEXTE.get() != null;
    }

    private String tracerUne(String etape) {
        ContexteRequete contexte = CONTEXTE.get();
        if (contexte == null) {
            return "absent:" + etape;
        }
        return contexte.identifiant() + ":" + etape;
    }
}
