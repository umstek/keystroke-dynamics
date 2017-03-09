/*
 * EventCollector.java
 * Keeps key events recorded. 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.input;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Collect information about key presses or releases.
 *
 * @author wickramaranga
 */
public class EventCollector {

    // The key which was released last and the time when it was released. 
    private int lastKey;
    private long lastKeyReleaseTime;
    // Whether a key has been pressed before the previous key is released. 
    private boolean overlap;
    private int lastKeyBackup; // helps handle overlap
    // Temporary map of currently pressed keys and the time they were pressed. 
    private final ConcurrentHashMap<Integer, Long> pressedKeys;

    // Total durations which each of the keys has been pressed.
    private final ConcurrentHashMap<Integer, Long> totalKeyDurations;
    // How many samples were counted, so we can get an average later. 
    private final ConcurrentHashMap<Integer, Integer> totalKeyCounts;

    // Delays between each pair of letters. 
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> digraphDelays;
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> digraphCounts;

    /**
     * Creates a new EventCollector.
     */
    public EventCollector() {
        lastKey = -1;
        lastKeyReleaseTime = -1;
        overlap = false;
        lastKeyBackup = -1;
        pressedKeys = new ConcurrentHashMap<>();
        totalKeyDurations = new ConcurrentHashMap<>();
        totalKeyCounts = new ConcurrentHashMap<>();

        digraphDelays = new ConcurrentHashMap<>();
        digraphCounts = new ConcurrentHashMap<>();
    }

    /**
     * Notifies that a key has been pressed.
     *
     * @param keyEvent
     * @param isError
     */
    public void press(java.awt.event.KeyEvent keyEvent, boolean isError) {
        Long time = System.currentTimeMillis();//System.nanoTime();
        int code = keyEvent.getKeyCode();

        // System.out.println(keyEvent.paramString());
        if (!isError) {
            // A key must be released to be pressed again; So,
            // pressedKeys will never have key code. 
            pressedKeys.put(code, time);

            // not the first ever key press
            // neither there is an overlap
            if (lastKey != -1 && !pressedKeys.contains(lastKey)) {
                long duration = time - lastKeyReleaseTime;

                if (digraphCounts.containsKey(lastKey)) {
                    if (digraphCounts.get(lastKey).contains(code)) {
                        digraphDelays.get(lastKey).put(code, digraphDelays.get(lastKey).get(code) + duration);
                        digraphCounts.get(lastKey).put(code, digraphCounts.get(lastKey).get(code) + 1);
                    } else {
                        digraphDelays.get(lastKey).put(code, duration);
                        digraphCounts.get(lastKey).put(code, 1);
                    }
                } else {
                    ConcurrentHashMap<Integer, Long> secondGraphDelay = new ConcurrentHashMap<>();
                    secondGraphDelay.put(code, duration);
                    digraphDelays.put(lastKey, secondGraphDelay);
                    ConcurrentHashMap<Integer, Integer> secondGraphCount = new ConcurrentHashMap<>();
                    secondGraphCount.put(code, 1);
                    digraphCounts.put(lastKey, secondGraphCount);
                }
            } else if (lastKey != 1) {
                lastKeyBackup = lastKey; // because we're going to change it
                overlap = true; // let it happen when the key is released
            }

            // set the last pressed key
            lastKey = code;
        }

    }

    /**
     * Notifies that a key has been released.
     *
     * @param keyEvent
     * @param isError
     */
    public void release(java.awt.event.KeyEvent keyEvent, boolean isError) {
        Long time = System.currentTimeMillis();//System.nanoTime();
        int code = keyEvent.getKeyCode();

        // System.out.println(keyEvent.paramString());
        if (!isError) {
            // A key must be pressed in order to be released; so, 
            // the key code is guaranteed to exist. 
            long duration = time - pressedKeys.get(code);

            // System.out.println(keyEvent.getKeyChar() + " pressed for " + duration);
            if (totalKeyCounts.containsKey(code)) {
                totalKeyCounts.put(code, totalKeyCounts.get(code) + 1);
                totalKeyDurations.put(code, duration + totalKeyDurations.get(code));
            } else {
                totalKeyCounts.put(code, 1);
                totalKeyDurations.put(code, duration);
            }

            if (overlap) {
                // assume that the othe overlapping key is still pressed
                long delay = pressedKeys.get(lastKey) - time; // minus value
                if (digraphCounts.containsKey(lastKeyBackup)) {
                    if (digraphCounts.get(lastKeyBackup).contains(lastKey)) {
                        digraphDelays.get(lastKeyBackup).put(lastKey, digraphDelays.get(lastKeyBackup).get(lastKey) + delay);
                        digraphCounts.get(lastKeyBackup).put(lastKey, digraphCounts.get(lastKeyBackup).get(lastKey) + 1);
                    } else {
                        digraphDelays.get(lastKeyBackup).put(lastKey, delay);
                        digraphCounts.get(lastKeyBackup).put(lastKey, 1);
                    }
                } else {
                    ConcurrentHashMap<Integer, Long> secondGraphDelay = new ConcurrentHashMap<>();
                    secondGraphDelay.put(lastKey, delay);
                    digraphDelays.put(lastKeyBackup, secondGraphDelay);
                    ConcurrentHashMap<Integer, Integer> secondGraphCount = new ConcurrentHashMap<>();
                    secondGraphCount.put(lastKey, 1);
                    digraphCounts.put(lastKeyBackup, secondGraphCount);
                }

                // reset overlap
                overlap = false;
                lastKeyBackup = -1;
            }

            lastKeyReleaseTime = time;
            pressedKeys.remove(code);
        }
    }

//    public void log() {
//        totalKeyCounts.forEach((t, u) -> {
//            System.out.println("Code: " + t + "; Avg: " + totalKeyDurations.get(t) / u);
//        });
//
//        digraphCounts.forEach((k, h) -> {
//            h.forEach((t, u) -> {
//                System.out.println(k + "->" + t + " = " + digraphDelays.get(k).get(t) / u);
//            });
//        });
//    }
    /**
     * @return the totalKeyDurations
     */
    public ConcurrentHashMap<Integer, Long> getTotalKeyDurations() {
        return totalKeyDurations;
    }

    /**
     * @return the totalKeyCounts
     */
    public ConcurrentHashMap<Integer, Integer> getTotalKeyCounts() {
        return totalKeyCounts;
    }

    /**
     * @return the digraphDelays
     */
    public ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> getDigraphDelays() {
        return digraphDelays;
    }

    /**
     * @return the digraphCounts
     */
    public ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Integer>> getDigraphCounts() {
        return digraphCounts;
    }
}
