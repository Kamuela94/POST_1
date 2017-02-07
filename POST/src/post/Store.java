/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post;

import java.io.*;
import java.util.*;

/**
 *
 * @author kamuela94
 */
public class Store {

    /**
     * Store is used to hold the catalog. Store should probably pull the info
     * from the file.
     */
    private HashMap inventory = new HashMap();
    private boolean isOpen;
    private FileInputStream in = null;
    private String[] tokens;
    private ArrayList catalog = new ArrayList();

    public Store() throws IOException {
        isOpen = true;
        try {
            in = new FileInputStream("catalog.txt");
        } catch (Exception e) {
            System.out.println("File not found:" + e);
        }
        InputStreamReader isr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(isr);
        String temp;
        while ((temp = br.readLine()) != null) {
            String delims = "[ ]+";
            tokens = temp.split(delims);
            catalog.add(temp);
            inventory.put(tokens[0], new Double(tokens[2]));
            
        }
    }
    
    public void openStore(){
        isOpen = true;
    }
    
    public void closeStore(){
        isOpen = false;
    }

    public ArrayList searchCatalog() throws IOException {
        return catalog;
    }

    public boolean isInInventory(String code) {
        if (inventory.containsKey(code)) {
            return true;
        }
        return false;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public String updateTransaction(HashMap cart, String payType, String name) throws FileNotFoundException, IOException {

        FileWriter fw = new FileWriter("transaction.txt", true);
        
        BufferedWriter bw = new BufferedWriter(fw);
        
        String fileInsert = name + "\n";

        Iterator it = cart.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            fileInsert += pair.getKey();
            if((int)pair.getValue() == 1){
                fileInsert += "\n";
            }else{
                Integer temp = (int)pair.getValue();
                String temp_0 = temp.toString();
                fileInsert += "     " + temp_0 + "\n";
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        fileInsert += payType.toUpperCase();
        
        fileInsert += "\n\n";
        
        bw.write(fileInsert);
        bw.flush();
        
        return fileInsert;

    }
    
    public void addItem(String code, String description, float price) throws IOException{
        FileWriter fw = new FileWriter("catalog.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(code + "    " + description + "     " + price);
        bw.flush();
        
        inventory.put(code, price);
        
    }

    public static void main(String args[]) throws IOException {
        Store store = new Store();
        if (store.isOpen()) {
            System.out.println("isOpen test passed");
        }
        store.addItem("DRDR", "Soda" , new Float(1.00));
        ArrayList test = store.searchCatalog();
        System.out.println(test);        
    }

}
