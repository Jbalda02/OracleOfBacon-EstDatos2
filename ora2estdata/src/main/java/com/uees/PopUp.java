package com.uees;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.FlowLayout;
public class PopUp extends JFrame {
    private int numberOfBacon;

    public PopUp(int numberOfBacon) {
        super("Answer");
        this.numberOfBacon = numberOfBacon;
        JLabel label = new JLabel("The number of bacon is: " + numberOfBacon);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(label);
        pack();
        setSize(300, 100);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
    public void addLabel(String text)
    {
        JLabel label = new JLabel(text);
        add(label);
    }
}
