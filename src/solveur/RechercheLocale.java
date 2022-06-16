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
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p250_n83_k5_l17.txt");
            Instance i = read.readInstance();
            
            Solveur solveurInitial = new CycleDeKAndChaineV2();
            RechercheLocale algo = new RechercheLocale(solveurInitial);
            Solution s = algo.solve(i);
            
            System.out.println(s.getMeilleurOperateurLocal(TypeOperateurLocal.INTER_DEPLACEMENT));
            
            System.out.println(s);
            System.out.println(i);
            
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
        
    }
}
