/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import instance.Instance;
import instance.reseau.Paire;
import io.exception.ReaderException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import solution.Chaine;
import solution.Cycle;
import solution.Solution;

/**
 *
 * @author lucas
 */
public class SolutionWriter {
    /*
    nomInstance_sol.txt
    sous forme d’un fichier texte, avec comme séparateurs des tabulations.
    //commentaire
    La première ligne de contenu contient un nombre entier qui représente le coût total de la solution
    
    Chacune des lignes suivantes doit contenir les informations sur un cycle ou
    une chaîne d’échange : il s’agit de donner les identifiants des donneurs dans
    la chaîne ou le cycle en respectant les modalités suivantes :
        – les donneurs altruistes sont indicés de 1 à N, et les paires patient donneur 
        sont indicées de N + 1 à N + P (s’il n’y a aucun donneur altruiste, la première patient-donneur a donc pour indice 1) ;
        – les cycles peuvent commencer par n’importe quel identifiant, et il ne doit pas y avoir d’identifiant en double ;
        – les chaînes doivent commencer par un identifiant d’un donneur altruiste (qui est celui qui initie la chaîne).

    */
    
    private File solutionFile;

    public SolutionWriter(String instanceName) {
        if(instanceName != null){
            this.solutionFile = new File("./instancesInitiales/" + instanceName + "_sol.txt");
        }
    }
    
    public SolutionWriter(String instanceName, String directory) {
        if(instanceName != null && directory != null){
            this.solutionFile = new File(directory + '/' + instanceName + "_sol.txt");
            if(!this.solutionFile.exists())
                this.solutionFile.getParentFile().mkdirs();
        }
    }
    
    public boolean writeSolution(Solution s) throws IOException{
        if(s != null){
            LinkedList<Chaine> chaines = s.getChaines();
            LinkedList<Cycle> cycles = s.getCycles();
            
            FileWriter f = new FileWriter(solutionFile);
            f.append("// Cout total de la solution\n");
            f.append(Integer.toString(s.getBenefice()) + "\n\n");
            
            f.append("// Description de la solution\n// Cycles\n");
            for(Cycle cl: cycles){
                for(Paire p :cl.getPaires()){
                    f.append(p.getId() + "\t");
                }
                f.append("\n");
            }            
            f.append("\n\n");      
            
            f.append("// Chaines\n");
            for(Chaine c: chaines){
                f.append(c.getDonneurAlt().getId() + "\t");
                for(Paire p : c.getPaires()){
                    f.append(p.getId() + "\t");
                }
                f.append("\n");
            }
            
            f.close();
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("Test de la classe SolutionWriter:");
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/KEP_p9_n1_k3_l3.txt");
            Instance i = read.readInstance();
            
            Solution s = new Solution(i);
            
            System.out.println(s.toString());
            System.out.println(s.check());//true;
            
            SolutionWriter sw = new SolutionWriter(s.getInstance().getName());
            sw.writeSolution(s);
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
