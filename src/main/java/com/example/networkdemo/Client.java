package com.example.networkdemo;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
    // IO streams
    DataOutputStream toServer = null;
    DataInputStream fromServer = null;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) throws IOException {

        // Button to send message.
        Button sendButton = new Button("send");
        sendButton.setDefaultButton(true);

        // Panel p to hold the label and text field
        BorderPane paneForTextField = new BorderPane();
        paneForTextField.setPadding(new Insets(5, 5, 5, 5));
        paneForTextField.setStyle("-fx-border-color: green");
        paneForTextField.setLeft(new Label("Enter message: "));
        paneForTextField.setRight(sendButton);

        TextField tf = new TextField();
        tf.setAlignment(Pos.BOTTOM_RIGHT);
        paneForTextField.setCenter(tf);

        BorderPane mainPane = new BorderPane();
        // Text area to display contents
        TextArea ta = new TextArea();
        mainPane.setCenter(new ScrollPane(ta));
        mainPane.setBottom(paneForTextField);

        // Create a scene and place it in the stage
        Scene scene = new Scene(mainPane, 450, 200);
        primaryStage.setTitle("Client"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        sendButton.setOnAction(e -> {

                /*String message;

                    // Get the radius from the text field
                    message = tf.getText().trim();

                    System.out.println(message);

                    // Send the message to the server
                    toServer.writeUTF(message);
                    toServer.flush();

                    // Clear text field area
                    tf.setText("");

                    // Get message from the server
                    message = fromServer.readUTF();
                    // Display to the text area
                    ta.appendText(message + "\n"); */
                // sendMessage thread
                Thread sendMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                            try {
                                // read the message to deliver.
                                System.out.println("Start print message");
                                String msg = tf.getText().trim();
                                // write on the output stream
                                toServer.writeUTF(msg);
                                //toServer.flush();

                                // Clear text field area
                                tf.setText("");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                });

                sendMessage.start();
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        System.out.println("Start read msg");
                        // read the message sent to this client
                        if (fromServer != null)
                        {
                            String msg = fromServer.readUTF();
                            System.out.println(msg);

                            ta.appendText(msg + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        readMessage.start();

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            // Socket socket = new Socket("130.254.204.36", 8000);
            // Socket socket = new Socket("drake.Armstrong.edu", 8000);

            // Create an input stream to receive data from the server
            fromServer = new DataInputStream(socket.getInputStream());

            // Create an output stream to send data to the server
            toServer = new DataOutputStream(socket.getOutputStream());

        }
        catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }

    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
