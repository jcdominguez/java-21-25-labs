import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KdfDemo {
    public static void main(String[] args) throws Exception {
        var ikm = new SecretKeySpec(bytes("secret partagé"), "Generic");
        var salt = bytes("sel public");

        SecretKey first = derive(ikm, salt, bytes("clé commandes"));
        SecretKey repeated = derive(ikm, salt, bytes("clé commandes"));
        SecretKey otherContext = derive(ikm, salt, bytes("clé facturation"));

        if (!Arrays.equals(first.getEncoded(), repeated.getEncoded())) {
            throw new AssertionError("Les mêmes entrées devraient produire la même clé");
        }
        if (Arrays.equals(first.getEncoded(), otherContext.getEncoded())) {
            throw new AssertionError("Un contexte différent devrait produire une autre clé");
        }

        System.out.println("KDF validée : résultat reproductible et séparé par contexte");
    }

    private static SecretKey derive(SecretKey ikm, byte[] salt, byte[] info) throws Exception {
        var parameters = HKDFParameterSpec.ofExtract()
                .addIKM(ikm)
                .addSalt(salt)
                .thenExpand(info, 32);
        return KDF.getInstance("HKDF-SHA256").deriveKey("AES", parameters);
    }

    private static byte[] bytes(String value) {
        return value.getBytes(StandardCharsets.UTF_8);
    }
}
