package com.boneless.cube.networking;

import java.io.*;
import java.net.*;

public class SimpleChat {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java SimpleChat <host> <port> <username>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        try {
            // Create a socket to connect to the server
            Socket socket = new Socket(host, port);

            // Input and output streams for the socket
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Input stream for user input from terminal
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to " + host + ":" + port);

            // Create a thread to read messages from the server
            Thread readThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readThread.start();

            // Main loop to send messages from the client
            String userInputLine;
            while ((userInputLine = userInput.readLine()) != null) {
                out.println(username + ": " + userInputLine);
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
