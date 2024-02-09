package com.boneless.cube.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String serverAddress = "192.0.0.2";  // Replace with actual server IP
        int portNumber = 8080;

        try (Socket socket = new Socket(serverAddress, portNumber)) {
            // Output to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // Input from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send data or commands to server
            out.println("Hello from client!");

            // Read server response
            String serverResponse = in.readLine();
            System.out.println("Server says: " + serverResponse);

        } catch (IOException e) {
            System.out.println("Exception on client: " + e);
        }
    }
}
