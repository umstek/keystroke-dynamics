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

    public static final int KEY_DURATION_TOLERANCE = 25;
    public static final int DIGRAPH_DELAY_TOLERANCE = 30;

    public static final double KEY_DURATION_IMPACT = 1.5;
    public static final double DIGRAPH_DELAY_IMPACT = 1 / 2.5;

    public static final double MATCH_TOLERANCE = 0.95;

    public static boolean match(FeatureModel actual, FeatureModel reference) {
        int durationMatches = 0;
        int durationMismatches = 0;

        int delayMatches = 0;
        int delayMismatches = 0;

        // Counts the number of times when the key press of the user is within 
        // the expected tolerance with respective to the reference value. 
        // Each key is considered separately. 
        for (int i : actual.getKeyDurationAvg().keySet()) {
            if (reference.getKeyDurationAvg().containsKey(i)) {
                if (Math.abs(reference.getKeyDurationAvg().get(i) - actual.getKeyDurationAvg().get(i)) < KEY_DURATION_TOLERANCE) {
                    durationMatches++;
                } else {
                    durationMismatches++;
                }
            } else {
                // There is no reference value for the duration of this key press
            }
        }

        // Counts the number of times when the digraph delay of the user is 
        // within the expected tolerance with respective to the reference value. 
        // Each digram is considered separately. 
        for (Pair pair : actual.getDigraphDelayAvg().keySet()) {
            if (reference.getDigraphDelayAvg().containsKey(pair)) {
                if (Math.abs(reference.getDigraphDelayAvg().get(pair) - actual.getDigraphDelayAvg().get(pair)) < DIGRAPH_DELAY_TOLERANCE) {
                    delayMatches++;
                } else {
                    delayMismatches++;
                }
            } else {
                // There is no reference value for the delay between this digram
            }
        }

        // Key duration and digraph delay may contribute to the match in different ratios
        double f = KEY_DURATION_IMPACT * durationMatches / (durationMismatches + durationMatches)
                + DIGRAPH_DELAY_IMPACT * delayMatches / (delayMismatches + delayMatches);

        // Default: > 0.95 match is a yes. 
        // Set to KEY_DURATION_IMPACT*(1/2) + DIGRAPH_DELAY_IMPACT*(1/2) which
        // is a 50% match. 
        return f >= MATCH_TOLERANCE;
    }
}
