/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package solveur;

import instance.Instance;
import solution.Solution;



/**
 *
 * @author Valek
 */
public interface Solveur {
    public String getNom();
    public Solution solve(Instance instance);
    
}
