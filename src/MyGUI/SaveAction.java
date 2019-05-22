package MyGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.io.FileWriter;

public class SaveAction implements ActionListener {
    private DrawArea panel;
    private java.util.List<String> VEC = new LinkedList<>();

    public SaveAction(DrawArea panel) {
        this.panel = panel;
    }
    @Override
    public void actionPerformed (ActionEvent e)  {
        String filename = "";
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Choose a directory to save your file: ");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFile().isDirectory()) {
                filename = fileChooser.getSelectedFile().toString();
            }
        }

        VEC = panel.getAllVEC();

        try {
            FileWriter writer = new FileWriter(new File(filename + "/output.VEC"));
            for(String str: VEC) {
                if (panel.getHistory().get(VEC.indexOf(str)).isVisible()){
                    String[] s = str.split(",");
                    for (String str2: s) {
                        writer.write(str2);
                        writer.write(System.getProperty("line.separator"));
                }
                }

            }
            writer.close();
            System.out.println("file successfully saved");
        } catch(IOException a) {
            System.out.println("failed to save VEC");
        }

        panel.setSize(new Dimension(4096,4096));
        System.out.println(panel.getWidth());
        BufferedImage bImg = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        panel.paintAll(cg);

        //Save as bmp
        try {
            if (ImageIO.write(bImg, "bmp", new File(filename + "/output.bmp")))
            {
                System.out.println("-- saved");
            }
        } catch (IOException a) {
            // TODO Auto-generated catch block
            System.out.println("failed to save BMP");
            a.printStackTrace();
        }


    }
}