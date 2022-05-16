package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class App 
{

    static ArrayList<Product> products = new ArrayList<>();
    static int portNumber = 1234;

    public static void main( String[] args )
    {
        buildProductList();

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

        while (true){
            String s = "";
            try {
                while (true) {
                    if (!((s = in.readLine()) != null))
                        break;

                    switch (s) {
                        case "cheaper":
                            System.out.println("cheaper");
                            break;
                        case "all":
                            System.out.println("all");
                            break;
                        case "all_sorted":
                            System.out.println("all_sorted");
                            break;
                        default:
                            System.out.println("Invalid option");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static void buildProductList() {
        products.add(new Product(36213,"Huawei Honor 8 BLACK",25.94, 6));
        products.add(new Product(36214,"Huawei Honor 8 RED",26.94, 1));
        products.add(new Product(36215,"Apple IPhone 13 RED",1226.94, 10));
        System.out.println(products);
    }

}
