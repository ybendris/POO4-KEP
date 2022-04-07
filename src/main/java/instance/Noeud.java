/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance;

import java.util.HashMap;
import java.util.Map;

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
     * Ajoutez une transplantation dans l'ensemble des transplantations vers le noeud receveur
     * @param receveur
     * @param benefice 
     */
    public void ajouterTransplantations(Paire receveur, int benefice){
        Transplantation t = new Transplantation(this,receveur,benefice);
        this.transplantations.put(receveur,t);
    }
    
    
    /**
     * Définie si le noeud courant peut donner à la Paire receveur
     * @param receveur
     * @return boolean
     */
    public boolean peutDonnerA(Paire receveur){
        return this.getBeneficeVers(receveur) > -1;
    }

    /**
     * Définie si le benefice de la transplantation du noeud courant vers la Paire receveur
     * @param receveur
     * @return boolean
     */
    public int getBeneficeVers(Paire receveur){
        Transplantation t = this.transplantations.get(receveur);
        if(t!=null){
            return t.getBenefice();
        }
        else{
            return -1;
        }
    }
    
    
    
    
    
}
