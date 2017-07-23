package com.example.miroku.weatherinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    public String Address;
    public URL url;
    public BufferedReader br;
    public HttpURLConnection conn;
    public String protocol = "GET";
    String ip;
    TextView textview;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Address = "http://www.findip.kr/";
        StringBuffer buffer = new StringBuffer();
        StringBuilder builder = new StringBuilder();
        textview = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textview.setText("IP주소를 불러오고 있습니다....");

                final Handler handler = new Handler() {
                    public void handleMessage(Message msg) {
                        textview.setText(ip);
                    }
                };//헨들러 ,쓰레드 속에 UI변경할때는 헨들러를 써야함

                new Thread() {
                    public void run(){
                        try{
                            url = new URL(Address);
                            conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod(protocol);

                            br = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                            String line;
                            while((line = br.readLine())!=null){
                                if(line.startsWith("<h1 id=\"title\">")) {
                                    ip = line.replace("<h1 id=\"title\">","").replace("(My IP Address)","").replace("<br/>","");
                                    Log.e("ip=",ip);
                                }
                            }
                            //헨들러
                            Message msg = handler.obtainMessage();
                            handler.sendMessage(msg);
                            //------
                        } catch(
                                UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch(
                                ProtocolException e) {
                            e.printStackTrace();
                        } catch(
                                MalformedURLException e) {
                            e.printStackTrace();
                        } catch(
                                IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }


}