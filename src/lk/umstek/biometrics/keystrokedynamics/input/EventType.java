/*
 * EventType.java
 * Holds the state of the event, i.e.: whether the key was pressed or was
 * released. 
 */
package lk.umstek.biometrics.keystrokedynamics.input;

/**
 * Pressed or released?
 *
 * @author wickramaranga
 */
public enum EventType {

    /**
     * User has pressed the button.
     */
    KeyDown,
    /**
     * User has released the button.
     */
    KeyUp
}
