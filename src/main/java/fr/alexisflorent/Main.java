package fr.alexisflorent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Socket socket = null;
        // Boucle de saisie des données
        Scanner scanner = new Scanner(System.in);

        // Création des listes de livres et de lecteurs
        ArrayList<String[]> livres = new ArrayList<>();
        ArrayList<String[]> lecteurs = new ArrayList<>();

        while (true) {
            // On affiche le menu
            afficherMenu();

            // Si l'utilisateur n'a pas saisi un nombre, on lui redemande
            while (!scanner.hasNextInt()) {
                System.out.print("Veuillez saisir un nombre : ");
                scanner.nextLine();
            }
            int option = scanner.nextInt();
            scanner.nextLine();

            // Traitement de l'option
            if (option == 1) {
                // Saisie des données du livre
                String titre = askUser("Titre : ");
                String auteur = askUser("Auteur : ");
                String nbPages = askUser("Nombre de pages : ");
                livres.add(new String[]{titre, auteur, nbPages});
                System.out.println("Livre ajouté");
            } else if (option == 2) {
                // Saisie des données du lecteur
                String nom = askUser("Nom : ");
                String prenom = askUser("Prénom : ");
                String email = askUser("Email : ");
                lecteurs.add(new String[]{nom, prenom, email});
                System.out.println("Lecteur ajouté");
            } else if (option == 3) {
                try {
                    // Connexion et envoie au serveur
                    socket = createSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    // On ajoute les objets à envoyer
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
                try {
                    // Connexion et envoie au serveur
                    socket = createSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    // On demande la liste des livres
                    oos.writeObject("liste");
                    oos.writeObject(null);
                    oos.flush();

                    // On récupère la liste des livres que le serveur nous envoie
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object listeLivres = ois.readObject();
                    // On convertit l'object de livres en ArrayList
                    ArrayList<String[]> listeLivre = (ArrayList<String[]>) listeLivres;

                    System.out.println(" ");
                    System.out.println("Liste des livres : ");
                    // Pour chaque livre dans la liste
                    for (String[] livre : listeLivre) {
                        System.out.println("ID : " + livre[0] + " -- Titre : " + livre[1] + " -- Auteur : " + livre[2] + " -- Nombre de pages : " + livre[3]);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (option == 5) {
                try {
                    // Connexion et envoie au serveur
                    socket = createSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    // On demande la liste des livres
                    oos.writeObject(null);
                    oos.writeObject("liste");
                    oos.flush();

                    // On récupère la liste des lecteurs que le serveur nous envoie
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Object listeLecteurs = ois.readObject();
                    // On convertit l'object de lecteurs en ArrayList
                    ArrayList<String[]> listeLecteur = (ArrayList<String[]>) listeLecteurs;

                    System.out.println(" ");
                    System.out.println("Liste des lecteurs : ");
                    // Pour chaque lecteur dans la liste
                    for (String[] lecteur : listeLecteur) {
                        System.out.println("ID : " + lecteur[0] + " -- Nom : " + lecteur[1] + " -- Prenom : " + lecteur[2] + " -- Email : " + lecteur[3]);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (option == 6) {
                break;
            } else {
                System.out.println("Option invalide");
            }
        }
        // Déconnexion
        disconnect(socket);
    }

    /**
     * Demande à l'utilisateur de saisir une valeur
     * @param message Message à afficher
     * @return String
     */
    private static String askUser(String message) {
        System.out.println(message);
        return new Scanner(System.in).nextLine();
    }

    /**
     * Affichage du menu
     */
    private static void afficherMenu() {
        System.out.println(" ");
        System.out.println("--[ Menu ]--");
        System.out.println("Que voulez-vous faire ?");
        System.out.println("1. Saisir un livre");
        System.out.println("2. Saisir un lecteur");
        System.out.println("3. Envoyer les données au serveur");
        System.out.println("4. Afficher la liste des livres");
        System.out.println("5. Afficher la liste des lecteurs");
        System.out.println("6. Se déconnecter");
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

    /**
     * Déconnecte proprement le socket
     * @param socket Socket
     */
    private static void disconnect(Socket socket) {
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