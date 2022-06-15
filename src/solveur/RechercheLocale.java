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
   
    
    private LinkedList<Paire> bestBeneficePaires(LinkedList<Paire> paires){
        int beneficeP1P2 = 0;
        int bestBenefice = 0;
        Paire paire1 = null;
        Paire paire2 = null;
        for(Paire P1 : paires){
                for(Paire P2 : paires){
                    if(P1.getBeneficeVers(P2) > -1){

                            beneficeP1P2 = P1.getBeneficeVers(P2);
                            if(beneficeP1P2 > bestBenefice){
                                bestBenefice = beneficeP1P2;
                                paire2 = P2;
                                paire1 = P1;
                            }  
                    }
                }
            }
        LinkedList<Paire> best =  new LinkedList<Paire>();
        best.add(paire1);
        best.add(paire2);
        return best;
}


    private Paire getNextPaireCycleK(LinkedList<Paire> paires, LinkedList<Paire> pairesAjoutCycle, int tailleMaxCycle) {
            Paire paire1 = paires.getLast();
            Paire paire2;
            int compatibilite = 1;
            while(!paires.isEmpty() && paires.size()>=2 && compatibilite==1 && paire1!=null){

                paire2=null;
                int bestBenefice = 0;
                int beneficeP1P = 0;
                if(pairesAjoutCycle.size()<tailleMaxCycle){
                    for(Paire p : paires){

                        if(paire1.getBeneficeVers(p) > -1){
                            beneficeP1P=paire1.getBeneficeVers(p);
                            if(beneficeP1P>bestBenefice){
                                paire2=p;
                                bestBenefice=beneficeP1P;
                            }
                        }
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


    private boolean verifDernierePaireCycle(Paire lastPaire, Paire beginPaire){

            if(lastPaire.getBeneficeVers(beginPaire)>-1){
                    return true;
                }
            return false;
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

    private void getCycleDeK(LinkedList<Paire> pairesRestantes, Solution s, int tailleMaxCycle) {
            LinkedList<Paire> pairesValideCycle = new LinkedList<Paire>();
            Set<Paire> pairesProblemes = new HashSet<Paire>();
            Paire paire1 = null;
            Paire paire2 =null;
            boolean chercher = true;
            int cpt=0;

            while(!pairesRestantes.isEmpty() && pairesRestantes.size()>=2 && chercher==true){

                int compatibilite=1;
                boolean valide = false;
                boolean continu = true;

                LinkedList<Paire> pairesAjoutCycle = null;

                pairesAjoutCycle=bestBeneficePaires(pairesRestantes);

                //System.out.println("pairesAjoutCycle"+pairesAjoutCycle);
                if(pairesAjoutCycle.get(1)!=null) paire1=pairesAjoutCycle.get(1);
                else{
                    paire1=null;
                }

                pairesRestantes.remove(paire1);
                pairesRestantes.remove(pairesAjoutCycle.get(0));

                paire1 = getNextPaireCycleK(pairesRestantes, pairesAjoutCycle, tailleMaxCycle);

                Paire lastPaire = pairesAjoutCycle.getLast();
                Paire beginPaire = pairesAjoutCycle.getFirst();

                while(valide==false && continu==true && paire1!=null && lastPaire!=null){


                    if(verifDernierePaireCycle(lastPaire,beginPaire)){
                        valide=true;
                    }
                    else{
                        if(pairesAjoutCycle.size()>1){
                            pairesRestantes.add(pairesAjoutCycle.getLast());
                            pairesAjoutCycle.removeLast();
                            pairesValideCycle=nouvelleDernierePaire(pairesRestantes, pairesAjoutCycle);
                            if(!pairesAjoutCycle.equals(pairesValideCycle)){
                                pairesAjoutCycle=pairesValideCycle;
                                pairesRestantes.remove(pairesAjoutCycle.getLast());
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
                        pairesRestantes.add(p);
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
                        pairesRestantes.add(p);
                        }
                        pairesProblemes.clear();

                    }
                }
            }
        }


    
    
    
    @Override
    public Solution solve(Instance instance) {

        Solution s = this.solveurInitial.solve(instance);



        boolean improve = true;

        while(improve == true){
            improve = false;

            for(TypeOperateurLocal type :TypeOperateurLocal.values()){
                OperateurLocal bestOperateur = s.getMeilleurOperateurLocal(type);
                //System.out.println(bestOperateur);
                if(bestOperateur.isMouvementAmeliorant()){
                    s.doMouvementRechercheLocale(bestOperateur);
                    s.insererPaireRestantes();
                    s.verifPairesRestantes();

                    //this.getCycleDeK(new LinkedList<Paire>(s.getPairesRestantes()), s, instance.getTailleMaxCycle());

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
            InstanceReader read = new InstanceReader("instancesInitiales/suppression.txt");
            Instance i = read.readInstance();
            
            Solveur solveurInitial = new CycleDe2();
            RechercheLocale algo = new RechercheLocale(solveurInitial);
            Solution s = algo.solve(i);
            
            System.out.println(s.getMeilleurOperateurLocal(TypeOperateurLocal.INTER_DEPLACEMENT));
            
            System.out.println(s);
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
}
