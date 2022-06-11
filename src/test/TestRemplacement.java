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
            
            Cycle c3 =new Cycle(5);
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
            InterRemplacement op3 = new InterRemplacement(c3,ch1,0,1,0,2); //-13 +1 =-12 // -inf
            System.out.println(op3);
            InterRemplacement op4 = new InterRemplacement(ch2,ch1,1,2,0,2); //-13 +1 = -12 // -inf
            System.out.println(op4);
            InterRemplacement op5 = new InterRemplacement(ch2,ch1,0,1,0,1); //-inf
            System.out.println(op5);
            
            
            System.out.println(op.isMouvementRealisable());//true
            System.out.println(op5.isMouvementRealisable());//false
            
            
            System.out.println("On va faire Insertion dans chaine:---------------");
            System.out.println(op);

            System.out.println("Avant Insertion");
            System.out.println(ch1);
            System.out.println(ch2);
            //op.doMouvementIfRealisable();
            
            System.out.println("Après Insertion");
            System.out.println(ch1);
            System.out.println(ch2);
            
            System.out.println("On va faire Remplacement dans chaine:---------------");
            System.out.println(op4);

            System.out.println("Avant remplacement");
            System.out.println(ch1);
            System.out.println(ch2);
            //op4.doMouvementIfRealisable();
            
            System.out.println("Après remplacement");
            System.out.println(ch1);
            System.out.println(ch2);
            
            System.out.println("On va faire Insertion dans cycle:---------------");
            System.out.println(op);

            System.out.println("Avant Insertion");
            System.out.println(ch1);
            System.out.println(ch2);
            //op.doMouvementIfRealisable();
            
            System.out.println("Après Insertion");
            System.out.println(ch1);
            System.out.println(ch2);
            
            System.out.println("On va faire Insertion de chaine à cycle:---------------");
            System.out.println("\n\n\n\n\n");
            InterRemplacement op6 = new InterRemplacement(ch1,c3,2,2,3,4);
            
            System.out.println("Avant Insertion");
            System.out.println(ch1);
            System.out.println(c3);
            //op6.doMouvementIfRealisable();
            
            System.out.println("Après Insertion");
            System.out.println(ch1);
            System.out.println(c3);
            
            
            System.out.println("On va faire Insertion de chaine à chaine en fin:---------------");
            System.out.println(ch1);
            System.out.println(ch2);
            
            InterRemplacement op7 = new InterRemplacement(ch2,ch1,4,4,2,3);
            op7.doMouvementIfRealisable();
            System.out.println(op7);
            System.out.println(ch1);
            System.out.println(ch2);
            
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
