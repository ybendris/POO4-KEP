/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solveur;

import instance.Instance;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import java.io.IOException;
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
                    
                    improve = true;
                } 
            }
        }
        return s;
    }
    
    /**
     * Test sur la première instance
     * @param args 
     */
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesTest/KEP_p250_n28_k5_l17.txt");
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
