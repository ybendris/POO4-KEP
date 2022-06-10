/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import instance.Instance;
import instance.reseau.DonneurAltruiste;
import instance.reseau.Paire;
import io.InstanceReader;
import io.exception.ReaderException;
import java.util.LinkedList;
import operateur.InterEchange;
import solution.Chaine;
import solution.Cycle;
import solution.Solution;

/**
 *
 * @author yanni
 */
public class TestEchange {
    
    
    
    public static void main(String[] args) {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/echange.txt");
            Instance i = read.readInstance();
            
            Solution s = new Solution(i);
            
            System.out.println(s.toString());
            System.out.println(s.check());//true
            
            DonneurAltruiste d1 = i.getDonneurById(1);
            DonneurAltruiste d2 = i.getDonneurById(2);
            Paire p3 = i.getPaireById(3);
            Paire p4 = i.getPaireById(4);
            Paire p5 = i.getPaireById(5);
            Paire p6 = i.getPaireById(6);
            Paire p7 = i.getPaireById(7);
            Paire p8 = i.getPaireById(8);
                       
            System.out.println(d1.getBeneficeVers(p3)); //2
            System.out.println(p3.getBeneficeVers(p4)); //2
            System.out.println(p4.getBeneficeVers(p5)); //2
            
            System.out.println(d2.getBeneficeVers(p6)); //2
            System.out.println(p6.getBeneficeVers(p7)); //2
            System.out.println(p7.getBeneficeVers(p8)); //2
            
            System.out.println(p4.getBeneficeVers(p8)); //3
            System.out.println(p7.getBeneficeVers(p5)); //3
            
            Chaine ch1 = new Chaine(d1,5);
            Chaine ch2 = new Chaine(d2,5);
            
            ch1.ajouterPaireFin(p3);
            ch1.ajouterPaireFin(p4);
            ch1.ajouterPaireFin(p5);
            
            ch2.ajouterPaireFin(p6);
            ch2.ajouterPaireFin(p7);
            ch2.ajouterPaireFin(p8);
            
            System.out.println(ch1); //Bénéfice 6
            System.out.println(ch2); //Bénéfice 6
            
            InterEchange op1 = new InterEchange(ch1,ch2,1,1,1,1);//8
            InterEchange op2 = new InterEchange(ch1,ch2,2,2,2,2);//6
            InterEchange op3 = new InterEchange(ch1,ch2,3,3,3,3);//2
            InterEchange op4 = new InterEchange(ch1,ch2,1,2,1,2);//6
            InterEchange op5 = new InterEchange(ch1,ch2,1,3,1,3);//4
            InterEchange op6 = new InterEchange(ch1,ch2,1,1,1,3);//-inf taille max
            
            
            System.out.println(op1);
            System.out.println(op2);
            System.out.println(op3);
            System.out.println(op4);
            System.out.println(op5);
           
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
