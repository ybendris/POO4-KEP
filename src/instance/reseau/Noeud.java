/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance.reseau;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author yanni
 */
public abstract class Noeud {
    protected final int id;
    private Map<Paire, Transplantation> transplantations;
    
    public Noeud(int id) {
        this.id = id;
        this.transplantations = new HashMap<>();
    }
    
    /**
     * /**
     * Ajoutez une transplantation dans l'ensemble des transplantations 
     * vers le noeud receveur
     * @param receveur
     * @param benefice 
     */
    public void ajouterTransplantation(Paire receveur, int benefice){
        Transplantation t = new Transplantation(this,receveur,benefice);
        this.transplantations.put(receveur,t);
    }
    
    /**
     * Définie si le noeud courant peut donner à la Paire receveur
     * @param receveur
     * @return boolean
     */
    public boolean peutDonnerA(Noeud receveur){
        Transplantation t = this.transplantations.get(receveur);
        if(t!=null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Définie si le benefice de la transplantation du noeud courant 
     * vers la Paire receveur
     * @param receveur
     * @return boolean
     */
    public int getBeneficeVers(Noeud receveur){
        Transplantation t = this.transplantations.get(receveur);
        if(t!=null){
            return t.getBenefice();
        }
        else{
            return -1;
        }
    }
    

    public int getId() {
        return id;
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
        final Noeud other = (Noeud) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.transplantations, other.transplantations);
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
        
        System.out.println(d1.peutDonnerA(p1)); //true
        System.out.println(d1.peutDonnerA(p2)); //false
        
        System.out.println(d1);
        System.out.println(p1);
    }
    
    
}
