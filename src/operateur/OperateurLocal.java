/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

import instance.reseau.Paire;
import java.util.LinkedList;
import solution.SchemaEchange;

/**
 *
 * @author yannis
 */
public abstract class OperateurLocal extends Operateur {
    protected int debutSequenceI;
    protected int debutSequenceJ;
    protected int finSequenceI;
    protected int finSequenceJ;
    protected LinkedList<Paire> pairesSequenceI;
    protected LinkedList<Paire> pairesSequenceJ;

    public OperateurLocal() {
        this.debutSequenceI = -1;
        this.debutSequenceJ = -1;
        this.finSequenceI = -1;
        this.finSequenceJ = -1;
    }
    
    public OperateurLocal(SchemaEchange sequence, int debutSequenceI, int debutSequenceJ, int finSequenceI, int finSequenceJ) {
        super(sequence);
        this.debutSequenceI = debutSequenceI;
        this.debutSequenceJ = debutSequenceJ;
        this.finSequenceI = finSequenceI;
        this.finSequenceJ = finSequenceJ;
       
        this.pairesSequenceI = (LinkedList)this.sequence.getPaires().subList(debutSequenceI, finSequenceI);
        this.pairesSequenceJ = (LinkedList)this.sequence.getPaires().subList(debutSequenceJ, finSequenceJ);
    }

    public int getDebutSequenceI() {
        return debutSequenceI;
    }

    public int getDebutSequenceJ() {
        return debutSequenceJ;
    }

    public int getFinSequenceI() {
        return finSequenceI;
    }

    public int getFinSequenceJ() {
        return finSequenceJ;
    }

    public LinkedList<Paire> getPairesSequenceI() {
        return pairesSequenceI;
    }

    public LinkedList<Paire> getPairesSequenceJ() {
        return pairesSequenceJ;
    }
    
    
    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch(type){
            case INTER_DEPLACEMENT:
                return new InterDeplacement();
            
            default:
                return null;
        }
    }
     
    /*
    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch(type){
            case INTER_DEPLACEMENT:
                return new InterDeplacement();
            case INTER_ECHANGE:
                return new InterEchange();
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement();
            case INTRA_ECHANGE:
                return new IntraEchange();
            default:
                return null;
        }
    }
    */
    
    /*
    public static OperateurIntraTournee getOperateurIntra(TypeOperateurLocal type, Tournee tournee, int positionI, int positionJ) {
        switch(type) {
            case INTRA_DEPLACEMENT:
                return new IntraDeplacement(tournee, positionI, positionJ);
            case INTRA_ECHANGE:
                return new IntraEchange(tournee, positionI, positionJ);
            default:
                return null;
        }
    }
    */
    
    /*
    public static OperateurInterTournees getOperateurInter(TypeOperateurLocal type, Tournee tournee, Tournee autreTournee, int positionI, int positionJ) {
        switch(type) {
            case INTER_DEPLACEMENT:
                return new InterDeplacement(tournee, autreTournee, positionI, positionJ);
            case INTER_ECHANGE:
                return new InterEchange(tournee, autreTournee, positionI, positionJ);
            default:
                return null;
        }
    }
    */
    
    @Override
    public String toString() {
        return "OperateurLocal{" + "debutSequenceI=" + debutSequenceI + ", debutSequenceJ=" + debutSequenceJ + ", finSequenceI=" + finSequenceI + ", finSequenceJ=" + finSequenceJ + ", pairesSequenceI=" + pairesSequenceI + ", pairesSequenceJ=" + pairesSequenceJ + '}';
    }

    
    
}