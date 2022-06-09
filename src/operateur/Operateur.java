/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operateur;

import solution.SchemaEchange;

/**
 *
 * @author yannis
 */
public abstract class Operateur {
    protected SchemaEchange sequence;
    /**
     * Bénéfice supplémentaire apporté à la solution
     */
    protected int deltaBenefice;

    public Operateur(){
        this.deltaBenefice = Integer.MIN_VALUE;
    }
    
    public Operateur(SchemaEchange sequence) {
        this();
        this.sequence = sequence;
    }

    /**
     * N'engendre pas un deltaBenefice -infini, donc on vérifie le deltaBenefice
     * @return true si le mouvement de l'opérateur courant est réalisable
     * false sinon
     */
    public boolean isMouvementRealisable(){
        if(this.deltaBenefice <= Integer.MIN_VALUE){
            return false;
        }
        return true;
    }
    
    /**
     * On cherche à maximiser les bénéfices, donc deltaBenefice doit être positif
     * @return true si le mouvement de l'opérateur courant est améliorant
     * false sinon
     */
    public boolean isMouvementAmeliorant(){
        if(deltaBenefice > 0)
            return true;
        return false;
    }
    
    /**
     * Renvoie true si l'opérateur courant est strictement meilleur que l'opérateur passé en paramètre
     * @param op l'opérateur à comparer
     * @return true si l'opérateur courant est strictement meilleur que l'opérateur passé en paramètre
     * false sinon
     */
    public boolean isMeilleur(Operateur op){
        if(op == null){
            return true;
        }
        if(this.getDeltaBenefice() <  op.getDeltaBenefice()){
            return false;
        }
        return true;
    }

    /**
     * Implémente le mouvement de l'opérateur courant si il est réalisable 
     * @return 
     */
    public boolean doMouvementIfRealisable(){
        if(!this.isMouvementRealisable()){
            return false;
        }
        
        return this.doMouvement();
    }

    public int getDeltaBenefice() {
        return deltaBenefice;
    }
    
    public SchemaEchange getSequence() {
        return sequence;
    }

    @Override
    public String toString() {
        return "Operateur{" + "sequence=" + sequence + ", deltaBenefice=" + deltaBenefice + '}';
    }

   
    
 
    protected abstract int evalDeltaBenefice();
    protected abstract boolean doMouvement();
}
