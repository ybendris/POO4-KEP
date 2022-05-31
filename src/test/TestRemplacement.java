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
            InstanceReader read = new InstanceReader("instancesInitiales/yannistest.txt");
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
            
            System.out.println(d1.getBeneficeVers(p3)); //5
            System.out.println(d2.getBeneficeVers(p3)); //2
            
            
            System.out.println(p5.getBeneficeVers(p7)); //2
            System.out.println(p7.getBeneficeVers(p6)); //6 
            System.out.println(p6.getBeneficeVers(p5)); //4
            System.out.println(p6.getBeneficeVers(p7)); //4 -> cout modifié (j'ai ajouté un cout retour)
            
            System.out.println();
            
            Cycle c1 = new Cycle(3); // taille max 3
            
            System.out.println("Test Insertion");
           
            c1.ajouterPaireFin(p7);
            System.out.println(c1.toString());
            
            c1.ajouterPaireFin(p6);
            System.out.println(c1.toString());
            
            Chaine ch1 = new Chaine(d1,4);
            
            ch1.ajouterPaireFin(p3);
            System.out.println(ch1.toString());
            ch1.ajouterPaireFin(p5);
            System.out.println(ch1.toString());
            
            
            LinkedList<Paire> PairesJ = new LinkedList();
            
            System.out.println("Suppression de chaine (fin)");
            System.out.println(ch1.deltaBeneficeRemplacementInter(1, 1, PairesJ)); //-2
           
            
            System.out.println("Insertion dans cycle");
            PairesJ.add(p5);
            System.out.println(c1.deltaBeneficeRemplacementInter(1 ,0, PairesJ)); //3
            
                    
            
           
            InterRemplacement operateur = new InterRemplacement(ch1,c1,1,1,1,0);
            
            System.out.println(operateur);
            
            System.out.println(operateur);
            //System.out.println(c2.deltaBeneficeRemplacementInter(1, 1, PairesJ));
           
         
            
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }
}
