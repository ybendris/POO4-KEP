/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance.reseau;

import instance.reseau.Noeud;

/**
 *
 * @author yanni
 */
public class Paire extends Noeud {

    

    public Paire(int id) {
        super(id);
    }
    
    @Override
    public String toString() {
        return "Paire{" + "id=" + id + '}';
    }
    
    public static void main(String[] args) {
        Paire p1 = new Paire(1);
        
        System.out.println(p1);
    }
    
}
