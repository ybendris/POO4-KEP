/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solveur;

import instance.Instance;
import instance.reseau.Paire;
import instance.reseau.Transplantation;
import io.InstanceReader;
import io.exception.ReaderException;
import java.util.LinkedList;
import solution.Cycle;
import solution.Solution;

/**
 *
 * @author Valek
 */
public class CycleDe2 implements Solveur{

    @Override
    public String getNom() {
        return null;
    }

    @Override
    public Solution solve(Instance instance) {
        Solution s = new Solution(instance);
        LinkedList<Paire> paires = instance.getPaires();
        Paire paireTest1 = null;
        Paire paireTest2 =null;
        int compatibilite = 1;
        
        while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1){
            System.out.println("C'est parti : Paires = " + paires + " Taille = " + paires.size());
            paireTest1 = null;
            paireTest2 =null;
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
                                paireTest2 = P2;
                                paireTest1 = P1;
                            }
                        }   
                    }
                }
            }
            if(paireTest1==null && paireTest2==null){
                compatibilite=0;
            }
            else{
                System.out.println("Paire 1 = " + paireTest1 + " Paire 2 = " + paireTest2);
                System.out.println("Ajout");
                s.ajouterPairesNouveauCycleDe2(paireTest1, paireTest2);
                System.out.println("Remove");
                paires.remove(paireTest1);
                paires.remove(paireTest2);
            }
           
        }
        s.evalBenefice();
        return s;
    }
    
    public static void main(String[] args) {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n0_k3_l0.txt");
            Instance i = read.readInstance();
            
            CycleDe2 Cycle2 = new CycleDe2();
            
            Solution sC2 = Cycle2.solve(i);
            
            System.out.println("Solution = " + sC2);
             
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
