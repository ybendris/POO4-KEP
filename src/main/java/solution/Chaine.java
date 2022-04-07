/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.DonneurAltruiste;
import instance.Paire;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Valek
 */
public class Chaine extends SchemaEchange {

    private DonneurAltruiste donneurAlt;
    
    @Override
    protected int evalCoutBenefice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Chaine(DonneurAltruiste donneurAlt, int coutBenefice, LinkedList<Paire> paire) {
        super(coutBenefice, paire);
        this.donneurAlt = donneurAlt;
    }

    public DonneurAltruiste getDonneurAlt() {
        return donneurAlt;
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }

    public LinkedList<Paire> getPaires() {
        return paires;
    }

    
    
    @Override
    public String toString() {
        return "Chaine{ Benefice = "+ this.coutBenefice + "donneurAlt=" + donneurAlt + "Paires : " + this.paires + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
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
        final Chaine other = (Chaine) obj;
        return Objects.equals(this.donneurAlt, other.donneurAlt);
    }

   
    
    
    
    
    
    
    
    
    
    
    
}
