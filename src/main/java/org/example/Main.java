package org.example;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Connexion au serveur
        String serverAddress = "localhost";
        int port = 5555;
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connecté au serveur " + serverAddress + " sur le port " + port);
        } catch (IOException e) {
            System.out.println("Connexion au serveur échouée");
            e.printStackTrace();
            return;
        }
        // Boucle de saisie des données
        Scanner scanner = new Scanner(System.in);
        HashMap<String, String> books = new HashMap<>();
        HashMap<String, String> readers = new HashMap<>();
        while (true) {
            System.out.println("1. Saisir un livre");
            System.out.println("2. Saisir un lecteur");
            System.out.println("3. Envoyer les données au serveur");
            System.out.println("4. Se déconnecter");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Titre : ");
                String title = scanner.nextLine();
                System.out.print("Auteur : ");
                String author = scanner.nextLine();
                books.put(title, author);
            } else if (option == 2) {
                System.out.print("Nom : ");
                String name = scanner.nextLine();
                System.out.print("Email : ");
                String email = scanner.nextLine();
                readers.put(name, email);
            } else if (option == 3) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(books);
                    oos.writeObject(readers);
                    oos.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Données envoyées au serveur");
                books.clear();
                readers.clear();
            } else if (option == 4) {
                break;
            } else {
                System.out.println("Option invalide");
            }
        }

        // Déconnexion propre
        try {
            socket.close();
            System.out.println("Déconnecté proprement du serveur");
        } catch (IOException e) {
            System.out.println("Erreur lors de la déconnexion");
            e.printStackTrace();
        }
    }
}