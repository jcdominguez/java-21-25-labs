package training.geometry;

public final class SegmentDescriberTest {
    private static int echecs = 0;

    public static void main(String[] args) {
        SegmentDescriber describer = new SegmentDescriber();

        verifier("Reconnaître un segment horizontal",
                "horizontal".equals(describer.describe(
                        new Segment(new Point(0, 2), new Point(5, 2)))));

        verifier("Reconnaître un segment vertical",
                "vertical".equals(describer.describe(
                        new Segment(new Point(3, 0), new Point(3, 7)))));

        verifier("Reconnaître un segment réduit à un point",
                "point unique".equals(describer.describe(
                        new Segment(new Point(1, 1), new Point(1, 1)))));

        verifier("Reconnaître un segment oblique",
                "oblique".equals(describer.describe(
                        new Segment(new Point(0, 0), new Point(2, 3)))));

        verifier("Refuser ce qui n'est pas un segment",
                "pas un segment".equals(describer.describe("bonjour")));

        conclure("Record patterns");
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
