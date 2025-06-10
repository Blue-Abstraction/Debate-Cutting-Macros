import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import dorkbox.notify.Notify;
import dorkbox.notify.Position;
import dorkbox.notify.Theme;

public class Main implements NativeMouseListener {
	
	private static int currentCycle;
	private static Robot robot;
	
	Main() {
		try {
			robot = new Robot();
			GlobalScreen.registerNativeHook();
			GlobalScreen.addNativeMouseListener(this);
		} catch (Exception e) {
			System.out.println(e);
		} 
		currentCycle = 0;
	}
	
	public static void main(String[] args) {
		new Main();
	}
	  
	  
	@Override
    public void nativeMouseReleased(NativeMouseEvent e) {
		//System.out.println(e.getButton());
        if (e.getButton() == 4) {
        	currentCycle--;
        	
    		newNotif("Current Mode: " + Mode.getMode(Math.abs(currentCycle % 3)));
        } else if (e.getButton() == 5) {
        	currentCycle++;
        	
    		newNotif("Current Mode: " + Mode.getMode(Math.abs(currentCycle % 3)));
        } else if (e.getButton() == 3) {
			try {
				switch (Mode.getMode(Math.abs(currentCycle % 3))) {
	        	case UNDERLINE:
	        		robot.keyPress(KeyEvent.VK_F9);
					Thread.sleep(25);
	        		robot.keyRelease(KeyEvent.VK_F9);
	    			break;
	        	case EMPHASIZE:
	        		robot.keyPress(KeyEvent.VK_F10);
					Thread.sleep(25);
	        		robot.keyRelease(KeyEvent.VK_F10);
	    			break;
	        	case HIGHLIGHT:
	        		robot.keyPress(KeyEvent.VK_F11);
					Thread.sleep(25);
	        		robot.keyRelease(KeyEvent.VK_F11);
        			break;
        			
        		default: break; // not possible but wtev
        	} 
				} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
        }
    }
  
	private void newNotif(String string) {
	    Notify.Companion.create()
        .text(string)
        .theme(Theme.Companion.getDefaultDark())
        .position(Position.CENTER)
        .hideAfter(1000)
        .showWarning();
	}
}
