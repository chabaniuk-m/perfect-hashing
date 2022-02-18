package hashing.key;

import java.util.List;
import java.util.Objects;

/**
 * vector of complex numbers
 */
public class Vector {
    private final List<Complex> vector;

    public Vector(List<Complex> list) {
        vector = list.stream().toList();
    }

    /**
     * Dimension of vector
     */
    public int size() {
        return vector.size();
    }

    /**
     * The same as vector[k]
     * @param k index of coordinate
     * @return copy of k-th coordinate
     */
    public Complex at(int k) {
        if (!(0 <= k && k < size())) {
            System.out.printf("ERROR: index%d out of bound for vector %s\n", k, toString());
//            throw new IndexOutOfBoundsException();
        }
        return vector.get(k).clone();
    }

    @Override
    public boolean equals(Object o) {
        // self check
        if (this == o) return true;
        // null check
        if (o == null) return false;
        // type check and cast
        if (getClass() != o.getClass())
            return false;
        Vector vect = (Vector) o;

        // hash code check
        if (this.hashCode() != o.hashCode()) return false;

        // dimension check
        if (this.vector.size() != vect.size()) return false;

        // deep check
        for (int i = 0; i < this.vector.size(); i++) {

            if (!Objects.equals(this.at(i), vect.at(i))) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        int size = vector.size();
        if (size == 0) return "([empty])";
        if (size == 1) return "(" + at(0) + ")";

        StringBuilder sb = new StringBuilder("(").append(at(0).toString());

        for (int i = 1; i < size; i++) {
            sb.append(";").append(at(i).toString());
        }

        return sb.append(")").toString();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (var clx :
                vector) {
            hash = hash * 31 + clx.hashCode();
        }
        return hash;
    }
}
