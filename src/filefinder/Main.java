package filefinder;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame("Music File Organizer");
    JButton button = new JButton("Click here!");
    button.setFont(new Font("Sans Serif", 1, 20));
    button.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        String msgToUsers = "Enter source path here";
        String boxTitle = "Music File Organizer";
        JOptionPane genOpPane = new JOptionPane();
        Object result = JOptionPane.showInputDialog(null, msgToUsers, boxTitle, 3, null, null, null);
        Main.arrangeFiles((String)result);
      }
    });
    JButton stop = new JButton("Exit");
    stop.setFont(new Font("Sans Serif", 1, 12));
    stop.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent arg0)
      {
        System.exit(0);
      }
    });
    frame.setLayout(new BorderLayout());
    frame.add(button, "North");
    frame.add(stop, "South");
    frame.setSize(400, 100);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(0);
  }
  
  static void arrangeFiles(String folder)
  {
    File dir = new File(folder);
    while (!dir.exists())
    {
      JOptionPane errorPane = new JOptionPane();
      JOptionPane.showMessageDialog(null, "Folder not found", "Error", 0);
      
      String msgToUsers = "Enter source path here";
      String boxTitle = "Music File Organizer";
      JOptionPane genOpPane = new JOptionPane();
      Object result = JOptionPane.showInputDialog(null, msgToUsers, boxTitle, 3, null, null, null);
      dir = new File((String)result);
    }
    String msgToUsers = "Enter name of new folder";
    String boxTitle = "Music File Organizer";
    JOptionPane genOpPane = new JOptionPane();
    Object name = JOptionPane.showInputDialog(null, msgToUsers, boxTitle, 3, null, null, null);
    String newFolderPath = dir.getParentFile() + "\\" + (String)name;
    File newFolder = new File(newFolderPath);
    if (!newFolder.exists()) {
      newFolder.mkdirs();
    } else {
      System.out.println("Already exists");
    }
    String destStemStr = dir.getParent() + "\\" + newFolder.getName();
    helperIter(dir, destStemStr);
  }
  
  static void helperIter(File musicFolder, String destStemStr)
  {
    for (File file : musicFolder.listFiles()) {
      if (!file.isDirectory())
      {
        Path destPath = Paths.get(destStemStr, new String[] { "\\", file.getName() });
        try
        {
          Files.copy(file.toPath(), destPath, new CopyOption[0]);
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
      else
      {
        helperIter(file, destStemStr);
      }
    }
  }
}
