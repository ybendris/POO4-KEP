/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

import io.InstanceReader;
import io.exception.ReaderException;

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
        this.name = nom;
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

    @Override
    public String toString() {
        return "Instance{" + "name=" + name + ", nbPairePatientDonneur=" + nbPairePatientDonneur + ", nbDonneurAlt=" + nbDonneurAlt + ", tailleMaxCycle=" + tailleMaxCycle + ", tailleMaxChaine=" + tailleMaxChaine + '}';
    }
    
    public static void main(String[] args) {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n0_k3_l0.txt");
            
            Instance i = read.readInstance();
            
            System.out.println(i.toString());
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
