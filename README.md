### 🔹 Phase 3 — Gestion des Exceptions & I/O
- Conception d'exceptions métier vérifiées (`checked exceptions`) :
  - `SeanceCompleteException` : déclenchée lors d'une tentative de réservation sur une séance pleine.
  - `AdherentIntrouvableException` : levée lors d'un échec de recherche par identifiant.
- Importation d'adhérents à partir d'un fichier texte externe via `try-with-resources`.
- Journalisation rigoureuse des incidents avec `java.util.logging` (sans `printStackTrace()` brut ni blocs `catch` silencieux).
