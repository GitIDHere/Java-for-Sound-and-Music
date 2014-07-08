package midimessagemonitor;

import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;

public class MessageThread extends Thread {

    Buffer buffer;
    MidiMonitorWindow monitorWindow;
    ReturnTime messageTime;

    /*
     * Constructor instantiates the byteBuffer variable, MidiMonitorWindow variable, 
     * and ReturnTime variable to use within this Thread. 
     */
    public MessageThread(Buffer byteBuffer, MidiMonitorWindow window, ReturnTime messTime) {
        this.buffer = byteBuffer;
        this.messageTime = messTime;
        this.monitorWindow = window;

    }

    /*
     * A mandatory run function that's synchronized is used to retreive the MIDI
     * message byte array from the buffer, and insert specific bytes from the MIDI
     * message byte array into the MidiMessageMonitor methods to display within the
     * MidiMonitorWindow.
     */
    @Override
    public synchronized void run() {

        // Run the Thread in an infinite loop so that it does not stop processing. 
        // If it does then it cannot be restarted again within the same file run.
        while (true) {

            // Set the current system time into the time variable.
            long time = System.currentTimeMillis();

            // Retrive a MIDI message byte array from the buffer.
            byte[] byteMess = buffer.get();

            // Check if the retrieved byte array is not empty.
            if (byteMess != null) {

                // Bitmask the first byte in the byte array to get the status
                // int and the channel int.
                int statusTopFourBits = byteMess[0] & 0xF0;
                
                int status = byteMess[0] & 0xFF;
                int channel = byteMess[0] & 0x0F;
                int data1 = 0;
                int data2 = 0;

                /*
                 * Check the length of the MIDI message byte array, and assign
                 * data1 and data2 accordingly. In some circumstances, the length
                 * of the Midi message byte array varies, that is why we need this check.
                 */
                if (byteMess.length >= 3) {
                    data2 = byteMess[2];
                }

                if (byteMess.length >= 2) {
                    data1 = byteMess[1];
                }

                // This switch statement is used to determine which message is shown in the 
                // MidiMessageWindow by looking at the first four bits of the MIDI message byte array.
                switch (statusTopFourBits) {
                    
                    case ShortMessage.NOTE_ON:
                        // This if statement checks if data2 (velocity) is 0 - displaying a note off message,
                        // else show a note on message.
                        if (data2 == 0) {
                            monitorWindow.showNoteOff(channel, data1, data2);
                            messageTime.setNoteTime();
                        } else {
                            monitorWindow.showNoteOn(channel, data1, data2);
                            messageTime.setNoteTime();
                        }
                        break;
                    case ShortMessage.NOTE_OFF:
                        monitorWindow.showNoteOff(channel, data1, data2);
                        messageTime.setNoteTime();
                        break;
                    case ShortMessage.PITCH_BEND:
                        monitorWindow.showPitchBend(channel, data1, data2);
                        messageTime.setPitchBendTime();
                        break;
                    case ShortMessage.POLY_PRESSURE:
                        monitorWindow.showPolyKeyPressure(channel, data1, data2);
                        messageTime.setPolyKeyTime();
                        break;
                    case ShortMessage.CONTROL_CHANGE:
                        monitorWindow.showControlChange(channel, data1, data2);
                        messageTime.setControlChangeTime();
                        break;
                    case ShortMessage.PROGRAM_CHANGE:
                        monitorWindow.showProgramChange(channel, data1);
                        messageTime.setProgramChangeTime();
                        break;
                    case ShortMessage.CHANNEL_PRESSURE:
                        monitorWindow.showChannelPressure(channel, data1);
                        messageTime.setChannelPressureTime();
                        break;
                    default:
                        if (status == ShortMessage.ACTIVE_SENSING) {
                            monitorWindow.showActiveSensing();
                        } else if (status == ShortMessage.TIMING_CLOCK) {
                            monitorWindow.showTimingClock();
                        } else if (status == SysexMessage.SYSTEM_EXCLUSIVE) {
                            monitorWindow.showSystemExclusive();
                        } else {
                            monitorWindow.showOtherMessage();
                        }
                }// Switch(); 
            }//if()

            //Allow other threads to run.
            Thread.yield();

            // Run the appropriate method to clear the notes displayed on the Midimessagewindow.
            messageTime.clearNotes(monitorWindow, time);
            messageTime.clearChannelPres(monitorWindow, time);
            messageTime.clearControlChange(monitorWindow, time);
            messageTime.clearPolyKey(monitorWindow, time);
            messageTime.clearProgChange(monitorWindow, time);
            messageTime.clearPitchBend(monitorWindow, time);

        }// While();
    }// Run();
}//class()
