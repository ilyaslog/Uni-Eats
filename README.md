# Uni-Eats
Une Application de Gestion de Restaurant Universitaire

# Interfaces Graphiques

## Préambule
Pour mener à bien le développement de ce projet de manière optimale, nous avons bénéficié de l'assistance de divers outils visant à améliorer l'organisation et la fluidité du travail :
- **IDE :** NetBeans
- **Langages :** Java, MySQL
- **Bibliothèques :** Spring Boot, JavaFX
- **Outils :** SceneBuilder, GitHub, Discord

## Équipe du Projet
Nous tenons à remercier chaleureusement tous les membres de l'équipe qui ont contribué à la réalisation de ce projet :

### Équipe Conception
Cette équipe a travaillé sur la création des diagrammes UML ainsi que le design de l'application :
- Saad Benmoussa
- Iliass Issaf
- Achraf Ikisse
- Fadi Bahtat

### Équipe Développement
Cette équipe a travaillé sur la partie code et sur la gestion de la base de données :
- Ilyas Himit
- Ammar Kasbaoui
- Moaad Ezzahir 
- Haytam El Hilali
## Login et Sign Up Page

### Interface Login
L’utilisateur peut entrer ses identifiants (nom d’utilisateur et mot de passe) pour se connecter, que ce soit en tant qu’administrateur ou client.
Si l’utilisateur ne dispose pas de compte, il peut appuyer sur "Create one" pour en créer un, mais seulement pour les utilisateurs ordinaires. Les autres comptes sont tous créés par l’administrateur.
![Interface Login](/Interfaces_UniEats/login.png)

### Interface SignUp
L’utilisateur peut entrer les informations nécessaires et appuyer sur le bouton "Sign up" pour créer son compte.
L’utilisateur recevra un code de vérification par e-mail qu’il devra saisir pour confirmer son inscription.
Il pourra ensuite se connecter via l’interface de connexion sans aucun problème.
![Interface SignUp](/Interfaces_UniEats/SignUp.png)

## Interface Admin

### Interface Statistiques
L’administrateur aura accès à plusieurs informations concernant le nombre d’utilisateurs, les revenus journaliers, les revenus totaux ainsi que le nombre de plats vendus.
Il disposera également d’un graphique représentant le revenu global généré.
![Interface Statistiques](/Interfaces_UniEats/Admin/Stats.png)

### Interface Side menu Admin
En appuyant sur le bouton "Menu" en haut à gauche, l’administrateur pourra ouvrir un menu déroulant qui lui permettra de naviguer à travers les pages.
Un bouton en haut à droite permettra d’ouvrir un nouvel menu déroulant pour voir les détails de son compte ou se déconnecter.
![Interface Side menu Admin](/Interfaces_UniEats/Admin/SideMenu.png)

### Interface Gestion Menu
L’administrateur disposera de trois tables :
- Une table (à gauche de la page) contenant tous les plats disponibles, qui peuvent être affectés à la table du menu quotidien (en bas à droite de la page) ou à la section du plat du jour.
- Une table (en bas à droite de la page) contenant tous les plats du menu quotidien, où l’on peut utiliser des flèches pour ajouter ou supprimer des plats à partir de la table des plats disponibles.
- Une section pour sélectionner le plat du jour, qui sera réinitialisée chaque jour.
-Le bouton "Imprimer menu quotidien" permettra d’imprimer le menu quotidien ainsi que le plat du jour.
Après avoir configuré ces éléments, l’administrateur pourra sauvegarder cette constitution.
![Interface Gestion Menu](/Interfaces_UniEats/Admin/Gestion_menu.png)

### Interface Gestion Plats
L’administrateur disposera d’une table des plats, dans laquelle il peut ajouter, supprimer ou même modifier un plat.
![Interface Gestion Plats](/Interfaces_UniEats/Admin/Gestion_Plat.png)

### Interface Gestion de commandes
L’administrateur disposera d’une table des commandes où il pourra visualiser toutes les commandes passées. Chaque commande sera présentée avec son ID, le client associé, la date de la commande ainsi que son statut. L’administrateur aura la possibilité de confirmer la réception des commandes ou de les supprimer si nécessaire.
![Interface Gestion de commandes](/Interfaces_UniEats/Admin/Menu_Gestion_Commande.png)

### Interface Gestion de comptes
L’administrateur aura accès à une table des comptes, affichant les comptes des administrateurs et des cuisiniers. Il aura la possibilité de créer de nouveaux comptes, de supprimer des comptes existants ou de modifier les informations des comptes selon les besoins.
![Interface Gestion de comptes](/Interfaces_UniEats/Admin/Menu_Gestion_compte.png)

### Interface  du menu quotidien
L’administrateur disposera d’une interface agencée de manière spécifique, présentant le point de vue côté utilisateur sans possibilité d’interagir.
![Interface Gestion du menu quotidien](/Interfaces_UniEats/Admin/Menu_Quotidien.png)

### Interface détail compte
L’administrateur pourra consulter les informations de son propre compte, telles que son nom, son adresse e-mail, etc. Il aura également la possibilité de modifier ces informations, comme son adresse de contact ou son mot de passe. Après modifications, il pourra appuyer sur le bouton "Sauvegarder" pour enregistrer ces nouvelles informations.
![Interface détail compte](/Interfaces_UniEats/Admin/Details_compte.png)

## Interface Utilisateur

### Interface Menu quotidien
L’utilisateur trouvera sur sa page d’accueil une barre de recherche contenant quatre éléments : un bouton menu, une icône de panier, le solde et une icône de compte. Le plat du jour et le menu quotidien seront affichés, permettant d’ajouter des plats au panier sans dépasser le stock disponible.
![Interface Menu quotidien](/Interfaces_UniEats/Admin/Menu_Quotidien.png)

### Interface Menu quotidien avec panier
L’utilisateur pourra voir ses plats commandés en appuyant sur l’icône du panier. Il verra le total à payer et pourra choisir de vider le panier ou de confirmer la commande.
![Interface Menu quotidien avec panier](/Interfaces_UniEats/User/Menu_QuotidienavecPanier.png)

### Interface side menu user
En utilisant le bouton menu en haut à droite, l’utilisateur pourra naviguer à travers les pages conçues (Menu quotidien, Commande).
![Interface side menu user](/Interfaces_UniEats/User/SideMenu_User.png)

### Interface de commande vide
Permet de visualiser les commandes passées, avec détails des plats, quantités, statut. L’utilisateur peut suivre l’avancement des commandes et confirmer la réception une fois livrées.

![Interface de commande](/Interfaces_UniEats/User/Page_Commande.png)

### Interface de compte
L’utilisateur peut accéder et modifier ses informations de compte (nom, adresse e-mail, etc.) et enregistrer les modifications.
![Interface de compte](/Interfaces_UniEats/User/Details_compte.png)

## Interface Cuisinier

### Interface d’accueil
Le cuisinier peut voir toutes les commandes confirmées, avec les détails nécessaires pour la préparation. Une fois prêtes, il peut changer leur statut pour indiquer qu’elles sont prêtes à être livrées.
![Interface d’accueil](/Interfaces_UniEats/Cuisto/Page_cuisto.png)
## Conclusion
Le projet Uni-Eats représente une avancée significative dans la gestion des restaurants universitaires, offrant une solution intégrée pour la gestion des utilisateurs, des commandes, des menus et des comptes. Grâce à la collaboration de nos équipes de conception et de développement, nous avons pu créer une application fonctionnelle et intuitive qui répond aux besoins spécifiques des administrateurs, des cuisiniers et des utilisateurs. Nous espérons que cette application améliorera l'efficacité et l'expérience des repas universitaires pour tous les parties prenantes.

