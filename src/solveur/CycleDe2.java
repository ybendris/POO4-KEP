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
public class CycleDe2 implements Solveur{

    @Override
    public String getNom() {

        return "cycleDe2";
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
        LinkedList<Paire> pairesAjoutCycle = null;
        Paire paire1 = null;
        Paire paire2 =null;
        int compatibilite = 1;
        
        while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1){
            /*System.out.println("C'est parti : Paires = " + paires 
                    + " Taille = " + paires.size());*/
            paire1 = null;
            paire2 =null;
            
            pairesAjoutCycle=bestBeneficePaires(paires, paire1, paire2);
            paire1=pairesAjoutCycle.get(0);
            paire2=pairesAjoutCycle.get(1);
            
            if(paire1==null && paire2==null){
                compatibilite=0;
            }
            else{
                s.ajouterPairesNouveauCycleDe2(paire1, paire2);
                paires.remove(paire1);
                paires.remove(paire2);
            }
           
        }
        s.evalBenefice();
        return s;
    }
    
    private LinkedList<Paire> bestBeneficePaires(LinkedList<Paire> paires, Paire paire1, Paire paire2){
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
        LinkedList<Paire> best =  new LinkedList<Paire>();
        best.add(paire1);
        best.add(paire2);
        return best;
    }
    
    public static void main(String[] args) throws IOException {
        try{
                        
            
           InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n0_k3_l0.txt");

            /*
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n0_k3_l0.txt");
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n1_k3_l3.txt");
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p18_n0_k4_l0.txt");
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p18_n2_k4_l4.txt");
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n0_k3_l0.txt");
                InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n0_k3_l0.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n0_k3_l0.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n3_k3_l4.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n3_k3_l7.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n3_k3_l13.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n3_k5_l17.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p100_n11_k5_l17.txt");
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p100_n11_k5_l17.txt");
            */
            Instance i = read.readInstance();
            
            CycleDe2 Cycle2 = new CycleDe2();
            Solution s = Cycle2.solve(i);
            
            System.out.println("\nsC2 check: " + s.check());
            
            System.out.println("Solution = " + s);

            System.out.println("sC2 check: " +s.check());
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
