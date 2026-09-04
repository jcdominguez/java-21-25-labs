package training.orders;

import java.util.List;

public final class ContextualOrderServiceTest {
    public static void main(String[] args) {
        var service = new ContextualOrderService();
        var context = new RequestContext("trace-42", "north");

        assert service.process(context, List.of("A-1", "A-2"))
                .equals(List.of("north:trace-42:A-1", "north:trace-42:A-2"))
                : "Le contexte doit être lié dans chaque tâche";
        assert !service.contextBound() : "Le contexte ne doit pas fuir après le traitement";

        System.out.println("TP 3 validé");
    }
}

