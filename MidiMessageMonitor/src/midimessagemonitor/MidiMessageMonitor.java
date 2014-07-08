/*
 * Main class for midi message monitor application
 */
package midimessagemonitor;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
/**
 * @author
 */
public class MidiMessageMonitor extends JApplet{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Instantiate a new MidiMonitorWindow object.
        MidiMonitorWindow window = new MidiMonitorWindow("MIDDI Message Monitor");

        // Aquire an array of devices from the computer.
        MidiDevice.Info[] infolist = MidiSystem.getMidiDeviceInfo();

        //Retreive a list of transmitter devices after filtering infolist.
        MidiDevice.Info[] TransInfoList = RefineInput.filtertrans(infolist);

        //Display a JOptionPane for the user to choose a transmitter (input) device. 
        MidiDevice.Info Transinfo = (MidiDevice.Info) JOptionPane.showInputDialog(null, "Please select a keyboard",
                "Keyboard select", JOptionPane.QUESTION_MESSAGE, null, TransInfoList, null);

        try {

            // Check if the uer has selected a transmitter device, else exit the program.
            if (Transinfo != null) {

                // Create and open the user selected MIDI transmitter device. 
                MidiDevice TransDevice = MidiSystem.getMidiDevice(Transinfo);
                TransDevice.open();

                //Instantiate a new Buffer object.
                Buffer buff = new Buffer();

                //Instantiate a transmitter object
                Transmitter transmitter = TransDevice.getTransmitter();

                // Instantiate the custom receiver, passing in a buffer object so that 
                // all messages from the transmitter are stored within the buffer.
                CustomReceiver messrec = new CustomReceiver(buff);

                //Set the transmitter to send data to custome recevier
                transmitter.setReceiver(messrec);

                //Instantiate a ReturnTime object passing in the seconds to clear stale data. 
                ReturnTime messageTimeObj = new ReturnTime(10);

                //Instantiate a MessageThread object passing in the required objects.
                MessageThread messageThread = new MessageThread(buff, window, messageTimeObj);

                //Run the MessageThread.
                messageThread.start();

            } else {
                System.exit(0);
            }
            // Catch the MidiUnavailableException thrown by getMidiDevice method of MidiSystem.    
        } catch (MidiUnavailableException e) {
            System.out.println(e);
        }
    }
}
