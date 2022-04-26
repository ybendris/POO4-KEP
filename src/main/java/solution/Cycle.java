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
    
    public Cycle(int tailleMax){
        super();
        this.tailleMax = tailleMax;
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

    
    public boolean check(){
        return verifTailleCycle() && verifBenefice();
    }
    
    /**
     * Vérifie la taille max et min d'un cycle
     * @return 
     */
    private boolean verifTailleCycle() {
        System.out.println("Erreur: La taille du cycle ne respecte pas les conditions: "+ this);
        return (this.paires.size() <= this.tailleMax && this.paires.size() >= 2);
    }
    
    /**
     * Vérifie le bénéfice d'un cycle
     * @return 
     */
    private boolean verifBenefice() {
        var beneficeAverif = this.coutBenefice;
        var beneficeReel = 0;
        
     
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(p.getId());
            if(nextPaire != null){
                beneficeReel += p.getBeneficeVers(nextPaire);
            }
            else{
                beneficeReel += p.getBeneficeVers(this.paires.getFirst());
            }
        }
        
        if(beneficeAverif == beneficeReel){
            return true;
        }
        System.out.println("Erreur: le bénéfice du cycle est mal calculé");
        return false;
    }
    
    @Override
    public String toString() {
        return "Cycle{"+
            "\n\tBenefice = "+ this.coutBenefice +
            "\n\tTailleMaxCycle = "+ this.tailleMax +
            "\n\tPaires = " + this.paires +
        "\n}";
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
        Cycle c2 = new Cycle(10);
        Paire p1 = new Paire(1);
        Paire p2 = new Paire(2);
        c1.paires.add(p1);
        c1.paires.add(p2);
        System.out.println("Cycle 1 : " + c1);
        System.out.println("Cycle 2 : " + c2);
    }
    
    
    
}
