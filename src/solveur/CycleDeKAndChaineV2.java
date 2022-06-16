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
import operateur.TypeOperateurLocal;
import solution.Solution;

/**
 *
 * @author yanni
 */
public class CycleDeKAndChaineV2 implements Solveur{

    @Override
    public String getNom() {
         return "CycleDeKAndChaineV2";
    }

    @Override
    public Solution solve(Instance instance) {
        Solution s = new CycleDeKAndChaine().solve(instance);
        s.insererPaireRestantes();
        
        
        
                
        return s;
    }
    
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p50_n6_k3_l7.txt");
            Instance i = read.readInstance();
            
            CycleDeKAndChaineV2 CycleKCH = new CycleDeKAndChaineV2();
            Solution s = CycleKCH.solve(i);
            
           
            System.out.println(s.getMeilleurOperateurLocal(TypeOperateurLocal.INTER_DEPLACEMENT));
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
