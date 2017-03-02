package e213.skeleton ;

import java.awt.Container ;
import java.awt.Dimension ;

import javax.swing.JFrame ;
import javax.swing.BoxLayout ;

import fr.lri.swingstates.sm.State ;
import fr.lri.swingstates.sm.Transition ;
import fr.lri.swingstates.sm.transitions.Press ;
import fr.lri.swingstates.sm.transitions.Drag ;
import fr.lri.swingstates.sm.transitions.Release ;
import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CStateMachine ;

// --------------------------------------------------------------------------------------

class CrossingTrace extends CStateMachine {

    // ...

    CrossingTrace(Canvas c) {
	   // ...
    }

    State waiting = new State() {
		  // ...
	   } ;

    // ...

} ;

// --------------------------------------------------------------------------------------

public class CrossingDemo extends JFrame {

    public CrossingDemo() {
	   super("CrossingDemo") ;

	   Container pane = getContentPane() ;
	   pane.setSize(new Dimension(400,300)) ;

	   Canvas canvas = new Canvas(getContentPane().getWidth(), getContentPane().getHeight()) ;
	   new CrossingTrace(canvas) ;

	   pane.add(canvas) ;

	   pack() ;
	   setVisible(true) ;
    }

    static public void main(String args[]) {
	   CrossingDemo demo = new CrossingDemo() ;
    }

}
