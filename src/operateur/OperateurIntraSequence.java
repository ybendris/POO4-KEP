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
public abstract class OperateurIntraSequence extends OperateurLocal{
    public OperateurIntraSequence() {
        super();
    }

    public OperateurIntraSequence(SchemaEchange sequence, int debutSequenceI, int finSequenceI, int debutSequenceJ, int finSequenceJ) {
        super(sequence,debutSequenceI, finSequenceI,debutSequenceJ,finSequenceJ);
        this.deltaBenefice = this.evalDeltaBenefice();
    }
}
