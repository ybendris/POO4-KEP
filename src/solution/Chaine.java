/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;


import instance.reseau.DonneurAltruiste;
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
public class Chaine extends SchemaEchange {

    private DonneurAltruiste donneurAltruiste;
    
    @Override
    protected int evalCoutBenefice() {
        int benefice = 0;
        int i=0;
        benefice = donneurAltruiste.getBeneficeVers(this.paires.getFirst());
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(i);
            if(nextPaire!=null) benefice += p.getBeneficeVers(nextPaire);
            i++;
        }
        System.out.println("\nBenef = " + benefice);
        return benefice;
    }


    public Chaine(DonneurAltruiste donneurAlt) {
        super();
        this.donneurAltruiste = donneurAlt;
    }
    
    public Chaine(DonneurAltruiste donneurAlt, int tailleMax) {
        super();
        this.donneurAltruiste = donneurAlt;
        this.tailleMax = tailleMax;
    }
    
    public Chaine(Chaine ch){
        this.coutBenefice = ch.coutBenefice;
        this.paires = ch.paires;
        this.tailleMax = ch.tailleMax;
        this.donneurAltruiste = ch.donneurAltruiste;
    }

    public DonneurAltruiste getDonneurAlt() {
        return donneurAltruiste;
    }

    public int getCoutBenefice() {
        return coutBenefice;
    }
    
    @Override
    public String toString() {
        return "Chaine{"+
            "\n\tBenefice = "+ this.coutBenefice +
            "\n\tTailleMaxChaine = "+ this.tailleMax +
            "\n\tdonneurAltruiste = " + donneurAltruiste +
            "\n\tPaires = " + this.paires +
        "\n}";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.donneurAltruiste);
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
        final Chaine other = (Chaine) obj;
        return Objects.equals(this.donneurAltruiste, other.donneurAltruiste);
    }

    @Override
    public boolean check(){
        return verifTailleChaine() && verifBenefice();
    }
    
    private boolean verifTransplantation(){
        if(!this.donneurAltruiste.peutDonnerA(this.paires.getFirst()))
            return false;
        
        int i=0;
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(i);
            if(nextPaire != null){
                if(!p.peutDonnerA(nextPaire))
                    return false;
                i++;
            }
        }
        return true;
    }
    
    /**
     * Vérifie la taille max et min d'une chaine (prend en compte le donneur -> +1)
     * 
     * @return 
     */
    private boolean verifTailleChaine() {
        if(!(this.paires.size()+1 <= this.tailleMax)){
            System.out.println("Erreur: La chaine dépasse la taille max ! "+ this);
            return false;
        }
        if(!(this.paires.size()+1 >= 2)){
            System.out.println("Erreur: La chaine doit etre au minimum de taille 2 "+ this);
            return false;
        }
        
        return true;
    }
    
    /**
     * Vérifie le calcul du bénéfice d'une chaine + vérifie les -1
     * @return 
     */
    private boolean verifBenefice() {
        var beneficeAverif = this.coutBenefice;
        var beneficeReel = 0;
        int i=0;
        
        if(this.donneurAltruiste.getBeneficeVers(this.paires.getFirst()) != -1)
            beneficeReel = this.donneurAltruiste.getBeneficeVers(this.paires.getFirst());
        else 
            return false;
        
        for(Paire p : this.paires){
            Paire nextPaire = this.getNextPaire(i);
            if(nextPaire != null){
                if(p.getBeneficeVers(nextPaire) != -1)
                    beneficeReel += p.getBeneficeVers(nextPaire);
                else{
                    System.out.println("Erreur Chaine : y a un -1");
                     return false;
                }
                   
            }
            i++;
        }
        
        if(beneficeAverif == beneficeReel){
            return true;
        }
        System.out.println("Erreur: le bénéfice de la Chaine est mal calculé"
                + "\n => beneficeAverif = " + beneficeAverif
                        + " // beneficeReel = " + beneficeReel);
        return false;
    }
    
    public boolean ajouterPaireChaine(Paire paireToAdd){
        if(paireToAdd != null && this.getNbPaires()<this.tailleMax){
            this.paires.addLast(paireToAdd);
            this.coutBenefice=this.evalCoutBenefice();
            return true;
        }
        else{
            return false;
        }
    }
    
    public int getNbPaires(){
        return this.paires.size();
    }
    
    public static void main(String[] args) {
        DonneurAltruiste Altruiste1 = new DonneurAltruiste(1);
        Chaine ch1 = new Chaine(Altruiste1);
        Paire p1 = new Paire(1);
        Paire p2 = new Paire(2);
        ch1.paires.add(p1);
        ch1.paires.add(p2);
        
        System.out.println(ch1.toString());
    }

    
    /**
     * Donne le bénéfice si on veut ajouter une Paire Patient-Donneur à une position donnée dans la liste des paires de la chaine
     * Donne aussi le deltaBenefice dans le cas ou on veut ajouter à la fin de la chaine
     * @param position
     * @param clientToAdd
     * @return 
     */
    @Override
    public int deltaBeneficeInsertion(Paire paireToAdd, int position){
        if(!this.isPositionInsertionValide(position) || paireToAdd == null){
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(position);
        
        if(position >= this.getNbPaires()){ //Si on veut insérer à la fin de la chaine
           
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(nPrec.getBeneficeVers(paireToAdd) == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        else{
            Paire nCour = this.getCurrent(position);
             
            deltaBenefice -= nPrec.getBeneficeVers(nCour);
            
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
            
            benefice = paireToAdd.getBeneficeVers(nCour);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        return deltaBenefice;
    }
    
    
    

    @Override
    public Noeud getPrec(int position) {
        if(position == 0) return this.donneurAltruiste;
        return this.paires.get(position-1);
    }

    @Override
    public Paire getCurrent(int position) {
        return this.paires.get(position);
    }

    @Override
    public Noeud getNext(int position) {
        return this.paires.get(position+1);
    }

    @Override
    public int deltaBeneficeInsertionSeq(LinkedList<Paire> pairesToAdd, int positionI) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    /**
     * Potentiellement ya de la place dans la chaine
     * @param paireToInsert
     * @return 
     */
    public boolean insertionPairePossible(Paire paireToInsert) {
        if(this.getNbPaires()+1 >= this.tailleMax) return false;
        return true;             
    }

    /**
     * Vérifie les paramètres pour le remplacement inter-sequences, 
     * renvoie Integer.MIN_VALUE s'il y a un problème, on calcule sinon
     * @param debutSequenceI
     * @param finSequenceI
     * @param pairesSequenceJ
     * @return 
     */
    @Override
    public int deltaBeneficeRemplacementInter(int debutSequenceI, int finSequenceI, LinkedList<Paire> pairesSequenceJ) {
        if(debutSequenceI > finSequenceI){
            return Integer.MIN_VALUE;
        }
        
        LinkedList<Paire> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI);
        
        //Attention ca ou pairesSequenceJ est vide (suppression)
        //Insertion si finI - debutI == 1 (Insertion)
        //Echange
        if(pairesSequenceI == null){
            return Integer.MIN_VALUE;
        }
         if(pairesSequenceJ == null){
            return Integer.MIN_VALUE;
        }
        
        if(this.getNbPaires() - pairesSequenceI.size() + pairesSequenceJ.size() > this.tailleMax){
            return Integer.MIN_VALUE;
        }
        
        
        return deltaBeneficeRemplacement(debutSequenceI,finSequenceI,pairesSequenceJ);
    }
    
    
    
    
    
   

    /**
     * Renvoie le benefice engendré par le remplacement de la chaine entre debutSequenceI et finSequenceI
     * par la sequence pairesSequenceJ
     * @param debutSequenceI
     * @param finSequenceI
     * @param pairesSequenceJ
     * @return 
     */
    private int deltaBeneficeRemplacement(int debutSequenceI, int finSequenceI, LinkedList<Paire> pairesSequenceJ) {
        int deltaBenefice = 0;
        //Attention ca ou pairesSequenceJ est vide (suppression)
        //Insertion si finI - debutI == 1 (Insertion)
        //Echange
        LinkedList<Paire> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI);

        Noeud nPrecSeqI = this.getPrec(debutSequenceI); //2
        
        
       
        
        Noeud nFirstSeqI = pairesSequenceI.getFirst(); //3
        Noeud nLastSeqI = pairesSequenceI.getLast(); //4
        int benefice =0;
        //Suppression
        if(pairesSequenceJ.size() == 0){
            if(finSequenceI >= this.getNbPaires()){ // supprimer la fin
                Noeud nNextSeqI = this.getNext(finSequenceI); //5
                
                benefice = nPrecSeqI.getBeneficeVers((Paire)nNextSeqI);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                
                deltaBenefice-= nPrecSeqI.getBeneficeVers((Paire)nFirstSeqI);
                deltaBenefice-= this.getBeneficeSequence(pairesSequenceI);
                deltaBenefice-= nLastSeqI.getBeneficeVers((Paire)nNextSeqI);
            }
            else{
                deltaBenefice-= nPrecSeqI.getBeneficeVers((Paire)nFirstSeqI);
                deltaBenefice-= this.getBeneficeSequence(pairesSequenceI);
            }
        }
        else{
            
            Noeud nFirstSeqJ = pairesSequenceJ.getFirst(); //8
            Noeud nLastSeqJ = pairesSequenceJ.getLast(); //10
            //Insertion
            if(finSequenceI - debutSequenceI == 1){
                deltaBenefice-= nFirstSeqI.getBeneficeVers((Paire)nLastSeqI);

                benefice = nFirstSeqI.getBeneficeVers((Paire)nFirstSeqJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                benefice = this.getBeneficeSequence(pairesSequenceJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                benefice = nLastSeqJ.getBeneficeVers((Paire)nLastSeqI);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
            }
            //Echange
            else{
                Noeud nNextSeqI = this.getNext(finSequenceI); //5
                nPrecSeqI = this.getCurrent(debutSequenceI); //2
                nNextSeqI = this.getCurrent(finSequenceI); //5

                pairesSequenceI = (LinkedList)this.paires.subList(debutSequenceI+1, finSequenceI); //3-4

                deltaBenefice-= nPrecSeqI.getBeneficeVers((Paire)nFirstSeqI);
                deltaBenefice-= this.getBeneficeSequence(pairesSequenceI);
                deltaBenefice-= nLastSeqI.getBeneficeVers((Paire)nNextSeqI);

                
                benefice = nPrecSeqI.getBeneficeVers((Paire)nFirstSeqJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                benefice = this.getBeneficeSequence(pairesSequenceJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                benefice = nLastSeqJ.getBeneficeVers((Paire)nNextSeqI);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
            }
        }

        return deltaBenefice;
    }

    /**
     * Ajout une paire à dernière position de liste de paire 
     * Permet l'ajout dans une chaine vide par exemple
     * @param paireToAdd
     * @return 
     */
    public boolean ajouterPaireFin(Paire paireToAdd){
        return ajouterPaire(paireToAdd,this.getNbPaires());
    }
    
    private boolean ajouterPaire(Paire paireToAdd, int position) {
       if(ajouterPairePossible(paireToAdd, position)){
            this.coutBenefice += this.deltaBeneficeInsertion(paireToAdd, position);
            this.paires.add(position, paireToAdd);
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Vérifie si la paire 'paireToAdd' peut être inséré avant la position 'position'
     * Vérifie la tailleMax de la séquence et les compatibilités
     * @param paireToAdd
     * @param position
     * @return 
     */
    public boolean ajouterPairePossible(Paire paireToAdd, int position){ 
        if(!(this.getNbPaires()+1 < this.tailleMax)) return false; // vérification taille max (L)
        if(paireToAdd == null) return false;
        
        if(position >= this.getNbPaires()){ // ajout à la fin
            Noeud nPrec = this.getPrec(position);
            
            return nPrec.peutDonnerA(paireToAdd);
        }
        else{
            Noeud nPrec = this.getPrec(position);
            Noeud nNext = this.getNext(position);
            return nPrec.peutDonnerA(paireToAdd) && paireToAdd.peutDonnerA((Paire)nNext);
        }
    }

    
}
