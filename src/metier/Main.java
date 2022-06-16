/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier;

import instance.Instance;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import solution.Solution;
import solveur.CycleDe2;
import solveur.CycleDeKAndChaineV2;
import solveur.RechercheLocale;

/**
 *
 * @author yanni
 */
public class Main {
    public static void main(String[] args) {
        boolean optionRepertoireSolutions = false;
        boolean optionFichierInstance = false;
        String nomRepertoireSolutions = "";
        String nomFichierInstance = "";
        
        
        int i;
        
        if (args.length != 4) {
            error("Missing argument(s)");
        }
        
        for (i = 0; i < args.length; i++) {
            String opt = args[i];
            switch (opt) {
                case "-inst":
                    optionFichierInstance = true;
                    nomFichierInstance = args[++i];
                    break;
               
                case "-dSol":
                   optionRepertoireSolutions = true;
                   nomRepertoireSolutions = args[++i];
                   break;
                   
                default:
                    if (!opt.isEmpty() && opt.charAt(0) == '-') {
                        error("Unknown option: " + opt);
                    }
                    break;
            }
        }
        
        
        
        System.out.println("Option nomFichierInstance: "+ nomFichierInstance);
        System.out.println("Option nomRepertoireSolutions: "+ nomRepertoireSolutions);
        
        try{
            InstanceReader read = new InstanceReader(nomFichierInstance);
            Instance inst = read.readInstance();
            
            RechercheLocale algo = new RechercheLocale(new CycleDeKAndChaineV2());
            Solution s = algo.solve(inst);
            
            System.out.println("Solution = " + s);
            System.out.println("checker: " +s.check());
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName(),nomRepertoireSolutions);
            sw.writeSolution(s); 
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        } 
        catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
    }

    private static void error(String message) {
        if (message != null) {
            System.err.println(message);
        }
        System.err.println("usage: java -jar Projet_Bendris_Konarski_Testard.jar -inst instancesInitiales/KEP_p50_n3_k3_l4.txt -dSol solutions/Bendris_Konarski_Testard");
        System.exit(1);
    }
}
