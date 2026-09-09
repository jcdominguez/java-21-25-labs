package training.modules;

// Les imports ont été retirés : cette classe ne compile pas en l'état.
//
// Les rétablir en une seule déclaration, sans nommer aucun type.

final class Inventaire {

    String resume() {
        List<String> articles = new ArrayList<>();
        articles.add("clavier");
        articles.add("écran");

        Map<String, Integer> stocks = new HashMap<>();
        stocks.put("clavier", 3);
        stocks.put("écran", 1);

        int total = 0;
        for (String article : articles) {
            total += stocks.get(article);
        }

        return articles.size() + " références, " + total + " unités";
    }
}
