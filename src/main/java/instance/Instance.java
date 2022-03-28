/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

/**
 *
 * @author lucas
 */
public class Instance {
    private String name;
    private int nbPairePatientDonneur;
    private int nbDonneurAlt;
    private int tailleMaxCycle;
    private int tailleMaxChaine;

    public Instance(String nom, int nbPairePatientDonneur, int nbDonneurAlt, int tailleMaxCycle, int tailleMaxChaine) {
        this.name = name;
        this.nbPairePatientDonneur = nbPairePatientDonneur;
        this.nbDonneurAlt = nbDonneurAlt;
        this.tailleMaxCycle = tailleMaxCycle;
        this.tailleMaxChaine = tailleMaxChaine;
    }

    public String getName() {
        return name;
    }

    public int getNbPairePatientDonneur() {
        return nbPairePatientDonneur;
    }

    public int getNbDonneurAlt() {
        return nbDonneurAlt;
    }

    public int getTailleMaxCycle() {
        return tailleMaxCycle;
    }

    public int getTailleMaxChaine() {
        return tailleMaxChaine;
    }
    
    
    
}
