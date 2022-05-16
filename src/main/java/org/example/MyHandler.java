package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.util.regex.Pattern;

import static org.example.App.json;

public class MyHandler implements HttpHandler {

    public void handle(HttpExchange t) throws IOException {
        InputStream is = t.getRequestBody();

        URI uri = t.getRequestURI();
        String query = uri.getQuery();

        String[] splitted = query.split(Pattern.quote("+"));
        // System.out.println(((Object)splitted[0]).getClass().getSimpleName());

        char c;
        for (int i = 0; i < query.length(); ++i) {
            c = query.charAt(i);
            if ((((Object)c).getClass().getSimpleName()) == "String"){
                System.out.println("Error");
            }
        }



        String s = read(is); // .. read the request body

        String response = "<!doctype html>\n" +
                "<html lang=en>\n" +
                "<head>\n" +
                "<meta charset=utf-8>\n" +
                "<title>Products</title>\n" +
                "</head>\n" +
                "<body>\n" +

                "</br>I'm the content" +
                "</br>\n" +
                s +

                "</br>query:" +
                "</br>\n" +
                query +
                " = " + query +

                "</body>\n" +
                "</html>\n";

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String read(InputStream is) {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is));

        System.out.println("\n");
        String received = null;
        while (true) {
            String s = null;
            try {
                if ((s = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(s);
            received += s;
        }
        return received;
    }


}