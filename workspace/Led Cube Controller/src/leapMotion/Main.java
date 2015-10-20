package leapMotion;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Listener;

import leapMotion.SampleListener;

public class Main {
	public static void main(String[] args){
		SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

		
		while(true);
	}
}

class SampleListener extends Listener {
	Arduino testeArd = new Arduino();
	public void onConnect(Controller controller) {
	    System.out.println("Connected");
	    controller.enableGesture(Gesture.Type.TYPE_SWIPE);
	    controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
	}

	public void onFrame(Controller controller) {
	    Frame frame = controller.frame();
	    int keyValue=0;
	    float X = frame.hands().get(0).fingers().get(1).tipPosition().getX();
	    float Y = frame.hands().get(0).fingers().get(1).tipPosition().getY();
	    float Z = frame.hands().get(0).fingers().get(1).tipPosition().getZ();
	    
	    if(frame.hands().get(0).fingers().extended().count()>0){
	    	if(Y>250)
	    		keyValue+=100;
	    	if(X<-50)
	    		keyValue += 10;
	    	if(X>=-50 && X<0)
	    		keyValue += 20;
	    	if(X>=0 && X<50)
	    		keyValue += 30;
	    	if(X>=50)
	    		keyValue += 40;
	    	
	    	if(Z>10)
	    		keyValue += 1;
	    	if(Z>=-60 && Z<10)
	    		keyValue += 2;
	    	if(Z>=-130 && Z<-60)
	    		keyValue += 3;
	    	if(Z<-130)
	    		keyValue += 4;
	    	
	    }else{
	    	keyValue=200;
	    }
		testeArd.arduino.enviaDados(keyValue);
	}
}