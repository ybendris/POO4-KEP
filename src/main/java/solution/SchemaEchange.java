/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Paire;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Valek
 */
public abstract class SchemaEchange {
 
    protected int coutBenefice;
    protected LinkedList<Paire> paires;

    public SchemaEchange(int coutBenefice, LinkedList<Paire> paire) {
        this.coutBenefice = 0;
        this.paires = this.getPaires();
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }
    
    public LinkedList<Paire> getPaires(){
        return new LinkedList<Paire>(paires);
    }
    
    public Paire getPaireById(int idPaire){ 
        return this.paires.get(idPaire);
    }
    
    protected abstract int evalCoutBenefice();

    @Override
    public String toString() {
        return "SchemaEchange{" + "coutBenefice=" + coutBenefice + ", paires=" + paires + '}';
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SchemaEchange other = (SchemaEchange) obj;
        if (this.coutBenefice != other.coutBenefice) {
            return false;
        }
        return Objects.equals(this.paires, other.paires);
    }
    
    
    
    
}
