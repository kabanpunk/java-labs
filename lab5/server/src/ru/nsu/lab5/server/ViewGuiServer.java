package ru.nsu.lab5.server;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ViewGuiServer {
    private static final Logger logger = LogManager.getLogger(ViewGuiServer.class);

    private static final int TEXT_AREA_ROWS_AMOUNT = 10;
    private static final int TEXT_AREA_COLUMNS_AMOUNT = 10;

    private static final String APP_NAME = "Server";
    private static final String RUN_BUTTON_NAME = "Run server";
    private static final String STOP_BUTTON_NAME = "Stop server";

    private final JFrame frame = new JFrame(APP_NAME);
    private final JTextArea dialogWindow = new JTextArea(TEXT_AREA_ROWS_AMOUNT, TEXT_AREA_COLUMNS_AMOUNT);
    private final JButton startServerButton = new JButton(RUN_BUTTON_NAME);
    private final JButton stopServerButton = new JButton(STOP_BUTTON_NAME);
    private final JPanel panelButtons = new JPanel();

    private final AbstractServer server;

    public ViewGuiServer(SimpleServer server) {
        this.server = server;
    }

    public ViewGuiServer(JsonServer server) {
        this.server = server;
    }


    public void initFrameServer() {
        dialogWindow.setEditable(false);
        dialogWindow.setLineWrap(true);
        frame.add(new JScrollPane(dialogWindow), BorderLayout.CENTER);
        panelButtons.add(startServerButton);
        panelButtons.add(stopServerButton);
        frame.add(panelButtons, BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                server.stopServer();
                System.exit(0);
            }
        });
        frame.setVisible(true);

        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = getPortFromOptionPane();
                server.startServer(port);
            }
        });
        stopServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
            }
        });
    }



    public void refreshDialogWindowServer(String serviceMessage) {
        dialogWindow.append(serviceMessage);
    }


    protected int getPortFromOptionPane() {
        while (true) {
            String port = JOptionPane.showInputDialog(
                    frame, "Type server's port",
                    "Server port",
                    JOptionPane.QUESTION_MESSAGE
            );
            try {
                return Integer.parseInt(port.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        frame, "Error with typing port occurred",
                        "Wrong port typed", JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}