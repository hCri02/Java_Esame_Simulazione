package org.example;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;


public class App 
{

    static ArrayList<Product> products = new ArrayList<>();
    static int portNumber = 1234;
    static double cheapest = 100000;
    static Gson gson = new Gson();
    static String json;

    public static void main( String[] args )
    {

        // HTTPS Server

        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //server.createContext("/applications/myapp", new MyHandler());
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();

        // Creazione della lista

        buildProductList();

        // Implementazione del server

        System.out.println("Server started!");

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept fallita" + e);
        }

        System.out.println("Accettato...");
        try {
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader in = null;
        try {
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // switch delle query

        while (true){
            String s = "";
            try {
                while (true) {
                    if (!((s = in.readLine()) != null))
                        break;

                    querySwitch(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    // Funzioni

    private static void querySwitch(String s) {
        switch (s) {
            case "cheapest":
                for (Product product : products) {
                    if (product.getCost() < cheapest) {
                        cheapest = product.getCost();
                        json = gson.toJson(product);
                    }
                }
                System.out.println(json);
                break;
            case "all":
                json = gson.toJson(products);
                break;
            case "all_sorted":
                sortProductList();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private static void sortProductList() {
        ArrayList<Product> orderList = new ArrayList<>();
        for(Product o:products)
            orderList.add(o);

        //Collections.sort(orderList);
        json = gson.toJson(orderList);
        System.out.println(json);
    }

    static void buildProductList() {
        products.add(new Product(36213,"Huawei Honor 8 BLACK",25.94, 6));
        products.add(new Product(36214,"Huawei Honor 8 RED",26.94, 1));
        products.add(new Product(36215,"Apple IPhone 13 RED",1226.94, 10));
        System.out.println(products);
    }

}
