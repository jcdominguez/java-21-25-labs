import java.util.List;
import java.util.stream.Gatherers;

public class GatherersDemo {
    public static void main(String[] args) {
        var windows = List.of(1, 2, 3, 4, 5)
                .stream()
                .gather(Gatherers.windowFixed(2))
                .toList();
        System.out.println(windows);
    }
}
