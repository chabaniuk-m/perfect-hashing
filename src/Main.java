import hashing.hash.Perfect;
import hashing.hash.Universal;
import hashing.Util;
import hashing.key.Complex;
import hashing.key.Vector;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("\t\t\t\t\t\t\t************************************");
        System.out.println("\t\t\t\t\t\t\t***** PERFECT HASH FUNCTION ********");
        System.out.println("\t\t\t\t\t\t\t************************************\n");

        runTestNumber(3);
    }

    private static void runTestNumber(int number) {
        System.out.printf("[ℹ] Test number %d\n", number);
        switch (number) {
            case 1: test1(); break;
            case 2: test2(); break;
            case 3: test3(); break;
            case 4: test4(); break;
            case 5: test5(); break;
            case 6: test6(); break;
            case 7: test7(); break;
            default:
                System.out.println("KILL Me plz");
        }
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

    private static void test1() {

        var keys = Util.randData(5, 1);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    private static void test2() {

        var keys = Util.randData(20, 1);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    private static void test3() {

        var keys = Util.randData(1000, 1);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    private static void test4() {

        var keys = Util.randData(5);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    private static void test5() {

        var keys = Util.randData(20);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    private static void test6() {

        var keys = Util.randData(1000);
        showKeys(keys);
        var h = new Perfect();
        var table = h.buildFor(keys);

        System.out.println(table);
    }

    // TODO: 2/14/2022 test from file
    private static void test7() {

    }

    private static void testVectors(int n, int dimension) {
        for (int i = 0; i < n; i++) {
            System.out.println(Util.randVector(dimension));
        }
    }

    private static void testHashingForComplex(int m) {
        Universal f = Util.randUHFunction(m);
        for (int i = 0; i < m; i++) {
            var c = Util.randComplex(100);
            System.out.println("Complex: " + c + "  \thash code: " + f.calcHash(c));
        }
    }
}
