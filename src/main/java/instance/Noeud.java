/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance;

/**
 *
 * @author yanni
 */
public abstract class Noeud {
    private final int id;

    public Noeud() {
        this.id = 0;
    }

    public Noeud(int id) {
        this.id = id;
    }
    
    public int peutDonnerA(Paire p){
        return -1;
    }
    
    
    
}
