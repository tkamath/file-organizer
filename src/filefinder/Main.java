package filefinder;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Parser {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Music File Organizer");
		JButton button = new JButton("Click here!");
		button.setFont(new Font("Sans Serif", Font.BOLD, 20));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String msgToUsers = "Enter source path here";
				String boxTitle = "Music File Organizer";
				JOptionPane genOpPane = new JOptionPane();
				Object result = genOpPane.showInputDialog(null, msgToUsers, boxTitle, JOptionPane.QUESTION_MESSAGE, null, null, null);
				arrangeFiles((String) result);
			}
		});
		
		JButton stop = new JButton("Exit");
		stop.setFont(new Font("Sans Serif", Font.BOLD, 12));
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
		frame.setLayout(new BorderLayout());
		frame.add(button, BorderLayout.NORTH);
		frame.add(stop, BorderLayout.SOUTH);
		frame.setSize(400, 100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}
	
	static void arrangeFiles(String folder) {
		File dir = new File(folder);
		String msgToUsers;
		String boxTitle;
		
		while (!dir.exists()) {
			JOptionPane errorPane = new JOptionPane();
			errorPane.showMessageDialog(null, "Folder not found", "Error", JOptionPane.ERROR_MESSAGE);
			
			msgToUsers = "Enter source path here";
			boxTitle = "Music File Organizer";
			JOptionPane genOpPane = new JOptionPane();
			Object result = genOpPane.showInputDialog(null, msgToUsers, boxTitle, JOptionPane.QUESTION_MESSAGE, null, null, null);			
			dir = new File((String) result);
		}
	//	System.out.println(dir.getParentFile());
		msgToUsers = "Enter name of new folder";
		boxTitle = "Music File Organizer";
		JOptionPane genOpPane = new JOptionPane();
		Object name = genOpPane.showInputDialog(null, msgToUsers, boxTitle, JOptionPane.QUESTION_MESSAGE, null, null, null);
		String newFolderPath = dir.getParentFile() + "\\" + (String) name;
		File newFolder = new File(newFolderPath);
		if (!newFolder.exists()) {
			newFolder.mkdirs();
		} else {
			System.out.println("Already exists");
		}
	//	helperIter(dir, newFolder);
		
		String destStemStr = dir.getParent() + "\\" + newFolder.getName();
		helperIter(dir, destStemStr);
		
		
	}
	
	static void helperIter(File musicFolder, String destStemStr) {
		for (File file: musicFolder.listFiles()) {
			if (!file.isDirectory()) {
				Path destPath = Paths.get(destStemStr, "\\", file.getName());
				try {
					Files.copy(file.toPath(), destPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				helperIter(file, destStemStr);
			}
		}
	}
	
}
