/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

import solution.SchemaEchange;

/**
 *
 * @author yanni
 */
public class InterRemplacement extends OperateurInterSequences{

    public InterRemplacement() {
        super();
    }

    public InterRemplacement(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence, autreSequence, debutSequenceI, finSequenceI, debutSequenceJ, finSequenceJ);
    }

    @Override
    protected boolean doMouvement() {
        return false;
    }

    @Override
    protected int evalDeltaBeneficeSequence() {
        if(this.sequence == null ) return Integer.MIN_VALUE;
        return this.sequence.deltaBeneficeSuppressionSequence(debutSequenceI, finSequenceI);
    }

    @Override
    protected int evalDeltaBeneficeAutreSequence() {
        if(this.autreSequence == null ) return Integer.MIN_VALUE;
        return this.autreSequence.deltaBeneficeInsertionSequence(noeudsSequenceI, debutSequenceJ, finSequenceJ);
    }

    @Override
    public String toString() {
        return "InterRemplacement{" + 
                "\n\tdebutSequenceI=" + debutSequenceI + 
                "\n\tfinSequenceI=" + finSequenceI + 
                "\n\tdebutSequenceJ=" + debutSequenceJ + 
                "\n\tfinSequenceJ=" + finSequenceJ + 
                "\n\tpairesSequenceI=" + noeudsSequenceI + 
                "\n\tpairesSequenceJ=" + noeudsSequenceJ + 
                "\n\tdeltaBeneficeSequence=" + deltaBeneficeSequence + 
                "\n\tdeltaBeneficeAutreSequence=" + deltaBeneficeAutreSequence + 
                "\n\tdeltaBenefice=" + deltaBenefice +
                "\n}";
    }
    
    
    
}
