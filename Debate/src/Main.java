import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import dorkbox.notify.Notify;
import dorkbox.notify.Position;
import dorkbox.notify.Theme;

public class Main implements NativeMouseListener {
	
	private static int currentCycle;
	private static Robot robot;
	private static double x, y;
	private static Consumer<Integer> hotkey = (key) -> {
		try {
			robot.keyPress(key);
			Thread.sleep(25);
			robot.keyRelease(key);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	};
	
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
		try {
	        if (e.getButton() == 4) {
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_Z);
				Thread.sleep(25);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				robot.keyRelease(KeyEvent.VK_Z);
			} else if (e.getButton() == 5) {
	        	currentCycle++;
	    		newNotif("Current Mode: " + Mode.getMode(Math.abs(currentCycle % 3)));

	        } else if (e.getButton() == 3) {
					switch (Mode.getMode(Math.abs(currentCycle % 3))) {
		        	case UNDERLINE:
		        		quickChangePos(hotkey, KeyEvent.VK_F9);
		    			break; 
		        	case EMPHASIZE:
		        		quickChangePos(hotkey, KeyEvent.VK_F10);
		    			break;
		        	case HIGHLIGHT:
		        		quickChangePos(hotkey, KeyEvent.VK_F11);
	        			break;
	        		default: break; // not possible but wtev
	        	} 
			}
		} catch (InterruptedException e1) {
		e1.printStackTrace();
		}
	}
   
  
	private void newNotif(String string) {
	    Notify.Companion.create()
        .text(string)
        .theme(Theme.Companion.getDefaultLight())
        .position(Position.CENTER)
        .hideAfter(1000)
        .showWarning();
	}
	
	private void quickChangePos(Consumer<Integer> consumer, int key) throws InterruptedException {
		x = MouseInfo.getPointerInfo().getLocation().x;
		y = MouseInfo.getPointerInfo().getLocation().y;
		
		robot.mouseMove(50, (int) y);

		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(25);
		robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

		consumer.accept(key);
		
		
		robot.mouseMove((int) x, (int) y);
	}
}
