package training.sequenced;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/// Une file d'attente de tickets.
///
/// Les tickets sont uniques et conservés dans leur ordre d'arrivée,
/// d'où le `LinkedHashSet`.
final class WaitingLine {
    private final Set<String> tickets = new LinkedHashSet<>();

    void add(String ticket) {
        tickets.add(ticket);
    }

    /// Le prochain ticket à servir.
    ///
    /// Écrit avant Java 21 : sur un `LinkedHashSet`, il n'existait aucun
    /// moyen de lire le premier élément autrement qu'en ouvrant une boucle.
    String first() {
        for (String ticket : tickets) {
            return ticket;
        }
        throw new IllegalStateException("File vide");
    }

    /// Le dernier ticket arrivé. Même contrainte, en pire : il faut
    /// parcourir toute la file pour en atteindre la fin.
    String last() {
        String found = null;
        for (String ticket : tickets) {
            found = ticket;
        }
        if (found == null) {
            throw new IllegalStateException("File vide");
        }
        return found;
    }

    /// À écrire : placer un ticket prioritaire en tête de file.
    void pushToFront(String ticket) {
        // à écrire
    }

    /// À écrire : retirer le premier ticket de la file et le retourner.
    String serveNext() {
        return null; // à écrire
    }

    /// À écrire : la file du plus récent au plus ancien.
    List<String> newestFirst() {
        return List.of(); // à écrire
    }
}
