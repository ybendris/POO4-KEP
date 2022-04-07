/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import instance.Instance;
import io.exception.FileExistException;
import io.exception.FormatFileException;
import io.exception.OpenFileException;
import io.exception.ReaderException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lucas
 */
public class InstanceReader {
    /**
     * Le fichier contenant l'instance.
    */
    private File instanceFile;
    
    /**
     * Constructeur par donnee du chemin du fichier d'instance.
     * @param inputPath le chemin du fichier d'instance, qui doit se terminer 
     * par l'extension du fichier (.xml).
     * @throws ReaderException lorsque le fichier n'est pas au bon format ou 
     * ne peut pas etre ouvert.
     */
    public InstanceReader(String inputPath) throws ReaderException {
        if (inputPath == null) {
            throw new OpenFileException();
        }
        if (!inputPath.endsWith(".txt")) {
            throw new FormatFileException("txt", "txt");
        }
        String instanceName = inputPath;
        this.instanceFile = new File(instanceName);
    }
    
    /**
     * Methode principale pour lire le fichier d'instance.
     * @return l'instance lue
     * @throws ReaderException lorsque les donnees dans le fichier d'instance 
     * sont manquantes ou au mauvais format.
     */
    public Instance readInstance() throws ReaderException {
        try{
            FileReader f = new FileReader(this.instanceFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(f);
            String nom = this.instanceFile.getName().replace(".txt", "");
            int nbPairePatientDonneur = lireLigne(br, "// P :");
            int nbDonneurAltruiste = lireLigne(br, "// N :");
            int tailleMaxCycle = lireLigne(br, "// K :");
            int tailleMaxChaine = lireLigne(br, "// L :");
            Instance instance = new Instance(nom, nbPairePatientDonneur, nbDonneurAltruiste, tailleMaxCycle, tailleMaxChaine);
            int[][] matriceBenef = lireMatrice(br, nbPairePatientDonneur, nbDonneurAltruiste);
            int i,j;
            for (i=0; i<9; i++){
                for (j=0; j<9; j++){
                    
                    System.out.println(matriceBenef[i][j]);
                    
                }
                System.out.println("\n");
            }
            br.close();
            f.close();
            return instance;
        } catch (FileNotFoundException ex) {
            throw new FileExistException(instanceFile.getName());
        } catch (IOException ex) {
            throw new ReaderException("IO exception", ex.getMessage());
        }
    }
    
    /**
    * Lecture de l'attribut.
    * La ligne du dessus doit commencer par le commentaire rentré en paramètre
    * @param br le lecteur courant du fichier d'instance
    * @param commentaire chaine de caratère au dessus de la ligne à récupérer
    * @return l'attribut
    * @throws IOException 
    */
    private int lireLigne(BufferedReader br, String commentaire) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains(commentaire)) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        ligne = ligne.trim();
        return Integer.parseInt(ligne);
    }
    
    /**
    * Lecture de le la matrice.
    * @param br le lecteur courant du fichier d'instance
    * @return la matrice
    * @throws IOException 
    */
    
    private int[][] lireMatrice(BufferedReader br, int nbPairePatientDonneur, int nbDonneurAltruiste)throws IOException{
        int[][] matrice = new int[nbPairePatientDonneur][nbPairePatientDonneur + nbDonneurAltruiste];
        int i=0, j=0;
        String ligne = br.readLine();
        while((ligne = br.readLine())!=null){
            if(!ligne.contains("/") && !ligne.isEmpty()){
                String l[] = ligne.split("\t");
                j=0;
                for(String benef: l){
                    matrice[i][j] = Integer.parseInt(benef);
                    j++;
                }
                //System.out.println(ligne);
                i++;
            }
        }        
        return matrice;
    }
    
     /**
    * Ajout des noeuds.
    * @param matrice la matrice des bénéfices
    * @param nbPairePatientDonneur nombre de paires patient donneur
    * @param nbDonneurAltruiste nombre de donneur altruiste
    * @param instance instance
    * @return
    */
    private void ajouterNoeuds(int[][] matrice, int nbPairePatientDonneur, int nbDonneurAltruiste, Instance instance){
        
    }
    
    
    /**
     * Test de lecture d'une instance.
     * @param args 
    */
    public static void main(String[] args) {
        try {
            InstanceReader reader = new InstanceReader("instancesInitiales/KEP_p9_n0_k3_l0.txt");
            reader.readInstance();
            System.out.println("Instance lue avec success !");;
        } catch (ReaderException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
