/*
 *
 * 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.util;

import java.io.Serializable;

/**
 *
 * @author wickramaranga
 * @param <L>
 * @param <R>
 */
public class Pair<L, R> implements Serializable {

    private final L left;
    private final R right;

    /**
     *
     * @param left
     * @param right
     */
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    /**
     *
     * @return
     */
    public L getLeft() {
        return left;
    }

    /**
     *
     * @return
     */
    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft())
                && this.right.equals(pairo.getRight());
    }

}
