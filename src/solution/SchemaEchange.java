/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.reseau.Noeud;
import instance.reseau.Paire;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import operateur.InsertionPaire;

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
        //System.out.println("Paires = " + this.paires);
        //System.out.println("Taille = " + this.paires.size());
        //System.out.println("Index = " + index);
        //System.out.println("id = " + idPaireCurrent);
        if((index+1)<this.paires.size()){
            //System.out.println("Index ok");
            if(this.paires.get(index)!=null){
                //System.out.println("C'est pas null");
                return this.paires.get(index+1);
            }
            return null;
        }
        else{
            //System.out.println("C'est null");
            return null;
        }
    }
    
    public Paire getFirstPaire(){
        //System.out.println("\nPaires First" + this.paires);
        return this.paires.getFirst();
    }
    
    //this.paires.subList(debutSequenceI, finSequenceI +1)
    public LinkedList<Paire> convertToLinkedList(int debut, int fin){
        if(debut <= fin){
            return new LinkedList<Paire>(this.paires.subList(debut, fin+1));
        }
        
        LinkedList<Paire> pairesSubList = new LinkedList<Paire>();
        
        int index;
        for(index=debut; index != fin; index = (index + 1) % this.getNbPaires()){
            pairesSubList.add(this.paires.get(index));
        }
        pairesSubList.add(this.paires.get(index));
        
        return pairesSubList;
    }
    
    
    public int getBeneficeSequence(LinkedList<Paire> pairesToAdd){
        int benefice = 0;
        
        
        
        for(int i=0; i < pairesToAdd.size(); i++){
            Paire current = pairesToAdd.get(i);
            
            if(!(i == pairesToAdd.size() -1)){
                Paire next = pairesToAdd.get(i+1);
                benefice += current.getBeneficeVers(next);
            }
            
        }
        return benefice;
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
    
    
    public abstract int deltaBeneficeInsertionPaire(Paire paireToAdd,int position);
    public abstract int deltaBeneficeInsertionSequence(LinkedList<Paire> pairesToAdd, int debut, int fin);
    public abstract Noeud getPrec(int position);
    public abstract Noeud getCurrent(int position);
    public abstract Noeud getNext(int position);
    public abstract boolean check();
    public abstract int deltaBeneficeSuppression(int debut, int fin);
    
    
    public boolean doInsertion(InsertionPaire infos){
        if(infos == null) return false;
        if(!infos.isMouvementRealisable()) return false;
        
        Paire paireToAdd = infos.getPaireToInsert();
        int position = infos.getPosition();
        /**
         * Ajoute le client à la position indiqué par l'opérateur d'insertion
         */
        this.paires.add(position, paireToAdd);
        this.coutBenefice += infos.getDeltaBenefice(); //MAJ cout total
       
        
        if (!this.check()){
            System.out.println("Mauvaise insertion du client, "+paireToAdd);
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        return true;
    }
    

    protected abstract boolean isPositionInsertionValide(int position);
    
    protected boolean isPositionSuppressionValide(int position) {
        if(0 < position && position < this.getNbPaires()+1){
            return true;
        }
        return false;
    }

    private int getNbPaires() {
        return this.paires.size();
    }
    
    /**
     * Retourne le meilleur opérateur d'insertion d'une paire
     * @param paireToInsert
     * @return 
     */
    InsertionPaire getMeilleureInsertion(Paire paireToInsert) {
        InsertionPaire meilleur = new InsertionPaire();
        if(!this.insertionPairePossible(paireToInsert)) 
            return meilleur;//return d'une valeur par défaut
        
        
        
        for(int pos = 0; pos<=this.paires.size(); pos++){
            InsertionPaire courrant = new InsertionPaire(this, paireToInsert, pos);
            if(courrant.isMeilleur(meilleur))
                meilleur = courrant;
        }
        
        return meilleur;
    }

    
    public abstract boolean insertionPairePossible(Paire paireToInsert);

    public abstract int deltaBeneficeRemplacementInter(int debutSequenceI, int finSequenceI, LinkedList<Paire> pairesSequenceJ);
    
    
    
    
    
}
