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
    protected final int id;

    public Noeud() {
        this.id = 0;
    }

    public Noeud(int id) {
        this.id = id;
    }
    
    /**
     * Définie si le noeud courant peut donner à la Paire p
     * @param p
     * @return boolean
     */
    public boolean peutDonnerA(Paire p){
        return false;
    }

    
    
    
    
    
}
