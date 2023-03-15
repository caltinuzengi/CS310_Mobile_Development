package com.example.trail_01;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentRepository {


    public void GetCommentsByNewsId(ExecutorService srv, Handler uiHandler, int newsId){

        srv.execute(()->{

            try{

                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + String.valueOf(newsId));

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject current = new JSONObject(buffer.toString());
                JSONArray arr = current.getJSONArray("items");
                List<Comment> data = new ArrayList<>();
                for(int i = 0; i < arr.length(); i++){
                    JSONObject obj = arr.getJSONObject(i);
                    Comment lstObj = new Comment(obj.getInt("id"), obj.getInt("news_id"),
                            obj.getString("text"), obj.getString("name"));

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

    public void postComment(ExecutorService srv, Handler uiHandler, String name,
                            String text, String news_id){

        srv.execute(()->{

            try{
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("name", name);
                outputData.put("text", text);
                outputData.put("news_id", news_id);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine()) != null){
                    buffer.append(line);
                }

                JSONObject retVal = new JSONObject(buffer.toString());

                conn.disconnect();

                String retValStr = "name:" + retVal.getString("name") +
                        ", text:" + retVal.getString("text") +
                        ", news_id:" + retVal.getString("news_id");

                //conn.disconnect();

                Message msg = new Message();
                msg.obj = retValStr;
                //msg.obj = retVal;

                uiHandler.sendMessage(msg);



            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }



}
