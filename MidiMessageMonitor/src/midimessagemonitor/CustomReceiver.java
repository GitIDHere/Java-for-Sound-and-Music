/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package midimessagemonitor;

import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;

/**
 *
 * @author s2-vora
 */
public class CustomReceiver implements Receiver {

    //Create an object of type Buffer.
    private Buffer messBuffer;

    /*
     * Constructor is used to instantiate the Buffer
     * object to handle to MIDI mssage byte array.
     */
    public CustomReceiver(Buffer mess) {
        this.messBuffer = mess;
    }
    
    /*
     * The send method of the CustomReceiver used to 
     * insert MIDI message byte array into the buffer.
     */
    @Override
    public void send(MidiMessage message, long l) {
        messBuffer.put(message);
    }

    // A method to close the CustomeReceiver.
    @Override
    public void close() {
    }
}
