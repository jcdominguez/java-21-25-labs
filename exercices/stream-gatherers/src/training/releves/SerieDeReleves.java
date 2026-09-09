package training.releves;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

final class SerieDeReleves {

    List<Releve> premierParCapteur(List<Releve> releves) {
        return releves.stream()
                .gather(premierPar(Releve::capteur))
                .toList();
    }

    List<List<Integer>> fenetres(List<Integer> valeurs, int taille) {
        return valeurs.stream()
                .gather(Gatherers.windowFixed(taille))
                .toList();
    }

    static <T, K> Gatherer<T, ?, T> premierPar(Function<? super T, ? extends K> cle) {
        return Gatherer.ofSequential((_, element, aval) -> aval.push(element));
    }
}
