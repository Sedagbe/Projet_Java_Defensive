### 🔹 Phase 5 — Interface Graphique JavaFX (Architecture MVC)
- Application de bureau modulaire respectant la séparation stricte **Modèle - Vue - Contrôleur** :
  - **Accueil (`accueil.fxml`) :** Navigation principale vers les différents modules.
  - **Planning des séances :** Consultation sous forme de `TableView` avec filtres de recherche (par coach, type de cours, date).
  - **Formulaire de réservation :** Interface en `GridPane` validant les données saisies avant soumission.
  - **Facturation (`facturation.fxml`) :** Consultation des détails de facturation par adhérent avec calcul automatique.
- Validation graphique défensive et affichage convivial des alertes (`Alert`) lors de la capture d'exceptions métier.
