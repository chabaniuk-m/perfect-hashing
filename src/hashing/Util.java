package hashing;

import hashing.key.Complex;
import hashing.key.Vector;
import hashing.hash.Universal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class for static utility functions
 * and variables
 */
public class Util {
    public final static Random random = new Random();
    // for generating random data
    public final static int COMPLEX_BOUND = 2;
    public final static int DIMENSION_BOUND = 5;

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
}
