import java.security.KeyPairGenerator;
import java.security.spec.NamedParameterSpec;
import java.util.Arrays;
import javax.crypto.KEM;

public class KemDemo {
    public static void main(String[] args) throws Exception {
        var generator = KeyPairGenerator.getInstance("XDH");
        generator.initialize(NamedParameterSpec.X25519);
        var receiverKeyPair = generator.generateKeyPair();

        var kem = KEM.getInstance("DHKEM");
        var encapsulated = kem.newEncapsulator(receiverKeyPair.getPublic()).encapsulate();
        var senderSecret = encapsulated.key();

        var receiverSecret = kem.newDecapsulator(receiverKeyPair.getPrivate())
                .decapsulate(encapsulated.encapsulation());

        if (!Arrays.equals(senderSecret.getEncoded(), receiverSecret.getEncoded())) {
            throw new AssertionError("Les secrets KEM devraient être identiques");
        }

        System.out.println("KEM validé : même secret, message d'encapsulation distinct");
    }
}
