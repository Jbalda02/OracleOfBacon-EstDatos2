package com.uees;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.GridLayout;
public class PopUp extends JFrame {

    public PopUp(int numberOfBacon) {
        super("Answer");
        JLabel label = new JLabel("The number of bacon is: " + (numberOfBacon - 2));
        label.setAlignmentX(SwingConstants.CENTER);
        label.setAlignmentY(SwingConstants.CENTER);
        setLayout(new GridLayout((numberOfBacon + 1) * 3, 1));
        add(label);
        pack();
        setLocationRelativeTo(super.getParent());
        setSize(300, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }
    public void addLabel(String text){

        JLabel label = new JLabel(text);
        label.setAlignmentX(SwingConstants.CENTER);
        label.setAlignmentY(SwingConstants.CENTER);
        label.setBackground(Color.white);
        add(label);
    }
}
