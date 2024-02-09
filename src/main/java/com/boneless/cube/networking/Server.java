package com.boneless.cube.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server { //todo rework entire project to work with server/client connecting and verification like source engine
    public static void main(String[] args) throws IOException {
        int portNumber = 8080; // Choose a port number for communication

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port " + portNumber);
            Socket clientSocket = serverSocket.accept(); // Wait for a client
            System.out.println("Client connected!");

            // Input from client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // Output to client
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Receive and send data (add your command handling logic here)
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                // Process command as needed ...

                out.println("Response from server"); // Example response
            }

        } catch (IOException e) {
            System.out.println("Exception on server: " + e);
        }
    }
}
