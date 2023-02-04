package fr.alexisflorent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Socket socket = null;
        // Boucle de saisie des données
        Scanner scanner = new Scanner(System.in);
        ArrayList<String[]> livres = new ArrayList<>();
        ArrayList<String[]> lecteurs = new ArrayList<>();
        while (true) {
            // On affiche le menu
            afficherMenu();

            // Vérification de la saisie
            while (!scanner.hasNextInt()) {
                System.out.print("Veuillez saisir un nombre : ");
                scanner.nextLine();
            }
            int option = scanner.nextInt();
            scanner.nextLine();

            // Traitement de l'option
            if (option == 1) {
                // Saisie des données
                System.out.print("Titre : ");
                String titre = scanner.nextLine();
                System.out.print("Auteur : ");
                String auteur = scanner.nextLine();
                System.out.print("Nombre de pages : ");
                String nbPages = scanner.nextLine();
                livres.add(new String[]{titre, auteur, nbPages});
            } else if (option == 2) {
                // Saisie des données
                System.out.print("Nom : ");
                String nom = scanner.nextLine();
                System.out.print("Prénom : ");
                String prenom = scanner.nextLine();
                System.out.print("Email : ");
                String email = scanner.nextLine();
                lecteurs.add(new String[]{nom, prenom, email});
            } else if (option == 3) {
                try {
                    // Connexion et envoie au serveur
                    socket = createSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(livres);
                    oos.writeObject(lecteurs);
                    oos.flush();
                    System.out.println("Données envoyées au serveur");
                    livres.clear();
                    lecteurs.clear();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (option == 4) {
                break;
            } else {
                System.out.println("Option invalide");
            }
        }

        // Déconnexion propre
        try {
            if (socket != null) {
                socket.close();
            }
            System.out.println("Déconnecté proprement du serveur");
        } catch (IOException e) {
            System.out.println("Erreur lors de la déconnexion");
            e.printStackTrace();
        }
    }

    /**
     * Affichage du menu
     * @return void
     */
    private static void afficherMenu() {
        System.out.println(" ");
        System.out.println("--[ Menu ]--");
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1. Saisir un livre");
        System.out.println("2. Saisir un lecteur");
        System.out.println("3. Envoyer les données au serveur");
        System.out.println("4. Se déconnecter");
        System.out.print("Votre choix : ");
    }

    /**
     * Création d'un socket client
     * @return Socket
     */
    private static Socket createSocket() {
        String serverAddress = "localhost";
        int port = 5555;
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connecté au serveur " + serverAddress + " sur le port " + port);
        } catch (IOException e) {
            System.out.println("Connexion au serveur échouée");
            e.printStackTrace();
        }
        return socket;
    }
}