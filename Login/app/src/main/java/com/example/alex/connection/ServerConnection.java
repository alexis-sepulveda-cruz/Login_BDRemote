package com.example.alex.connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @autor Armando Alexis Sepulveda Cruz
 * @date 2015-09-28
 * @private Clase que comunica los datos entre la aplicaci�n Android y Servicio Web (un script como php)
 * por medio de JSON.
 */
public class ServerConnection {

    public final String POST = "POST";
    public final String GET = "GET";

    /**
     * @private Recibe la respuesta del Servicio Web
     * @param webUrl La url con la cual se comunicar� la aplicaci�n Android
     * @return JSONObject con la respuesta del Servicio Web
     */
    public JSONObject makeHttpRequestGet(String webUrl) {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        String line = "";

        try {
            url = new URL(webUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * @private Env�a par�metros y recibe la respuesta del Servicio Web
     * @param webUrl La url con la cual se comunicar� la aplicaci�n Android
     * @param params Par�metros a enviar al servicio Web
     * @return JSONObject con la respuesta del Servicio Web
     */
    public JSONObject makeHttpRequestPost(String webUrl, String params) {
        URL url;
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;
        DataOutputStream outputStream;
        String line = "";

        try {
            url = new URL(webUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(POST);

            // Send post request
            connection.setDoOutput(true);
            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(params);
            outputStream.flush();
            outputStream.close();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            jsonObject = new JSONObject(buffer.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}