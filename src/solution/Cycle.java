/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.Noeud;
import instance.reseau.Paire;
import io.InstanceReader;
import io.exception.ReaderException;
import java.util.Iterator;
import java.util.LinkedList;
import operateur.InsertionPaire;
import operateur.InterRemplacement;
import solveur.CycleDe2;

/**
 *
 * @author Valek
 */
public class Cycle extends SchemaEchange{

    @Override
    protected int evalCoutBenefice() {
        int benefice = 0;
        int i=0;
        for(Noeud p : this.paires){
            //System.out.println("GET NEXT PAIRE ");
            Noeud nextPaire = this.getNextPaire(i);
            //System.out.println("\nAprès nextPaire");
            if(nextPaire!=null) benefice += p.getBeneficeVers(nextPaire);
            else{
                //System.out.println("Recup First");
                nextPaire = this.getFirstPaire();
                //System.out.println("Somme benefice");
                benefice += p.getBeneficeVers(nextPaire);
            }
            i++;
        }
        //System.out.println("BENEF du cycle = " + benefice);
        return benefice;
    }

    public Cycle() {
        super();
    }
    
    public Cycle(int tailleMax){
        super();
        this.tailleMax = tailleMax;
    }
    
    public Cycle(Cycle c){
        this.coutBenefice = c.coutBenefice;
        this.paires = c.paires;
        this.tailleMax = c.tailleMax;
    }

    @Override
    public int getCoutBenefice() {
        return coutBenefice;
    }

    
    @Override
    public boolean check(){
        return verifTailleCycle() && verifBenefice();
    }
    
    /**
     * Vérifie la taille max et min d'un cycle
     * @return 
     */
    private boolean verifTailleCycle() {

        if(!(this.paires.size() <= this.tailleMax)){
            System.out.println("Erreur: Le cycle dépasse la taille max ! "+ this);
            return false;
        }
        if(!(this.paires.size() >= 2)){
            System.out.println("Erreur: Le cycle doit etre au minimum de taille 2 "+ this);
            return false;
        }
        return true;

    }
    
    /**
     * Vérifie le bénéfice d'un cycle
     * @return 
     */
    private boolean verifBenefice() {
        var beneficeAverif = this.coutBenefice;
        var beneficeReel = 0;
        
        int i=0;
        for(Noeud p : this.paires){
            Noeud nextPaire = this.getNextPaire(i);
            int beneficeToAdd;
            if(nextPaire != null){
                beneficeToAdd = p.getBeneficeVers(nextPaire);
            }
            else{
                beneficeToAdd = p.getBeneficeVers(this.paires.getFirst());
            }
            if(beneficeToAdd != -1){
                beneficeReel += beneficeToAdd;
            }
            else{
                System.out.println("Erreur Cycle : y a un -1");
                System.out.println("id paire = " + p.getId());
                return false;
            }
            i++;
        }
        
        if(beneficeAverif == beneficeReel){
            return true;
        }
        System.out.println("Erreur: le bénéfice du cycle est mal calculé("+ beneficeAverif +" , "+beneficeReel+")");
        return false;
    }
    
    
    private boolean verifTransplantation() {
        int i=0;
        for(Noeud p : this.paires){
            Noeud nextPaire = this.getNextPaire(i);
            if(nextPaire == null)
                nextPaire = this.paires.getFirst();
            
            if(!p.peutDonnerA(nextPaire))
                return false;
 
            i++;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Cycle{"+
            "\n\tBenefice = "+ this.coutBenefice +
            "\n\tTailleMaxCycle = "+ this.tailleMax +
            "\n\tPaires = " + this.paires +
        "\n}";
    }
    
   
    
    public int getNbPaires(){
        return this.paires.size();
    }
    
    public static void main(String[] args) {
        /*Cycle c1 = new Cycle();
        Cycle c2 = new Cycle(10);
        Paire p1 = new Paire(1);
        Paire p2 = new Paire(2);
        c1.paires.add(p1);
        c1.paires.add(p2);
        System.out.println("Cycle 1 : " + c1);
        System.out.println("Cycle 2 : " + c2);*/
        
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/testInstance.txt");
            Instance i = read.readInstance();
            Cycle c3 = new Cycle(3);
            c3.ajouterPaireFin(i.getPaireById(4));
            c3.ajouterPaireFin(i.getPaireById(6));
            System.out.println("eval: "+c3.evalCoutBenefice());
            
            System.out.println(c3);
            
            System.out.println(c3.check());
            
            /*System.out.println("Solution = " + s);
            System.out.println("sC2 check: " +s.check());*/
             
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
    * Note: ne vérifie pas la possibilité les transplantations
    * @param paireToAdd
    * @return 
    */
    public boolean ajouterPaireAuCycle(Paire paireToAdd){
        if(paireToAdd != null && this.getNbPaires()<this.tailleMax){
            this.paires.addLast(paireToAdd);
            return true;
        }
        else{
            return false;
        }
    }
    
    public int deltaBeneficeInsertionFin(Paire paireToAdd){
        return deltaBeneficeInsertionPaire(paireToAdd, this.getNbPaires());
    }
    
    public boolean ajouterPairePossibleFin(Paire paireToAdd){
        return this.ajouterPairePossible(paireToAdd, this.getNbPaires());
    }
    
    
    
    /**
     * Ajout une paire à dernière position de liste de paire 
     * Permet l'ajout dans un cycle vide par exemple
     * @param paireToAdd
     * @return 
     */
    public boolean ajouterPaireFin(Paire paireToAdd){
        return ajouterPaire(paireToAdd,this.getNbPaires());
    }
        
    
    /**
     * Si l'ajout d'une paire à une position donnée est réalisable alors on réalise l'ajout
     * @param paireToAdd
     * @param position
     * @return 
     */
    public boolean ajouterPaire(Paire paireToAdd, int position){
        if(ajouterPairePossible(paireToAdd, position)){
            this.coutBenefice += this.deltaBeneficeInsertionPaire(paireToAdd, position);
            this.paires.add(position, paireToAdd);
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     * Vérifie si la paire 'paireToAdd' peut être inséré dans le cycle avant la position 'position'
     * Vérifie la tailleMax de la séquence et les compatibilités
     * @param paireToAdd
     * @param position
     * @return 
     */
    public boolean ajouterPairePossible(Paire paireToAdd, int position){ 
        if(!(this.getNbPaires() < this.tailleMax)) return false; // vérification taille max (K)
        if(paireToAdd == null) return false;
        
        if(this.paires.isEmpty()) {
            return true;
        }
        else{
            Noeud nPrec = this.getPrec(position);
            Noeud nCour = this.getCurrent(position);
            return nPrec.peutDonnerA(paireToAdd) && paireToAdd.peutDonnerA(nCour);
        }
    }
    
    /**
     * Donne le bénéfice si on veut ajouter une Paire Patient-Donneur à une position donnée dans la liste des paires du cycle
     * @param position
     * @param clientToAdd
     * @return 
     */
    @Override
    public int deltaBeneficeInsertionPaire(Paire paireToAdd, int position) {
        if(!this.isPositionInsertionValide(position) || paireToAdd == null){
            System.out.println("Integer.MIN_VALUE");
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        if(this.paires.isEmpty()){
            return 0;
        }
        else if(this.getNbPaires() == 1){
            Noeud nPrec = this.getPrec(position);
            
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
            
            
            benefice = paireToAdd.getBeneficeVers(nPrec);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        else{
            Noeud nPrec = this.getPrec(position);
            Noeud nCour = this.getCurrent(position);
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
    
    /**
     * Donne le bénéfice si on veut ajouter une séquence de Paire Patient-Donneur entre deux positions donnée
     * La liste de paires est ordonnée et les transplantations sont réalisables
     * @param pairesToAdd
     * @param positionDebut 
     * @param positionFin
     * @return 
     */
    @Override
    public int deltaBeneficeInsertionSequence(LinkedList<Noeud> pairesToAdd, int debut, int fin) {
        if(!this.isPositionInsertionValide(debut)){
            System.out.println("isPositionInsertionValide(debut");
            return Integer.MIN_VALUE;
        }
        if(!this.isPositionInsertionValide(fin)){
            System.out.println("isPositionInsertionValide(fin");
            return Integer.MIN_VALUE;
        }
        if(pairesToAdd == null){
            System.out.println("null");
            return Integer.MIN_VALUE;
        }
        if(debut == fin){
            System.out.println("On peut pas faire ça debut == fin");
            return Integer.MIN_VALUE;
        }
        
        int deltaBenefice = 0;
        int benefice = 0;
        
        Noeud firstPaireI = getCurrent(debut);
        Noeud lastPaireI = getCurrent(fin);
        Noeud firstPaireJ = pairesToAdd.getFirst();
        Noeud lastPaireJ = pairesToAdd.getLast();
        
        System.out.println(firstPaireI.getId());
        System.out.println(lastPaireI.getId());
        System.out.println(firstPaireJ.getId());
        System.out.println(lastPaireJ.getId());
        
        
         benefice = firstPaireI.getBeneficeVers((Paire) firstPaireJ);
        if(benefice == -1) {
            System.out.println(firstPaireI.getId()+"->"+firstPaireJ.getId());
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
        benefice = this.getBeneficeSequence(pairesToAdd);
        if(benefice == -1) {
            System.out.println("Mauvais paires to add");
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
        benefice = lastPaireJ.getBeneficeVers((Paire) lastPaireI);
        if(benefice == -1) {
            System.out.println(lastPaireJ.getId()+"->"+lastPaireI.getId());
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
        
        LinkedList<Noeud> toDelete = this.convertToLinkedList(debut, fin);
        deltaBenefice -= this.getBeneficeSequence(toDelete);
        
        
        return deltaBenefice;
    }
    
   
    
    /**
     * Renvoie la paire du cycle qui précède la position 'position'
     *  si 'position' correspond à la position de la première paire du cycle alors on renvoie la dernière paire
     * @param position
     * @return 
     */
    @Override
    public Noeud getPrec(int position) {
        if(position == 0) return this.paires.getLast();
        return this.paires.get(position-1);
    }

    /**
     * Renvoie la paire du cycle qui correspond à la position 'position'
     *  si 'position' est égal au nombre de paire alors on renvoie la première paire
     * @param position
     * @return 
     */
    @Override
    public Noeud getCurrent(int position) {
        if(position == this.getNbPaires()) return this.paires.getFirst();
        return this.paires.get(position);
    }

    /**
     * Renvoie la paire du cycle qui succède la position 'position'
     *  si 'position' correspond à la position de la dernière paire du cycle (ou plus) alors on renvoie la première paire
     * @param position
     * @return 
     */
    @Override
    public Noeud getNext(int position) {
        if(position >= this.getNbPaires()-1) return this.paires.getFirst();
        return this.paires.get(position+1);
    }

    
    /**
     * Potentiellement ya de la place dans le cycle
     * @param paireToInsert
     * @return 
     */
    public boolean insertionPairePossible(Paire paireToInsert) {
        if(this.getNbPaires() >= this.tailleMax) return false;
        return true;             
    }

    /*@Override
    public int deltaBeneficeRemplacementInter(int debutSequenceI, int finSequenceI, LinkedList<Paire> pairesSequenceJ) {
        LinkedList<Paire> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI );
        
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
       
        
        return deltaBeneficeRemplacement(debutSequenceI, finSequenceI, pairesSequenceJ);
    }*/

    public int deltaBeneficeRemplacement(int debutSequenceI, int finSequenceI, LinkedList<Noeud> pairesSequenceJ) {
        int deltaBenefice = 0;
        //Attention ca ou pairesSequenceJ est vide (suppression)
        //Insertion si finI - debutI == 1 (Insertion)
        //Echange
        LinkedList<Noeud> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI );

        Noeud nPrecSeqI = this.getPrec(debutSequenceI); //2
        
        
       
        
        Noeud nFirstSeqI = pairesSequenceI.getFirst(); //3
        Noeud nLastSeqI = pairesSequenceI.getLast(); //4
        int benefice = 0;
        
        if(pairesSequenceJ.size() == 0){
            return deltaBeneficeSuppressionSequence(debutSequenceI, finSequenceI);
        }
        else{
            
            Noeud nFirstSeqJ = pairesSequenceJ.getFirst(); //8
            Noeud nLastSeqJ = pairesSequenceJ.getLast(); //10
            //Insertion
            if(Math.abs(finSequenceI - debutSequenceI) == 1){
                
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

    @Override
    public int deltaBeneficeSuppressionSequence(int debut, int fin) {
        if(!this.isPositionSuppressionValide(debut) || !this.isPositionSuppressionValide(fin)){
            System.out.println("position suppression cycle pas valide");
            return Integer.MIN_VALUE;
            
        }
        LinkedList<Noeud> pairesToSupp = this.convertToLinkedList(debut, fin);
        /**
         * Permet de garder un cycle correct
         */
        if(this.getNbPaires() - pairesToSupp.size()<2){
            System.out.println("cycle taille <2");
            return Integer.MIN_VALUE;
        }

        int deltaCout = 0;
        int benefice;
        
        Noeud nPrec = this.getPrec(debut);
        Noeud nNext = this.getNext(fin);
        
        benefice = nPrec.getBeneficeVers((Paire)nNext);
        if(benefice == -1) {
            System.out.println("-1: " + nPrec.getId()+" "+nNext.getId());
            return Integer.MIN_VALUE;
        }
        deltaCout += benefice;
        
        //deltaCout += nPrec.getBeneficeVers((Paire)nNext);
        
        deltaCout -= nPrec.getBeneficeVers((Paire)pairesToSupp.getFirst());
        deltaCout -= this.getBeneficeSequence(pairesToSupp);
        deltaCout -= pairesToSupp.getLast().getBeneficeVers((Paire)nNext);
        
        
        return deltaCout;
    }

    @Override
    protected boolean isPositionInsertionValide(int position) {
        if(0 <= position && position <= this.getNbPaires()){
            return true;
        }
        return false;
    }

    @Override
    protected boolean isPositionSuppressionValide(int position) {
        if(0 <= position && position <= this.getNbPaires()){
            return true;
        }
        return false;
    }

    @Override
    public int getNbNoeud() {
        return this.getNbPaires();
    }

    @Override
    public boolean doRemplacement(InterRemplacement infos) {
        return false;
    }

    

    
    
    
    
}
