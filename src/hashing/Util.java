package hashing;

import hashing.hash.Perfect;
import hashing.key.Complex;
import hashing.key.Vector;
import hashing.hash.Universal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * class for static utility functions
 * and variables
 */
public class Util {
    public final static Random random = new Random();
    // for generating random data
    public final static int COMPLEX_BOUND = 10;
    public final static int DIMENSION_BOUND = 5;

    public static void interactiveTest(Perfect hashFunc) {

        System.out.println("\n\t\t\t*** Interactive test ***\n");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter key to search: ");
        try {
            String input = reader.readLine();
            while (!input.equals("\\end")) {
                Vector key = null;
                try {
                    key = Util.parseVector(input);
                } catch (Exception e) {
                    System.out.println("Input \"" + input + "\" is incorrect!\n" +
                            "Try something like these: (0+0i) or (-13-16i;4-0i)");
                    System.out.print("Enter key to search: ");
                    input = reader.readLine();
                    continue;
                }
                String location = null;
                try {
                    location = hashFunc.find(key);
                    System.out.println("Hash table contains this key in location: " + location);
                } catch (Exception e) {
                    System.out.println("Hash table does not contain key \"" + input + "\"");
                }
                System.out.print("Enter key to search: ");
                input = reader.readLine();
                continue;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates random complex number
     * @param bound absolute(real) < bound & absolute(image) < bound
     */
    public static Complex randComplex(int bound) {
        return new Complex(
                random.nextInt(bound) * (random.nextBoolean() ? 1 : -1),
                random.nextInt(bound) * (random.nextBoolean() ? 1 : -1)
        );
    }

    /**
     * Generates random vector of a given dimension.
     * Each complex has bound Util.COMPLEX_BOUND
     * @param dimension positive number
     */
    public static Vector randVector(int dimension) {
        var coordinates = new ArrayList<Complex>(dimension);
        for (int i = 0; i < dimension; i++) {
            coordinates.add(Util.randComplex(Util.COMPLEX_BOUND));
        }

        return new Vector(coordinates);
    }


    public static List<Vector> randData(int n) {
        var result = new ArrayList<Vector>(n);

        for (int i = 0; i < n; i++) {
            result.add(Util.randVector(
                    random.nextInt(Util.DIMENSION_BOUND - 1) + 1
            ));
        }
        return result;
    }

    public static List<Vector> randData(int n, int dimension) {
        var result = new ArrayList<Vector>(n);
        for (int i = 0; i < n; i++) {
            result.add(Util.randVector(1));
        }

        return result;
    }

    /**
     * Generates random universal hash function
     * @param m number of cells in hash table
     */
    public static Universal randUHFunction(int m) {
        return new Universal(
                random.nextInt(Universal.P - 1) + 1,
                random.nextInt(Universal.P),
                m
        );
    }

    /**
     * list of n {null, null, ..., null}
     */
    public static List<Vector> emptyRowHashList(int n) {
        var result = new ArrayList<Vector>(n);
        for (int i = 0; i < n; i++) {
            result.add(null);
        }

        return result;
    }

    /**
     * Converts s to a complex number
     * @param s if not convertable throws RuntimeException
     */
    public static Complex parseComplex(String s) {
        var str = s.substring(0, s.length() - 1);
        int idx = 0;
        boolean isNegative = false;
        // find the index idx where to split
        for (int i = str.length() - 1; i >= 0; i--) {
            if (str.charAt(i) == '-') {
                idx = i; isNegative = true;
                break;
            } else if (str.charAt(i) == '+') {
                idx = i;
                break;
            }
        }
        var real = Integer.parseInt(str.substring(0, idx));
        var img = Integer.parseInt(str.substring(idx + 1)) * (isNegative ? -1 : 1);

        return new Complex(real, img);
    }

    /**
     * Converts string s to a vector of complex numbers
     * @param s examples: '()', '(0+0i;-5-14i;7+3i)'
     * @throws RuntimeException s is not parsable
     */
    public static Vector parseVector(String s) {
        return new Vector(Stream.of(s.substring(1, s.length() - 1).split(";")).map(Util::parseComplex).toList());
    }
}
