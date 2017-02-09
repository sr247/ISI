/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package e2b;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author lhuillan
 */
public class ConsoleView implements Percentage_View{ // TODO this view should implement the propoer interface
	
	// TODO : create a views that display the percentage in the console of the application
	private static final long serialVersionUID = 1L;
	
	private final Percentage_Controller myController;
	
    public ConsoleView(Percentage_Controller controller) {
    	myController = controller;
    	System.out.println("ConsoleView Created");
    }
    
    public void update() {
		// TODO update the JLabel with the value of the percentage
		float value = myController.getValue();
		System.out.println(value);
	}
}
