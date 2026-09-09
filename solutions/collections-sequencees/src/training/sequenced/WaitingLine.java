package training.sequenced;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;

/// Une file d'attente de tickets.
///
/// Les tickets sont uniques et conservés dans leur ordre d'arrivée,
/// d'où le `LinkedHashSet`. Le champ est déclaré `SequencedSet` : c'est
/// ce type qui porte l'ordre de rencontre, et donc les quatre gestes.
final class WaitingLine {
    private final SequencedSet<String> tickets = new LinkedHashSet<>();

    void add(String ticket) {
        tickets.add(ticket);
    }

    String first() {
        return tickets.getFirst();
    }

    String last() {
        return tickets.getLast();
    }

    void pushToFront(String ticket) {
        tickets.addFirst(ticket);
    }

    String serveNext() {
        return tickets.removeFirst();
    }

    List<String> newestFirst() {
        return List.copyOf(tickets.reversed());
    }
}
