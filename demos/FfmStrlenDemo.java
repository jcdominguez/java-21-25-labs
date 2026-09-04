import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

public class FfmStrlenDemo {
    public static void main(String[] args) throws Throwable {
        var linker = Linker.nativeLinker();
        var strlenAddress = linker.defaultLookup().find("strlen").orElseThrow();
        var strlen = linker.downcallHandle(
                strlenAddress,
                FunctionDescriptor.of(JAVA_LONG, ADDRESS));

        try (var arena = Arena.ofConfined()) {
            var text = arena.allocateFrom("Java 25");
            long length = (long) strlen.invokeExact(text);

            if (length != 7) {
                throw new AssertionError("strlen devrait retourner 7, résultat : " + length);
            }
            System.out.println("FFM validée : strlen(\"Java 25\") = " + length);
        }
    }
}
