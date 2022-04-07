/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Paire;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Valek
 */
public class Cycle extends SchemaEchange{
    
    

    public Cycle(int coutBenefice) {
        super(coutBenefice);
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }

    
    @Override
    protected int evalCoutBenefice() {
        
        return 0;
    }
    
    
    @Override
    public String toString() {
        return "Cycle{ CoutBénéfice = " + this.coutBenefice + "Paires : " + this.paires + '}';
    }
    
    
    
}
