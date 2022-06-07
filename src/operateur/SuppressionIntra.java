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
public class SuppressionIntra extends OperateurIntraSequence{

    @Override
    protected int evalDeltaBenefice() {
        if(sequence == null) return Integer.MIN_VALUE;
        return this.sequence.deltaBeneficeSuppressionSequence(this.debutSequenceI, this.finSequenceI);
    }

    @Override
    protected boolean doMouvement() {
        return false;
    }
    
    public SuppressionIntra() {
        super();
    }

    public SuppressionIntra(SchemaEchange sequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence, debutSequenceI, finSequenceI, debutSequenceJ, finSequenceJ);
    }
    
}
