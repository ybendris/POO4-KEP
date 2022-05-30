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
        System.out.println("V222222222");
        s.wola();
        
        return s;
    }
    
    public static void main(String[] args) throws IOException {
        try{
            InstanceReader read = new InstanceReader("instancesTest/KEP_p250_n13_k5_l17.txt");
            Instance i = read.readInstance();
            
            CycleDeKAndChaineV2 CycleKCH = new CycleDeKAndChaineV2();
            Solution s = CycleKCH.solve(i);
            
           
            
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
