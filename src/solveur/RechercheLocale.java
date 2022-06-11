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
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;
import solution.Solution;

/**
 *
 * @author yanni
 */
public class RechercheLocale implements Solveur{
    private Solveur solveurInitial;
    public RechercheLocale(Solveur solveurInitial) {
        this.solveurInitial = solveurInitial;
    }
    
    
    @Override
    public String getNom() {
        return "RechercheLocale("+this.solveurInitial.getNom()+')';
    }
    
    /*private LinkedList<Paire> getNextPaireCycleK(LinkedList<Paire> paires, Paire paire1, LinkedList<Paire> pairesAjoutCycle, int tailleMaxCycle) {
        Paire paire2;
        int compatibilite =1;
        while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1 && paire1!=null){
            
            paire2=null;
            int bestBenefice = 0;
            int beneficeP1P = 0;
            for(Paire p : paires){
                if(pairesAjoutCycle.size()<tailleMaxCycle){
                    if(paire1.getBeneficeVers(p) > -1){
                        beneficeP1P=paire1.getBeneficeVers(p);
                        if(beneficeP1P>bestBenefice){
                            paire2=p;
                            bestBenefice=beneficeP1P;
                        }
                    }
                }
                else{
                    break;
                }
            }
            if(paire2==null){
                compatibilite=0;
            }
            else{
                pairesAjoutCycle.add(paire2);
                paires.remove(paire2);
                paire1=paire2;
            }
        }
        return pairesAjoutCycle;
    }*/

    /*private LinkedList<Paire> bestBeneficePaires(LinkedList<Paire> paires){
        int beneficeP1P2 = 0;
        int beneficeP2P1 = 0;
        int beneficeTotal = 0;
        int bestBenefice = 0;
        Paire paire1 = null;
        Paire paire2 = null;
        for(Paire P1 : paires){
                for(Paire P2 : paires){
                    if(P1.getBeneficeVers(P2) > -1){

                        //if(P2.getBeneficeVers(P1) > -1){

                            beneficeP1P2 = P1.getBeneficeVers(P2);
                            //beneficeP2P1 = P2.getBeneficeVers(P1);
                            //beneficeTotal = beneficeP1P2 + beneficeP2P1;
                            if(beneficeP1P2 > bestBenefice){
                                bestBenefice = beneficeP1P2;
                                paire2 = P2;
                                paire1 = P1;
                            }
                        //}   
                    }
                }
            }
        LinkedList<Paire> best =  new LinkedList<Paire>();
        best.add(paire1);
        best.add(paire2);
        return best;
    }*/
    
    
    @Override
    public Solution solve(Instance instance) {
        
        Solution s = this.solveurInitial.solve(instance);

        boolean improve = true;
        
        while(improve == true){
            improve = false;
            
            for(TypeOperateurLocal type :TypeOperateurLocal.values()){
                OperateurLocal bestOperateur = s.getMeilleurOperateurLocal(type);
                System.out.println(bestOperateur);
                if(bestOperateur.isMouvementAmeliorant()){
                    s.doMouvementRechercheLocale(bestOperateur);
                    
                    s.insererPaireRestantes();

                    System.out.println("ça improve");
                    improve = true;
                } 
            }
        }
        
        
        //LinkedList<Paire> tkt = this.bestBeneficePaires(s.getPairesRestantes());
        //System.out.println(this.getNextPaireCycleK(s.getPairesRestantes(), tkt.getLast(), tkt, 5));
        
        return s;
    }
    
    /**
     * Test sur la première instance
     * @param args 
     */
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesTest/KEP_p250_n83_k5_l17.txt");
            Instance i = read.readInstance();
            
            Solveur solveurInitial = new CycleDeKAndChaineV2();
            RechercheLocale algo = new RechercheLocale(solveurInitial);
            Solution s = algo.solve(i);
            
           
            System.out.println(s);
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
}
