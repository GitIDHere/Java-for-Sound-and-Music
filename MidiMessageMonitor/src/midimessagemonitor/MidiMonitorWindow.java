/*
 * Midi Monitor Window
 */
package midimessagemonitor;

import java.text.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.border.*;

/**
 * Custom display component for Midi Message Monitor application.
 */
public class MidiMonitorWindow {

    private static final int PITCH_C = 0,
            PITCH_C_SHARP = 1,
            PITCH_D = 2,
            PITCH_D_SHARP = 3,
            PITCH_E = 4,
            PITCH_F = 5,
            PITCH_F_SHARP = 6,
            PITCH_G = 7,
            PITCH_G_SHARP = 8,
            PITCH_A = 9,
            PITCH_A_SHARP = 10,
            PITCH_B = 11;

    private MidiMonitorFrame frame;

    private JTextField[] textFields;

    private JCheckBox[] checkBoxes;

    private DecimalFormat formatter = new DecimalFormat (" ###;-###");

    private int sysexCount = 0;

    private int activeSensingCount = 0;

    private int timingClockCount = 0;

    private int otherMessageCount = 0;

    /**
     * Create a new display component with specified program title.
     */
    public MidiMonitorWindow (String title)
    {
        frame = new MidiMonitorFrame (title);
    } // MidiMonitorWindow ()

    private void selectNoteWidgets (int pitchLetter)
    {
        switch (pitchLetter) {
            case PITCH_C:
                JTextField[] tfc = {frame.cChan, frame.cPitch, frame.cVel};
                JCheckBox[] cbc = {frame.cOn, frame.cOff};
                textFields = tfc;
                checkBoxes = cbc;
                break;
            case PITCH_C_SHARP:
                JTextField[] tfcs = {frame.csChan, frame.csPitch, frame.csVel};
                JCheckBox[] cbcs = {frame.csOn, frame.csOff};
                textFields = tfcs;
                checkBoxes = cbcs;
                break;
            case PITCH_D:
                JTextField[] tfd = {frame.dChan, frame.dPitch, frame.dVel};
                JCheckBox[] cbd = {frame.dOn, frame.dOff};
                textFields = tfd;
                checkBoxes = cbd;
                break;
            case PITCH_D_SHARP:
                JTextField[] tfds = {frame.dsChan, frame.dsPitch, frame.dsVel};
                JCheckBox[] cbds = {frame.dsOn, frame.dsOff};
                textFields = tfds;
                checkBoxes = cbds;
                break;
            case PITCH_E:
                JTextField[] tfe = {frame.eChan, frame.ePitch, frame.eVel};
                JCheckBox[] cbe = {frame.eOn, frame.eOff};
                textFields = tfe;
                checkBoxes = cbe;
                break;
            case PITCH_F:
                JTextField[] tff = {frame.fChan, frame.fPitch, frame.fVel};
                JCheckBox[] cbf = {frame.fOn, frame.fOff};
                textFields = tff;
                checkBoxes = cbf;
                break;
            case PITCH_F_SHARP:
                JTextField[] tffs = {frame.fsChan, frame.fsPitch, frame.fsVel};
                JCheckBox[] cbfs = {frame.fsOn, frame.fsOff};
                textFields = tffs;
                checkBoxes = cbfs;
                break;
            case PITCH_G:
                JTextField[] tfg = {frame.gChan, frame.gPitch, frame.gVel};
                JCheckBox[] cbg = {frame.gOn, frame.gOff};
                textFields = tfg;
                checkBoxes = cbg;
                break;
            case PITCH_G_SHARP:
                JTextField[] tfgs = {frame.gsChan, frame.gsPitch, frame.gsVel};
                JCheckBox[] cbgs = {frame.gsOn, frame.gsOff};
                textFields = tfgs;
                checkBoxes = cbgs;
                break;
            case PITCH_A:
                JTextField[] tfa = {frame.aChan, frame.aPitch, frame.aVel};
                JCheckBox[] cba = {frame.aOn, frame.aOff};
                textFields = tfa;
                checkBoxes = cba;
                break;
            case PITCH_A_SHARP:
                JTextField[] tfas = {frame.asChan, frame.asPitch, frame.asVel};
                JCheckBox[] cbas = {frame.asOn, frame.asOff};
                textFields = tfas;
                checkBoxes = cbas;
                break;
            case PITCH_B:
                JTextField[] tfb = {frame.bChan, frame.bPitch, frame.bVel};
                JCheckBox[] cbb = {frame.bOn, frame.bOff};
                textFields = tfb;
                checkBoxes = cbb;
                break;
            default:
                JTextField[] tfx = {frame.cChan, frame.cPitch, frame.cVel};
                JCheckBox[] cbx = {frame.cOn, frame.cOff};
                textFields = tfx;
                checkBoxes = cbx;
        } // switch
    } // selectNoteWidgets ()

    private void selectPolyKeyPressure ()
    {
        JTextField[] tf = {frame.pkChan, frame.pkData1, frame.pkData2};
        textFields = tf;
        checkBoxes = null;
    } // selectPolyKeyPressure ()

    private void selectControlChange ()
    {
        JTextField[] tf = {frame.ccChan, frame.ccData1, frame.ccData2};
        textFields = tf;
        checkBoxes = null;
    } // selectControlChange ()

    private void selectProgramChange ()
    {
        JTextField[] tf = {frame.pcChan, frame.pcData1};
        textFields = tf;
        checkBoxes = null;
    } // selectProgramChange ()

    private void selectChannelPressure ()
    {
        JTextField[] tf = {frame.cpChan, frame.cpData1};
        textFields = tf;
        checkBoxes = null;
    } // selectChannelPressure ()

    private void selectPitchBend ()
    {
        JTextField[] tf = {frame.pbChan, frame.pbData1, frame.pbData2};
        textFields = tf;
        checkBoxes = null;
    } // selectPitchBend ()

    /**
     * Method which displays a Note On message.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param midiPitch The midi note pitch - the first data byte of the message
     * @param velocity The note velocity - the second data byte of the message
     */
    public void showNoteOn (int channel,
                            int midiPitch,
                            int velocity)
    {
        int pitchLetter = midiPitch % 12;
        selectNoteWidgets (pitchLetter);
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (midiPitch));
        textFields[2].setText (formatter.format (velocity));
        checkBoxes[0].setSelected (true);
        checkBoxes[1].setSelected (false);
    } // showNoteOn ()

    /**
     * Method which displays a Note Off message.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param midiPitch The midi note pitch - the first data byte of the message
     * @param velocity The note release velocity - the second data byte of the
     * message
     */
    public void showNoteOff (int channel,
                             int midiPitch,
                             int velocity)
    {
        int pitchLetter = midiPitch % 12;
        selectNoteWidgets (pitchLetter);
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (midiPitch));
        textFields[2].setText (formatter.format (velocity));
        checkBoxes[0].setSelected (false);
        checkBoxes[1].setSelected (true);
    } // showNoteOff ()

    private void clearNoteOnOffPitch (int pitchLetter)
    {
        selectNoteWidgets (pitchLetter);
        textFields[0].setText ("");
        textFields[1].setText ("");
        textFields[2].setText ("");
        checkBoxes[0].setSelected (false);
        checkBoxes[1].setSelected (false);
    } // clearNoteOnOffPitch ()

    /**
     * Method which clears all Note On and Off message data from the display.
     */
    public void clearNoteData ()
    {
        for (int pitch = 0; pitch < 12; pitch++) {
            clearNoteOnOffPitch (pitch);
        }
    } // clearNoteData ()

    /**
     * Method which displays a Poly Key Pressure message.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param data1 The note pitch - The first data byte of the message
     * @param data2 The note pressure - The second data byte of the message
     */
    public void showPolyKeyPressure (int channel,
                                     int data1,
                                     int data2)
    {
        selectPolyKeyPressure ();
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (data1));
        textFields[2].setText (formatter.format (data2));
    } // showPolyKeyPressure ()

    /**
     * Method which clears Poly Key Pressure message data from the display.
     */
    public void clearPolyKeyPressure ()
    {
        selectPolyKeyPressure ();
        textFields[0].setText ("");
        textFields[1].setText ("");
        textFields[2].setText ("");
    } // clearPolyKeyPressure ()

    /**
     * Method which displays a Control Change message.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param data1 The controller number - The first data byte of the message
     * @param data2 The new value - The second data byte of the message
     */
    public void showControlChange (int channel,
                                   int data1,
                                   int data2)
    {
        selectControlChange ();
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (data1));
        textFields[2].setText (formatter.format (data2));
    } // showControlChange ()

    /**
     * Method which clears Control Change message data from the display.
     */
    public void clearControlChange ()
    {
        selectControlChange ();
        textFields[0].setText ("");
        textFields[1].setText ("");
        textFields[2].setText ("");
    } // clearControlChange ()

    /**
     * Method which displays a Program Change message. This message has only one
     * data byte.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param data1 The program number - The first data byte of the message
     */
    public void showProgramChange (int channel,
                                   int data1)
    {
        selectProgramChange ();
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (data1));
    } // showProgramChange ()

    /**
     * Method which clears Program Change message data from the display.
     */
    public void clearProgramChange ()
    {
        selectProgramChange ();
        textFields[0].setText ("");
        textFields[1].setText ("");
    } // clearProgramChange ()

    /**
     * Method which displays a Channel Pressure message. This message has only
     * one data byte.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param data1 The note pitch - The first data byte of the message
     */
    public void showChannelPressure (int channel,
                                     int data1)
    {
        selectChannelPressure ();
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (data1));
    } // showChannelPressure ()

    /**
     * Method which clears Channel Pressure message data from the display.
     */
    public void clearChannelPressure ()
    {
        selectChannelPressure ();
        textFields[0].setText ("");
        textFields[1].setText ("");
    } // clearChannelPressure ()

    /**
     * Method which displays a Pitch Bend message.
     *
     * @param channel The channel number extracted from the bottom four bits of
     * the status byte
     * @param data1 Pitch bend MSB - The first data byte of the message
     * @param data2 Pitch bend LSB - The second data byte of the message
     */
    public void showPitchBend (int channel,
                               int data1,
                               int data2)
    {
        selectPitchBend ();
        textFields[0].setText (formatter.format (channel));
        textFields[1].setText (formatter.format (data1));
        textFields[2].setText (formatter.format (data2));
    } // showPitchBend ()

    /**
     * Method which clears Pitch Bend message data from the display.
     */
    public void clearPitchBend ()
    {
        selectPitchBend ();
        textFields[0].setText ("");
        textFields[1].setText ("");
        textFields[2].setText ("");
    } // clearPitchBend ()

    /**
     * Method to increment the System Exclusive message counter. <p>It does not
     * display the message data and so it does not have any parameters.
     */
    public void showSystemExclusive ()
    {
        sysexCount++;
        frame.sysexCount.setText (formatter.format (sysexCount));
    } // showSystemExclusive ()

    /**
     * Method which clears the System Exclusive message counter to zero
     */
    public void clearSystemExclusive ()
    {
        sysexCount = 0;
        frame.sysexCount.setText ("");
    } // clearSystemExclusive ()

    /**
     * Method to increment the Active Sensing message counter. <p>The Active
     * Sensing message does not have any data so this method does not need any
     * parameters.
     */
    public void showActiveSensing ()
    {
        activeSensingCount++;
        frame.asCount.setText (formatter.format (activeSensingCount));
    } // showActiveSensing ()

    /**
     * Method which clears the Active Sensing message counter to zero
     */
    public void clearActiveSensing ()
    {
        activeSensingCount = 0;
        frame.asCount.setText ("");
    } // clearActiveSensing ()

    /**
     * Method to increment the Timing Clock message counter. <p>The Timing Clock
     * message does not have any data so this method does not need any
     * parameters.
     */
    public void showTimingClock ()
    {
        timingClockCount++;
        frame.tcCount.setText (formatter.format (timingClockCount));
    } // showTimingClock ()

    /**
     * Method which clears the Timing Clock message counter to zero
     */
    public void clearTimingClock ()
    {
        timingClockCount = 0;
        frame.tcCount.setText ("");
    } // clearTimingClock ()

    /**
     * Method to increment the "Other messages" counter. <p>It does not display
     * the message data and so it does not have any parameters.
     */
    public void showOtherMessage ()
    {
        otherMessageCount++;
        frame.omCount.setText (formatter.format (otherMessageCount));
    } // showOtherMessage ()

    /**
     * Method which clears the "Other messages" counter to zero
     */
    public void clearOtherMessage ()
    {
        otherMessageCount = 0;
        frame.omCount.setText ("");
    } // clearOtherMessage ()
} // class MidiMonitorWindow

// Source file generated by UWE GUI BUILDER Version 1.6 beta  Free-Ware
// File Pass 1...(Class file creation)
class MidiMonitorFrame extends JFrame
        implements ActionListener, WindowListener {

    // Listener objects
    ActionListener actionListener = this;

    WindowListener windowListener = this;

    // Components for Panel notePanel
    JPanel notePanel = new JPanel ();

    JLabel label1 = new JLabel ("C", JLabel.CENTER);

    JTextField cChan = new JTextField ("   ", 3);

    JTextField cPitch = new JTextField ("   ", 3);

    JTextField cVel = new JTextField ("   ", 3);

    JCheckBox cOn = new JCheckBox ("On");

    JCheckBox cOff = new JCheckBox ("Off");

    JLabel label2 = new JLabel ("C#", JLabel.CENTER);

    JTextField csChan = new JTextField ("   ", 3);

    JTextField csPitch = new JTextField ("   ", 3);

    JTextField csVel = new JTextField ("   ", 3);

    JCheckBox csOn = new JCheckBox ("On");

    JCheckBox csOff = new JCheckBox ("Off");

    JLabel label3 = new JLabel ("D", JLabel.CENTER);

    JTextField dChan = new JTextField ("   ", 3);

    JTextField dPitch = new JTextField ("   ", 3);

    JTextField dVel = new JTextField ("   ", 3);

    JCheckBox dOn = new JCheckBox ("On");

    JCheckBox dOff = new JCheckBox ("Off");

    JLabel label4 = new JLabel ("D#", JLabel.CENTER);

    JTextField dsChan = new JTextField ("   ", 3);

    JTextField dsPitch = new JTextField ("   ", 3);

    JTextField dsVel = new JTextField ("   ", 3);

    JCheckBox dsOn = new JCheckBox ("On");

    JCheckBox dsOff = new JCheckBox ("Off");

    JLabel label5 = new JLabel ("E", JLabel.CENTER);

    JTextField eChan = new JTextField ("   ", 3);

    JTextField ePitch = new JTextField ("   ", 3);

    JTextField eVel = new JTextField ("   ", 3);

    JCheckBox eOn = new JCheckBox ("On");

    JCheckBox eOff = new JCheckBox ("Off");

    JLabel label6 = new JLabel ("F", JLabel.CENTER);

    JTextField fChan = new JTextField ("   ", 3);

    JTextField fPitch = new JTextField ("   ", 3);

    JTextField fVel = new JTextField ("   ", 3);

    JCheckBox fOn = new JCheckBox ("On");

    JCheckBox fOff = new JCheckBox ("Off");

    JLabel label7 = new JLabel ("F#", JLabel.CENTER);

    JTextField fsChan = new JTextField ("   ", 3);

    JTextField fsPitch = new JTextField ("   ", 3);

    JTextField fsVel = new JTextField ("   ", 3);

    JCheckBox fsOn = new JCheckBox ("On");

    JCheckBox fsOff = new JCheckBox ("Off");

    JLabel label8 = new JLabel ("G", JLabel.CENTER);

    JTextField gChan = new JTextField ("   ", 3);

    JTextField gPitch = new JTextField ("   ", 3);

    JTextField gVel = new JTextField ("   ", 3);

    JCheckBox gOn = new JCheckBox ("On");

    JCheckBox gOff = new JCheckBox ("Off");

    JLabel label9 = new JLabel ("G#", JLabel.CENTER);

    JTextField gsChan = new JTextField ("   ", 3);

    JTextField gsPitch = new JTextField ("   ", 3);

    JTextField gsVel = new JTextField ("   ", 3);

    JCheckBox gsOn = new JCheckBox ("On");

    JCheckBox gsOff = new JCheckBox ("Off");

    JLabel label10 = new JLabel ("A", JLabel.CENTER);

    JTextField aChan = new JTextField ("   ", 3);

    JTextField aPitch = new JTextField ("   ", 3);

    JTextField aVel = new JTextField ("   ", 3);

    JCheckBox aOn = new JCheckBox ("On");

    JCheckBox aOff = new JCheckBox ("Off");

    JLabel label11 = new JLabel ("A#", JLabel.CENTER);

    JTextField asChan = new JTextField ("   ", 3);

    JTextField asPitch = new JTextField ("   ", 3);

    JTextField asVel = new JTextField ("   ", 3);

    JCheckBox asOn = new JCheckBox ("On");

    JCheckBox asOff = new JCheckBox ("Off");

    JLabel label12 = new JLabel ("B", JLabel.CENTER);

    JTextField bChan = new JTextField ("   ", 3);

    JTextField bPitch = new JTextField ("   ", 3);

    JTextField bVel = new JTextField ("   ", 3);

    JCheckBox bOn = new JCheckBox ("On");

    JCheckBox bOff = new JCheckBox ("Off");

    // Components for Panel channelPanel
    JPanel channelPanel = new JPanel ();

    JLabel label13 = new JLabel ("Poly Key Pressure", JLabel.CENTER);

    JTextField pkChan = new JTextField ("  ", 2);

    JTextField pkData1 = new JTextField ("  ", 2);

    JTextField pkData2 = new JTextField ("  ", 2);

    JLabel label14 = new JLabel ("Control Change", JLabel.CENTER);

    JTextField ccChan = new JTextField ("  ", 2);

    JTextField ccData1 = new JTextField ("  ", 2);

    JTextField ccData2 = new JTextField ("  ", 2);

    JLabel label15 = new JLabel ("Program Change", JLabel.CENTER);

    JTextField pcChan = new JTextField ("  ", 2);

    JTextField pcData1 = new JTextField ("  ", 2);

    JLabel label16 = new JLabel ("Channel Pressure", JLabel.CENTER);

    JTextField cpChan = new JTextField ("  ", 2);

    JTextField cpData1 = new JTextField ("  ", 2);

    JLabel label17 = new JLabel ("Pitch Bend", JLabel.CENTER);

    JTextField pbChan = new JTextField ("  ", 2);

    JTextField pbData1 = new JTextField ("  ", 2);

    JTextField pbData2 = new JTextField ("  ", 2);

    // Components for Panel sysMessagesPanel
    JPanel sysMessagesPanel = new JPanel ();

    JLabel label18 = new JLabel ("System Exclusive", JLabel.CENTER);

    JTextField sysexCount = new JTextField ("   ", 3);

    JLabel label19 = new JLabel ("                 ", JLabel.CENTER);

    JLabel label20 = new JLabel ("Active Sensing", JLabel.CENTER);

    JTextField asCount = new JTextField ("   ", 3);

    JLabel label21 = new JLabel ("Timing Clock", JLabel.CENTER);

    JTextField tcCount = new JTextField ("   ", 3);

    JLabel label22 = new JLabel ("Other Messages", JLabel.CENTER);

    JTextField omCount = new JTextField ("   ", 3);

    JLabel label23 = new JLabel ("MIDI Monitor", JLabel.CENTER);
// File Pass 2...(Constructor creation)

    // Public constructor
    public MidiMonitorFrame (String title)
    {
        // Call parent constructor to give title to frame
        super (title);

        // Make this frame its own window listener
        addWindowListener (windowListener);

        // Panel components: notePanel
        notePanel.setBorder (new TitledBorder (
                new BevelBorder (BevelBorder.RAISED), "Note Messages"));
        GridBagLayout notePanelGridBag = new GridBagLayout ();
        GridBagConstraints notePanelConstr = new GridBagConstraints ();
        notePanel.setLayout (notePanelGridBag);
        notePanelConstr.anchor = GridBagConstraints.CENTER;
        notePanelConstr.weightx = 1.0;
        notePanelConstr.weighty = 1.0;
        notePanelConstr.fill = GridBagConstraints.BOTH;
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label1, notePanelConstr);
        notePanel.add (label1);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (cChan, notePanelConstr);
        notePanel.add (cChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (cPitch, notePanelConstr);
        notePanel.add (cPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (cVel, notePanelConstr);
        notePanel.add (cVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (cOn, notePanelConstr);
        notePanel.add (cOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 0;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (cOff, notePanelConstr);
        notePanel.add (cOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label2, notePanelConstr);
        notePanel.add (label2);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (csChan, notePanelConstr);
        notePanel.add (csChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (csPitch, notePanelConstr);
        notePanel.add (csPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (csVel, notePanelConstr);
        notePanel.add (csVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (csOn, notePanelConstr);
        notePanel.add (csOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 1;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (csOff, notePanelConstr);
        notePanel.add (csOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label3, notePanelConstr);
        notePanel.add (label3);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dChan, notePanelConstr);
        notePanel.add (dChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dPitch, notePanelConstr);
        notePanel.add (dPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dVel, notePanelConstr);
        notePanel.add (dVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dOn, notePanelConstr);
        notePanel.add (dOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 2;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dOff, notePanelConstr);
        notePanel.add (dOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label4, notePanelConstr);
        notePanel.add (label4);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dsChan, notePanelConstr);
        notePanel.add (dsChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dsPitch, notePanelConstr);
        notePanel.add (dsPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dsVel, notePanelConstr);
        notePanel.add (dsVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dsOn, notePanelConstr);
        notePanel.add (dsOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 3;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (dsOff, notePanelConstr);
        notePanel.add (dsOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label5, notePanelConstr);
        notePanel.add (label5);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (eChan, notePanelConstr);
        notePanel.add (eChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (ePitch, notePanelConstr);
        notePanel.add (ePitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (eVel, notePanelConstr);
        notePanel.add (eVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (eOn, notePanelConstr);
        notePanel.add (eOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 4;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (eOff, notePanelConstr);
        notePanel.add (eOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label6, notePanelConstr);
        notePanel.add (label6);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fChan, notePanelConstr);
        notePanel.add (fChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fPitch, notePanelConstr);
        notePanel.add (fPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fVel, notePanelConstr);
        notePanel.add (fVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fOn, notePanelConstr);
        notePanel.add (fOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 5;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fOff, notePanelConstr);
        notePanel.add (fOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label7, notePanelConstr);
        notePanel.add (label7);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fsChan, notePanelConstr);
        notePanel.add (fsChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fsPitch, notePanelConstr);
        notePanel.add (fsPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fsVel, notePanelConstr);
        notePanel.add (fsVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fsOn, notePanelConstr);
        notePanel.add (fsOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 6;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (fsOff, notePanelConstr);
        notePanel.add (fsOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label8, notePanelConstr);
        notePanel.add (label8);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gChan, notePanelConstr);
        notePanel.add (gChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gPitch, notePanelConstr);
        notePanel.add (gPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gVel, notePanelConstr);
        notePanel.add (gVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gOn, notePanelConstr);
        notePanel.add (gOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 7;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gOff, notePanelConstr);
        notePanel.add (gOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label9, notePanelConstr);
        notePanel.add (label9);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gsChan, notePanelConstr);
        notePanel.add (gsChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gsPitch, notePanelConstr);
        notePanel.add (gsPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gsVel, notePanelConstr);
        notePanel.add (gsVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gsOn, notePanelConstr);
        notePanel.add (gsOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 8;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (gsOff, notePanelConstr);
        notePanel.add (gsOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label10, notePanelConstr);
        notePanel.add (label10);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (aChan, notePanelConstr);
        notePanel.add (aChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (aPitch, notePanelConstr);
        notePanel.add (aPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (aVel, notePanelConstr);
        notePanel.add (aVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (aOn, notePanelConstr);
        notePanel.add (aOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 9;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (aOff, notePanelConstr);
        notePanel.add (aOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label11, notePanelConstr);
        notePanel.add (label11);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (asChan, notePanelConstr);
        notePanel.add (asChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (asPitch, notePanelConstr);
        notePanel.add (asPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (asVel, notePanelConstr);
        notePanel.add (asVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (asOn, notePanelConstr);
        notePanel.add (asOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 10;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (asOff, notePanelConstr);
        notePanel.add (asOff);
        notePanelConstr.gridx = 0;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (label12, notePanelConstr);
        notePanel.add (label12);
        notePanelConstr.gridx = 1;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (bChan, notePanelConstr);
        notePanel.add (bChan);
        notePanelConstr.gridx = 2;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (bPitch, notePanelConstr);
        notePanel.add (bPitch);
        notePanelConstr.gridx = 3;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (bVel, notePanelConstr);
        notePanel.add (bVel);
        notePanelConstr.gridx = 4;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (bOn, notePanelConstr);
        notePanel.add (bOn);
        notePanelConstr.gridx = 5;
        notePanelConstr.gridy = 11;
        notePanelConstr.gridwidth = 1;
        notePanelConstr.gridheight = 1;
        notePanelGridBag.setConstraints (bOff, notePanelConstr);
        notePanel.add (bOff);

        // Panel components: channelPanel
        channelPanel.setBorder (new TitledBorder (
                new BevelBorder (BevelBorder.RAISED), "Channel Messages"));
        GridBagLayout channelPanelGridBag = new GridBagLayout ();
        GridBagConstraints channelPanelConstr = new GridBagConstraints ();
        channelPanel.setLayout (channelPanelGridBag);
        channelPanelConstr.anchor = GridBagConstraints.CENTER;
        channelPanelConstr.weightx = 1.0;
        channelPanelConstr.weighty = 1.0;
        channelPanelConstr.fill = GridBagConstraints.BOTH;
        channelPanelConstr.gridx = 0;
        channelPanelConstr.gridy = 0;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (label13, channelPanelConstr);
        channelPanel.add (label13);
        channelPanelConstr.gridx = 1;
        channelPanelConstr.gridy = 0;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pkChan, channelPanelConstr);
        channelPanel.add (pkChan);
        channelPanelConstr.gridx = 2;
        channelPanelConstr.gridy = 0;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pkData1, channelPanelConstr);
        channelPanel.add (pkData1);
        channelPanelConstr.gridx = 3;
        channelPanelConstr.gridy = 0;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pkData2, channelPanelConstr);
        channelPanel.add (pkData2);
        channelPanelConstr.gridx = 0;
        channelPanelConstr.gridy = 1;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (label14, channelPanelConstr);
        channelPanel.add (label14);
        channelPanelConstr.gridx = 1;
        channelPanelConstr.gridy = 1;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (ccChan, channelPanelConstr);
        channelPanel.add (ccChan);
        channelPanelConstr.gridx = 2;
        channelPanelConstr.gridy = 1;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (ccData1, channelPanelConstr);
        channelPanel.add (ccData1);
        channelPanelConstr.gridx = 3;
        channelPanelConstr.gridy = 1;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (ccData2, channelPanelConstr);
        channelPanel.add (ccData2);
        channelPanelConstr.gridx = 0;
        channelPanelConstr.gridy = 2;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (label15, channelPanelConstr);
        channelPanel.add (label15);
        channelPanelConstr.gridx = 1;
        channelPanelConstr.gridy = 2;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pcChan, channelPanelConstr);
        channelPanel.add (pcChan);
        channelPanelConstr.gridx = 2;
        channelPanelConstr.gridy = 2;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pcData1, channelPanelConstr);
        channelPanel.add (pcData1);
        channelPanelConstr.gridx = 0;
        channelPanelConstr.gridy = 3;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (label16, channelPanelConstr);
        channelPanel.add (label16);
        channelPanelConstr.gridx = 1;
        channelPanelConstr.gridy = 3;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (cpChan, channelPanelConstr);
        channelPanel.add (cpChan);
        channelPanelConstr.gridx = 2;
        channelPanelConstr.gridy = 3;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (cpData1, channelPanelConstr);
        channelPanel.add (cpData1);
        channelPanelConstr.gridx = 0;
        channelPanelConstr.gridy = 4;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (label17, channelPanelConstr);
        channelPanel.add (label17);
        channelPanelConstr.gridx = 1;
        channelPanelConstr.gridy = 4;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pbChan, channelPanelConstr);
        channelPanel.add (pbChan);
        channelPanelConstr.gridx = 2;
        channelPanelConstr.gridy = 4;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pbData1, channelPanelConstr);
        channelPanel.add (pbData1);
        channelPanelConstr.gridx = 3;
        channelPanelConstr.gridy = 4;
        channelPanelConstr.gridwidth = 1;
        channelPanelConstr.gridheight = 1;
        channelPanelGridBag.setConstraints (pbData2, channelPanelConstr);
        channelPanel.add (pbData2);

        // Panel components: sysMessagesPanel
        sysMessagesPanel.setBorder (new TitledBorder (
                new BevelBorder (BevelBorder.RAISED), "Message Counters"));
        GridBagLayout sysMessagesPanelGridBag = new GridBagLayout ();
        GridBagConstraints sysMessagesPanelConstr = new GridBagConstraints ();
        sysMessagesPanel.setLayout (sysMessagesPanelGridBag);
        sysMessagesPanelConstr.anchor = GridBagConstraints.CENTER;
        sysMessagesPanelConstr.weightx = 1.0;
        sysMessagesPanelConstr.weighty = 1.0;
        sysMessagesPanelConstr.fill = GridBagConstraints.BOTH;
        sysMessagesPanelConstr.gridx = 0;
        sysMessagesPanelConstr.gridy = 0;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (label18, sysMessagesPanelConstr);
        sysMessagesPanel.add (label18);
        sysMessagesPanelConstr.gridx = 1;
        sysMessagesPanelConstr.gridy = 0;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (sysexCount, sysMessagesPanelConstr);
        sysMessagesPanel.add (sysexCount);
        sysMessagesPanelConstr.gridx = 2;
        sysMessagesPanelConstr.gridy = 0;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (label19, sysMessagesPanelConstr);
        sysMessagesPanel.add (label19);
        sysMessagesPanelConstr.gridx = 0;
        sysMessagesPanelConstr.gridy = 1;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (label20, sysMessagesPanelConstr);
        sysMessagesPanel.add (label20);
        sysMessagesPanelConstr.gridx = 1;
        sysMessagesPanelConstr.gridy = 1;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (asCount, sysMessagesPanelConstr);
        sysMessagesPanel.add (asCount);
        sysMessagesPanelConstr.gridx = 0;
        sysMessagesPanelConstr.gridy = 2;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (label21, sysMessagesPanelConstr);
        sysMessagesPanel.add (label21);
        sysMessagesPanelConstr.gridx = 1;
        sysMessagesPanelConstr.gridy = 2;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (tcCount, sysMessagesPanelConstr);
        sysMessagesPanel.add (tcCount);
        sysMessagesPanelConstr.gridx = 0;
        sysMessagesPanelConstr.gridy = 3;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (label22, sysMessagesPanelConstr);
        sysMessagesPanel.add (label22);
        sysMessagesPanelConstr.gridx = 1;
        sysMessagesPanelConstr.gridy = 3;
        sysMessagesPanelConstr.gridwidth = 1;
        sysMessagesPanelConstr.gridheight = 1;
        sysMessagesPanelGridBag.setConstraints (omCount, sysMessagesPanelConstr);
        sysMessagesPanel.add (omCount);
        GridBagLayout thisGridBag = new GridBagLayout ();
        GridBagConstraints thisConstr = new GridBagConstraints ();
        this.getContentPane ().setLayout (thisGridBag);
        thisConstr.anchor = GridBagConstraints.CENTER;
        thisConstr.weightx = 1.0;
        thisConstr.weighty = 1.0;
        thisConstr.fill = GridBagConstraints.BOTH;
        thisConstr.gridx = 0;
        thisConstr.gridy = 0;
        thisConstr.gridwidth = 2;
        thisConstr.gridheight = 1;
        thisGridBag.setConstraints (label23, thisConstr);
        this.getContentPane ().add (label23);
        thisConstr.gridx = 0;
        thisConstr.gridy = 1;
        thisConstr.gridwidth = 1;
        thisConstr.gridheight = 2;
        thisGridBag.setConstraints (notePanel, thisConstr);
        this.getContentPane ().add (notePanel);
        thisConstr.gridx = 1;
        thisConstr.gridy = 1;
        thisConstr.gridwidth = 1;
        thisConstr.gridheight = 1;
        thisGridBag.setConstraints (channelPanel, thisConstr);
        this.getContentPane ().add (channelPanel);
        thisConstr.gridx = 1;
        thisConstr.gridy = 2;
        thisConstr.gridwidth = 1;
        thisConstr.gridheight = 1;
        thisGridBag.setConstraints (sysMessagesPanel, thisConstr);
        this.getContentPane ().add (sysMessagesPanel);

        // Set frame size and show it
        setSize (600, 400);
        setVisible (true);
    } // Frame constructor MidiMonitorFrame ()

//File Pass 3...(Window and Action Listeners)
    // Window listener interface methods
    public void windowActivated (WindowEvent e)
    {
        //System.out.println ("Window activated");
    } // windowActivated ()

    public void windowClosed (WindowEvent e)
    {
        //System.out.println ("Window closed");
    } // windowClosed ()

    public void windowClosing (WindowEvent e)
    {
        //System.out.println ("Window closing");
        System.exit (0);
    } // windowClosing ()

    public void windowDeactivated (WindowEvent e)
    {
        //System.out.println ("Window deactivated");
    } // windowDeactivated ()

    public void windowDeiconified (WindowEvent e)
    {
        //System.out.println ("Window deiconified");
    } // windowDeiconified ()

    public void windowIconified (WindowEvent e)
    {
        //System.out.println ("Window iconified");
    } // windowIconifed ()

    public void windowOpened (WindowEvent e)
    {
        //System.out.println ("Window opened");
    } // windowOpened ()

    // Action Listener interface method
    public void actionPerformed (ActionEvent event)
    {
    } // actionPerformed ()
} // Frame MidiMonitorFrame
