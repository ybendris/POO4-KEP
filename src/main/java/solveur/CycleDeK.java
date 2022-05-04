/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solveur;

import instance.Instance;
import instance.reseau.Paire;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import java.io.IOException;
import java.util.LinkedList;
import solution.Solution;

/**
 *
 * @author Valek
 */
public class CycleDeK implements Solveur{

    @Override
    public String getNom() {
        return "CycleDe2";
    }

    /***
     * On réalise des cycles de taille 2, en utilisant l'association de paires 
     * donnant le meilleur bénéfice
     * @param instance
     * @return 
     */
    @Override
    public Solution solve(Instance instance) {
        Solution s = new Solution(instance);
        LinkedList<Paire> paires = instance.getPaires();
        Paire paire1 = null;
        Paire paire2 =null;
        int compatibilite = 1;
        
        while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1){
            /*System.out.println("C'est parti : Paires = " + paires 
                    + " Taille = " + paires.size());*/
            paire1 = null;
            paire2 =null;
            int beneficeP1P2 = 0;
            int beneficeP2P1 = 0;
            int beneficeTotal = 0;
            int bestBenefice = 0;
            
            for(Paire P1 : paires){
                for(Paire P2 : paires){
                    if(P1.getBeneficeVers(P2) > -1){

                        if(P2.getBeneficeVers(P1) > -1){

                            beneficeP1P2 = P1.getBeneficeVers(P2);
                            beneficeP2P1 = P2.getBeneficeVers(P1);
                            beneficeTotal = beneficeP1P2 + beneficeP2P1;
                            if(beneficeTotal > bestBenefice){
                                bestBenefice = beneficeTotal;
                                paire2 = P2;
                                paire1 = P1;
                            }
                        }   
                    }
                }
            }
            if(paire1==null && paire2==null){
                compatibilite=0;
            }
            else{
                boolean affecte=false;
                /*if(s.ajouterPaireDernierCycle(paire1)){
                    affecte=true;
                }
                if(affecte=false){
                    s.ajouterPaireNouveauCycle(paire1);
                }
                
                s.ajouterPaireDernierCycle(paire2);
               
                paires.remove(paire1);
                paires.remove(paire2);*/
            }
           
        }
        s.evalBenefice();
        return s;
    }
    
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p100_n0_k3_l0.txt");
            Instance i = read.readInstance();
            
            CycleDeK CycleK = new CycleDeK();
            Solution s = CycleK.solve(i);
            
            System.out.println("\nsC2 check: " + s.check());
            
            System.out.println("Solution = " + s);
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
