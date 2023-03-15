package com.example.trail_01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.example.cs310newstrail.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {

    String BASE_URL = "http://10.3.0.14:8080/newsapp/";


    public void getAllData(ExecutorService srv, Handler uiHandler){

        srv.execute(()->{
            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");
                HttpURLConnection conn =(HttpURLConnection)url.openConnection();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject current = new JSONObject(buffer.toString());
                JSONArray arr = current.getJSONArray("items");
                //JSONArray arr = new JSONArray(buffer.toString());
                List<com.example.cs310newstrail.News> data = new ArrayList<>();
                for(int i = 0; i < arr.length(); i++){
                    JSONObject obj = arr.getJSONObject(i);
                    com.example.cs310newstrail.News lstObj = new com.example.cs310newstrail.News(obj.getInt("id"),
                            obj.getString("title"), obj.getString("text") , obj.getString("date"),
                            obj.getString("image"), obj.getString("categoryName"));

                    data.add(lstObj);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        });


    }



    public void GetNewsByCategoryId(ExecutorService srv, Handler uiHandler, int categoryId){

        srv.execute(()->{

            try{

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + String.valueOf(categoryId));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject current = new JSONObject(buffer.toString());
                JSONArray arr = current.getJSONArray("items");
                List<com.example.cs310newstrail.News> data = new ArrayList<>();
                for(int i = 0; i < arr.length(); i++){
                    JSONObject obj = arr.getJSONObject(i);
                    com.example.cs310newstrail.News lstObj = new com.example.cs310newstrail.News(obj.getInt("id"),
                            obj.getString("title"), obj.getString("text") , obj.getString("date"),
                            obj.getString("image"), obj.getString("categoryName"));

                    data.add(lstObj);
                }

                conn.disconnect();

                Message msg = new Message();
                msg.obj = data;

                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void GetNewsById(ExecutorService srv, Handler uiHandler, int newsId){

        srv.execute(()->{
            try{
                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + String.valueOf(newsId));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject current = new JSONObject(buffer.toString());
                JSONArray arr = current.getJSONArray("items");
                List<com.example.cs310newstrail.News> data = new ArrayList<>();
                for(int i = 0; i < arr.length(); i++){
                    JSONObject obj = arr.getJSONObject(i);
                    com.example.cs310newstrail.News lstObj = new com.example.cs310newstrail.News(obj.getInt("id"),
                            obj.getString("title"), obj.getString("text") , obj.getString("date"),
                            obj.getString("image"), obj.getString("categoryName"));

                    data.add(lstObj);
                }

                conn.disconnect();

                Message msg = new Message();
                msg.obj = data.get(0);

                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }


    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });


    }


}
