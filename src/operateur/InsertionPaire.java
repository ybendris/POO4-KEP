/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

import instance.reseau.Paire;
import solution.SchemaEchange;

/**
 *
 * @author yanni
 */
public class InsertionPaire extends Operateur{
    private Paire paireToInsert;
    private int position;

    public InsertionPaire() {
        super();
    }

    /**
     * Met à jour correctement le cout de l'opérateur
     * @param tournee
     * @param clientToInsert
     * @param position 
     */
    public InsertionPaire(SchemaEchange sequence, Paire paireToInsert, int position) {
        super(sequence);
        this.paireToInsert = paireToInsert;
        this.position = position;
        this.deltaBenefice = this.evalDeltaBenefice();
    }
    
    @Override
    protected int evalDeltaBenefice() {
        if(sequence == null) return Integer.MIN_VALUE;
        return this.sequence.deltaBeneficeInsertionPaire(paireToInsert, position);
    }

    @Override
    protected boolean doMouvement() {
        return this.sequence.doInsertion(this);
    }

    @Override
    public String toString() {
        return "InsertionPaire{\n" + 
            "sequence=" + sequence + 
            "\n\tdeltaBenefice=" + deltaBenefice + 
            "\n\tpaireToInsert=" + paireToInsert + 
            "\n\tposition=" + position + 
        "\n}";
    }

    public Paire getPaireToInsert() {
        return paireToInsert;
    }

    public int getPosition() {
        return position;
    }

    public SchemaEchange getSequence() {
        return sequence;
    }

    public int getDeltaBenefice() {
        return deltaBenefice;
    }
    
    
    
}
