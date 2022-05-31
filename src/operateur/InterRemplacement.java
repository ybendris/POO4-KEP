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

    public InterRemplacement(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int debutSequenceJ, int finSequenceI, int finSequenceJ) {
        super(sequence, autreSequence, debutSequenceI, debutSequenceJ,finSequenceI,finSequenceJ);
    }
    

    

    @Override
    protected boolean doMouvement() {
        return false;
    }

    @Override
    protected int evalDeltaBeneficeSequence() {
        if(this.sequence == null ) return Integer.MIN_VALUE;
        return this.sequence.deltaBeneficeRemplacementInter(debutSequenceI, finSequenceI, pairesSequenceJ);
    }

    @Override
    protected int evalDeltaBeneficeAutreSequence() {
        if(this.autreSequence == null ) return Integer.MIN_VALUE;
        return this.autreSequence.deltaBeneficeRemplacementInter(debutSequenceJ, finSequenceJ, pairesSequenceI);
    }

    @Override
    public String toString() {
        return "InterRemplacement{" + 
                "\n\tdebutSequenceI=" + debutSequenceI + 
                "\n\tdebutSequenceJ=" + debutSequenceJ + 
                "\n\tfinSequenceI=" + finSequenceI + 
                "\n\tfinSequenceJ=" + finSequenceJ + 
                "\n\tpairesSequenceI=" + pairesSequenceI + 
                "\n\tpairesSequenceJ=" + pairesSequenceJ + 
                "\n\tdeltaBeneficeSequence=" + deltaBeneficeSequence + 
                "\n\tdeltaBeneficeAutreSequence=" + deltaBeneficeAutreSequence + 
                "\n\tdeltaBenefice=" + deltaBenefice +
                "\n}";
    }
    
    
    
}
