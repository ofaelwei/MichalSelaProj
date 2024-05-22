package com.cyberark.selapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class BackendAPI {

    private String m_url_events = "http://54.163.58.248:3000/events";
    private String m_url_sessions = "http://54.163.58.248:3000/app-session";

    public void SendDataToBackend(String eventType, String eventTime) {
        try {
            // Create URL object
            URL url = new URL(m_url_events);

            // Create JSON object dynamically
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("userId", "user123");
            jsonInput.put("eventName", eventType);
            jsonInput.put("eventTime", eventTime);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input/output streams
            connection.setDoOutput(true);

            // Write JSON input string to output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check the response code
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            // Read the response from the server
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                in.close();
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("POST request not worked");
            }

            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SendSessionDataToBackend(String userId, String appName, String startDateServer, String endTimeServer) {
        try {
            // Create URL object
            URL url = new URL(m_url_sessions);

            // Create JSON object dynamically
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("userId", "user123");
            jsonInput.put("appName", appName);
            jsonInput.put("startSession", startDateServer);
            jsonInput.put("endSession", endTimeServer);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to POST
            connection.setRequestMethod("POST");

            // Set request headers
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input/output streams
            connection.setDoOutput(true);

            // Write JSON input string to output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check the response code
            int responseCode = connection.getResponseCode();
            System.out.println("POST Response Code: " + responseCode);

            // Read the response from the server
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                in.close();
                System.out.println("Response: " + response.toString());
            } else {
                System.out.println("POST request not worked");
            }

            // Close connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

