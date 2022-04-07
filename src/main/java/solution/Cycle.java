/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Paire;
import java.util.LinkedList;

/**
 *
 * @author Valek
 */
public class Cycle extends SchemaEchange{
    
    @Override
    protected int evalCoutBenefice() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Cycle(int coutBenefice, LinkedList<Paire> paire) {
        super(coutBenefice, paire);
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }

    public LinkedList<Paire> getPaires() {
        return paires;
    }
    
    
    
    @Override
    public String toString() {
        return "Cycle{ CoutBénéfice = " + this.coutBenefice + "Paires : " + this.paires + '}';
    }
    
    
    
}
