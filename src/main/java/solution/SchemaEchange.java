/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Paire;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Valek
 */
public abstract class SchemaEchange {
 
    protected int coutBenefice;
    protected int tailleMax;
    protected LinkedList<Paire> paires;


    public SchemaEchange() {
        this.coutBenefice = 0;
        this.tailleMax = 0;
        this.paires = new LinkedList<>();
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }
    
    public Paire getNextPaire(int index){
        System.out.println("Paires = " + this.paires);
        System.out.println("Taille = " + this.paires.size());
        System.out.println("Index = " + index);
        //System.out.println("id = " + idPaireCurrent);
        if((index+1)<this.paires.size()){
            System.out.println("Index ok");
            if(this.paires.get(index)!=null){
                System.out.println("C'est pas null");
                return this.paires.get(index+1);
            }
            return null;
        }
        else{
            System.out.println("C'est null");
            return null;
        }
    }
    
    public Paire getFirstPaire(){
        System.out.println("\nPaires First" + this.paires);
        return this.paires.getFirst();
    }

    public LinkedList<Paire> getPaires() {
        return paires;
    }
    
    /*
    public Paire getPaireById(int id){
        if(id >= this.getNbPaires()){
            return null;
        }
        return this.paires.get(id)
    }
    
    private int getNbPaires(){
        return this.paires.size();
    }
    
     public Client getClientByPosition(int pos){
        if(pos < 0 || pos >= this.getNbClients()){
            return null;
        }
        return this.clients.get(pos);
    }
    */
    protected abstract int evalCoutBenefice();

    @Override
    public String toString() {
        return "SchemaEchange{" + "coutBenefice=" + coutBenefice + ", paires=" + paires + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.coutBenefice;
        hash = 97 * hash + Objects.hashCode(this.paires);
        return hash;
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
        final SchemaEchange other = (SchemaEchange) obj;
        if (this.coutBenefice != other.coutBenefice) {
            return false;
        }
        return Objects.equals(this.paires, other.paires);
    }
    
    
    
    
}
