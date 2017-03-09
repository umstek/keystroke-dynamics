/*
 * 
 * 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.features;

import java.io.Serializable;
import java.util.HashMap;
import lk.umstek.biometrics.keystrokedynamics.util.Pair;

/**
 *
 * @author wickramaranga
 */
public class FeatureModel implements Serializable {

    private HashMap<Integer, Long> keyDurationAvg;
    private HashMap<Pair, Long> digraphDelayAvg;

    public FeatureModel() {
        this.keyDurationAvg = new HashMap<>();
        this.digraphDelayAvg = new HashMap<>();
    }

    /**
     * @return the keyDurationAvg
     */
    public HashMap<Integer, Long> getKeyDurationAvg() {
        return keyDurationAvg;
    }

    /**
     * @param keyDurationAvg the keyDurationAvg to set
     */
    public void setKeyDurationAvg(HashMap<Integer, Long> keyDurationAvg) {
        this.keyDurationAvg = keyDurationAvg;
    }

    /**
     * @return the digraphDelayAvg
     */
    public HashMap<Pair, Long> getDigraphDelayAvg() {
        return digraphDelayAvg;
    }

    /**
     * @param digraphDelayAvg the digraphDelayAvg to set
     */
    public void setDigraphDelayAvg(HashMap<Pair, Long> digraphDelayAvg) {
        this.digraphDelayAvg = digraphDelayAvg;
    }

}
