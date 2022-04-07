/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance.reseau;

import instance.reseau.Paire;
import instance.reseau.Noeud;

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
        return "Transplantation{\n" + 
                "benefice=" + benefice + 
                ",\nreceveur=" + receveur + 
                ", \ndonneur=" + donneur + 
            '}';
    }

    public int getBenefice() {
        return benefice;
    }
    
    
    public static void main(String[] args) {
        DonneurAltruiste d1 = new DonneurAltruiste(1);
        Paire p1 = new Paire(2);
        Paire p2 = new Paire(3);
        Paire p3 = new Paire(4);
        
        d1.ajouterTransplantation(p1, 10);
        p3.ajouterTransplantation(p2, 2);
        p2.ajouterTransplantation(p3, 3);
        
        System.out.println(d1.getBeneficeVers(p1)); //10
        System.out.println(d1.getBeneficeVers(p2)); //-1
        
        System.out.println(p3.getBeneficeVers(p2)); //2
        System.out.println(p2.getBeneficeVers(p3)); //3
        
        System.out.println(p1.getBeneficeVers(p1)); //-1: se donne à lui même
        
        System.out.println(d1);
        System.out.println(p1);
        
        
    }
    
}
