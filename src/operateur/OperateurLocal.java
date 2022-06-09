/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

import instance.reseau.Noeud;
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
    protected LinkedList<Noeud> noeudsSequenceI;
    protected LinkedList<Noeud> noeudsSequenceJ;

    public OperateurLocal() {
        this.debutSequenceI = -1;
        this.debutSequenceJ = -1;
        this.finSequenceI = -1;
        this.finSequenceJ = -1;
    }
    
    
    
    public OperateurLocal(SchemaEchange sequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence);
        this.debutSequenceI = debutSequenceI;
        this.debutSequenceJ = debutSequenceJ;
        this.finSequenceI = finSequenceI;
        this.finSequenceJ = finSequenceJ;
        this.noeudsSequenceI = this.convertToLinkedList(this.sequence,debutSequenceI, finSequenceI);
        this.noeudsSequenceJ = new LinkedList<Noeud>();
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

    public LinkedList<Noeud> getPairesSequenceI() {
        return noeudsSequenceI;
    }

    public LinkedList<Noeud> getPairesSequenceJ() {
        return noeudsSequenceJ;
    }
    
    public LinkedList<Noeud> convertToLinkedList(SchemaEchange sequence,int debut, int fin){
        LinkedList<Noeud> noeudsSubList = new LinkedList<Noeud>();
        int index;
        for(index=debut; index != fin%sequence.getNbNoeud(); index = (index + 1) % sequence.getNbNoeud()){
            noeudsSubList.add(sequence.getCurrent(index));
        }
        noeudsSubList.add(sequence.getCurrent(index));
        
        return noeudsSubList;
    }
    
    public static OperateurLocal getOperateur(TypeOperateurLocal type){
        switch(type){
            case INTER_REMPLACEMENT:
                return new InterRemplacement();
            
            default:
                return null;
        }
    }
     
    
    
    
    
    public static OperateurIntraSequence getOperateurIntra(TypeOperateurLocal type, SchemaEchange sequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        switch(type) {
            /*case INTRA_SUPPRESSION:
                return new IntraSuppression(sequence, debutSequenceI, finSequenceI,  debutSequenceJ, finSequenceJ);*/
           
            default:
                return null;
        }
    }
    
    
    
    public static OperateurInterSequences getOperateurInter(TypeOperateurLocal type, SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        switch(type) {
            case INTER_REMPLACEMENT:
                return new InterRemplacement(sequence, autreSequence, debutSequenceI, finSequenceI,  debutSequenceJ, finSequenceJ);
            
            default:
                return null;
        }
    }
    
    
    @Override
    public String toString() {
        return "OperateurLocal{" + "debutSequenceI=" + debutSequenceI + ", debutSequenceJ=" + debutSequenceJ + ", finSequenceI=" + finSequenceI + ", finSequenceJ=" + finSequenceJ + ", pairesSequenceI=" + noeudsSequenceI + ", pairesSequenceJ=" + noeudsSequenceJ + '}';
    }

    
    
}
