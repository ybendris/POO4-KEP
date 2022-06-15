/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import instance.Instance;
import instance.reseau.DonneurAltruiste;
import instance.reseau.Paire;
import io.InstanceReader;
import io.SolutionWriter;
import io.exception.ReaderException;
import java.util.LinkedList;
import operateur.InterDeplacement;
import operateur.TypeOperateurLocal;
import solution.Chaine;
import solution.Cycle;
import solution.Solution;
import solveur.CycleDe2;
import solveur.RechercheLocale;
import solveur.Solveur;

/**
 *
 * @author yanni
 */
public class TestDeltaBeneficeSuppression {
    public static void main(String[] args) {
        /*try{
            InstanceReader read = new InstanceReader("instancesInitiales/yannis.txt");
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
            Paire p9 = i.getPaireById(9);
            
            System.out.println(d1.getBeneficeVers(p3)); //5
            System.out.println(p3.getBeneficeVers(p4)); //4
            System.out.println(p4.getBeneficeVers(p5)); //3
            System.out.println(d2.getBeneficeVers(p6)); //4
            System.out.println(p6.getBeneficeVers(p7)); //4
            System.out.println(p7.getBeneficeVers(p8)); //3
            System.out.println(p8.getBeneficeVers(p9)); //1
            
            
            Chaine ch1 = new Chaine(d1,5);
            Chaine ch2 = new Chaine(d2,5);
            Cycle c1 = new Cycle(4);
            Cycle c2 = new Cycle(4);
            
            ch1.ajouterPaireFin(p3);
            ch1.ajouterPaireFin(p4);
            ch1.ajouterPaireFin(p5);
            
            ch2.ajouterPaireFin(p6);
            ch2.ajouterPaireFin(p7);
            ch2.ajouterPaireFin(p8);
            ch2.ajouterPaireFin(p9);
            
            c1.ajouterPaireFin(p6);
            c1.ajouterPaireFin(p7);
            c1.ajouterPaireFin(p8);
            c1.ajouterPaireFin(p9);
            
            c2.ajouterPaireFin(p6);
            c2.ajouterPaireFin(p7);
            
            System.out.println(ch1); //Bénéfice 12
            System.out.println(ch2); //Bénéfice 12
            System.out.println(c1); //Bénéfice 9
            System.out.println(c2); //Bénéfice 5
            
            LinkedList<Paire> pairesSequenceJ = new LinkedList<Paire>();
            
            System.out.println("--------------Test deltaBeneficeSuppression chaine---------------");
            System.out.println(ch1.deltaBeneficeSuppressionSequence(1, 1)); //-7
            
            System.out.println(ch1.deltaBeneficeSuppressionSequence(0, 0)); //-inf (on peut pas supprimer le donneur altruiste)
            System.out.println(ch1.deltaBeneficeSuppressionSequence(2, 2)); //-inf
            System.out.println(ch1.deltaBeneficeSuppressionSequence(3, 3)); //-3
            System.out.println(ch1.deltaBeneficeSuppressionSequence(2, 3)); //-7
            System.out.println(ch1.deltaBeneficeSuppressionSequence(1, 3)); //-inf (TODO: on accepte pas un seul donneur altruist solo)
            
            
            System.out.println(ch1.deltaBeneficeSuppression(1, 0)); //-7
            System.out.println(ch1.deltaBeneficeSuppression(0, 1)); //-2
            System.out.println(ch1.deltaBeneficeSuppression(0, 2)); //-12
            
            System.out.println("--------------Test deltaBeneficeSuppression cycle---------------");
            System.out.println(c1.deltaBeneficeSuppressionSequence(3, 3));//0
            System.out.println(c1.deltaBeneficeSuppressionSequence(1, 1));//-inf (6 pas compatible vers 8)
            System.out.println(c1.deltaBeneficeSuppressionSequence(2, 3));//-4
            System.out.println(c1.deltaBeneficeSuppressionSequence(3, 0));//-5
            
           
            System.out.println("--------------Test deltaBeneficeSuppression cycle (cycle non valide) ---------------");
            System.out.println(c2.deltaBeneficeSuppressionSequence(0, 1));
            
            pairesSequenceJ.add(p5);
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }*/
        
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/suppression.txt");
            Instance i = read.readInstance();
            
            Solveur solveurInitial = new CycleDe2();
            RechercheLocale algo = new RechercheLocale(solveurInitial);
            Solution s = algo.solve(i);
            
            
            System.out.println(s.getMeilleurOperateurLocal(TypeOperateurLocal.INTER_DEPLACEMENT));
            
            System.out.println(s);
            
            
          

        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
    }
}
