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
        //System.out.println("\nBenef = " + benefice);
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
    public int deltaBeneficeInsertionPaire(Paire paireToAdd, int position){
        if(!this.isPositionInsertionValide(position) || paireToAdd == null){
            //System.out.println("return min");
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(position);
        
        if(position > this.getNbPaires()){ //Si on veut insérer à la fin de la chaine
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(nPrec.getBeneficeVers(paireToAdd) == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        else{
            //System.out.println("insert milieu");
            Noeud nCour = this.getCurrent(position);
             
            deltaBenefice -= nPrec.getBeneficeVers((Paire)nCour);
            
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
            
            benefice = paireToAdd.getBeneficeVers((Paire)nCour);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        return deltaBenefice;
    }
    
    @Override
    public boolean doInsertion(InsertionPaire infos){
        if(infos == null) return false;
        if(!infos.isMouvementRealisable()) return false;
        
        Paire paireToAdd = infos.getPaireToInsert();
        int position = infos.getPosition();
        /**
         * Ajoute le client à la position indiqué par l'opérateur d'insertion
         */
        this.paires.add(position-1, paireToAdd);
        this.coutBenefice += infos.getDeltaBenefice(); //MAJ cout total
       
        
        if (!this.check()){
            System.out.println("Mauvaise insertion du client, "+paireToAdd);
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        return true;
    }
    
    
    /**
     * Renvoie le noeud de la chaine qui précède la position 'position'
     *  si 'position' correspond à la position de la première paire de la chaine alors on renvoie le donneur Altruiste
     *  si 'position' = 0 on renvoie null
     * @param position
     * @return 
     */
    @Override
    public Noeud getPrec(int position) {
        if(position == 0) return null;
        if(position == 1) return this.donneurAltruiste;
        return this.paires.get(position-2);
    }

    /**
     * Renvoie le noeud de la chaine qui correspond à la position 'position'
     *  si 'position' = 0 on renvoie le donneur altruiste
     * @param position
     * @return 
     */
    @Override
    public Noeud getCurrent(int position) {
        if(position == 0) return this.donneurAltruiste;
        return this.paires.get(position-1);
    }

    /**
     * Renvoie le noeud de la chaine qui succède la position 'position'
     *  si 'position' correspond à la position de la dernière paire du cycle (ou plus) alors on renvoie null
     * @param position
     * @return 
     */
    @Override
    public Noeud getNext(int position) {
        if(position >= this.getNbPaires()) return null;
        return this.paires.get(position);
    }

    /**
     * Renvoie le deltaBenefice associé au mouvement d'intertion d'une Liste de paire entre deux curseurs (exclus)
     * 
     * @param pairesToAdd
     * @param debut
     * @param fin
     * @return 
     */
    @Override
    public int deltaBeneficeInsertionSequence(LinkedList<Paire> pairesToAdd, int debut, int fin) {
        if(!this.isPositionInsertionValide(debut)){
            return Integer.MIN_VALUE;
        }
        if(!this.isPositionInsertionValide(fin)){
            return Integer.MIN_VALUE;
        }
        if(pairesToAdd == null){
            return Integer.MIN_VALUE;
        }
        
        
        return 50;
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
    
    
    
    
    
    public int deltaBenefice(int debut, int fin, LinkedList<Paire> pairesSequenceJ) {
        if(debut > fin){ //c'est une chaine on accepte pas ces curseurs
            return Integer.MIN_VALUE;
        }
        //On accepte entre 0 et nbPaire Inclus (pour insérer en fin de chaine
        if(!this.isPositionInsertionValide(debut) || !this.isPositionInsertionValide(fin)){
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(debut); 
        LinkedList<Paire> pairesToSupp = this.convertToLinkedList(debut, fin); // entre debut et fin inclus
        
        //System.out.println(pairesToSupp);
        
        if(fin == this.getNbPaires()){ // si on veut supprimer à la fin
            System.out.println("Insérer à la fin");
            //System.out.println(-nPrec.getBeneficeVers(pairesToSupp.getFirst()));
            //System.out.println(-this.getBeneficeSequence(pairesToSupp));
            deltaBenefice += nPrec.getBeneficeVers(pairesSequenceJ.getFirst());
            deltaBenefice += this.getBeneficeSequence(pairesSequenceJ);
        }
        else{
            Noeud nNext = this.getNext(fin);
            
            benefice = nPrec.getBeneficeVers((Paire)nNext);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
            
            deltaBenefice -= nPrec.getBeneficeVers(pairesToSupp.getFirst());
            deltaBenefice -= this.getBeneficeSequence(pairesToSupp);
            deltaBenefice -= pairesToSupp.getLast().getBeneficeVers((Paire)nNext);
        }
        
        
        return deltaBenefice;
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
        LinkedList<Paire> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI);
        Noeud nPrecSeqI = this.getPrec(debutSequenceI); //2
        
        System.out.println("debutSequenceI "+debutSequenceI);
        System.out.println("finSequenceI "+finSequenceI);
        System.out.println("pairesSequenceJ "+pairesSequenceJ);
        
        Noeud nFirstSeqI = pairesSequenceI.getFirst(); //3
        Noeud nLastSeqI = pairesSequenceI.getLast(); //4
        int benefice =0;
        //Suppression
        if(pairesSequenceJ.size() == 0){
            return deltaBeneficeSuppression(debutSequenceI, finSequenceI);
        }
        else{
            return 17;
            /*
            Noeud nFirstSeqJ = pairesSequenceJ.getFirst(); //8
            Noeud nLastSeqJ = pairesSequenceJ.getLast(); //10
            //Insertion
            if(Math.abs(finSequenceI - debutSequenceI) == 1){
                deltaBenefice-= nFirstSeqI.getBeneficeVers((Paire)nLastSeqI);

                benefice = nFirstSeqI.getBeneficeVers((Paire)nFirstSeqJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                //2
                benefice = this.getBeneficeSequence(pairesSequenceJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                //3
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
                
                //2
                benefice = this.getBeneficeSequence(pairesSequenceJ);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
                
                //3
                benefice = nLastSeqJ.getBeneficeVers((Paire)nNextSeqI);
                if(benefice == -1) return Integer.MIN_VALUE;
                deltaBenefice += benefice;
            }*/
        }

        //return deltaBenefice;
    }

    /**
     * Ajout une paire à dernière position de liste de paire 
     * Permet l'ajout dans une chaine vide par exemple
     * @param paireToAdd
     * @return 
     */
    public boolean ajouterPaireFin(Paire paireToAdd){
        return ajouterPaire(paireToAdd,this.getNbPaires()+1);
    }
    
    
    
    public boolean ajouterPaire(Paire paireToAdd, int position) {
       if(ajouterPairePossible(paireToAdd, position)){
            this.coutBenefice += this.deltaBeneficeInsertionPaire(paireToAdd, position);
            this.paires.add(position-1, paireToAdd);
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
        
        if(position > this.getNbPaires()){ // ajout à la fin
            Noeud nPrec = this.getPrec(position);
            return nPrec.peutDonnerA(paireToAdd);
        }
        else{
            Noeud nPrec = this.getPrec(position);
            Noeud nNext = this.getCurrent(position);
            return nPrec.peutDonnerA(paireToAdd) && paireToAdd.peutDonnerA((Paire)nNext);
        }
    }
    
    
    public LinkedList<Paire> convertToLinkedList(int debut, int fin){
        if(debut <= fin){
            return new LinkedList<Paire>(this.paires.subList(debut-1, fin));
        }
        
        LinkedList<Paire> pairesSubList = new LinkedList<Paire>();
        
        int index;
        for(index=debut; index != fin; index = (index + 1) % this.getNbPaires()){
            pairesSubList.add(this.paires.get(index));
        }
        pairesSubList.add(this.paires.get(index));
        
        return pairesSubList;
    }

    @Override
    public int deltaBeneficeSuppression(int debut, int fin) {
        if(debut > fin){
            return Integer.MIN_VALUE;
        }
        if(!this.isPositionSuppressionValide(debut) || !this.isPositionSuppressionValide(fin)){
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(debut); 
        LinkedList<Paire> pairesToSupp = this.convertToLinkedList(debut, fin);
        
        //System.out.println(pairesToSupp);
        
        if(fin == this.getNbPaires()){ // si on veut supprimer à la fin
            System.out.println("Supprimer à la fin");
            //System.out.println(-nPrec.getBeneficeVers(pairesToSupp.getFirst()));
            //System.out.println(-this.getBeneficeSequence(pairesToSupp));
            deltaBenefice -= nPrec.getBeneficeVers(pairesToSupp.getFirst());
            deltaBenefice -= this.getBeneficeSequence(pairesToSupp);
        }
        else{
            Noeud nNext = this.getNext(fin);
            /*System.out.println("Supprimer au milieu");
            System.out.println(nPrec);
            System.out.println(nNext);
            System.out.println(pairesToSupp);*/
            benefice = nPrec.getBeneficeVers((Paire)nNext);
            //System.out.println("benefice:"+benefice);
            if(benefice == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
            
            deltaBenefice -= nPrec.getBeneficeVers(pairesToSupp.getFirst());
            deltaBenefice -= this.getBeneficeSequence(pairesToSupp);
            deltaBenefice -= pairesToSupp.getLast().getBeneficeVers((Paire)nNext);
        }
        
        
        return deltaBenefice;
    }

    @Override
    protected boolean isPositionInsertionValide(int position) {
        if(0 < position && position <= this.getNbPaires()+1){
            return true;
        }
        return false;
    }

    @Override
    InsertionPaire getMeilleureInsertion(Paire paireToInsert) {
        InsertionPaire meilleur = new InsertionPaire();
        if(!this.insertionPairePossible(paireToInsert)) 
            return meilleur;//return d'une valeur par défaut
        
        
        
        for(int pos = 0; pos<=this.paires.size()+1; pos++){
            InsertionPaire courrant = new InsertionPaire(this, paireToInsert, pos);
            if(courrant.isMeilleur(meilleur))
                meilleur = courrant;
        }
        
        return meilleur;
    }
    
}
