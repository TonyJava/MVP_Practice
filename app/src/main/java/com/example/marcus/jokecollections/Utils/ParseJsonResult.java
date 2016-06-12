package com.example.marcus.jokecollections.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by marcus on 16/6/9.
 */
public class ParseJsonResult {
    private static final String TARGET = "http://v.juhe.cn/joke/randJoke.php";
    private static final int DEF_CONN_TIMEOUT = 30000;
    private static final String DEF_CHARSET = "UTF-8";
    public static String userAgent =  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.79 Safari/537.36";
    public static String fetchResult(Map<String,String> params) {
        return fetchResult(TARGET,params,"GET");
    }

    private static String fetchResult(String targetUrl, Map<String,String> params, String method) {
        String queryUrl = targetUrl + "?" + paramsToString(params);
        String result;
        //append each line and finally as a result
        StringBuilder allRead = new StringBuilder();

        HttpURLConnection connection;
        BufferedReader reader = null;
        InputStream in = null;
        URL url;
        try {
            //open the connection
            url = new URL(queryUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(DEF_CONN_TIMEOUT);
            connection.setReadTimeout(DEF_CONN_TIMEOUT);
            connection.setRequestMethod(method);
            connection.setUseCaches(false);
            connection.setRequestProperty("User-agent",userAgent);
            connection.setInstanceFollowRedirects(false);
            connection.connect();
            //read from the url
            in = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in,DEF_CHARSET));
            String aLine;
            while ((aLine = reader.readLine()) != null) {
                allRead.append(aLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //move content to result
        result = allRead.toString();
        return result;
    }

    private static String paramsToString(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : params.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"",DEF_CHARSET)).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
