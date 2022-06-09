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
 * @author yanni
 */
public abstract class OperateurInterSequences extends OperateurLocal {
    protected SchemaEchange autreSequence;
    protected int deltaBeneficeSequence;
    protected int deltaBeneficeAutreSequence;
    
    
    public OperateurInterSequences() {
        super();
        this.deltaBeneficeSequence = Integer.MIN_VALUE;
        this.deltaBeneficeAutreSequence = Integer.MIN_VALUE;
    }

       
    public OperateurInterSequences(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence,debutSequenceI, finSequenceI, debutSequenceJ,finSequenceJ);
        this.autreSequence = autreSequence;
        this.noeudsSequenceJ = this.convertToLinkedList(this.autreSequence,debutSequenceJ, finSequenceJ%autreSequence.getNbNoeud());
        this.deltaBenefice = this.evalDeltaBenefice();
    }
    
    
    @Override
    protected int evalDeltaBenefice() {
        this.deltaBeneficeSequence = this.evalDeltaBeneficeSequence();
        if(this.deltaBeneficeSequence == Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        //System.out.println(this);
        this.deltaBeneficeAutreSequence = this.evalDeltaBeneficeAutreSequence();
        if(this.deltaBeneficeAutreSequence == Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        
        return this.deltaBeneficeSequence+ this.deltaBeneficeAutreSequence;
    }

    public SchemaEchange getAutreSequence() {
        return autreSequence;
    }

    public SchemaEchange getSequence() {
        return sequence;
    }

    public int getDeltaBeneficeSequence() {
        return deltaBeneficeSequence;
    }

    public int getDeltaBeneficeAutreSequence() {
        return deltaBeneficeAutreSequence;
    }
    
    protected abstract int evalDeltaBeneficeSequence();
    protected abstract int evalDeltaBeneficeAutreSequence();
    
}
