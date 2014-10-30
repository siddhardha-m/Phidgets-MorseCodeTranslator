package phidgetslab;

import com.phidgets.*;
import com.phidgets.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhidgetsLab {

    public static void main(String[] args) throws Exception {
        
        final int led_pin = 0;
        
        final InterfaceKitPhidget ik = new InterfaceKitPhidget();
       
        
        ik.addAttachListener(new AttachListener() {
			public void attached(AttachEvent ae) {
				System.out.println("Attached " + ae);
			}
	});
	
        ik.addDetachListener(new DetachListener() {
			public void detached(DetachEvent de) {
				System.out.println("Detached " + de);
			}
	});
	
        ik.addErrorListener(new ErrorListener() {
			public void error(ErrorEvent ee) {
				System.out.println(ee);
			}
	});
        
        ik.addSensorChangeListener(new SensorChangeListener() {
			public void sensorChanged(SensorChangeEvent se) {
                            if(se.getValue() > 200) {
                                try {
                                    ik.setOutputState(led_pin, true);
                                } catch (PhidgetException ex) {
                                    Logger.getLogger(PhidgetsLab.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            else{
                                try {
                                    ik.setOutputState(led_pin, false);
                                } catch (PhidgetException ex) {
                                    Logger.getLogger(PhidgetsLab.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                                
			}
	});
	
        
        System.out.println("Software Object for InterfaceKit has been created.");
        
        ik.openAny();
        
        System.out.println("InterfaceKit Software Object is opened. Waiting for PhidgetInterfaceKit attachment...");
        
        ik.waitForAttachment();
        
        Thread.sleep(500);
        
        boolean o = ik.getOutputState(led_pin);
        
        if (o)
        {
            ik.setOutputState(led_pin, false);
        }
        
        System.out.print("Press 'Enter' key to end anytime...\n");
        System.in.read();
        
        System.out.println("Closing InterfaceKit Software Object...");
	ik.setOutputState(led_pin, false);
        
        ik.close();
	//ik = null;
        System.out.println("InterfaceKit Software Object is closed.");
    }
}
