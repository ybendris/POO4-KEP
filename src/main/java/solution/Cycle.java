/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Paire;
import java.util.LinkedList;

/**
 *
 * @author Valek
 */
public class Cycle extends SchemaEchange{
    
    @Override
    protected int evalCoutBenefice() {
        int benefice = 0;
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(p.getId());
            benefice += p.getBeneficeVers(nextPaire);
        }
        return benefice;
    }

    public Cycle() {
        super();
    }
    
    public Cycle(Cycle c){
        this.coutBenefice = c.coutBenefice;
        this.paires = c.paires;
        this.tailleMax = c.tailleMax;
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }
    
    @Override
    public String toString() {
        return "Cycle{ CoutBénéfice = " + this.coutBenefice 
                + " Paires : " + this.paires + '}';
    }
    
    public static void main(String[] args) {
        Cycle c1 = new Cycle();
        Paire p1 = new Paire(1);
        Paire p2 = new Paire(2);
        c1.paires.add(p1);
        c1.paires.add(p2);
        System.out.println("Cycle 1 : " + c1);
    }
    
    
}
