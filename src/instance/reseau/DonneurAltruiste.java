/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instance.reseau;

/**
 *
 * @author yanni
 */
public class DonneurAltruiste extends Noeud {
    

    public DonneurAltruiste(int id) {
        super(id);
    }

    
    @Override
    public String toString() {
        return "DonneurAltruiste{" + "id=" + id + '}';
    }
    
    
    public static void main(String[] args) {
        DonneurAltruiste d1 = new DonneurAltruiste(1);
        
        System.out.println(d1);
    }
}
