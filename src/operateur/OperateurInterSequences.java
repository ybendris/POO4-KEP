/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

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
        this.pairesSequenceJ = (LinkedList)this.autreSequence.getPaires().subList(debutSequenceJ, finSequenceJ);
        this.deltaBenefice = this.evalDeltaBenefice();
    }
    
    
    @Override
    protected int evalDeltaBenefice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    protected abstract int evalDeltaCoutTournee();
    protected abstract int evalDeltaCoutAutreTournee();
    
}
