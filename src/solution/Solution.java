/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solution;

import instance.Instance;
import instance.reseau.DonneurAltruiste;
import instance.reseau.Noeud;
import instance.reseau.Paire;

import io.InstanceReader;
import io.exception.ReaderException;
import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import operateur.InsertionPaire;
import operateur.OperateurInterSequences;
import operateur.OperateurIntraSequence;
import operateur.OperateurLocal;
import operateur.TypeOperateurLocal;


/**
 *
 * @author Valek
 */
public class Solution {
    private Instance instance;
    private int benefice; 
    private LinkedList<Chaine> chaines;
    private LinkedList<Cycle> cycles;
    private LinkedList<Paire> pairesRestantes;

    /*
        TODO: ajouter un constructeur par copie de chaine et Cycle
    */
    public Solution(Solution s) {
        this.benefice = s.benefice;
        this.instance = s.instance;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
        this.pairesRestantes = new LinkedList<>();
        for(Chaine chaineToAdd : s.chaines){
            this.chaines.add(new Chaine(chaineToAdd));
        }
        for(Cycle cycleToAdd : s.cycles){
            this.cycles.add(new Cycle(cycleToAdd));
        }
    }

    public Solution(Instance i) {
        this.benefice = 0;
        this.instance = i;
        this.chaines = new LinkedList<>();
        this.cycles = new LinkedList<>();
        this.pairesRestantes = new LinkedList<>();
    }

    public int getBenefice() {
        return benefice;
    }

    public LinkedList<Chaine> getChaines() {
        return chaines;
    }

    public LinkedList<Cycle> getCycles() {
        return cycles;
    }    
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.instance);
        hash = 79 * hash + this.benefice;
        hash = 79 * hash + Objects.hashCode(this.chaines);
        hash = 79 * hash + Objects.hashCode(this.cycles);
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
        final Solution other = (Solution) obj;
        if (this.benefice != other.benefice) {
            return false;
        }
        if (!Objects.equals(this.instance, other.instance)) {
            return false;
        }
        if (!Objects.equals(this.chaines, other.chaines)) {
            return false;
        }
        return Objects.equals(this.cycles, other.cycles);
    }
    
    public boolean check(){
        return verifCycles() && verifChaines() && verifTransplantations();
    }
    
    /**
     * Vérifie que les cycles sont valides
     * @return 
     */
    private boolean verifCycles() {
        for(Cycle cycle : this.cycles){ //Ses cycles sont tous réalisables
            if(!cycle.check())
                return false;
        }
        return true;
    }

    /**
     * Vérifie que les chaines sont valides
     * @return 
     */
    private boolean verifChaines() {
        for(Chaine chaine : this.chaines){ //Ses chaines sont toutes réalisables
            if(!chaine.check())
                return false;
        }
        return true;
    }

    /**
     * Vérifie que chaque paire patient-donneur n’apparaissant que dans une seule chaîne ou un seul cycle au maximum.
     * @return 
     */
    private boolean verifTransplantations() {
        List<Paire> pairesAverif = this.instance.getPaires();
        
        /**
         * Opération en temps constant sur un Hashset
         */
        Set pairesToCheck = new HashSet();
        
        for(Cycle cycle : this.cycles){
            for(Noeud p : cycle.paires){
                if(!pairesToCheck.add(p)){
                    System.out.println("Une paire n’apparait pas que dans une seule chaîne ou un seul cycle au maximum.");
                    return false;
                }
            }
        }
        
        for(Chaine chaine : this.chaines){
            for(Noeud p : chaine.paires){
                if(!pairesToCheck.add(p)){
                    System.out.println("Une paire n’apparait pas que dans une seule chaîne ou un seul cycle au maximum.");
                    return false;
                }
            }
        }
        
        
        return true;
    }

    public Instance getInstance() {
        return instance;
    }

    
    public boolean ajouterPaireNouveauCycle(Paire paireToAdd){
        int tailleMaxCycle = this.instance.getTailleMaxCycle();
        Cycle nouveauCycle = new Cycle(tailleMaxCycle);
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd)){
            this.cycles.add(nouveauCycle);
            nouveauCycle.coutBenefice = nouveauCycle.evalCoutBenefice();
            return true;
        }
        return false;
    }
    
    public boolean ajouterPairesNouveauCycleDe2(Paire paireToAdd1, Paire paireToAdd2){
        Cycle nouveauCycle = new Cycle(2);
        if(nouveauCycle.ajouterPaireAuCycle(paireToAdd1) && nouveauCycle.ajouterPaireAuCycle(paireToAdd2)){
            //System.out.println("EvalBenef");
            nouveauCycle.coutBenefice=nouveauCycle.evalCoutBenefice();
            //System.out.println("ADDCycle");
            this.cycles.add(nouveauCycle);
            return true;
        }
        return false;
    }

    
    
    public void evalBenefice(){
        for(Cycle c : this.cycles){
            this.benefice += c.coutBenefice;
        }
        for(Chaine ch : this.chaines){
            this.benefice+=ch.coutBenefice;
        }
    }

    @Override
    public String toString() {
        return "Solution{\n" + 
                "instance=" + instance + 
                ", benefice=" + benefice + 
                ", chaines=" + chaines + 
                ", cycles=" + cycles + 
                ", nbPairesRestant=" + this.pairesRestantes.size()+ 
            '}';
    }
    
    
    public boolean ajouterPaireDernierCycle(Paire paireToAdd){
        
        
        if(this.cycles.isEmpty()){
            return false;
        }
        
        Cycle dernierCycle = this.cycles.getLast();
        
        
        if(dernierCycle.ajouterPaireAuCycle(paireToAdd)){
            dernierCycle.coutBenefice=dernierCycle.evalCoutBenefice();
            return true;
        }
        return false;
    }

    
    
    
    public boolean ajouterPaireNouvelleChaine(DonneurAltruiste DAToAdd, Paire paireToAdd){
        
        int tailleMaxChaine = this.instance.getTailleMaxChaine();
        Chaine nouvelleChaine = new Chaine(DAToAdd,tailleMaxChaine);
        if(nouvelleChaine.ajouterPaireChaine(paireToAdd)){
            this.chaines.add(nouvelleChaine);
            return true;
        }
        return false;
    }
    
    public int getSizeChaineByIndex(int i){
        Chaine ch = this.chaines.get(i);
        if(ch!=null){
            return ch.paires.size();
        }
        return -1;
    }
    
    public boolean ajouterPaireDerniereChaine(Paire paireToAdd){
        
        if(this.chaines.isEmpty()){
            return false;
        }
        
        Chaine derniereChaine = this.chaines.getLast();
        
        if(derniereChaine.ajouterPaireChaine(paireToAdd)){
            derniereChaine.coutBenefice=derniereChaine.evalCoutBenefice();
            return true;
        }
        return false;
    }
    
    public InsertionPaire insererPaireRestantes(){
        InsertionPaire best = new InsertionPaire();
        InsertionPaire current = new InsertionPaire();
       
        boolean improve = true;
        while(improve == true){
            best = new InsertionPaire();
            improve = false;
            for(Paire p : this.pairesRestantes){
                current = this.getMeilleureInsertion(p);
                if(current.isMeilleur(best)){
                    best = current;
                }
            }
            
            if(best.isMouvementAmeliorant()){
                this.doInsertion(best);
                improve = true;
                this.pairesRestantes.remove(best.getPaireToInsert());
            }
        }
        
        return best;
    }
    
      
    public InsertionPaire getMeilleureInsertion(Paire paireToInsert){
        InsertionPaire best = new InsertionPaire();
        
        //System.out.println("Cycle");
        for(Cycle cycle : this.cycles){
            InsertionPaire courrant = cycle.getMeilleureInsertion(paireToInsert);
            
            if(courrant.isMeilleur(best)) best = courrant;
        }
        
        //System.out.println("Chaine");
        for(Chaine chaine : this.chaines){
            InsertionPaire courrant = chaine.getMeilleureInsertion(paireToInsert);
            if(courrant.isMeilleur(best)) best = courrant;
        }
        
        return best;
    }
    
    public boolean doInsertion(InsertionPaire infos){
        if(infos == null) return false;
        if(!infos.doMouvementIfRealisable())return false;
        
        this.benefice += infos.getDeltaBenefice();
        return true;
    }

    public void setPairesRestantes(LinkedList<Paire> pairesRestantes) {
        this.pairesRestantes = pairesRestantes;
    }
    
    
    
    private OperateurLocal getMeilleurOperateurInter(TypeOperateurLocal type){
        OperateurLocal best = OperateurLocal.getOperateur(type);
        LinkedList<SchemaEchange> cycleEtChaine = new LinkedList<SchemaEchange>();
        cycleEtChaine.addAll(cycles);
        cycleEtChaine.addAll(chaines);
        
        
        
        for(SchemaEchange seq1 : cycleEtChaine){
            for(SchemaEchange seq2 : cycleEtChaine){
                OperateurLocal op = seq1.getMeilleurOperateurInter(seq2,type);
                if(op.isMeilleur(best)) {
                    best = op;
                }
            }
        }
        return best;
    }
    
    private OperateurLocal getMeilleurOperateurIntra(TypeOperateurLocal type) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public OperateurLocal getMeilleurOperateurLocal(TypeOperateurLocal type) {
        if(OperateurLocal.getOperateur(type) instanceof OperateurIntraSequence){
            //System.out.println("Intra");
            return this.getMeilleurOperateurIntra(type);
        }
        else if(OperateurLocal.getOperateur(type) instanceof OperateurInterSequences){
            //System.out.println("Inter");
            return this.getMeilleurOperateurInter(type);
        }
        else{
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Test de la classe Solution:");
        try{
            InstanceReader read = new InstanceReader("instancesInitiales/yannistest.txt");
            Instance i = read.readInstance();
            
            Solution s = new Solution(i);
            
            System.out.println(s.toString());
            System.out.println(s.check());//true
            
            DonneurAltruiste d1 = i.getDonneurById(1);
            DonneurAltruiste d2 = i.getDonneurById(2);
            Paire p3 = i.getPaireById(3);
            Paire p4 = i.getPaireById(4);
            Paire p5 = i.getPaireById(5);
            Paire p6 = i.getPaireById(6);
            Paire p7 = i.getPaireById(7);
            
            System.out.println(d1.getBeneficeVers(p3)); //5
            System.out.println(d2.getBeneficeVers(p3)); //2
            
            
            System.out.println(p5.getBeneficeVers(p7)); //2
            System.out.println(p7.getBeneficeVers(p6)); //6 
            System.out.println(p6.getBeneficeVers(p5)); //4
            System.out.println(p6.getBeneficeVers(p7)); //4 -> cout modifié (j'ai ajouté un cout retour)
            
            System.out.println();
            
            Cycle c1 = new Cycle(3); // taille max 3
            
            System.out.println("Test Insertion");
           
            c1.ajouterPaireFin(p7);
            System.out.println(c1.toString());
            
            c1.ajouterPaireFin(p6);
            System.out.println(c1.toString());
            
            System.out.println("ICI");
            System.out.println(c1.deltaBeneficeInsertionPaire(p5, 2)); //2
            System.out.println(c1.deltaBeneficeInsertionPaire(p5, 1)); //-2147483648
            
            InsertionPaire op = new InsertionPaire(c1,p5,2);
            System.out.println(op.toString());
            c1.doInsertion(op);
            
            System.out.println("\n\n");
            //c1.ajouterPaire(p5, 2);
            System.out.println("---------------");
            System.out.println(c1.toString());
            
            
            System.out.println("---------------");
            
           
            
            s.cycles.add(c1);
            
            Chaine chaine1 = new Chaine(d1,3); 
            s.chaines.add(chaine1);
            
            
            System.out.println(s.toString());
            
            
            InsertionPaire operateur = s.getMeilleureInsertion(p3);
            System.out.println(operateur);
            
            s.doInsertion(operateur);
            
            System.out.println(s.toString());
            
            //InsertionPaire operateur2 = s.getMeilleureInsertion(p5);
            
            
            System.out.println("Solution"+s);
            
            System.out.println(s.getMeilleurOperateurInter(TypeOperateurLocal.INTER_REMPLACEMENT));
        }
        catch(ReaderException ex){
            System.out.println(ex.getMessage());
        }
        
    }

    public boolean doMouvementRechercheLocale(OperateurLocal infos) {
        if(infos == null) return false;
        if(!infos.doMouvementIfRealisable())return false;
        
        System.out.println("On gagne"+infos.getDeltaBenefice());
        this.benefice += infos.getDeltaBenefice();
        
        if (!this.check()){
            System.out.println("Mauvais mouvement recherche locale, "+this.toString());
            System.out.println(infos);
            System.exit(-1); //Termine le programme
        }
        
        return true;
    }

    

    
    
    
    
}
