/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance;

/**
 *
 * @author yanni
 */
public class Transplantation {
    private final Noeud donneur;
    private final Paire receveur;
    private final int benefice;
    

    public Transplantation(Noeud donneur, Paire receveur, int benefice) {
        this.donneur = donneur;
        this.receveur = receveur;
        this.benefice = benefice;
    }

    @Override
    public String toString() {
        return "Transplantation{\n" + "benefice=" + benefice + ",\nreceveur=" + receveur + ", \ndonneur=" + donneur + '}';
    }
    
    
    
}
