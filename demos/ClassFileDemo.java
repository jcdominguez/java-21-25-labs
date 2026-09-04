import java.lang.classfile.ClassFile;

public class ClassFileDemo {
    public static void main(String[] args) throws Exception {
        byte[] bytes;
        try (var input = ClassFileDemo.class.getResourceAsStream("/ClassFileDemo.class")) {
            if (input == null) {
                throw new IllegalStateException("ClassFileDemo.class est introuvable");
            }
            bytes = input.readAllBytes();
        }

        var model = ClassFile.of().parse(bytes);
        var className = model.thisClass().asSymbol().displayName();

        if (!className.equals("ClassFileDemo")) {
            throw new AssertionError("Nom de classe inattendu : " + className);
        }
        System.out.println("Class-File API validée : " + className);
    }
}
