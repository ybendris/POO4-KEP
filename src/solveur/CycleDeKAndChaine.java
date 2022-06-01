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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import solution.Solution;
import static solveur.CycleDeK.compareLists;

/**
 *
 * @author Valek
 */
public class CycleDeKAndChaine implements Solveur{

    @Override
    public String getNom() {
        return "CycleDeKAndChaine";
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
       
        LinkedList<DonneurAltruiste> donneurAltruiste = instance.getDonneursAltruistes();
        
        DonneurAltruiste donneurAltruiste1=null;
        
        int compatibiliteDAP = 1;
        int compatibilitePP = 1;
        
        Paire paire1 = null;
        Paire paire2 = null;
        
        int tailleMaxChaine=instance.getTailleMaxChaine();
        
        boolean chercher = true;
        int tailleMaxCycle=instance.getTailleMaxCycle();
        
        getCycleDeK(instance, paires, s);
        
        
        getChaineCycleDeK(paires, compatibiliteDAP, donneurAltruiste, s, compatibilitePP, tailleMaxChaine);
        
        s.setPairesRestantes(paires);
        s.evalBenefice();
        
        
        return s;
    }

    private void getChaineCycleDeK(LinkedList<Paire> paires, int compatibiliteDAP, LinkedList<DonneurAltruiste> donneurAltruiste, Solution s, int compatibilitePP, int tailleMaxChaine) {
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
        
        Paire precPaire = null;
        Paire pTemp = null;
         
        if(tailleCycle>2){
            precPaire = temp.get(tailleCycle-1);   
        }
        else{
            precPaire = temp.getFirst();
        }
        
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
     
     private void getCycleDeK(Instance instance, LinkedList<Paire> paires, Solution s) {
        LinkedList<Paire> pairesValideCycle = new LinkedList<Paire>();
        Set<Paire> pairesProblemes = new HashSet<Paire>();
        Paire paire1 = null;
        Paire paire2 =null;
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
    
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesTest/KEP_p250_n13_k5_l17.txt");
            Instance i = read.readInstance();
            
            CycleDeKAndChaine CycleKCH = new CycleDeKAndChaine();
            Solution s = CycleKCH.solve(i);
            
           
            
            System.out.println("\nsCKCH check: " + s.check());
            
            System.out.println("Solution = " + s);
            
           
            System.out.println("Amélioration");
            
            s.wola();
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
            Paire p64 = s.getInstance().getPaireById(64);
            Paire p68 = s.getInstance().getPaireById(68);
            Paire p161 = s.getInstance().getPaireById(161);
            
            Paire p88 = s.getInstance().getPaireById(88);
            Paire p205 = s.getInstance().getPaireById(205);
            Paire p111 = s.getInstance().getPaireById(111);
            Paire p173 = s.getInstance().getPaireById(173);
            Paire p142 = s.getInstance().getPaireById(142);
            
            
            Paire p73 = s.getInstance().getPaireById(73);
            Paire p140 = s.getInstance().getPaireById(140);
            Paire p242 = s.getInstance().getPaireById(242);
            Paire p157 = s.getInstance().getPaireById(157);
            
            System.out.println(p73.getBeneficeVers(p157));
            System.out.println(p157.getBeneficeVers(p140));
            System.out.println(p73.getBeneficeVers(p140));

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
