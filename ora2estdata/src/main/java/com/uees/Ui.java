package com.uees;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import java.util.LinkedList;

public class Ui extends JFrame {
    private com.uees.GraphLA<String> grafo;
    private HashMap<String, HashSet<String>> filmActorsMap;
    private JPanel center;
    private JLabel label2;
    private JLabel label3;
    private JLabel titleLabel;
    private JButton button;
    private LinkedList<Movie> movies;
    private LinkedList<String> uniqueActors;

    private JComboBox<String> comboBoxSrc;
    private JComboBox<String> comboBoxDest;
    private String csvFile;
    private String csvSplitBy;
    private String line;

    @SuppressWarnings("unchecked")
    public Ui() {
        super("Oracle Of Bacon");
        /** Previous Calculations */
        LinkedList<Movie> movies = new LinkedList<>();
        grafo = new com.uees.GraphLA<>(false);

        LinkedList<String> uniqueActors = new LinkedList<>();
        // Make a method that copy only the authors of the Movies List
        JButton button = new JButton("Encontrar Numero");
        JLabel label3 = new JLabel("To Actor:");
        JComboBox<String> comboBoxSrc = new JComboBox<>();
        JComboBox<String> comboBoxDest = new JComboBox<>();
        JLabel label2 = new JLabel("From Actor:");
        JLabel titleLabel = new JLabel("Movie Oracle", SwingConstants.CENTER);
        JPanel center = new JPanel(new GridBagLayout());

        // Read the CSV file and populate the movies list
        String csvFile = "ora2estdata/src/main/resources/dataset/actorfilms.csv";
        String csvSplitBy = ",";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(csvSplitBy);
                if (fields.length < 7) {
                    // Skip lines that do not have enough fields
                    continue;
                }

                String actor = fields[0];
                String actorID = fields[1];
                String film = fields[2];
                int year;
                int votes;
                double rating;
                String filmID = fields[6];

                try {
                    year = Integer.parseInt(fields[3].trim());
                    votes = Integer.parseInt(fields[4].trim());
                    rating = Double.parseDouble(fields[5].trim());
                } catch (NumberFormatException e) {
                    // Skip this record if parsing fails
                    System.err.println("Skipping record due to parsing error: " + e.getMessage());
                    continue;
                }

                Movie movie = new Movie(actor, actorID, film, year, votes, rating, filmID);
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Populate the comboboxes with unique actors
        for (Movie movie : movies) {
            if (!uniqueActors.contains(movie.getActor())) {
                uniqueActors.add(movie.getActor());
            }
        }
    
        Collections.sort(uniqueActors);
        // Add the unique actors to the comboboxes
        for (String actor : uniqueActors) {
            comboBoxSrc.addItem(actor);
            comboBoxDest.addItem(actor);
        }
    
        System.out.println("Total Movies: " + movies.size());
        System.out.println("Unique Actors: " + uniqueActors.size());
        HashMap<String, HashSet<String>> filmActorsMap = new HashMap<>();

        // Create a map of films to actors
        for (Movie movie : movies) {
            String film = movie.getFilm();
            String actor = movie.getActor();
            filmActorsMap.putIfAbsent(film, new HashSet<>());
            filmActorsMap.get(film).add(actor);
        }
        // Add vertices to the graph
        for (String actor : uniqueActors) {
            grafo.addVertex(actor);
        }

        // Add edges to the graph
        for (HashSet<String> actors : filmActorsMap.values()) {
            for (String actor1 : actors) {
                for (String actor2 : actors) {
                    if (!actor1.equals(actor2)) {
                        grafo.addEdge(actor1, actor2, 1);
                    }
                }
            }
        }
        System.out.println("Graph created");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        addComponents(center, label2, comboBoxSrc, label3, comboBoxDest, button, titleLabel, center);

        setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void addComponents(JPanel panel, JLabel label2, JComboBox<String> comboBoxSrc, JLabel label3,
            JComboBox<String> comboBoxDest, JButton button, JLabel titleLabel, JPanel center) {

        // Center
        GridBagConstraints gbc = new GridBagConstraints();
        ;
        gbc.insets.set(10, 10, 10, 10);// Padding

        comboBoxDest.setMinimumSize(new Dimension(150, 20));
        comboBoxDest.setPreferredSize(new Dimension(150, 20));

        comboBoxSrc.setMinimumSize(new Dimension(150, 20));
        comboBoxSrc.setPreferredSize(new Dimension(150, 20));

        if (label2 != null && comboBoxSrc != null && label3 != null && comboBoxDest != null && button != null
                && center != null) {
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // Span across two columns
            center.add(titleLabel, gbc);
            gbc.gridwidth = 1;

            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            center.add(label2, gbc);
            gbc.gridx = 1;
            center.add(comboBoxSrc, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            center.add(label3, gbc);
            gbc.gridx = 1;
            center.add(comboBoxDest, gbc);

            add(center, BorderLayout.CENTER);
            // Add button under textDest
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2; // Span across two columns
            center.add(button, gbc);

            button.addActionListener(e -> {
                String src = comboBoxSrc.getSelectedItem().toString();
                String dest = comboBoxDest.getSelectedItem().toString();
                System.out.println("From: " + src + " To: " + dest);
                if(grafo == null) {
                    System.out.println("Error: Null graph");
                    return;
                }
                grafo.dijkstra(src);
                System.out.println("Path from " + src );
               // System.out.println(grafo.toString());
                LinkedList<Vertex<String>> tmp = grafo.pathFindertoVertex(dest);
                PopUp pop = new PopUp(tmp.size());
                tmp.forEach(a -> {
                    pop.addLabel(a.getData());
                });
                pop.setVisible(true);



                return;
 
            });

        } else {
            System.out.println("Error: Null component");
        }

    }

    public static void initiateGui() {
        new Ui();
    }

    // Test Main
    public static void main(String[] args) {
        initiateGui();
    }

}
