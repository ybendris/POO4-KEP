/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import java.util.LinkedList;

/**
 *
 * @author Valek
 */
public class Solution {
    
    
    private Instance instance;
    private int benefice;
    private LinkedList<Chaine> chaines;
    private LinkedList<Cycle> cycles;

    public Solution(Solution s) {
        this.benefice = s.benefice;
        this.instance = s.instance;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
        /*for(Chaine chaineToAdd : s.getChaines()){
            this.chaines.add(new Chaine(chaineToAdd));
        }
        for(Cycle cycleToAdd : s.getCycles()){
            this.cycles.add(new Cycle(cycleToAdd));
        }*/
    }

    public Solution(Instance i) {
        this.benefice = 0;
        this.instance = i;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
    }

    public int getBenefice() {
        return benefice;
    }

    public LinkedList<Chaine> getChaines() {
        return chaines;
    }

    public LinkedList<Cycle> getCycles() {
        return cycles;
    }
    
    public static void main(String[] args) {
        System.out.println("coucou");
    }
    
    
    
    
}
