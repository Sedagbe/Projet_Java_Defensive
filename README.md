### 🔹 Phase 4 — Programmation Défensive
- Application systématique du principe **fail-fast** à chaque méthode publique critique (`IllegalArgumentException`).
- Documentation contractuelle complète en **Javadoc** (spécification des `@param`, `@throws`, préconditions et postconditions).
- Utilisation de `Optional<T>` pour les recherches sans résultat afin de proscrire l'usage de `null`.
- Couverture explicite des cas limites métier (séances passées, capacité négative ou nulle, annulations redondantes, etc.).
- Utilisation de l'instruction `assert` pour la validation des invariants internes.
