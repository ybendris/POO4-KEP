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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import solution.Solution;

/**
 *
 * @author Valek
 */
public class CycleDeK implements Solveur{

    @Override
    public String getNom() {
        return "CycleDeK";
    }

    /***
     * On réalise des cycles de taille K, en utilisant l'association de paires 
     * donnant le meilleur bénéfice
     * @param instance
     * @return 
     */
    @Override
    public Solution solve(Instance instance) {
        Solution s = new Solution(instance);
        LinkedList<Paire> paires = instance.getPaires();
        LinkedList<Paire> pairesValideCycle = null;
        getCycleDeK(instance, paires, s);
        s.setPairesRestantes(paires);
        s.evalBenefice();
        return s;
    }

    private void getCycleDeK(Instance instance, LinkedList<Paire> paires, Solution s) {
        LinkedList<Paire> pairesValideCycle = new LinkedList<Paire>();
        Set<Paire> pairesProblemes = new HashSet<Paire>();
        Paire paire1 = null;
        Paire paire2 =null;
        Paire paireARemettre =null;
        boolean chercher = true;
        int tailleMaxCycle=instance.getTailleMaxCycle();
        int cpt=0;
        
        while(!paires.isEmpty() && paires.size()>=2 && chercher==true){
            
            int compatibilite=1;
            boolean valide = false;
            boolean continu = true;
            
            LinkedList<Paire> pairesAjoutCycle = null;
            
            pairesAjoutCycle=bestBeneficePaires(paires);

            if(pairesAjoutCycle.get(1)!=null) paire1=pairesAjoutCycle.get(1);
            else{
                paire1=null;
            }
            
            paires.remove(paire1);
            paires.remove(pairesAjoutCycle.get(0));
          
            paire1 = getNextPaireCycleK(paires, compatibilite, paire1, pairesAjoutCycle, tailleMaxCycle);

            //System.out.println("n°1 Paires ajout cycle => " + pairesAjoutCycle);
            
            Paire lastPaire = pairesAjoutCycle.getLast();
            Paire beginPaire = pairesAjoutCycle.getFirst();
            
            while(valide==false && continu==true && paire1!=null){
                
                
                if(verifDernierePaireCycle(lastPaire,beginPaire)){
                    valide=true;
                }
                else{
                    //System.out.println(" Ah non on peut pas boucler");
                    if(pairesAjoutCycle.size()>1){
                        paires.add(pairesAjoutCycle.getLast());
                        pairesAjoutCycle.removeLast();
                        pairesValideCycle=nouvelleDernierePaire(paires, pairesAjoutCycle);
                        //System.out.println("Ajout = " + pairesAjoutCycle + " Valide = " + pairesValideCycle);
                        if(!pairesAjoutCycle.equals(pairesValideCycle)){
                            //System.out.println("Les 2 listes ne sont pas égales");
                            pairesAjoutCycle=pairesValideCycle;
                            paires.remove(pairesAjoutCycle.getLast());
                            //System.out.println("n°2 Paires ajout cycle => " + pairesAjoutCycle);
                            valide = true;
                        }
                    }
                    else{                    
                        continu=false;
                        pairesProblemes.add(pairesAjoutCycle.getFirst());
                    }
                }
                
            }
            
            if(valide==true){
                insererPairesCycleSolution(pairesAjoutCycle, s);
                if(cpt<1){
                    for(Paire p : pairesProblemes){
                    paires.add(p);
                    }
                    pairesProblemes.clear();
                }
            }
            
            if(paire1==null)
            {
                cpt++;
                if(cpt>1){
                    chercher=false;
                }
                else{
                    for(Paire p : pairesProblemes){
                    paires.add(p);
                    }
                    pairesProblemes.clear();
                
                }
            }
        }
    }
    
    private Paire getNextPaireCycleK(LinkedList<Paire> paires, int compatibilite, Paire paire1, LinkedList<Paire> pairesAjoutCycle, int tailleMaxCycle) {
        Paire paire2;
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
        return paire1;
    }

    private void insererPairesCycleSolution(LinkedList<Paire> pairesAjoutCycle, Solution s) {
        boolean nouveau=true;
        for(Paire p : pairesAjoutCycle){
            if(nouveau==true){
                
                s.ajouterPaireNouveauCycle(p);
                nouveau = false;
            }
            else{
                
                s.ajouterPaireDernierCycle(p);
            }
        }
    }
    
    private boolean verifDernierePaireCycle(Paire lastPaire, Paire beginPaire){
        
        if(lastPaire.getBeneficeVers(beginPaire)>-1){
                return true;
            }
        return false;
    }   
        
    
    private LinkedList<Paire> nouvelleDernierePaire(LinkedList<Paire> paires,LinkedList<Paire> pairesCycle){
        
        LinkedList<Paire> temp =  new LinkedList<Paire>(pairesCycle);
        
        int tailleCycle=temp.size();
        
        Paire pTemp = null;
        
        Paire lastPaire = temp.getLast();
        Paire beginPaire = temp.getFirst();
        int beneficeTotal = 0;
        int bestBenefice = 0;
        
        for(Paire p : paires)
        {
                if(lastPaire.getBeneficeVers(p)>-1){
                    //System.out.println("Paire précedente : " + lastPaire.getId()+ " paire actuelle : " + p.getId() + " benefice " + p.getBeneficeVers(beginPaire));
                    if(p.getBeneficeVers(beginPaire)>-1){
                     
                        beneficeTotal=p.getBeneficeVers(beginPaire)+lastPaire.getBeneficeVers(p);
                
                        if(beneficeTotal>bestBenefice){
                            pTemp=p;
                        }
                    }
                }
            
            
        }
        if(pTemp!=null){
            temp.add(pTemp);    
        }
        
        return temp;
    }
    
    private LinkedList<Paire> bestBeneficePaires(LinkedList<Paire> paires){
        int beneficeP1P2 = 0;
        int beneficeP2P1 = 0;
        int beneficeTotal = 0;
        int bestBenefice = 0;
        Paire paire1 = null;
        Paire paire2 = null;
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
    
    static boolean compareLists(LinkedList<Paire> list1, LinkedList<Paire> list2) {
        boolean verif=true;
    while(list1 != null && list2 != null && verif==true) {
        if(list1.getFirst() == list2.getFirst()){
            verif=false;
        }
        else{
            list1.removeFirst();
            list2.removeFirst();
        }
    }

    return verif;
}
    
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p100_n11_k3_l13.txt");
            Instance i = read.readInstance();
            
            CycleDeK CycleK = new CycleDeK();
            Solution s = CycleK.solve(i);
            
            System.out.println("\nsCK check: " + s.check());
            
            System.out.println("Solution = " + s);
            
            System.out.println(s.wola());
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
