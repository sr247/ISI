package e5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import javax.swing.JFrame;
import javax.swing.border.StrokeBorder;

import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.debug.StateMachineVisualization;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.jtransitions.EnterOnComponent;
import fr.lri.swingstates.canvas.transitions.*;
import fr.lri.swingstates.sm.transitions.*;

public class SimpleButton {

	private CRectangle rectangle;
	private CText label;
	private CStateMachine sm;
	private String text;

	// Padding around the label
	private int padding = 5;
	// Border widths for the different states
	private int[] borderwidth = { 1, 2 };
	// Background colors for the different states
	private Color[] color = { Color.WHITE, Color.YELLOW };

	SimpleButton(Canvas canvas, String text) {
		this(canvas, text, CStateMachine.ANYBUTTON, CStateMachine.NOMODIFIER);
	}

	@SuppressWarnings("unused")
	SimpleButton(Canvas canvas, String text, final int button, final int modifier) {

		// Create the label
		this.text = text;
		label = canvas.newText(0, 0, text, new Font("verdana", Font.PLAIN, 16));

		// The label should not generate mouse events
		label.setPickable(false);

		// TODO Build a rectangle around the label (use canvas.newRectangle()
		// with label.getMinX/Y(), label.getWidth/Height() and padding)
		rectangle = canvas.newRectangle(label.getMinX(), label.getMinY(), label.getWidth(), label.getHeight());
		rectangle.below(label);
		label.setParent(rectangle);
		// TODO Set the rectangle border (use rectangle.setStroke() with a
		// BasicStroke)
		// and the background color (use setFillPaint() with color defined as an
		// attribute)

		// TODO Set the rectangle as the parent of the label, so that any
		// translation of the rectangle also translates the label

		// TODO Put it below the label (last created shapes always appear on top
		// of previous shapes)

		// Set the button behavior with this state machine
		sm = new CStateMachine() {
			// TODO Create the state machine for the button
			State idling = new State() {
				Transition idling = new EnterOnShape("=> hover") {
					public void action() {
						rectangle.setFillPaint(color[0]);
						rectangle.setStroke(new BasicStroke(borderwidth[1]));
					}
				};
			};

			State hover = new State() {
				Transition hover = new LeaveOnShape("=> idling") {
					public void action() {
						rectangle.setFillPaint(Color.LIGHT_GRAY);
						rectangle.setStroke(new BasicStroke(borderwidth[0]));
					}
					
					Transition pressed = new PressOnShape("=> pressed") {
						public void action() {
							rectangle.setFillPaint(color[1]);
							rectangle.setStroke(new BasicStroke(borderwidth[0]));
						}
				};
			};
			
			State pressed = new State() {
				Transition pressed = new LeaveOnShape("=> idling") {
					public void action() {

					}
				};
			};

		// TODO Attach the state machine to the button
	rectangle.attachSM(sm, true);
	}

	// Gets called when the user clicks the button
	public void doAction() {
		System.out.println("Clicked on button '" + text + "'");
	}

	public CShape getShape() {
		return rectangle;
	}

	// Sets the draggable state of the button by adding or removing the
	// "draggable" tag
	public void setDraggable(boolean draggable) {
		if (draggable && !rectangle.hasTag("draggable")) {
			rectangle.addTag("draggable");
			rectangle.setOutlinePaint(Color.LIGHT_GRAY);
		} else if (rectangle.hasTag("draggable")) {
			rectangle.removeTag("draggable");
			rectangle.setOutlinePaint(Color.BLACK);
		}
	}

	// Gets the draggable state of a button
	public boolean getDraggable() {
		return rectangle.hasTag("draggable");
	}

	// Shows a graphical representation of the state machine in a separate
	// window
	public static void showStateMachine(CStateMachine sm) {
		JFrame viz = new JFrame();
		viz.getContentPane().add(new StateMachineVisualization(sm));
		viz.pack();
		viz.setVisible(true);
	}

	// Shows the state machine of the button
	private void showStateMachine() {
		showStateMachine(sm);
	}

	// Creates a state machine that drags any shape with the "draggable" tag
	// when the right mouse button is pressed
	@SuppressWarnings("unused")
	public static void addDragger(Canvas canvas) {

		// Add a label explaining what the user can do
		CText label = canvas.newText(20, 20, "Draggable buttons can be dragged with the right mouse button",
				new Font("verdana", Font.PLAIN, 12));
		label.setFillPaint(Color.GRAY);

		// Create the state machine and attach it to the canvas
		CStateMachine dragger = new CStateMachine(canvas) {

			Point2D pressLocation;
			Point2D shapeLocation;
			CShape shape;

			public State idling = new State() {
				// The mouse button "BUTTON3" is the right mouse button
				// ("BUTTON1" is the left mouse button)
				Transition down = new PressOnTag("draggable", CStateMachine.BUTTON3, ">> dragging") {
					public void action() {
						pressLocation = getPoint();
						shape = getShape();
						shapeLocation = new Point2D.Double(shape.getCenterX(), shape.getCenterY());
					};
				};
			};
			public State dragging = new State() {
				Transition move = new Drag() {
					public void action() {
						// TODO Translate the shape (use shape.translateTo())
						shape.translateTo(pressLocation.getX(), pressLocation.getY());
					};
				};
				Transition release = new Release(">> idling") {
				};
			};
		};

		// Show the state machine
		showStateMachine(dragger);
	}

	static public void main(String[] args) {
		// Create a window containing a canvas
		JFrame frame = new JFrame();
		Canvas canvas = new Canvas(450, 200);
		frame.getContentPane().add(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// TODO Add the dragger state machine to the canvas
		// for the draggable button (just uncomment the following line)
		// addDragger(canvas);
		// Create a simple non-draggable button
		SimpleButton button1 = new SimpleButton(canvas, "Simple button");
		button1.getShape().translateBy(30, 50);

		// TODO Show the state machines of the button
		button1.showStateMachine(button1.sm);
		// TODO Create a draggable button

		// TODO Create another button with a custom behavior

	}
}