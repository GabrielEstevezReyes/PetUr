package com.example.urpet.connections;

import android.os.StrictMode;
import android.util.Log;

import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class ApiPetition {

    private static String apiURl = "http://216.250.119.28:8586/UrpetApi/api/";

    private static String createSubFiler(String table, String filter, String Value){
        String res = table;
        if(!filter.isEmpty()){
            res += ("/filterBy" + filter);
        }
        if(!Value.isEmpty()){
            res += ("/" + Value);
        }
        return res;
    }

    public static JSONObject getData(String table, String filter, String Value){
        JSONObject jsonObject = null;
        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;

        try {
            url = new URL(apiURl + createSubFiler(table, filter, Value)      );
            Log.println(Log.DEBUG, "PetitionURL:", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";

            while((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();
            JSONArray jsonArray = new JSONArray(json);
            jsonObject = jsonArray.getJSONObject(0);
            return jsonObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }
        return jsonObject;
    }

    public static ArrayList<JSONObject> getDataList(String table, String filter, String Value){
        ArrayList<JSONObject> objects = new ArrayList<>();
        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;

        try {
            url = new URL(apiURl + createSubFiler(table, filter, Value));
            Log.println(Log.DEBUG, "PetitionURL:", url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            String json = "";

            while((inputLine = br.readLine()) != null){
                response.append(inputLine);
            }
            json = response.toString();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                objects.add(jsonObject);
            }
            return objects;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return objects;
        } catch (IOException e) {
            e.printStackTrace();
            return objects;
        } catch (JSONException e) {
            e.printStackTrace();
            return objects;
        }
    }

    public static void insertData(String table, JSONObject obj){
        String result = null;

        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        HttpURLConnection connection;

        try {
            // creación de la conexión
            url = new URL(apiURl + table);
            Log.println(Log.DEBUG, "PetitionURLS:", url.toString());
            connection = (HttpURLConnection) url.openConnection();

            // definición de los parametros de conexion
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            // Obtener el desultado del request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode(); // para ver si la conexion resulto bien
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder("");
                String linea;
                while((linea = in.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                in.close();
                result = sb.toString();
            }else{
                result = "Error: " + responseCode;
            }
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean insertDatar(String table, JSONObject obj){

        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url;
        HttpURLConnection connection;

        try {
            // creación de la conexión
            url = new URL(apiURl + table);
            Log.println(Log.DEBUG, "PetitionURLS:", url.toString());
            connection = (HttpURLConnection) url.openConnection();

            // definición de los parametros de conexion
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            // Obtener el desultado del request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode(); // para ver si la conexion resulto bien
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder("");
                String linea;
                while((linea = in.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                in.close();
                return  true;
            }else{
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void updateData(String table, JSONObject obj){
        String result = null;

        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;

        try {
            // creación de la conexión
            url = new URL(apiURl + table);
            Log.println(Log.DEBUG, "PetitionURLS:", url.toString());
            connection = (HttpURLConnection) url.openConnection();

            // definición de los parametros de conexion
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            // Obtener el desultado del request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(obj.toString());
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode(); // para ver si la conexion resulto bien
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String linea = "";
                while((linea = in.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                in.close();
                result = sb.toString();
                System.out.println(result);
            }else{
                result = new String("Error: " + responseCode);
                System.out.println(result);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(String table, int id){
        String result = null;

        // Para dar permisos
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection connection;

        try {
            // creación de la conexión
            url = new URL(apiURl + table + "/" + id);
            Log.println(Log.DEBUG, "PetitionURLS:", url.toString());
            connection = (HttpURLConnection) url.openConnection();

            // definición de los parametros de conexion
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setUseCaches(false);

            // Obtener el desultado del request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode(); // para ver si la conexion resulto bien
            if(responseCode==HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String linea = "";
                while((linea = in.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                in.close();
                result = sb.toString();
                System.out.println(result);
            }else{
                result = new String("Error: " + responseCode);
                System.out.println(result);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Transformacion de JSON Object a String
    public static String getPostDataString(JSONObject jsonObject) throws Exception{

        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> iterator = jsonObject.keys();

        while(iterator.hasNext()){
            String key = iterator.next();
            Object value = jsonObject.get(key);

            if(first) first = false;
            else result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return  result.toString();
    }
}
