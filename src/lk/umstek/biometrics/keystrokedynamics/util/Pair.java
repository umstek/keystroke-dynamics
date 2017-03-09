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
 */
public class Pair implements Serializable {

    private final int left;
    private final int right;

    /**
     *
     * @param left
     * @param right
     */
    public Pair(int left, int right) {
        this.left = left;
        this.right = right;
    }

    /**
     *
     * @return
     */
    public int getLeft() {
        return left;
    }

    /**
     *
     * @return
     */
    public int getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left ^ right;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) {
            return false;
        }
        Pair pairo = (Pair) o;
        return this.left == pairo.getLeft()
                && this.right == pairo.getRight();
    }

}
