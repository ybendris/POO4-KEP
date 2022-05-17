/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.Paire;
import io.InstanceReader;
import io.exception.ReaderException;
import java.util.Iterator;
import java.util.LinkedList;
import solveur.CycleDe2;

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
            //System.out.println("GET NEXT PAIRE ");
            Paire nextPaire = this.getNextPaire(i);
            //System.out.println("\nAprès nextPaire");
            if(nextPaire!=null) benefice += p.getBeneficeVers(nextPaire);
            else{
                //System.out.println("Recup First");
                nextPaire = this.getFirstPaire();
                //System.out.println("Somme benefice");
                benefice += p.getBeneficeVers(nextPaire);
            }
            i++;
        }
        //System.out.println("BENEF du cycle = " + benefice);
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

        if(!(this.paires.size() <= this.tailleMax)){
            System.out.println("Erreur: Le cycle dépasse la taille max ! "+ this);
            return false;
        }
        if(!(this.paires.size() >= 2)){
            System.out.println("Erreur: Le cycle doit etre au minimum de taille 2 "+ this);
            return false;
        }
        return true;

    }
    
    /**
     * Vérifie le bénéfice d'un cycle
     * @return 
     */
    private boolean verifBenefice() {
        var beneficeAverif = this.coutBenefice;
        var beneficeReel = 0;
        
        int i=0;
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(i);
            int beneficeToAdd;
            if(nextPaire != null){
                beneficeToAdd = p.getBeneficeVers(nextPaire);
            }
            else{
                beneficeToAdd = p.getBeneficeVers(this.paires.getFirst());
            }
            if(beneficeToAdd != -1){
                beneficeReel += beneficeToAdd;
            }
            else{
                return false;
            }
            i++;
        }
        
        if(beneficeAverif == beneficeReel){
            return true;
        }
        System.out.println("Erreur: le bénéfice du cycle est mal calculé("+ beneficeAverif +" , "+beneficeReel+")");
        return false;
    }
    
    
    private boolean verifTransplantation() {
        int i=0;
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(i);
            if(nextPaire == null)
                nextPaire = this.paires.getFirst();
            
            if(!p.peutDonnerA(nextPaire))
                return false;
 
            i++;
        }
        return true;
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
        if(paireToAdd != null && this.getNbPaires()<this.tailleMax){
            this.paires.addLast(paireToAdd);
            return true;
        }
        else{
            return false;
        }
    }
    
    public int getNbPaires(){
        return this.paires.size();
    }
    
    public static void main(String[] args) {
        /*Cycle c1 = new Cycle();
        Cycle c2 = new Cycle(10);
        Paire p1 = new Paire(1);
        Paire p2 = new Paire(2);
        c1.paires.add(p1);
        c1.paires.add(p2);
        System.out.println("Cycle 1 : " + c1);
        System.out.println("Cycle 2 : " + c2);*/
        
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/testInstance.txt");
            Instance i = read.readInstance();
            Cycle c3 = new Cycle(3);
            c3.ajouterPaireAuCycle(i.getPaireById(4));
            c3.ajouterPaireAuCycle(i.getPaireById(6));
            System.out.println("eval: "+c3.evalCoutBenefice());
            
            System.out.println(c3);
            
            System.out.println(c3.check());
            
            /*System.out.println("Solution = " + s);
            System.out.println("sC2 check: " +s.check());*/
             
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }

    
    
    
    
}
