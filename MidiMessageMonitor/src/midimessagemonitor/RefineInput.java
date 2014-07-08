package midimessagemonitor;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * 
 */
public class RefineInput {

    public static MidiDevice.Info[] filtertrans(MidiDevice.Info[] infolist) {
        
        //Create a boolean array to represent whether to filter the dive or not.
        boolean[] copyElement = new boolean[infolist.length];
        int deviceCount = 0;
        
        //loop through each device in the list.
        for (int index = 0; index < infolist.length; index++) {
            
            try {
                //Get the deivce at position index in the device infolist.
                MidiDevice device = MidiSystem.getMidiDevice(infolist[index]);
                //Check whether the deivce has more than 0 Transmitter and no Receivers
                boolean hasTransmitter = (device.getMaxTransmitters() != 0 && device.getMaxReceivers() == 0);
                
                //If the device has a Transmitter then set copyElement at position index to true
                // and incriment the deivceCount variable.
                if (hasTransmitter) {
                    copyElement[index] = true;
                    deviceCount++;
                }
                
            }
            catch (MidiUnavailableException e) {
            }
            
        }   
        
        // create an array to hold the midi devices which are Transmitters.
        MidiDevice.Info[] outList = new MidiDevice.Info[deviceCount];
        int outIndex = 0;
        
        //Loop through through the legth of the infolist 
        for (int index = 0; index < infolist.length; index++) {
            
            /*
             * If we have set copyElement at position index to true then get 
             * the device from the infolist at position index and copy it to outList.
             */
            if (copyElement[index]) {
                outList[outIndex] = infolist[index];
                outIndex++;
            }
        }
        return outList;
    }//transmitter
    
}
