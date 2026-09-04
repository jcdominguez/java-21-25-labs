package training.orders;

import java.util.function.Function;
import java.util.stream.Gatherer;

final class DistinctByKeyGatherer {
    private DistinctByKeyGatherer() {
    }

    static <T, K> Gatherer<T, ?, T> distinctBy(Function<? super T, ? extends K> keyExtractor) {
        return Gatherer.ofSequential((_, element, downstream) -> downstream.push(element));
    }
}

