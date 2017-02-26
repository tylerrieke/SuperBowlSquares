package com.rieke.bmore.squares.gui;

import com.rieke.bmore.squares.jetty.SuperBowlSquaresJettyRunner;
import com.rieke.jettylauncher.JettyRunner;
import com.rieke.jettylauncher.gui.Startup;

import javax.swing.*;
import java.awt.*;

public class SuperBowlSquaresStartup extends Startup {

    public static SuperBowlSquaresJettyRunner jettyServer;

    public SuperBowlSquaresStartup(JettyRunner jettyRunner) {
        super(jettyRunner);
    }

    @Override
    protected void init(JettyRunner jettyRunner, Container cp) {
        jettyServer = (SuperBowlSquaresJettyRunner) jettyRunner;
        setTitle("Super Bowl Squares Launcher");
        super.init(jettyRunner,cp);
    }

    public static void main(String[] args) {
        // Run the GUI construction in the Event-Dispatching thread for thread-safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SuperBowlSquaresStartup(new SuperBowlSquaresJettyRunner()); // Let the constructor do the job
            }
        });
    }
}
