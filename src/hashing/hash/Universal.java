package hashing.hash;

import hashing.key.Complex;
import hashing.key.Vector;

/**
 * universal hash function
 * for complex numbers
 */
public class Universal {
    private int a;
    private int b;
    public static final int P = Integer.MAX_VALUE;                  // 2147483647 is a (proven) prime
    private int m;                                                  // number of cells in hash table

    public Universal(int a, int b, int m) {
        this.a = a;
        this.b = b;
        this.m = m;
    }

    /**
     * Calculate hash for the complex number c
     * using universal hash mehtod
     */
    public int calcHash(Complex c) {

        return calcHash(Math.abs(
                c.hashCode()));
    }

    /**
     * Calculates hash using universal hash method
     * @param v vector of complex numbers
     * @return index in range [0, m-1]
     */
    public int calcHash(Vector v) {
        if (v.size() == 0) return 0;

        return calcHash(Math.abs(
                v.hashCode()));
    }

    private int calcHash(int k) {
        return (int) ( ( (long)a * k + b ) % P % m );
    }

    @Override
    public String toString() {

        return "u(k) = (a*k+b)mod p mod m; " +
                "a=" + a +
                ", b=" + b +
                ", p=" + P +
                ", m=" + m;
    }
}
