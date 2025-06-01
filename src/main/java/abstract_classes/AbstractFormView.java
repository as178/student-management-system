/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstract_classes;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import utility_classes.NavigationUtil;

/**
 *
 * @author Angela Saric (24237573) & William Niven (24229618)
 *
 * General abstract form viewing class which is extended by any
 * view class with input fields/viewing fields. This class handles
 * a lot of the GUI setup and leaves the unique details and nitty
 * gritty logic to the classes extending it. Using this class
 * ensures a consistent style throughout our program.
 *
 */
public abstract class AbstractFormView<T> extends JFrame {

    protected T currentObject;

    protected AbstractFormView(T currentObject, String frameTitle, String headerTitle) {

        this.currentObject = currentObject;
        this.setTitle(frameTitle);

        //Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        //Header title
        JLabel headerLabel = new JLabel(headerTitle, SwingConstants.CENTER);
        headerLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        //Wrapper panel for header title, lower padding ==> main panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        //Scrollable panel where all the information is presented to the user, added to main panel
        JPanel formPanel = buildFormPanel();
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //Buttons at the bottom of frame
        JPanel buttonPanel = buildButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        //Child components inserted
        this.setContentPane(mainPanel);
    }

    /*
    Method to configure the form panel. To be overriden.
     */
    protected abstract JPanel buildFormPanel();

    /*
    Small helper method for making quick labels with a specified font.
     */
    protected JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    /*
    Small helper method for making quick labels for providing the
    user with formatting hints for editable fields.
     */
    protected JLabel createHintLabel(String text) {
        JLabel hint = new JLabel(text);
        hint.setFont(new Font("Monospaced", Font.ITALIC, 13));
        hint.setForeground(Color.GRAY);
        return hint;
    }

    /*
    Method for configuring the button panel.
     */
    protected JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); //each row to be centered with custom gaps

        JButton saveButton = new JButton("Save Changes");
        JButton backButton = new JButton("Back");
        JButton exitButton = new JButton("Exit");

        /*
        Adding action listeners for buttons:
        
        - "Save Changes" calls on the handleSave() method
            for handling saving of user inputs from the
            text fields to the appropriate tables in the database.
        
        - "Back" lets the user know their changes won't
            be saved and takes them back to the previous
            dashboard.
        
        - "Exit" calls on the NavigationUtil.exitProgram();
            and confirms the user's choice before
            quitting the program.
         */
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSave();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NavigationUtil.exitProgram();
            }
        });

        //Quick array and for loop for final config and adding buttons to the panel
        JButton[] buttons = {saveButton, backButton, exitButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Monospaced", Font.BOLD, 14));
            panel.add(button);
        }

        return panel;
    }

    /*
    This method takes the input from all the text fields and checks
    them using the ValidationUtil (established rules and restrictions
    for the information). If a restriction is breached the user is 
    alerted, otherwise the new information is saved accordingly.
    To be overriden.
     */
    protected abstract void handleSave();

    /*
    Helper method for handling going back to the previous dashboard.
    To be overriden.
     */
    protected abstract void handleBack();
}
