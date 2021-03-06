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
import operateur.InterEchange;
import operateur.InterDeplacement;
import operateur.ListeTabou;
import operateur.OperateurInterSequences;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;

/**
 *
 * @author Valek
 */
public abstract class SchemaEchange {
    protected int coutBenefice;
    protected int tailleMax;
    protected LinkedList<Noeud> paires;

    public SchemaEchange() {
        this.coutBenefice = 0;
        this.tailleMax = 0;
        this.paires = new LinkedList<>();
    }
    
    

    public int getCoutBenefice() {
        return coutBenefice;
    }
    
    public Noeud getNextPaire(int index){
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
    
    public Noeud getFirstPaire(){
        //System.out.println("\nPaires First" + this.paires);
        return this.paires.getFirst();
    }
    
    //this.paires.subList(debutSequenceI, finSequenceI +1)
    public LinkedList<Noeud> convertToLinkedList(int debut, int fin){
        LinkedList<Noeud> noeudsSubList = new LinkedList<Noeud>();
        int index;
        for(index=debut; index != fin%this.getNbNoeud(); index = (index + 1) % this.getNbNoeud()){
            noeudsSubList.add(this.getCurrent(index));
        }
        noeudsSubList.add(this.getCurrent(index));
        
        return noeudsSubList;
    }
    
    
    public int getBeneficeSequence(LinkedList<Noeud> pairesToAdd){
        int benefice = 0;
        for(int i=0; i < pairesToAdd.size(); i++){
            Noeud current = pairesToAdd.get(i);
            if(!(i == pairesToAdd.size() -1)){
                Noeud next = pairesToAdd.get(i+1);
                benefice += current.getBeneficeVers(next);
            }
        }
        return benefice;
    }

    public LinkedList<Noeud> getPaires() {
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
    
    public boolean doInsertion(InsertionPaire infos){
        if(infos == null) return false;
        if(!infos.isMouvementRealisable()) return false;
        
        Paire paireToAdd = infos.getPaireToInsert();
        int position = infos.getPosition();
        /**
         * Ajoute le client ?? la position indiqu?? par l'op??rateur d'insertion
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
    
    private int getNbPaires() {
        return this.paires.size();
    }
    
    /**
     * Retourne le meilleur op??rateur d'insertion d'une paire
     * @param paireToInsert
     * @return 
     */
    InsertionPaire getMeilleureInsertion(Paire paireToInsert) {
        InsertionPaire meilleur = new InsertionPaire();
        if(!this.insertionPairePossible(paireToInsert)) 
            return meilleur;//return d'une valeur par d??faut
        
        
        
        for(int pos = 0; pos<=this.paires.size(); pos++){
            InsertionPaire courrant = new InsertionPaire(this, paireToInsert, pos);
            if(courrant.isMeilleur(meilleur))
                meilleur = courrant;
        }
        
        return meilleur;
    }
    
    public abstract boolean check();
    protected abstract int evalCoutBenefice();
    
    public abstract boolean replacePaires(int debut, int fin, LinkedList<Noeud> pairesToAdd);
    
    
    public abstract boolean insertionPairePossible(Paire paireToInsert);
    protected abstract boolean isPositionInsertionValide(int position);
    protected abstract boolean isPositionSuppressionValide(int position);
    public abstract boolean doRemplacement(InterDeplacement infos);
    public abstract boolean doEchange(InterEchange infos);

    
    public abstract Noeud getPrec(int position);
    public abstract Noeud getCurrent(int position);
    public abstract Noeud getNext(int position);
    
    public abstract int deltaBeneficeInsertionPaire(Paire paireToAdd,int position);
    
    public abstract int deltaBeneficeSuppressionSequence(int debut, int fin);
    public abstract int deltaBeneficeInsertionSequence(LinkedList<Noeud> pairesToAdd, int debut, int fin);
    
    public abstract int getNbNoeud();
    
    public abstract boolean insertSequenceAtPos(LinkedList<Noeud> pairesToAdd, int position);
    
    
    
        
    
    OperateurLocal getMeilleurOperateurInter(SchemaEchange autreSequence, TypeOperateurLocal type) {
        OperateurLocal best = OperateurLocal.getOperateur(type);
        ListeTabou liste = ListeTabou.getInstance(); 
        if(!this.equals(autreSequence)) {
            
            for(int debutI=0; debutI<this.getNbNoeud(); debutI++) {
                for(int finI=debutI; finI<this.getNbNoeud(); finI++) {
                    
                    for(int debutJ=0; debutJ<autreSequence.getNbPaires(); debutJ++) {
                        for(int finJ=debutJ; finJ<autreSequence.getNbPaires(); finJ++) {
                            OperateurInterSequences op = OperateurLocal.getOperateurInter(type, this, autreSequence, debutI, finI , debutJ, finJ);
                            //System.out.println(op);
                            if(op.isMeilleur(best) && !liste.isTabou(op)) {
                                best = op;
                            } 
                        } 
                    }
                    
                }
            }
        }
        
        return best;
    }
    
    public abstract int deltaBeneficeRemplacementInter(int debutSequenceI, int finSequenceI, LinkedList<Noeud> pairesSequenceJ);
    public abstract int deltaBeneficeRemplacement(int debutSequenceI, int finSequenceI, LinkedList<Noeud> pairesSequenceJ);

    
    
}
