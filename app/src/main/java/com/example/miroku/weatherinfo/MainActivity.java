package com.example.miroku.weatherinfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Address = "http://www.findip.kr/";

        StringBuffer buffer = new StringBuffer();
        StringBuilder builder = new StringBuilder();
        textview = (TextView) findViewById(R.id.textView);
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                textview.setText(ip);
            }
        };


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
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
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


//        TextView textView = (TextView)findViewById(R.id.textView);
//
//        WeatherConnection weatherConnection = new WeatherConnection();
//
//        AsyncTask<String, String, String> result = weatherConnection.execute("","");
//
//        System.out.println("RESULT");
//
//        try{
//            String msg = result.get();
//            System.out.println(msg);
//
//            textView.setText(msg.toString());
//
//        }catch (Exception e){
//
//        }
//    }
//
//    // 네트워크 작업은 AsyncTask 를 사용해야 한다
//    public class WeatherConnection extends AsyncTask<String, String, String>{
//
//        // 백그라운드에서 작업하게 한다
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            // Jsoup을 이용한 날씨데이터 Pasing하기.
//            try{
//
//                String path = "http://weather.naver.com/rgn/townWetr.nhn?naverRgnCd=09650510";
//
//                Document document = Jsoup.connect(path).get();
//
//                Elements elements = document.select("em");
//
//                System.out.println(elements);
//
//                Element targetElement = elements.get(2);
//
//                String text = targetElement.text();
//
//                System.out.println(text);
//
//                return text;
//
//
//                /*
//                // URL접속하여 HTML 가져오기.
//                URL url = new URL("http://weather.naver.com/rgn/townWetr.nhn?naverRgnCd=09650510");
//
//                System.out.println("Call target URL try....");
//
//                InputStream inputStream = url.openStream();
//                Scanner scanner = new Scanner(inputStream, "UTF-8");
//
//                 while (true){
//                    try{
//                        System.out.println(scanner.nextLine());
//                    }catch (Exception e){
//                        e.printStackTrace();
//                        break;
//                    }
//                }
//                */
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
    }
}