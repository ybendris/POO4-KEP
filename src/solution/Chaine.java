/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;


import instance.reseau.DonneurAltruiste;
import instance.reseau.Noeud;
import instance.reseau.Paire;
import java.util.LinkedList;
import java.util.Objects;
import operateur.InsertionPaire;
import operateur.InterEchange;
import operateur.InterDeplacement;

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
        for(Noeud p : this.paires){
            Noeud nextPaire = this.getNextPaire(i);
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
        for(Noeud p : this.paires){
            Noeud nextPaire = this.getNextPaire(i);
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
        
        for(Noeud p : this.paires){
            Noeud nextPaire = this.getNextPaire(i);
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
     * position ne peut pas être 0 dans le cas d'une chaine
     * @param position
     * @param clientToAdd
     * @return 
     */
    @Override
    public int deltaBeneficeInsertionPaire(Paire paireToAdd, int position){
        if(!this.isPositionInsertionValide(position) || paireToAdd == null || position == 0){
            //System.out.println("return min");
            return Integer.MIN_VALUE;
        }
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(position);
        
        
        if(position > this.getNbPaires()){ //Si on veut insérer à la fin de la chaine
            //System.out.println("LA");
            benefice = nPrec.getBeneficeVers(paireToAdd);
            if(nPrec.getBeneficeVers(paireToAdd) == -1) return Integer.MIN_VALUE;
            deltaBenefice += benefice;
        }
        else{
            //System.out.println("insert milieu");
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
    public int deltaBeneficeInsertionSequence(LinkedList<Noeud> pairesToAdd, int debut, int fin) {
        if(!this.isPositionInsertionValide(debut)){
            //System.out.println("isPositionInsertionValide(debut");
            return Integer.MIN_VALUE;
        }
        if(!this.isPositionInsertionValide(fin)){
            //System.out.println("isPositionInsertionValide(fin");
            return Integer.MIN_VALUE;
        }
        if(pairesToAdd == null){
            //System.out.println("null");
            return Integer.MIN_VALUE;
        }
        if(debut == fin){
            //System.out.println("On peut pas faire ça debut == fin");
            return Integer.MIN_VALUE;
        }
        if(this.getNbNoeud()+pairesToAdd.size()>this.tailleMax){
            //System.out.println("La taille MAX va être dépassée");
            return Integer.MIN_VALUE;
        }
        if(Math.abs(fin-debut) != 1){
            return Integer.MIN_VALUE;
        }
        if(fin<debut){
            //System.out.println("fin>debut");
            return Integer.MIN_VALUE;
        }
        
        int deltaBenefice = 0;
        int benefice = 0;
        
        Noeud firstPaireI = getCurrent(debut);
        
        Noeud firstPaireJ = pairesToAdd.getFirst();
        Noeud lastPaireJ = pairesToAdd.getLast();
        
        if(firstPaireJ instanceof DonneurAltruiste){
            //System.out.println("On peut pas faire ça Déplacement de donneur altruiste");
            return Integer.MIN_VALUE;
        }
        
        if(pairesToAdd.size() ==1){
            return this.deltaBeneficeInsertionPaire((Paire)pairesToAdd.getFirst(), fin);
        }
        
        benefice = firstPaireI.getBeneficeVers( firstPaireJ);
        if(benefice == -1) {
            //System.out.println(firstPaireI.getId()+"->"+firstPaireJ.getId());
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
        benefice = this.getBeneficeSequence(pairesToAdd);
        if(benefice == -1) {
            //System.out.println("Mauvais paires to add");
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
        
        
        Noeud lastPaireI = getCurrent(fin);
        benefice = lastPaireJ.getBeneficeVers( lastPaireI);
        if(benefice == -1) {
            //System.out.println(lastPaireJ.getId()+"->"+lastPaireI.getId());
            return Integer.MIN_VALUE;
        }
        deltaBenefice += benefice;
        
                
        LinkedList<Noeud> toDelete = this.convertToLinkedList(debut, fin);
        deltaBenefice -= this.getBeneficeSequence(toDelete);
        
        return deltaBenefice;
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
            return nPrec.peutDonnerA(paireToAdd) && paireToAdd.peutDonnerA(nNext);
        }
    }
    
    @Override
    public int deltaBeneficeSuppressionSequence(int debut, int fin) {
        if(debut > fin){
            //System.out.println("debut > fin");
            return Integer.MIN_VALUE;
        }
        if(!this.isPositionSuppressionValide(debut) || !this.isPositionSuppressionValide(fin)){
            //System.out.println("position suppression invalide");
            return Integer.MIN_VALUE;
        }
        LinkedList<Noeud> pairesToSupp = this.convertToLinkedList(debut, fin);
        if(this.getNbNoeud()-pairesToSupp.size()<2){
            //System.out.println("Taille min dépassée");
            return Integer.MIN_VALUE;
        }
        
        
        int deltaBenefice = 0;
        int benefice;
        Noeud nPrec = this.getPrec(debut); 
        
        
        //System.out.println(pairesToSupp);
        
        if(fin == this.getNbPaires()){ // si on veut supprimer à la fin
            //System.out.println("Supprimer à la fin");
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
            benefice = nPrec.getBeneficeVers(nNext);
            //System.out.println("benefice:"+benefice);
            if(benefice == -1) {
                //System.out.println("-1=" +nPrec.getId()+"->"+nNext.getId());
                return Integer.MIN_VALUE;
            }
            deltaBenefice += benefice;
            
            deltaBenefice -= nPrec.getBeneficeVers(pairesToSupp.getFirst());
            deltaBenefice -= this.getBeneficeSequence(pairesToSupp);
            deltaBenefice -= pairesToSupp.getLast().getBeneficeVers(nNext);
        }
        
        
        return deltaBenefice;
    }

    @Override
    protected boolean isPositionInsertionValide(int position) {
        if(0 <= position && position <= this.getNbNoeud()){
            return true;
        }
        return false;
    }

    @Override
    InsertionPaire getMeilleureInsertion(Paire paireToInsert) {
        InsertionPaire meilleur = new InsertionPaire();
        if(!this.insertionPairePossible(paireToInsert)) 
            return meilleur;//return d'une valeur par défaut
        
        
        
        for(int pos = 1; pos<=this.paires.size()+1; pos++){
            InsertionPaire courrant = new InsertionPaire(this, paireToInsert, pos);
            if(courrant.isMeilleur(meilleur))
                meilleur = courrant;
        }
        
        return meilleur;
    }

    @Override
    protected boolean isPositionSuppressionValide(int position) {
        if(0 < position && position < this.getNbPaires()+1){
            return true;
        }
        return false;
    }

    @Override
    public int getNbNoeud() {
        return this.getNbPaires()+1;
    }

    

    @Override
    public boolean insertSequenceAtPos(LinkedList<Noeud> pairesToAdd, int position) {
        if(position > this.paires.size())
            return this.paires.addAll(this.paires.size(), pairesToAdd);
        return this.paires.addAll(position, pairesToAdd);
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
    public int deltaBeneficeRemplacementInter(int debutSequenceI, int finSequenceI, LinkedList<Noeud> pairesSequenceJ) {
        LinkedList<Noeud> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI);
        if(pairesSequenceI == null){
            //System.out.println("pairesSequenceI == null");
            return Integer.MIN_VALUE;
        }
        if(pairesSequenceJ == null){
            //System.out.println("pairesSequenceJ == null");
            return Integer.MIN_VALUE;
        }
        if(this.getNbNoeud() - pairesSequenceI.size() + pairesSequenceJ.size() > this.tailleMax){
            //System.out.println("La chaine va dépasser la taille max");
            return Integer.MIN_VALUE;
        }
        if(this.getNbNoeud() - pairesSequenceI.size() + pairesSequenceJ.size() < 2){
            //System.out.println("La chaine va dépasser la taille min");
            return Integer.MIN_VALUE;
        }
        if(debutSequenceI == 0){
            //System.out.println("non");
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
    @Override
    public int deltaBeneficeRemplacement(int debutSequenceI, int finSequenceI, LinkedList<Noeud> pairesSequenceJ) {
        int deltaBenefice = 0;
        int benefice;
        
        LinkedList<Noeud> pairesSequenceI = this.convertToLinkedList(debutSequenceI, finSequenceI);
        Noeud avantSeqI = this.getPrec(debutSequenceI);
        
        //System.out.println("avantSeqI"+avantSeqI.getId());
        
        deltaBenefice -= avantSeqI.getBeneficeVers(pairesSequenceI.getFirst());
        deltaBenefice -= this.getBeneficeSequence(pairesSequenceI);

        benefice = avantSeqI.getBeneficeVers(pairesSequenceJ.getFirst());
        if(benefice == -1) {
            //System.out.println(avantSeqI.getId()+"->"+pairesSequenceJ.getFirst().getId());
            return Integer.MIN_VALUE;
        }
        //System.out.println("+benefice: "+benefice);
        deltaBenefice += benefice;

        benefice = this.getBeneficeSequence(pairesSequenceJ);
        if(benefice == -1) {
            //System.out.println("this.getBeneficeSequence(pairesSequenceJ)");
            return Integer.MIN_VALUE;
        }
        //System.out.println("+benefice: "+benefice);
        deltaBenefice += benefice;
        
        
        if(finSequenceI != this.getNbPaires()){
            Noeud apresSeqI = this.getNext(finSequenceI);
            deltaBenefice -= pairesSequenceI.getLast().getBeneficeVers(apresSeqI);

            benefice = pairesSequenceJ.getLast().getBeneficeVers(apresSeqI);
            if(benefice == -1) {
                //System.out.println(pairesSequenceJ.getLast().getId()+"-->"+apresSeqI.getId());
                return Integer.MIN_VALUE;
            }
            //System.out.println("+benefice: "+benefice);
            deltaBenefice += benefice;
        }        
        
        
        return deltaBenefice;
    }
    
    
    /**
     * Remplace les paires patient-donneur entre debutJ et finJ par les clientsI
     * Si il n'y a rien entre debutJ et finJ, c'est une insertion
     * Sinon on remplace
     * @param infos
     * @return 
     */
    @Override
    public boolean doRemplacement(InterDeplacement infos) {
        if(infos == null) return false;
        if(!infos.isMouvementRealisable()) return false;
        
        int debutI = infos.getDebutSequenceI();
        int finI = infos.getFinSequenceI();
        int debutJ = infos.getDebutSequenceJ();
        int finJ = infos.getFinSequenceJ();
        LinkedList<Noeud> pairesI = infos.getPairesSequenceI();
        LinkedList<Noeud> pairesJ = infos.getPairesSequenceJ();
        
        SchemaEchange autreSequence = infos.getAutreSequence();
        
        
        /**
         * Suppression de la chaine
         */
        this.paires.removeAll(pairesI); //On inclus finI
        
        //Insertion
        if(autreSequence.getCurrent(debutJ).equals(autreSequence.getPrec(finJ))){
            //System.out.println("Insertion (Chaine)");
            //System.out.println("Insertion dans une chaine");
            autreSequence.insertSequenceAtPos(pairesI, debutJ);
        }
        //Remplacement
        else{
            //System.out.println("Remplacement (Chaine)");
            //Supprimer les trucs au milieux
            LinkedList<Noeud> pairesToRemove = new LinkedList<Noeud>(pairesJ);
            pairesToRemove.removeFirst();
            pairesToRemove.removeLast();
            
            
            //System.out.println("1 avant"+autreSequence.paires+ " "+pairesToRemove);
            autreSequence.paires.removeAll(pairesToRemove);
            //System.out.println("2 removeALL"+autreSequence.paires+ " "+pairesToRemove);
            autreSequence.insertSequenceAtPos(pairesI, debutJ);
            //System.out.println("3"+autreSequence.paires);
        }
        
        
        //LinkedList<Paire> pairesToAdd = new LinkedList<>();
        
        
        //maj cout
        this.coutBenefice += infos.getDeltaBeneficeSequence();
        autreSequence.coutBenefice += infos.getDeltaBeneficeAutreSequence();
        
        
        if (!this.check()){
            System.out.println("Mauvais remplacement inter-sequence, (courante)"+this.toString()+"\n"+autreSequence.toString());
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        if (!autreSequence.check()){
            System.out.println("Mauvais remplacement inter-sequence, (autre)"+autreSequence.toString());
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        
        return true;
    }
    
    

    @Override
    public boolean doEchange(InterEchange infos) {
        if(infos == null) return false;
        if(!infos.isMouvementRealisable()) return false; 
        
        int debutI = infos.getDebutSequenceI();
        int finI = infos.getFinSequenceI();
        int debutJ = infos.getDebutSequenceJ();
        int finJ = infos.getFinSequenceJ();
        LinkedList<Noeud> pairesI = infos.getPairesSequenceI();
        LinkedList<Noeud> pairesJ = infos.getPairesSequenceJ();
        
        SchemaEchange autreSequence = infos.getAutreSequence();
        
        //System.out.println("Avant----------");
        //System.out.println(this);
        //System.out.println(autreSequence);
        
        
        //Remplacer les paires de 'this' [debutI ; finI] par les pairesJ
        this.replacePaires(debutI,finI,pairesJ);
        //Remplacer les paires de 'autreSequence' [debutJ ; finJ] par les pairesI
        autreSequence.replacePaires(debutJ,finJ,pairesI);
        
        //System.out.println("Après");
        //System.out.println(this);
        //System.out.println(autreSequence);
        
        //maj cout
        this.coutBenefice += infos.getDeltaBeneficeSequence();
        autreSequence.coutBenefice += infos.getDeltaBeneficeAutreSequence();
        
        if (!this.check()){
            System.out.println("Mauvais échange inter-sequence, (courante)"+this.toString()+"\n"+autreSequence.toString());
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        if (!autreSequence.check()){
            System.out.println("Mauvais échange inter-sequence, (autre)"+autreSequence.toString());
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        return true;
    }

    @Override
    public boolean replacePaires(int debut, int fin, LinkedList<Noeud> pairesToAdd) {
        //Suppression des paires entre debut et fin (compris)
        this.paires.removeAll(this.convertToLinkedList(debut, fin));
        if(this.paires.isEmpty())
            return this.paires.addAll(pairesToAdd);
        return this.paires.addAll(debut-1, pairesToAdd);
    }
    
}
