/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instance;

import instance.reseau.DonneurAltruiste;
import instance.reseau.Paire;
import io.InstanceReader;
import io.exception.ReaderException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

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
    /*
    * la clé correspond à l'id du donneurAltruiste supposé unique
    */
    private final Map<Integer,DonneurAltruiste> donneursAltruistes;
    private final Map<Integer,Paire> paires;

    public Instance(String nom, int nbPairePatientDonneur, int nbDonneurAlt, int tailleMaxCycle, int tailleMaxChaine) {
        this.name = nom;
        this.nbPairePatientDonneur = nbPairePatientDonneur;
        this.nbDonneurAlt = nbDonneurAlt;
        this.tailleMaxCycle = tailleMaxCycle;
        this.tailleMaxChaine = tailleMaxChaine;
        this.donneursAltruistes = new LinkedHashMap<>();
        this.paires = new LinkedHashMap<>();
    }
    
    public boolean ajouterDonneurAltruiste(DonneurAltruiste donneurToAdd){
        if(donneurToAdd == null) return false;
        
        int id = donneurToAdd.getId();
        
        if(this.donneursAltruistes.containsKey(id)) return false;
        
        this.donneursAltruistes.put(id, donneurToAdd);
        
        return true;
    }
    
    public boolean ajouterPaire(Paire paireToAdd){
        if(paireToAdd == null) return false;
        
        int id = paireToAdd.getId();
        
        if(this.paires.containsKey(id)) return false;
        
        this.paires.put(id, paireToAdd);
        
        return true;
    }
    
    
    /**
    * @return  une copie de la liste des donneurs altruistes
    */ 
    public LinkedList<DonneurAltruiste> getDonneursAltruistes(){
        return new LinkedList<>(this.donneursAltruistes.values());
    }
    
    public int getNbDonneurs(){
        return this.donneursAltruistes.size();
    }
    
    /**
     * Recherche en complexite O(1)
     */
    public DonneurAltruiste getDonneurById(int id){
        return this.donneursAltruistes.get(id);
    }
    
    /**
    * @return  une copie de la liste des paires
    */ 
    public LinkedList<Paire> getPaires(){
        return new LinkedList<>(this.paires.values());
    }
    
    public int getNbPaires(){
        return this.paires.size();
    }
    
    /**
     * Recherche en complexite O(1)
     */
    public Paire getPaireById(int id){
        return this.paires.get(id);
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
        return "Instance{" + 
            "\n\tname=" + name + 
            ",\n\tnbPairePatientDonneur = " + nbPairePatientDonneur + 
            ",\n\tnbDonneurAlt = " + nbDonneurAlt + 
            ",\n\ttailleMaxCycle = " + tailleMaxCycle + 
            ",\n\ttailleMaxChaine = " + tailleMaxChaine + 
            ",\n\tdonneursAltruistes = " + donneursAltruistes +
            ",\n\tpaires = " + paires + 
        "\n}";
    }


    
    public static void main(String[] args) {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n1_k3_l3.txt");
            
            Instance i = read.readInstance();
            
            System.out.println(i.toString());
            
            System.out.println("Benefice Paire 4 - Paire 5 : " + i.getPaireById(4).getBeneficeVers(i.getPaireById(5))); //8
            System.out.println("Benefice Donneur 1 - Paire 5 : " + i.getDonneurById(1).getBeneficeVers(i.getPaireById(5))); //5
            System.out.println("Benefice Donneur 1 - Paire 3 : " + i.getDonneurById(1).getBeneficeVers(i.getPaireById(3))); //-1
            System.out.println("Benefice Paire id:3 - Paire id:7 : " + i.getPaireById(3).getBeneficeVers(i.getPaireById(7))); //10
            System.out.println("Benefice Paire id:8 - Paire id:5 : " + i.getPaireById(8).getBeneficeVers(i.getPaireById(5))); //8
            System.out.println("Benefice Paire id:8 - Paire id:6 : " + i.getPaireById(8).getBeneficeVers(i.getPaireById(6))); //6
            System.out.println("Benefice Paire id:8 - Paire id:7 : " + i.getPaireById(8).getBeneficeVers(i.getPaireById(7))); //7
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
