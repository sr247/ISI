
//package e3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

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

		String[] dirTab = directory.split("/");
		ArrayList<String> fileTab = new ArrayList<String>();
		file = "";
		Vector model = new Vector();
		
		JComboBox mainBox = new JComboBox();
		mainBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
			      System.out.println("événement déclenché sur : " + e.getItem());
					File f = new File(file);
					for (File fs : f.listFiles()) {
						System.out.println("#" + fs.getName());
						model.add(fs.isDirectory() ? fs.getName() + "/" : fs.getName());
					}
			    }
			
		});

		show(System.getProperty("user.dir"));

		
		

		for (String s : dirTab) {
			file += s + "/";
			fileTab.add(file);
			System.out.println("Dossier en cours :" + file);
			mainBox.addItem(s);

		}

		// File f = new File("/home/stephane");
		// File[] fs = f.listFiles();
		// System.out.println(fs[0].getName());

		JList fileList = new JList(model);
		fileList.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

			}

		});

		JButton open = new JButton(lbl_ok);
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("ActionListener : action sur " + fileList.getSelectedValue());				
				Color color1 = new Color(255, 0, 0, 128);
				fileList.setSelectionBackground(color1);
				/*
				 * Ici on fera open : si fichier alors on l'ouvre
				 * 
				 * TODO
				 * 
				 * Si Directory alors on l'ouvre dans la JcomboBox
				 */
			}
		});

		JButton cancel = new JButton(lbl_cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		JPanel pBottom = new JPanel();
		pBottom.add(cancel, BorderLayout.SOUTH);
		pBottom.add(open, BorderLayout.SOUTH);

		Container pan = this.getContentPane();
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		pan.setLayout(new BorderLayout());
		pan.add(mainBox, BorderLayout.NORTH);
		pan.add(fileList, BorderLayout.CENTER);
		pan.add(pBottom, BorderLayout.SOUTH);

		pack();
		setVisible(true);
	}

	void JBoxDirSelector() {

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
