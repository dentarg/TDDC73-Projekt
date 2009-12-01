package tests;

import ui.components.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import java.util.ArrayList;

class ListItem implements Displayable {
    private Image image;
    private String text;

    public ListItem(Image image, String text) {
        this.image = image;
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public String toString() {
        return text;
    }
}

class TestAddRemoveComponent {
    private static final String frameTitle = "Test";

    public static void main(String[] args) {
        new TestAddRemoveComponent();
    }

    private AddRemoveComponent addRemoveComponent;

    public TestAddRemoveComponent() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame(frameTitle);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addRemoveComponent = new AddRemoveComponent(frame);

        BufferedImage horseImage = null;
        BufferedImage ballImage = null;
        BufferedImage smileImage = null;

        try {
            horseImage = ImageIO.read(new File("horse.png"));
            ballImage = ImageIO.read(new File("ball.png"));
            smileImage = ImageIO.read(new File("smile.png"));
        } catch(IOException e) {
            System.out.println("meeeh");
        }

        ArrayList<Object> contents = new ArrayList<Object>();
        contents.add(new ListItem(horseImage, "Hest"));
        contents.add(new ListItem(ballImage, "Boll"));
        contents.add(new ListItem(horseImage, "En till hest"));
        contents.add(new ListItem(ballImage, "En till boll"));
        contents.add(new ListItem(smileImage, "Nisse"));
        contents.add(new ListItem(smileImage, "Olle"));
        contents.add(new ListItem(horseImage, "Kalle"));
        contents.add(new ListItem(smileImage, "Kent"));

        addRemoveComponent.setContents(contents);

        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(addRemoveComponent, BorderLayout.NORTH);

        //frame.pack();
        frame.setSize(new Dimension(300, 500));
        frame.setVisible(true);

        addRemoveComponent.setSelected(new ListItem(smileImage, "untz"));

        addRemoveComponent.addAddRemoveListener(new AddRemoveListener() {
            public void objectAdded(Object o) {
                System.out.println(o.toString() + " added");
            }

            public void objectRemoved(Object o) {
                System.out.println(o.toString() + " removed");
            }

            public void objectSelected(Object o) {
                System.out.println(o.toString() + " selected");
            }

            public void selectedObjectRemoved(Object o) {
                System.out.println(o.toString() + " removed while selected");
            }
        });
    }
}
