/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midimessagemonitor;

/**
 *
 * @author s2-vora
 */
public class ReturnTime {

    private long sysTimeSeconds;
    private long noteTime;
    private long channelPressTime;
    private long controlChangeTime;
    private long polyKeyTime;
    private long pitchBendTime;
    private long progChangeTime;
    private long clearSeconds;

    /*
     * Constructor used to set the seconds in which the 
     * data within the MidiMessageWindow is allowed to be displayed for.
     */
    public ReturnTime(long seconds) {
        //Store the amount of seconds the message is displayed for in clearSeconds.
        this.clearSeconds = seconds;
    }

    /*
     * setNoteTime method is used to reset the timer for removing 
     * the note messages from the MidiMessageWindow window pane.
     */
    public void setNoteTime() {
        //Resets the note clear timer by adding the seconds from clearSeconds to the current system time. 
        this.noteTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * setChannelPressureTime is used to reset the channel pressure note timer.
     */
    public void setChannelPressureTime() {
        // Resets the Channel Message time by adding the seconds from clearSeconds to the current system time.
        this.channelPressTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * setControlChangeTime is used to reset the control change note timer.
     */
    public void setControlChangeTime() {
        // Resets the Channel Message time by adding the seconds from clearSeconds to the current system time.
        this.controlChangeTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * setPolyKeyTime is used to reset the poly key note timer.
     */
    public void setPolyKeyTime() {
        this.polyKeyTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * setPitchBendTime is used to reset the pitch bend note timer.
     */
    public void setPitchBendTime() {
        this.pitchBendTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * setProgramChangeTime is used to reset the program change note timer.
     */
    public void setProgramChangeTime() {
        this.progChangeTime = this.sysTimeSeconds + this.clearSeconds;
    }

    /*
     * clearNotes method checks the current system time against the noteTime variable
     * to see if they are both are equal. If they are then call clear the note data
     * on the MidiMonitorWindow pane.
     */
    public void clearNotes(MidiMonitorWindow window, long sysTime) {
        // Set sysTimeSeconds to the current system time in seconds. 
        this.sysTimeSeconds = sysTime / 1000;

        // If noteTime is equal to sysTimeSeconds then clear the MidiMonitorWindow pane.
        if (this.noteTime == this.sysTimeSeconds) {
            window.clearNoteData();
        }
    }

    /*
     * clearChannelPres checks if the current system time is equal to the
     * channelPressTime variable. If it is then clear the channel pressure note.
     */
    public void clearChannelPres(MidiMonitorWindow window, long sysTime) {
        
        // Set sysTimeSeconds to the current system time in seconds.
        this.sysTimeSeconds = sysTime / 1000;

        if (this.channelPressTime == this.sysTimeSeconds) {
            window.clearChannelPressure();
        }
    }

    /*
     * clearPolyKey checks if the current system time is equal to the
     * polyKeyTime variable. If it is then clear the poly key pressure note.
     */
    public void clearPolyKey(MidiMonitorWindow window, long sysTime) {
        
        // Set sysTimeSeconds to the current system time in seconds.
        this.sysTimeSeconds = sysTime / 1000;

        if (this.polyKeyTime == this.sysTimeSeconds) {
            window.clearPolyKeyPressure();
        }
    }

    /*
     * clearProgChange checks if the current system time is equal to the
     * progChangeTime variable. If it is then clear the program change note.
     */
    public void clearProgChange(MidiMonitorWindow window, long sysTime) {
        
        // Set sysTimeSeconds to the current system time in seconds.
        this.sysTimeSeconds = sysTime / 1000;

        if (this.progChangeTime == this.sysTimeSeconds) {
            window.clearProgramChange();
        }
    }

    /*
     * clearPitchBend checks if the current system time is equal to the
     * pitchBendTime variable. If it is then clear the pitch bend note.
     */
    public void clearPitchBend(MidiMonitorWindow window, long sysTime) {
        
        // Set sysTimeSeconds to the current system time in seconds.
        this.sysTimeSeconds = sysTime / 1000;

        if (this.pitchBendTime == this.sysTimeSeconds) {
            window.clearPitchBend();
        }
    }

    /*
     * clearControlChange checks if the current system time is equal to the
     * controlChangeTime variable. If it is then clear the control change note.
     */
    public void clearControlChange(MidiMonitorWindow window, long sysTime) {

        // Set sysTimeSeconds to the current system time in seconds. 
        this.sysTimeSeconds = sysTime / 1000;

        if (this.controlChangeTime == this.sysTimeSeconds) {
            window.clearControlChange();
        }

    }
}//class

