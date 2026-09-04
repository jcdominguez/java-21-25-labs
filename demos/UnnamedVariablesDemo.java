public class UnnamedVariablesDemo {
    public static void main(String[] args) {
        try {
            Integer.parseInt("Java");
        } catch (NumberFormatException _) {
            System.out.println("Valeur non numérique");
        }
    }
}

