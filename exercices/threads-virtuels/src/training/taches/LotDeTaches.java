package training.taches;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

final class LotDeTaches {

    List<String> executer(List<String> identifiants) {
        try (var executeur = Executors.newFixedThreadPool(4)) {
            List<Future<String>> encours = new ArrayList<>();
            for (String identifiant : identifiants) {
                encours.add(executeur.submit(() -> executerUne(identifiant)));
            }

            List<String> resultats = new ArrayList<>();
            for (Future<String> future : encours) {
                resultats.add(future.get());
            }
            return List.copyOf(resultats);
        } catch (InterruptedException interruption) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Lot interrompu", interruption);
        } catch (Exception echec) {
            throw new IllegalStateException("Lot en échec", echec);
        }
    }

    private String executerUne(String identifiant) throws InterruptedException {
        Thread.sleep(10);
        return "fait:" + identifiant;
    }
}
