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
public class InterEchange extends OperateurInterSequences{

    public InterEchange() {
        super();
    }

    public InterEchange(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence, autreSequence, debutSequenceI, finSequenceI, debutSequenceJ, finSequenceJ);
    }

    @Override
    protected boolean doMouvement() {
        return this.sequence.doEchange(this);
    }

    @Override
    protected int evalDeltaBeneficeSequence() {
        if(this.sequence == null ) return Integer.MIN_VALUE;
        return this.sequence.deltaBeneficeRemplacementInter(debutSequenceI, finSequenceI, noeudsSequenceJ);
    }

    @Override
    protected int evalDeltaBeneficeAutreSequence() {
        if(this.autreSequence == null ) return Integer.MIN_VALUE;
        return this.autreSequence.deltaBeneficeRemplacementInter(debutSequenceJ, finSequenceJ, noeudsSequenceI);
    }

    @Override
    public String toString() {
        return "InterEchange{" + 
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

    @Override
    public boolean isTabou(OperateurLocal operateur) {
        if(operateur == null) return false;
        if(!(operateur instanceof InterEchange)) return false;
        if(operateur.sequence == null || operateur.noeudsSequenceI == null || operateur.noeudsSequenceJ == null) return false;
        
        if(this.noeudsSequenceI.equals(operateur.noeudsSequenceI) || this.noeudsSequenceJ.equals(operateur.noeudsSequenceJ))
            return true;
        
        return false;
    }
}
