/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
    private boolean isPostOn;
    private FileInputStream in = null;
    private String[] tokens;
    private ArrayList catalog = new ArrayList();
    private String name = "Store";

    public Store() throws IOException {
        isPostOn = false;
        try {
            in = new FileInputStream("products.txt");
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
        isPostOn = true;
    }
    
    public void closeStore(){
        isPostOn = false;
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

    public boolean isPostOn() {
        return isPostOn;
    }

    public String[][] updateTransaction(HashMap cart, String payType, String name, int cardNum) throws FileNotFoundException, IOException {

        FileWriter fw = new FileWriter("transaction.txt", true);
        
        BufferedWriter bw = new BufferedWriter(fw);
        float price = 0;
        String fileInsert = name + "\n";
        int index = 0;
        String[][] receipt;
        receipt = new String[cart.size()][4];
        
        Iterator it = cart.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            fileInsert += pair.getKey();
            price += (((double)(inventory.get(pair.getKey()))) * (int)pair.getValue());
            receipt[index][0] = (String)pair.getKey();
            int value = (int)pair.getValue();
            receipt[index][1] = Integer.toString(value);
            receipt[index][2] = Double.toString((double)(inventory.get(pair.getKey())));
            receipt[index][3] = Float.toString(price);
            
            if((int)pair.getValue() == 1){
                fileInsert += "\n";
            }else{
                Integer temp = (int)pair.getValue();
                String temp_0 = temp.toString();
                fileInsert += "      " + temp_0 + "\n";
            }
            it.remove();
            index++;
        }
        
        
        if(payType.equals("Cash")){
            fileInsert += payType.toUpperCase() + "      " + price;
            
        }else{
            fileInsert += payType.toUpperCase() + "      " + cardNum;
        }
        
        fileInsert += "\n\n";
        
        bw.write(fileInsert);
        bw.flush();
        
        
        return receipt;
        
    }
    
    public void addItem(String code, String description, float price) throws IOException{
        FileWriter fw = new FileWriter("catalog.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(code + "     " + description);
        int spaceNum = 19 - description.length();
        for(int i = 0; i < spaceNum; i++){
            bw.write(" ");
        }
        bw.write("      " + price);
        bw.flush();
        
        inventory.put(code, price);
        
    }

    public static void main(String args[]) throws IOException {
        Store store = new Store();
        if (store.isPostOn()) {
            System.out.println("isOpen test passed");
        }
        store.addItem("BTBT", "Grapefruit" , new Float(5.00));
        ArrayList test = store.searchCatalog();
        System.out.println(test);        
    }

}
