# Installation

### Pour chacun des projets, deux solutions sont possibles :

- Télécharger le projet ou clonez-le.

Une fois le projet récupéré :

- Lancer le projet avec la commande suivante : 
```java -jar Java_V3_Client-1.0-SNAPSHOT.jar```
*(Le fichier jar sera déplacé à la racine du projet pour éviter d'avoir à aller les chercher dans le dossier target)*

**OU**

- Ouvrir le projet sur IntelliJ et le lancer normalement.

# Utilisation

Une fois le client lancé, un menu s’affichera sous cette forme :

--[ Menu ]--
Que voulez-vous faire ?
1. Saisir un livre
2. Saisir un lecteur
3. Envoyer les données au serveur
4. Afficher la liste des livres
5. Afficher la liste des lecteurs
6. Se déconnecter

  
Les instructions sont claires, lorsque vous tapez 1 il vous sera proposer d’ajouter un livre *(Titre, auteur et nombre de pages)*, tapez 2 pour ajouter un lecteur *(Nom, prénom, email)*.
*Vous pouvez créer plusieurs livres et/ou lecteurs pour les envoyer d'un coup.*

Pour envoyer les informations sur le serveur pour qu'elles soient sauvegardées, tapez 3.

Ces informations seront ensuite visualisables par le biais des touches 4 et 5 pour afficher la liste des livres et des lecteurs séparément.

La touche 6 quant à elle servira à se déconnecter du serveur et arrêter le projet.
