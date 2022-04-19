/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Paire;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Valek
 */
public class Cycle extends SchemaEchange{

    @Override
    protected int evalCoutBenefice() {
        int benefice = 0;
        int i=0;
        for(Paire p : this.paires){
            System.out.println("GET NEXT PAIRE ");
            Paire nextPaire = this.getNextPaire(i);
            System.out.println("\nAprès nextPaire");
            if(nextPaire!=null) benefice += p.getBeneficeVers(nextPaire);
            else{
                System.out.println("Recup First");
                nextPaire = this.getFirstPaire();
                System.out.println("Somme benefice");
                benefice += p.getBeneficeVers(nextPaire);
            }
            i++;
        }
        System.out.println("BENEF du cycle = " + benefice);
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

    @Override
    public int getCoutBenefice() {
        return coutBenefice;
    }

    
    @Override
    public String toString() {
        return "Cycle{ CoutBénéfice = " + this.coutBenefice 
                + " Paires : " + this.paires + '}';
    }
    
    public boolean ajouterPaireAuCycle(Paire paireToAdd){
        if(paireToAdd != null){
            this.paires.addLast(paireToAdd);
            return true;
        }
        else{
            return false;
        }
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
