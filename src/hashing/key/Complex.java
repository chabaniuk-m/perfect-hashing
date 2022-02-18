package hashing.key;

/**
 * Immutable complex number
 */
public class Complex {
    private final int real;
    private final int img;

    public Complex(int real, int img) {
        this.real = real;
        this.img = img;
    }

    public int getReal() {
        return real;
    }

    public int getImg() {
        return img;
    }

    @Override
    // tested
    public int hashCode() {
        int hash = 23;
        hash = hash * 31 + real;
        hash = hash * 31 + img;

        return hash;
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

        Complex clx = (Complex) o;

        // hash code check
        if (this.hashCode() != clx.hashCode()) return false;

        return  this.real == clx.real &&
                this.img == clx.img;
    }

    @Override
    // tested
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(real);
        if (img >= 0) {
            sb.append('+');
        }
        sb.append(img).append('i');

        return sb.toString();
    }

    @Override
    public Complex clone() {
        return new Complex(real, img);
    }
}
