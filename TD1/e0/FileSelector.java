
//package e3;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 * @author Nicolas Roussel (roussel@lri.fr)
 *
 */
public class FileSelector extends JDialog {

	private String directory = null;
	private String file = null;

	public FileSelector(String title, String lbl_cancel, String lbl_ok) {
		super((JFrame) null, title, true /* modal */);

		Container pan = this.getContentPane();
		pan.setLayout(new BorderLayout());
		JComboBox mainBox = new JComboBox();

		show(System.getProperty("user.dir"));

		String[] dirTab = directory.split("/");
		for (String s : dirTab) {
			System.out.println(s);
			mainBox.addItem(s);
		}

		JList fileList = new JList();
		for (String s : dirTab) {
			System.out.println(s);
			mainBox.addItem(s);
		}

		JButton open = new JButton(lbl_ok);
		JButton cancel = new JButton(lbl_cancel);

		JPanel pBottom = new JPanel();
		pBottom.add(cancel, BorderLayout.SOUTH);
		pBottom.add(open, BorderLayout.SOUTH);

		pan.add(mainBox, BorderLayout.NORTH);
		pan.add(fileList, BorderLayout.CENTER);
		pan.add(pBottom, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	Boolean show(String path) {
		File dir = new File(path);
		if (!dir.exists() || !dir.isDirectory())
			return false;

		directory = dir.getAbsolutePath();
		System.out.println(directory);
		file = null;

		String[] files = dir.list();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				File f = new File(path, files[i]);
				if (f.isDirectory())
					files[i] = files[i] + File.separator;
				System.out.format("  file: %s\n", files[i]);
			}
		}

		System.out.format("  parentdir: %s\n", File.separator);
		String[] dirs = path.split("/");
		for (String p : dirs) {
			if (p.equals(""))
				continue;
			System.out.format("  parentdir: %s\n", p);
		}

		return true;
	}

	public String getFilePath() {
		if (directory == null || file == null)
			return null;
		return directory + File.separator + file;
	}

	static public void main(String args[]) {
		FileSelector fs = new FileSelector("File open...", "Cancel", "Open");
		System.out.println(fs.getFilePath());
		System.exit(1);
	}

}
