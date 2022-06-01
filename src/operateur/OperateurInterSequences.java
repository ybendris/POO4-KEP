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

       
    public OperateurInterSequences(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int debutSequenceJ, int finSequenceI, int finSequenceJ) {
        super(sequence,debutSequenceI, debutSequenceJ,finSequenceI,finSequenceJ);
        this.autreSequence = autreSequence;
        System.out.println(this);
        this.pairesSequenceJ = this.convertToLinkedList(this.autreSequence.getPaires(),debutSequenceJ, finSequenceJ);
        this.pairesSequenceJ.pollFirst();
        this.pairesSequenceJ.pollLast();
        
        this.deltaBenefice = this.evalDeltaBenefice();
    }
    
    
    
    @Override
    protected int evalDeltaBenefice() {
        this.deltaBeneficeSequence = this.evalDeltaBeneficeSequence();
        this.deltaBeneficeAutreSequence = this.evalDeltaBeneficeAutreSequence();
        
        if(this.deltaBeneficeSequence == Integer.MIN_VALUE || this.deltaBeneficeAutreSequence == Integer.MIN_VALUE){
            return Integer.MIN_VALUE;
        }
        return this.deltaBeneficeSequence + this.deltaBeneficeAutreSequence;
    }
    
    protected abstract int evalDeltaBeneficeSequence();
    protected abstract int evalDeltaBeneficeAutreSequence();
    
}
