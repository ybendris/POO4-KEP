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
import operateur.InsertionPaire;
import operateur.InterRemplacement;
import solution.Chaine;
import solution.Cycle;
import solution.Solution;

/**
 *
 * @author yanni
 */
public class TestRemplacement {
    public static void main(String[] args) {
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/remplacement.txt");
            Instance i = read.readInstance();
            
            Solution s = new Solution(i);
                
            DonneurAltruiste d1 = i.getDonneurById(1);
            DonneurAltruiste d2 = i.getDonneurById(2);
            Paire p3 = i.getPaireById(3);
            Paire p4 = i.getPaireById(4);
            Paire p5 = i.getPaireById(5);
            Paire p6 = i.getPaireById(6);
            Paire p7 = i.getPaireById(7);
            Paire p8 = i.getPaireById(8);
           
            
          
            Chaine ch1 = new Chaine(d1,5);
            
            ch1.ajouterPaireFin(p3);
            ch1.ajouterPaireFin(p5);
            
            Chaine ch2 = new Chaine(d2,5);
            ch2.ajouterPaireFin(p4);
            ch2.ajouterPaireFin(p6);
            ch2.ajouterPaireFin(p7);
            ch2.ajouterPaireFin(p8);
            
            Cycle c3 =new Cycle(4);
            c3.ajouterPaireFin(p4);
            c3.ajouterPaireFin(p6);
            c3.ajouterPaireFin(p7);
            c3.ajouterPaireFin(p8);
            
            System.out.println(ch1);
            System.out.println(ch2);
            System.out.println(c3);
            
            System.out.println(ch2.deltaBeneficeSuppressionSequence(1, 2)); //-13
            
            InterRemplacement op = new InterRemplacement(ch2,ch1,1,2,0,1); //-13 +6 =-7
            System.out.println(op);
            InterRemplacement op2 = new InterRemplacement(c3,ch1,0,1,0,1); //-13 +6 =-7
            System.out.println(op2);
            InterRemplacement op3 = new InterRemplacement(c3,ch1,0,1,0,2); //-13 +2 =-15
            System.out.println(op3);
           

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
