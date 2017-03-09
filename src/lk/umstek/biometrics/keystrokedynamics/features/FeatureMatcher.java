/*
 * 
 * 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.features;

import lk.umstek.biometrics.keystrokedynamics.util.Pair;

/**
 *
 * @author wickramaranga
 */
public class FeatureMatcher {

    public static final int KEY_DURATION_TOLERENCE = 25;
    public static final int DIGRAPH_DELAY_TOLERENCE = 20;

    public static double match(FeatureModel actual, FeatureModel reference) {
        int durationMatches = 0;
        int durationMismatches = 0;

        int delayMatches = 0;
        int delayMismatches = 0;

        for (int i : actual.getKeyDurationAvg().keySet()) {
            if (reference.getKeyDurationAvg().containsKey(i)) {
                if (Math.abs(reference.getKeyDurationAvg().get(i) - actual.getKeyDurationAvg().get(i)) < KEY_DURATION_TOLERENCE) {
                    durationMatches++;
                } else {
                    durationMismatches++;
                }
            } else {
                durationMismatches++;
            }
        }

        for (Pair pair : actual.getDigraphDelayAvg().keySet()) {
            if (reference.getDigraphDelayAvg().containsKey(pair)) {
                if (Math.abs(reference.getDigraphDelayAvg().get(pair) - actual.getDigraphDelayAvg().get(pair)) < DIGRAPH_DELAY_TOLERENCE) {
                    delayMatches++;
                } else {
                    delayMismatches++;
                }
            } else {
                delayMismatches++;
            }
        }

        return (durationMatches / (durationMismatches * 1.0 + durationMatches)) + 1.5 * (delayMatches / (delayMatches * 1.0 + delayMismatches));
    }
}
