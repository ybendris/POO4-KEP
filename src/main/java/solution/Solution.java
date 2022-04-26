/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.Paire;
import java.util.LinkedList;
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
    
    public boolean ajouterPaireNouveauCycle(Paire paireToAdd){
        Cycle nouveauCycle = new Cycle();
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd)){
            this.cycles.add(nouveauCycle);
            return true;
        }
        return false;
    }
    
    public boolean ajouterPairesNouveauCycleDe2(Paire paireToAdd1, Paire paireToAdd2){
        Cycle nouveauCycle = new Cycle();
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd1) && nouveauCycle.ajouterPaireAuCycle(paireToAdd2)){
            System.out.println("EvalBenef");
            nouveauCycle.coutBenefice=nouveauCycle.evalCoutBenefice();
            System.out.println("ADDCycle");
            this.cycles.add(nouveauCycle);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Solution{" + "benefice=" + benefice + ", chaines=" + chaines + ", cycles=" + cycles + '}';
    }
    
    public void evalBenefice(){
        for(Cycle c : this.cycles){
            this.benefice += c.coutBenefice;
        }
        for(Chaine ch : this.chaines){
            this.benefice+=ch.coutBenefice;
        }
    }
    
    
    public static void main(String[] args) {
        System.out.println("Test de la classe Solution:");
        
        
    }
    
    
    
    
}
