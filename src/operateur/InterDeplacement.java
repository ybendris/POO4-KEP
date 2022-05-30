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
public class InterDeplacement extends OperateurInterSequences{

    public InterDeplacement() {
        super();
    }

    public InterDeplacement(SchemaEchange sequence, SchemaEchange autreSequence, int debutSequenceI, int debutSequenceJ, int finSequenceI, int finSequenceJ) {
        super(sequence, autreSequence, debutSequenceI, debutSequenceJ,finSequenceI,finSequenceJ);
    }
    

    @Override
    protected int evalDeltaBenefice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected boolean doMouvement() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected int evalDeltaCoutTournee() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected int evalDeltaCoutAutreTournee() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
