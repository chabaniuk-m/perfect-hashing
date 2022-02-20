import hashing.hash.Perfect;
import hashing.hash.Universal;
import hashing.Util;
import hashing.key.Vector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("\t\t\t\t\t\t\t************************************");
        System.out.println("\t\t\t\t\t\t\t***** PERFECT HASH FUNCTION ********");
        System.out.println("\t\t\t\t\t\t\t************************************\n");

        var keys = runTestNumber(7);

        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);

        Util.interactiveTest(h);
    }

    private static List<Vector> runTestNumber(int number) {
        System.out.printf("[ℹ] Test number %d\n", number);
        return switch (number) {
            case 1 -> Util.randData(5, 1);
            case 2 -> Util.randData(20, 1);
            case 3 -> Util.randData(100, 1);
            case 4 -> Util.randData(5);
            case 5 -> Util.randData(20);
            case 6 -> Util.randData(100);
            case 7 -> test7();
            default -> null;
        };
    }

    private static void showKeys(List<Vector> keys) {
        System.out.println("[data] = {\t");
        int count = 0;
        for (Vector key : keys) {
            if (++count == 6) {
                System.out.println();
                count = 1;
            }
            System.out.printf("  \t\t%s", key.toString());
        }
        System.out.println("\n         }\n");
    }

    // read array of single complex numbers from file
    private static List<Vector> test7() {
        List<Vector> keys = null;
        try {
            var lines = Files.readAllLines(Paths.get("complex.txt"));
            keys = new ArrayList<>(lines.size());
            for (var line : lines) {
                keys.add(new Vector(List.of(Util.parseComplex(line))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keys;
    }
}
