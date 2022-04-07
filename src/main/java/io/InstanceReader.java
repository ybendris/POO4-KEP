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
            int nbPairePatientDonneur = lireNbPairePatientDonneur(br);
            int nbDonneurAlt = lireNbDonneurAlt(br);
            int tailleMaxCycle = lireTailleMaxCycle(br);
            int tailleMaxChaine = lireTailleMaxChaine(br);
            Instance instance = new Instance(nom, nbPairePatientDonneur, nbDonneurAlt, tailleMaxCycle, tailleMaxChaine);
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
     * Lecture du nombre de paires patient-donneur.
     * La ligne du dessus doit commencer par "// P :"
     * @param br le lecteur courant du fichier d'instance
     * @return le nombre de paires patient-donneur
     * @throws IOException 
     */
    private int lireNbPairePatientDonneur(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("// P :")) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        ligne = ligne.trim();
        return Integer.parseInt(ligne);        
    }
    
     /**
     * Lecture du nombre de donneurs altruistes.
     * La ligne du dessus doit commencer par "// N :"
     * @param br le lecteur courant du fichier d'instance
     * @return le nombre de donneurs altruistes
     * @throws IOException 
     */
    private int lireNbDonneurAlt(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("// N :")) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        ligne = ligne.trim();
        return Integer.parseInt(ligne);        
    }
    
    /**
     * Lecture de la taille max cycles.
     * La ligne du dessus doit commencer par "// K :"
     * @param br le lecteur courant du fichier d'instance
     * @return la taille max cycles
     * @throws IOException 
     */
    private int lireTailleMaxCycle(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("// K :")) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        ligne = ligne.trim();
        return Integer.parseInt(ligne);        
    }
    
    /**
     * Lecture de la taille max cycles.
     * La ligne du dessus doit commencer par "// K :"
     * @param br le lecteur courant du fichier d'instance
     * @return la taille max cycles
     * @throws IOException 
     */
    private int lireTailleMaxChaine(BufferedReader br) throws IOException {
        String ligne = br.readLine();
        while(!ligne.contains("// L :")) {
            ligne = br.readLine();
        }
        ligne = br.readLine();
        ligne = ligne.trim();
        return Integer.parseInt(ligne);        
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
