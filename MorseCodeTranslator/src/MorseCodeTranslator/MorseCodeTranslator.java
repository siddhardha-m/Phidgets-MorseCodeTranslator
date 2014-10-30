package MorseCodeTranslator;

//Importing the necessary Phidget classes and events
import com.phidgets.*;
import com.phidgets.event.*;

//Importing necessary classes for HashMap and logging
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MorseCodeTranslator {

    //String variables to store the input morse code
    static String input_morse_code = "";
    static String input_mc_str = "";
    
    //Variables to store the start and end time of the PressureSensor button press
    static long start_time_in_secs = 0;
    static long end_time_in_secs = 1;
    
    //Variables to store the start and end time of the delay between successive PressureSensor button presses
    static long start_delay_time_in_secs = 0;
    static long end_delay_time_in_secs = 0;
    
    public static void main(String[] args) throws Exception {
        
        /* //Part A of the Phidgets lab
        
        //Temporary input string
        String str = "Hello World";
        
        //Converting the input string to lower case
        str = str.toLowerCase();
        
        */
        
        //HashMap storing the morse code encodings of all characters
        HashMap hm = new HashMap();
        
        hm.put("a", "021");
        hm.put("b", "1202020");
        hm.put("c", "1202120");
        hm.put("d", "12020");
        hm.put("e", "0");
        hm.put("f", "0202120");
        hm.put("g", "12120");
        hm.put("h", "0202020");
        hm.put("i", "020");
        hm.put("j", "0212121");
        hm.put("k", "12021");
        hm.put("l", "0212020");
        hm.put("m", "121");
        hm.put("n", "120");
        hm.put("o", "12121");
        hm.put("p", "0212120");
        hm.put("q", "1212021");
        hm.put("r", "02120");
        hm.put("s", "02020");
        hm.put("t", "1");
        hm.put("u", "02021");
        hm.put("v", "0202021");
        hm.put("w", "02121");
        hm.put("x", "1202021");
        hm.put("y", "1202121");
        hm.put("z", "1212020");
        hm.put(" ", "2");
        hm.put("1", "021212121");
        hm.put("2", "020212121");
        hm.put("3", "020202121");
        hm.put("4", "020202021");
        hm.put("5", "020202020");
        hm.put("6", "120202020");
        hm.put("7", "121202020");
        hm.put("8", "121212020");
        hm.put("9", "121212120");
        hm.put("0", "121212121");

        //Output LED pin number on the Phidget InterfaceKit
        final int led_pin = 0;
        
        //Creating a software object for Phidget InterfaceKit
        final InterfaceKitPhidget ik = new InterfaceKitPhidget();
        
        System.out.println("Software Object for InterfaceKit has been created.");
       
        //Adding AttachListener to Phidget InterfaceKit
        ik.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("Attached " + ae);
			}
	});
	
        //Adding DetachListener to Phidget InterfaceKit
        ik.addDetachListener(new DetachListener() {
			public void detached(DetachEvent de) {
				System.out.println("Detached " + de);
			}
	});
	
        //Adding ErrorListener to Phidget InterfaceKit
        ik.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println(ee);
			}
	});
        
        //Adding SensorChangeListener to Phidget InterfaceKit
        ik.addSensorChangeListener(new SensorChangeListener() {
                        //This method is triggered every time when the input sensor value is changed by a value of 10 (default setting)
			public void sensorChanged(SensorChangeEvent se) {
                            //A value of 20 or greater is being considered as a PressureSensor button press
                            if(se.getValue() >= 20) {
                            //When the PressureSensor button is pressed
                                try {
                                        //If the time of the button press is not set
                                        //Occurs only once during a key press when the PressureSensor value is greater than 20
                                        if (start_time_in_secs == 0)
                                        {
                                           //Setting the start time of the button press and the end time of the delay between successive button presses
                                           //with the current time
                                           end_delay_time_in_secs = System.currentTimeMillis()/1000;
                                           start_time_in_secs = System.currentTimeMillis()/1000;
                                           
                                           //Unsetting the end time of the button press
                                           end_time_in_secs = 0;
                                           
                                           //If the delay time between successive button presses is greater than 7 seconds, 
                                           //then consider it as a space between words
                                           if (end_delay_time_in_secs - start_delay_time_in_secs  > 7)
                                            {
                                                //Adding space to the morse code to indicate the space between words
                                                input_morse_code += " ";
                                            }
                                           
                                           //If the delay time between successive button presses is greater than 3 seconds, 
                                           //then consider it as a gap between characters of a word
                                           else if (end_delay_time_in_secs - start_delay_time_in_secs  > 3)
                                            {
                                                //Adding 3 to the morse code to indicate gap between characters of a word
                                                input_morse_code += "3";
                                            }
                                           
                                           //Unsetting the start time of the delay between successive button presses
                                           start_delay_time_in_secs = 0;
                                        }
                                        
                                        //Turn ON the LED to indicate button press
                                        ik.setOutputState(led_pin, true);
                                        
                                    } catch (PhidgetException ex) {
                                        //Catching any unexpected PhidgetExceptions and logging
                                        Logger.getLogger(MorseCodeTranslator.class.getName()).log(Level.SEVERE, null, ex);
                                    }    
                            }
                            else {
                            //When the PressureSensor button is released
                                try {
                                        //If the time of the button release is not set
                                        //Occurs only once during a key press when the PressureSensor value is less than 20
                                        if (end_time_in_secs == 0)
                                        {
                                            //Setting the end time of the button press and the start time of the delay between successive button presses
                                            //with the current time
                                            end_time_in_secs = System.currentTimeMillis()/1000;
                                            start_delay_time_in_secs = System.currentTimeMillis()/1000;
                                            
                                            //Unsetting the end time of the delay between successive button presses
                                            end_delay_time_in_secs = 0;
                                            
                                            //If the time of the button press is greater than 1 second, then consider it to be a dash
                                            if (end_time_in_secs - start_time_in_secs  > 1)
                                            {
                                                //Adding 1 to the morse code to indicate dash
                                                input_morse_code += "12";
                                            }
                                            
                                            //If the time of the button press is lesser than 1 second, then consider it to be a dot
                                            else
                                            {
                                                //Adding 0 to the morse code to indicate dot
                                                input_morse_code += "02";
                                            }
                                            
                                            //Unsetting the start time of the button press
                                            start_time_in_secs = 0;
                                        }
                                        
                                        //Turn OFF the LED to indicate button release
                                        ik.setOutputState(led_pin, false);
                                        
                                    } catch (PhidgetException ex) {
                                        //Catching any unexpected PhidgetExceptions and logging
                                        Logger.getLogger(MorseCodeTranslator.class.getName()).log(Level.SEVERE, null, ex);
                                    }    
                            }
			}
	});
	
        //Opening the software object of Phidget InterfaceKit to listen to any events
        ik.openAny();
        
        System.out.println("InterfaceKit Software Object is opened. Waiting for PhidgetInterfaceKit attachment...");
        
        //Wait until the Phidget InterfaceKit is attached to the computer
        ik.waitForAttachment();
        
        Thread.sleep(500);
        
        //Get the current state of the LED
        boolean o = ik.getOutputState(led_pin);
        
        //If the LED is ON, set it to OFF state
        if (o)
        {
            ik.setOutputState(led_pin, false);
        }
        
        
        /* //Part A of the Phidgets lab
        
        String morse_code = "";
        
        for(char ch : str.toCharArray())
        {
           String c = Character.toString(ch);
           
            //           String char_morse_code = hm.get(c).toString();
           
            //           for(char digit : char_morse_code.toCharArray())
            //           {
            //               morse_code += digit;
            //           }
           
            //           morse_code += "222";
           
           
           morse_code += hm.get(c).toString() + "222";
           
        }
        
        morse_code = morse_code.substring(0, morse_code.length() - 3);
        
        
        System.out.println(morse_code.replace('2', ' '));
        
        
        for(char mc : morse_code.toCharArray())
        {
            if (mc == '0') {
                ik.setOutputState(led_pin, true);
                
                Thread.sleep(500);
        
            } else if (mc == '1') {
                ik.setOutputState(led_pin, true);
                
                Thread.sleep(1500);
        
            } else {
                ik.setOutputState(led_pin, false);
                
                Thread.sleep(500);
        
            }
        }
        
        System.out.println(morse_code);
        
        ik.setOutputState(led_pin, false);
        
        */
        
        //Display text to enter Morse Code from analog PressureSensor input
        System.out.print("Enter Morse Code from analog input\n");
        
        //Press any key to indicate the end of Morse Code input
        System.out.print("Press 'Enter' key to end anytime...\n");
        System.in.read();
        
        //Removing the first character to get the correct Morse Code string of the PressureSensor input 
        input_morse_code = input_morse_code.substring(1);
        
        //Printing the Morse Code sequence entered through the PressureSensor input
        System.out.println(input_morse_code);
        
        //Split the input Morse Code string by spaces and store the individual word sequences in a string array
        String[] input_morse_code_word_array = input_morse_code.split("\\s+");
        
        //Looping through each word sequence in the words array
        for(String input_morse_code_word : input_morse_code_word_array) {
            
            //Split the Morse Code sequence of each word by the character 3 and store the individual character sequences in a string array
            String[] input_morse_code_letter_array = input_morse_code_word.split("3");
            
            //Looping through each character sequence in the characters array
            for(String input_morse_code_letter: input_morse_code_letter_array) {
                
                //Removing the last character to get the correct Morse Code string of each character
                input_morse_code_letter = input_morse_code_letter.substring(0, input_morse_code_letter.length() - 1);
                
                //Creating an Iterator object on the HashMap containing the Morse Code encodings
                Iterator<Map.Entry<String,String>> iter = hm.entrySet().iterator();
                
                //Looping over each Map entry of the HashMap Iterator
                while (iter.hasNext()) {
                    //Getting the current Map entry in the HashMap Iterator
                    Map.Entry<String,String> entry = iter.next();
                    
                    //If the value of the current Map entry equals the Morse Code string of the character
                    if (entry.getValue().equals(input_morse_code_letter)) {
                        //Store the current Map entry's key in the converted Morse Code string
                        input_mc_str += entry.getKey();
                    }
                }
            }
            
            //At the end of each word, append a space to the converted Morse Code string
            input_mc_str += " ";
        }
        
        //Printing the converted Morse Code string of the PressureSensor input
        System.out.println(input_mc_str);
        
        //Press any key to indicate the end of the program
        System.out.print("Press 'Enter' key to end anytime...\n");
        System.in.read();
        
        //Turning OFF the LED at the end of the program
        ik.setOutputState(led_pin, false);
        
        //Closing the software object of Phidget InterfaceKit to stop listening to any events
        System.out.println("Closing InterfaceKit Software Object...");
	ik.close();
	
        //ik = null;
        System.out.println("InterfaceKit Software Object is closed.");
    }
}
