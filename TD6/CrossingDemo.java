import java.awt.Container;
import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.ObjectInputStream.GetField;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import fr.lri.swingstates.sm.JStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Press;
import fr.lri.swingstates.sm.transitions.Drag;
import fr.lri.swingstates.sm.transitions.Release;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CStateMachine;

// --------------------------------------------------------------------------------------



class CrossingTrace extends CStateMachine {

        // ...
        private CPolyLine line;
        private Point2D p1;
        private Canvas canvas;

        CrossingTrace(Canvas c) {
                canvas = c;
        }

        State waiting = new State() {
                Transition press = new Press(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER, ">> drawing") {

                        public void action() {
                                p1 = getPoint();
                                line = canvas.newPolyLine(p1);
                                line.setFilled(false);
                        }
                };
        };

        State drawing = new State() {
                Transition draw = new Drag(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER) {
                        public void action() {
                                line.lineTo(getPoint());
                        }
                };
                Transition release = new Release(CStateMachine.BUTTON1, CStateMachine.NOMODIFIER, ">> waiting") {
                        public void action() {
                                line.remove();
                        }
                };
        };
};



// --------------------------------------------------------------------------------------

public class CrossingDemo extends JFrame {

        public CrossingDemo() {
                super("CrossingDemo");

                Container pane = getContentPane();
                pane.setSize(new Dimension(400, 300));
                pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

                Canvas canvas = new Canvas(getContentPane().getWidth(), getContentPane().getHeight());
                CrossingTrace ct = new CrossingTrace(canvas);

                canvas.attachSM(ct, true);

                // Question 3
                // Créer une série de JCheckBox JButton et de JRadioButton
                JCheckBox b1 = new JCheckBox("Option 1");
                JCheckBox b2 = new JCheckBox("Option 2");
                JCheckBox b3 = new JCheckBox("Cool");

                JCheckBox b4 = new JCheckBox("Option 3");
                JCheckBox b5 = new JCheckBox("Option 4");
                JRadioButton b6 = new JRadioButton("Cool");
                JRadioButton b7 = new JRadioButton("Awesome");

                pane.add(canvas);
                setGlassPane(canvas);
                canvas.setVisible(true);
                canvas.setOpaque(false);

                // Organiser les boutons en colonnes
                pane.add(b1);
                pane.add(b2);
                pane.add(b3);
                pane.add(b4);
                pane.add(b5);
                pane.add(b6);
                pane.add(b7);

                pack();
                setVisible(true);
        }

        static public void main(String args[]) {
                CrossingDemo demo = new CrossingDemo();
        }

}
