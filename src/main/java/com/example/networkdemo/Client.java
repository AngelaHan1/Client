package com.example.networkdemo;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {
    // IO streams
//    DataOutputStream toServer = null;
//    DataInputStream fromServer = null;
    ObjectOutputStream toServer = null;
    ObjectInputStream fromServer = null;


    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

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

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("localhost", 8000);
            // Socket socket = new Socket("130.254.204.36", 8000);
            // Socket socket = new Socket("drake.Armstrong.edu", 8000);

            // Create an output stream to send data to the server
            toServer = new ObjectOutputStream(socket.getOutputStream());

            // Create an input stream to receive data from the server
            fromServer = new ObjectInputStream(socket.getInputStream());

        } catch (IOException ex) {
            ta.appendText(ex.toString() + '\n');
        }

        sendButton.setOnAction(e -> {
            try {
                // Get the message from the text field
//                String message = tf.getText().trim();

                //Typess type = HumanTypes.CREATE_GAME;

                //Typess type = HumanTypes.CREATE_GAME;
                Object message = new Message("single", HumanTypes.CREATE_GAME);


                // uncomment this block to send the MAKE_MOVE message for testing
                //********************************
                //Move move = new Move(0,2,'X', "room1");
                //Object message = new Message(move, HumanTypes.MAKE_MOVE);
                //********************************

                
                // Downcast message from Object
                Message msg = (Message)message;
                System.out.println("sending: " + msg.getType().getDescription());

                //Object msg = message;
                // Send the message to the server
                toServer.writeObject(message);


            } catch (IOException ex) {
                System.err.println(ex);
            }
        });

        new Thread( () -> {

                while (true) {
                    try {
                        // read the message sent to this client
                        Object msg = fromServer.readObject();

                        // Downcast message from Object
                        Message mess = (Message)msg;

                        Platform.runLater( () -> {
                            // Display to the text area
                            ta.appendText(mess.getType().getDescription() + "\n");
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                    catch (ClassNotFoundException ex){
                        ex.printStackTrace();
                    }
                }
        }).start();


    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
