package training.releves;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        final class Etat {
            private final Set<K> vues = new HashSet<>();
        }

        return Gatherer.ofSequential(
                Etat::new,
                (etat, element, aval) -> {
                    if (etat.vues.add(cle.apply(element))) {
                        return aval.push(element);
                    }
                    return true;
                }
        );
    }
}
