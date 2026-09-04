package training.orders;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Gatherer;

final class DistinctByKeyGatherer {
    private DistinctByKeyGatherer() {
    }

    static <T, K> Gatherer<T, ?, T> distinctBy(Function<? super T, ? extends K> keyExtractor) {
        final class State {
            private final Set<K> seen = new HashSet<>();
        }

        return Gatherer.ofSequential(
                State::new,
                (state, element, downstream) -> {
                    if (state.seen.add(keyExtractor.apply(element))) {
                        return downstream.push(element);
                    }
                    return true;
                }
        );
    }
}

