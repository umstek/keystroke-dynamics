/*
 * EventCollector.java
 * Keeps key events recorded. 
 * 
 */
package lk.umstek.biometrics.keystrokedynamics.input;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collect information about key presses or releases.
 *
 * @author wickramaranga
 */
public class EventCollector {

    // Last pressed keys in the pressed order
    private final LinkedList<Integer> pressed;
    // Currently pressed keys and the time they were pressed: KeyCode, TimeStamp
    private final ConcurrentHashMap<Integer, Long> pressedKeys;

    // Total durations which each of the keys has been pressed.
    private final ConcurrentHashMap<Integer, Long> totalKeyDurations;
    // How many samples were counted, so we can get an average later. 
    private final ConcurrentHashMap<Integer, Integer> totalKeyCount;

    // Delays between each pair of letters. 
    private final ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Long>> digraphDelays;

    /**
     * Creates a new EventCollector.
     */
    public EventCollector() {
        pressed = new LinkedList<>();
        pressedKeys = new ConcurrentHashMap<>();
        totalKeyDurations = new ConcurrentHashMap<>();
        totalKeyCount = new ConcurrentHashMap<>();

        digraphDelays = new ConcurrentHashMap<>();
    }

    /**
     * Notifies that a key has been pressed.
     *
     * @param keyEvent
     * @param isError
     */
    public void press(java.awt.event.KeyEvent keyEvent, boolean isError) {
        Long time = System.currentTimeMillis();//System.nanoTime();

        // System.out.println(keyEvent.paramString());
        if (!isError) {
            // A key must be released to be pressed again; So,
            // pressedKeys will never have key code. 
            pressedKeys.put(keyEvent.getKeyCode(), time);
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

            System.out.println(keyEvent.getKeyChar() + " Pressed for " + duration);

            if (totalKeyCount.containsKey(code)) {
                totalKeyCount.put(code, totalKeyCount.get(code) + 1);
                totalKeyDurations.put(code, duration + totalKeyDurations.get(code));
            } else {
                totalKeyCount.put(code, 1);
                totalKeyDurations.put(code, duration);
            }
        }
    }
}
