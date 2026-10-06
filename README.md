#  SportZone Système de Gestion de Salle de Sport

Application de bureau Java / JavaFX de gestion pour la salle de sport fictive **SportZone**, développée selon les principes de la programmation orientée objet, de la programmation défensive et de la qualité logicielle.

Projet académique réalisé dans le cadre du module **Programmation défensive** (3ème année Bachelor Cybersécurité) sous la direction de **Dr. Ines Ben Tekaya**.



##  Présentation du projet

L'application permet d'informatiser les activités quotidiennes de la salle de sport :
- Gestion des adhérents et des coachs.
- Gestion du catalogue de cours et du planning des séances.
- Réservation de places avec contrôle d'intégrité et de capacité.
- Calcul polymorphe et édition des factures avec tarification dégressive.
- Interface graphique moderne développée avec JavaFX (MVC).



##  Stack technique & Outils

- **Langage :** Java 17+ (ou supérieur)
- **Interface graphique :** JavaFX (avec FXML & Scene Builder)
- **Tests unitaires :** JUnit 5 (Jupiter)
- **Gestionnaire de dépendances & Build :** Maven
- **Journalisation :** `java.util.logging`
- **Contrôle de version :** Git & GitHub

##  Structure du Projet

```text
sportzone/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── sportzone/
│   │   │       ├── MainApp.java             # Point d'entrée JavaFX
│   │   │       ├── ContexteApplication.java # Données en mémoire
│   │   │       ├── controleur/              # Contrôleurs FXML (MVC)
│   │   │       ├── exceptions/              # Exceptions métier
│   │   │       ├── modele/                  # Entités du domaine
│   │   │       └── service/                 # Logique métier & facturation
│   │   └── resources/
│   │       └── sportzone/
│   │           └── vue/                     # Vues FXML & Styles CSS
│   └── test/
│       └── java/
│           └── sportzone/
│               └── tests/                   # Suite de tests JUnit 5
├── pom.xml                                  # Configuration Maven
└── README.md
```

### 🔹 Phases 1 & 2 — Modèle objet, Encapsulation, Héritage & Polymorphisme
- **Modélisation de base :** Implémentation des classes métier (`Adherent`, `Coach`, `Cours`, `Seance`, `Reservation`, `Facture`) avec encapsulation stricte des attributs.
- **Relations UML respectées :** Composition pour les lignes de facture (classe interne `LigneFacture`), agrégation pour les séances et réservations.
- **Hiérarchie de cours :** Classe abstraite `Cours` et sous-classes concrètes (`CoursCollectif`, `CoursIndividuel`, `StageIntensif`) implémentant le calcul dynamique du tarif de séance (`calculerTarifSeance()`).
- **Interface métier :** Définition de l'interface `Reservable` appliquée uniquement aux cours le permettant.
