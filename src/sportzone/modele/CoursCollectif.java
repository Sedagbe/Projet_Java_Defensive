package sportzone.modele;

import sportzone.exceptions.SceanceCompleteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Cours collectif : plusieurs adhérents suivent la même séance, ce qui
 * permet un tarif par personne réduit par rapport à un cours individuel.
 */
public class CoursCollectif extends Cours implements Reservable {

    private double tarifParPersonne;
    private final List<Adherent> adherentsReserves = new ArrayList<>();

    /**
     * Courant
     *
     * @param intitule         intitulé du cours, non vide
     * @param dureeMinutes     durée en minutes, strictement positive
     * @param capaciteMax      capacité maximale, strictement positive
     * @param tarifParPersonne tarif facturé à chaque participant, strictement
     *                         positif
     * @throws IllegalArgumentException si l'un des paramètres viole son
     *                                  invariant (voir {@link Cours})
     */
    public CoursCollectif(String intitule, int dureeMinutes, int capaciteMax, double tarifParPersonne) {
        super(intitule, dureeMinutes, capaciteMax);
        if (tarifParPersonne <= 0) {
            throw new IllegalArgumentException("Le tarif par personne doit être strictement positif.");
        }
        this.tarifParPersonne = tarifParPersonne;
    }

    public double getTarifParPersonne() {
        return tarifParPersonne;
    }

    public void setTarifParPersonne(double tarifParPersonne) {
        if (tarifParPersonne <= 0) {
            throw new IllegalArgumentException("Le tarif par personne doit être strictement positif.");
        }
        this.tarifParPersonne = tarifParPersonne;
    }

    @Override
    public double calculerTarifSceance() {
        return tarifParPersonne;
    }

    @Override
    public void reserverPlace(Adherent adherent) throws SceanceCompleteException {
        if (adherent == null) {
            throw new IllegalArgumentException("L'adhérent ne peut pas être null.");
        }
        if (placesRestantes() <= 0) {
            throw new SceanceCompleteException(
                    "Le cours collectif '" + getIntitule() + "' a atteint sa capacité maximale de "
                            + getCapaciteMax() + " places.");
        }
        adherentsReserves.add(adherent);
    }

    @Override
    public int placesRestantes() {
        return getCapaciteMax() - adherentsReserves.size();
    }
}