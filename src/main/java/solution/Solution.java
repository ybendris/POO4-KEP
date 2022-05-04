/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.DonneurAltruiste;
import instance.reseau.Paire;

import io.InstanceReader;
import io.exception.ReaderException;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Valek
 */
public class Solution {
    private Instance instance;
    private int benefice;
    private LinkedList<Chaine> chaines;
    private LinkedList<Cycle> cycles;

    /*
        TODO: ajouter un constructeur par copie de chaine et Cycle
    */
    public Solution(Solution s) {
        this.benefice = s.benefice;
        this.instance = s.instance;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
        for(Chaine chaineToAdd : s.chaines){
            this.chaines.add(new Chaine(chaineToAdd));
        }
        for(Cycle cycleToAdd : s.cycles){
            this.cycles.add(new Cycle(cycleToAdd));
        }
    }

    public Solution(Instance i) {
        this.benefice = 0;
        this.instance = i;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
    }

    public int getBenefice() {
        return benefice;
    }

    public LinkedList<Chaine> getChaines() {
        return chaines;
    }

    public LinkedList<Cycle> getCycles() {
        return cycles;
    }    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.instance);
        hash = 79 * hash + this.benefice;
        hash = 79 * hash + Objects.hashCode(this.chaines);
        hash = 79 * hash + Objects.hashCode(this.cycles);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Solution other = (Solution) obj;
        if (this.benefice != other.benefice) {
            return false;
        }
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        if (!Objects.equals(this.chaines, other.chaines)) {
            return false;
        }
        return Objects.equals(this.cycles, other.cycles);
    }
    
    public boolean check(){
        return verifCycles() && verifChaines() && verifTransplantations();
    }
    
    /**
     * Vérifie que les cycles sont valides
     * @return 
     */
    private boolean verifCycles() {
        for(Cycle cycle : this.cycles){ //Ses cycles sont tous réalisables
            if(!cycle.check())
                return false;
        }
        return true;
    }

    /**
     * Vérifie que les chaines sont valides
     * @return 
     */
    private boolean verifChaines() {
        for(Chaine chaine : this.chaines){ //Ses chaines sont toutes réalisables
            if(!chaine.check())
                return false;
        }
        return true;
    }

    /**
     * Vérifie que chaque paire patient-donneur n’apparaissant que dans une seule chaîne ou un seul cycle au maximum.
     * @return 
     */
    private boolean verifTransplantations() {
        List<Paire> pairesAverif = this.instance.getPaires();
        
        
        for(Cycle cycle : this.cycles){
            for(Paire p : cycle.paires){
                if(!pairesAverif.remove(p)){
                    System.out.println("Une paire n’apparait pas que dans une seule chaîne ou un seul cycle au maximum.");
                    return false;
                }
            }
        }
        
        for(Chaine chaine : this.chaines){
            for(Paire p : chaine.paires){
                if(!pairesAverif.remove(p)){
                    System.out.println("Une paire n’apparait pas que dans une seule chaîne ou un seul cycle au maximum.");
                    return false;
                }
            }
        }
        
        
        return true;
    }

    public Instance getInstance() {
        return instance;
    }

    
    public boolean ajouterPaireNouveauCycle(Paire paireToAdd){
        int tailleMaxCycle = this.instance.getTailleMaxCycle();
        Cycle nouveauCycle = new Cycle(tailleMaxCycle);
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd)){
            this.cycles.add(nouveauCycle);
            nouveauCycle.coutBenefice = nouveauCycle.evalCoutBenefice();
            return true;
        }
        return false;
    }
    
    public boolean ajouterPairesNouveauCycleDe2(Paire paireToAdd1, Paire paireToAdd2){
        Cycle nouveauCycle = new Cycle(2);
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd1) && nouveauCycle.ajouterPaireAuCycle(paireToAdd2)){
            //System.out.println("EvalBenef");
            nouveauCycle.coutBenefice=nouveauCycle.evalCoutBenefice();
            //System.out.println("ADDCycle");
            this.cycles.add(nouveauCycle);
            return true;
        }
        return false;
    }

    
    
    public void evalBenefice(){
        for(Cycle c : this.cycles){
            this.benefice += c.coutBenefice;
        }
        for(Chaine ch : this.chaines){
            this.benefice+=ch.coutBenefice;
        }
    }

    @Override
    public String toString() {
        return "Solution{" + 
                "instance=" + instance + 
                ", benefice=" + benefice + 
                ", chaines=" + chaines + 
                ", cycles=" + cycles + 
            '}';
    }
    
    
    /*public boolean ajouterPaireDernierCycle(Paire paireToAdd){
        
        
        if(this.cycles.isEmpty()){
            return false;
        }
        
        Cycle dernierCycle = this.cycles.getLast();
        
        
        if(dernierCycle.ajouterPaireAuCycle(paireToAdd)){
            dernierCycle.coutBenefice=dernierCycle.evalCoutBenefice();
            return true;
        }
        return false;
    }
*/
    
    public boolean ajouterPaireNouvelleChaine(DonneurAltruiste DAToAdd, Paire paireToAdd){
        
        int tailleMaxChaine = this.instance.getTailleMaxChaine();
        Chaine nouvelleChaine = new Chaine(DAToAdd,tailleMaxChaine);
        if(nouvelleChaine.ajouterPaireChaine(paireToAdd)){
            this.chaines.add(nouvelleChaine);
            return true;
        }
        return false;
    }
    
    public int getSizeChaineByIndex(int i){
        Chaine ch = this.chaines.get(i);
        if(ch!=null){
            return ch.paires.size();
        }
        return -1;
    }
    
    public boolean ajouterPaireDerniereChaine(Paire paireToAdd){
        
        if(this.chaines.isEmpty()){
            return false;
        }
        
        Chaine derniereChaine = this.chaines.getLast();
        
        if(derniereChaine.ajouterPaireChaine(paireToAdd)){
            derniereChaine.coutBenefice=derniereChaine.evalCoutBenefice();
            return true;
        }
        return false;
    }
      
    
    
    public static void main(String[] args) {
        System.out.println("Test de la classe Solution:");
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n1_k3_l3.txt");
            Instance i = read.readInstance();
            
            Solution s = new Solution(i);
            
            System.out.println(s.toString());
            System.out.println(s.check());//true;
            
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
    }

    
    
    
    
}
