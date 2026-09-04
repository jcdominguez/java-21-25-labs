class Account {
    private final String owner;

    Account(String owner) {
        this.owner = owner;
    }

    String owner() {
        return owner;
    }
}

public class FlexibleConstructorDemo extends Account {
    FlexibleConstructorDemo(String owner) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("owner is required");
        }
        super(owner.strip());
    }

    public static void main(String[] args) {
        var account = new FlexibleConstructorDemo("  James  ");
        if (!account.owner().equals("James")) {
            throw new AssertionError("Le nom devrait être validé puis normalisé");
        }
        System.out.println("Constructeur flexible validé : " + account.owner());
    }
}
