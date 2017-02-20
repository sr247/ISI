

import fr.lri.swingstates.canvas.Canvas ;
import fr.lri.swingstates.canvas.CStateMachine ;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.CImage;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape ;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.StateMachineListener ;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.debug.StateMachineVisualization ;

import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel;
import javax.swing.BoxLayout ;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory ;
import java.awt.Container ;
import java.awt.Font;
import java.awt.PopupMenu;
import java.awt.geom.Point2D;
import java.awt.Color ;
import java.util.ArrayList;
import java.util.EventObject ;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class GraphicalEditor extends JFrame implements StateMachineListener {

    private Canvas canvas ;
    private SelectionTool selector ;

    public GraphicalEditor(String title, int width, int height) {
	   super(title) ;

	   canvas = new Canvas(width, height) ;
	   
	   
	   Point2D p1 = new Point2D.Double(50.0,30.0) ;
	   Point2D p2 = new Point2D.Double(150,33.0) ;
	   
	   CText toolsLabel = canvas.newText(p2, "TOOLS");
	   toolsLabel.setFont(new Font("verdana", Font.BOLD, 12));
	   CRectangle rectangle = canvas.newRectangle(p1, 250, 20) ;	   
	   rectangle.below(toolsLabel);
	   canvas.newImage(50.0,50.0, "Icons/images/e210.SelectionTool.png");
	   canvas.newImage(100.0,50.0, "Icons/images/e210.RectangleTool.png");
	   canvas.newImage(150.0,50.0, "Icons/images/e210.EllipseTool.png");
	   canvas.newImage(200.0,50.0, "Icons/images/e210.LineTool.png");
	   canvas.newImage(250.0,50.0, "Icons/images/e210.PathTool.png");
	   getContentPane().add(canvas) ;
	  
	   
	   selector = new SelectionTool(CStateMachine.BUTTON1, CStateMachine.SHIFT) ;
	   selector.attachTo(canvas) ;

	   CStateMachine toolbar = new CStateMachine(){ ///.BUTTON1, CStateMachine.NOMODIFIER) ;
		   
		   Point2D p1 = new Point2D.Double(50.0,30.0) ;
		   Point2D p2 = new Point2D.Double(150,33.0) ;
		   CText toolsLabel;
		   CRectangle rectangle;
		   ArrayList<CImage> list; 
		   int button, modifier;
		  
		   
		   
		   State start = new State() {
				 Transition press = new Press(button, modifier, ">> draw") {
					    public void action() {
						   Canvas canvas = (Canvas)getEvent().getSource() ;
						   p1 = getPoint() ;
						   rectangle= canvas.newRectangle(p1, 1, 1) ;
					    }
					} ;
			  } ;
		 
		   
	   };
	   
	   CStateMachine tool = new RectangleTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER) ;
	   tool.attachTo(canvas) ;
	   tool.addStateMachineListener(this) ;
	   
	   
	   CStateMachine tool2 = new EllipseTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER) ;
	   tool2.attachTo(canvas) ;
	   tool2.addStateMachineListener(this) ;
	   
	   CStateMachine tool3 = new PolylineTool(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER) ;
	   tool3.attachTo(canvas) ;
	   tool3.addStateMachineListener(this) ;
	  
	   JFrame smviz = new JFrame("StateMachine Viz") ;
	   Container pane = smviz.getContentPane() ;
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));
	   pane.add(new JLabel("SelectionTool")) ;
	   pane.add(new StateMachineVisualization(selector)) ;
	   
	   pane.add(new JLabel("RectangleTool")) ;
	   pane.add(new StateMachineVisualization(tool)) ;
	   
	   pane.add(new JLabel("EllipseTool")) ;
	   pane.add(new StateMachineVisualization(tool2)) ;
	   
	   pane.add(new JLabel("PolylineTool")) ;
	   pane.add(new StateMachineVisualization(tool3)) ;
	   
	   smviz.pack() ;
	   smviz.setVisible(false) ;

	   pack() ;
	   setVisible(true) ;
    }

    public void eventOccured(EventObject e) {
	   ShapeCreatedEvent csce = (ShapeCreatedEvent)e ;
	   csce.getShape().addTag(selector.getMovableTag()).setFillPaint(Color.white) ;
    }
	
    public static void main(String[] args) {
	   GraphicalEditor editor = new GraphicalEditor("Graphical Editor",400,600) ;
	   editor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    }

}
