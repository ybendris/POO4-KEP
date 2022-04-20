/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.Paire;
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
    
    
    public static void main(String[] args) {
        System.out.println("Test de la classe Solution:");
        
        
    }

    
    
    
    
}
