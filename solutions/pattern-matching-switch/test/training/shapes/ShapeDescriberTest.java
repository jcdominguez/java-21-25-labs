package training.shapes;

public final class ShapeDescriberTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        ShapeDescriber describer = new ShapeDescriber();

        verifier("Décrire un cercle",
                "cercle de rayon 2.0".equals(describer.describe(new Circle(2))));

        verifier("Décrire un carré",
                "carré de côté 3.0".equals(describer.describe(new Square(3))));

        verifier("Décrire un rectangle",
                "rectangle 2.0 x 5.0".equals(describer.describe(new Rectangle(2, 5))));

        verifier("Décrire un triangle",
                "triangle de base 4.0".equals(describer.describe(new Triangle(4, 6))));

        verifier("Signaler un carré de côté nul",
                "carré dégénéré".equals(describer.describe(new Square(0))));

        conclure("Pattern matching pour switch");
    }

    private static void verifier(String intitule, boolean reussi) {
        System.out.println((reussi ? "OK    " : "ÉCHEC ") + intitule);
        if (!reussi) {
            echecs++;
        }
    }

    private static void conclure(String sujet) {
        if (echecs == 0) {
            System.out.println(sujet + " : exercice validé");
        } else {
            System.out.println(echecs + " vérification(s) en échec");
            System.exit(1);
        }
    }
}
