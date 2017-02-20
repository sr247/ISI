


import fr.lri.swingstates.canvas.CStateMachine ;
import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CRectangle ;
import fr.lri.swingstates.sm.State ;
import fr.lri.swingstates.sm.Transition ;
import fr.lri.swingstates.sm.transitions.* ;

import java.awt.geom.Point2D ;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class Toolbar  extends CStateMachine {

    private CRectangle rect ;
    private Point2D p1 = new Point2D.Double(50.0,30.0) ;
	private Point2D p2 = new Point2D.Double(150,33.0) ;

    public State start, draw ;

    public Toolbar() {
	   this(BUTTON1,NOMODIFIER) ;
    }

    public Toolbar(final int button, final int modifier) {
	   start = new State() {
			 Transition press = new Press(button, modifier, ">> draw") {
				    public void action() {
					   Canvas canvas = (Canvas)getEvent().getSource() ;
					   p1 = getPoint() ;
					   rect = canvas.newRectangle(p1, 1, 1) ;
				    }
				} ;
		  } ;
	   draw = new State() {
			 Transition draw = new Drag(button, modifier) {
				    public void action() {
					   rect.setDiagonal(p1, getPoint()) ;
				    }
				} ;
			 Transition stop = new Release(button, modifier, ">> start") {
				    public void action() {
					   rect.setDiagonal(p1, getPoint()) ;
					   fireEvent(new ShapeCreatedEvent(Toolbar.this, rect)) ;
				    }
				} ;
		  } ;
    }

}
