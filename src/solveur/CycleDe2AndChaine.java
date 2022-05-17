/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solveur;

import instance.Instance;
import instance.reseau.DonneurAltruiste;
import instance.reseau.Paire;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import java.io.IOException;
import java.util.LinkedList;
import solution.Chaine;
import solution.Solution;

/**
 *
 * @author Valek
 */
public class CycleDe2AndChaine implements Solveur{
    
    @Override
    public String getNom() {
        return "CycleDe2AndChaine";
    }

    /***
     * On réalise des cycles de taille 2, puis des chaines de tailles K.
     * donnant le meilleur bénéfice
     * @param instance
     * @return 
     */
    @Override
    public Solution solve(Instance instance) {
        Solution s = new Solution(instance);
        LinkedList<Paire> paires = instance.getPaires();
        LinkedList<Paire> pairesAjoutCycle = null;
        LinkedList<DonneurAltruiste> donneurAltruiste = instance.getDonneursAltruistes();
        Paire paire1 = null;
        Paire paire2 = null;
        DonneurAltruiste donneurAltruiste1=null;
        int compatibilite = 1;
        int compatibilitePP = 1;
        int compatibiliteDAP = 1;
        int tailleMaxChaine=instance.getTailleMaxChaine();
        
        while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1){
            paire1 = null;
            paire2 =null;
            
            pairesAjoutCycle=bestBeneficeCyclePaires(paires, paire1, paire2);
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
        
        getChaineCycle2(paires, compatibiliteDAP, donneurAltruiste, s, compatibilitePP, tailleMaxChaine);
        s.evalBenefice();
        return s;
    }

    private void getChaineCycle2(LinkedList<Paire> paires, int compatibiliteDAP, LinkedList<DonneurAltruiste> donneurAltruiste, Solution s, int compatibilitePP, int tailleMaxChaine) {
        DonneurAltruiste donneurAltruiste1;
        Paire paire1;
        Paire paire2;
        while(!paires.isEmpty() && compatibiliteDAP==1){
            
            int beneficeDA1P1 = 0;
            int bestBenefice = 0;
            donneurAltruiste1 = null;
            paire1 = null;
            int tailleChaineActuelle = -1;
            int index = -1;
            
            for(DonneurAltruiste DA1 : donneurAltruiste){
                for(Paire P1 : paires){
                    beneficeDA1P1 = DA1.getBeneficeVers(P1);
                    if(beneficeDA1P1 > -1){
                        if(beneficeDA1P1>bestBenefice){
                            bestBenefice=beneficeDA1P1;
                            donneurAltruiste1 =DA1;
                            paire1=P1;
                        }
                    }
                }
            }
            if(paire1==null){
                compatibiliteDAP=0;
            }
            else{
                s.ajouterPaireNouvelleChaine(donneurAltruiste1, paire1);
                donneurAltruiste.remove(donneurAltruiste1);
                paires.remove(paire1);
                index++;
                
                while(compatibilitePP==1 && s.getSizeChaineByIndex(index)<tailleMaxChaine-1){
                    bestBenefice=0;
                    paire2 = null;
                    
                    paire2=bestBenefChainePaire(paires, paire1);
                    
                    if(paire2==null){
                        compatibilitePP=0;
                    }
                    else{
                        s.ajouterPaireDerniereChaine(paire2);
                        paires.remove(paire2);
                        paire1=paire2;
                    }
                }
            }
        }
    }
    
    private Paire bestBenefChainePaire (LinkedList<Paire> paires, Paire paire1){
        int beneficeP1P2 = 0;
        int bestBenefice = 0;
        Paire bestPaire = null;
        for(Paire P2 : paires){
                        beneficeP1P2 = paire1.getBeneficeVers(P2);
                        if(beneficeP1P2>bestBenefice){
                            bestBenefice=beneficeP1P2;
                            bestPaire=P2;
                        }
        }
        return bestPaire;
    }
    
    private LinkedList<Paire> bestBeneficeCyclePaires(LinkedList<Paire> paires, Paire paire1, Paire paire2){
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
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p100_n11_k3_l13.txt");
            Instance i = read.readInstance();
            
            CycleDe2AndChaine c2CH = new CycleDe2AndChaine();
            Solution s = c2CH.solve(i);
            
            System.out.println("Solution = " + s);
            System.out.println("sc2CH check: " + s.check());
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
             
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
