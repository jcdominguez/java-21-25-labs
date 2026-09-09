package training.modules;

import module java.base;



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
