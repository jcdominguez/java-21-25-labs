/// Démonstration des commentaires de documentation en **Markdown**.
public class MarkdownDocsDemo {
    private MarkdownDocsDemo() {
    }

    /// Retourne le **premier** identifiant disponible.
    ///
    /// @param ids identifiants ordonnés
    /// @return premier identifiant
    public static String first(java.util.List<String> ids) {
        return ids.getFirst();
    }
}
