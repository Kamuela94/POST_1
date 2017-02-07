/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author kamuela94
 */

public class POST {
    private Customer cust;
    private Manager man;
    private Store store;
    private boolean isRunning;
    
    public POST() throws IOException{
        cust = new Customer("Customer");
        store = new Store();
        man = new Manager(store);
        isRunning = true;
    }
    
    public void CustomerUI() throws IOException{
        Scanner sc = new Scanner(System.in);
        int temp;
        while(isRunning){
            System.out.println("Please select an option: \n 1) View Catalog \n 2) Add Item"
                    + "\n 3) Remove Item \n 4) Make Payment \n 5) Exit");
            temp = sc.nextInt();
            
            switch(temp){
                case 1:
                    viewCatalog();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    makePayment();
                    break;
                case 5:
                    isRunning = false;
                    break;
            }
        }
    }
    public void addItem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter your product code: ");
        String code = sc.next();
        System.out.print("Please Enter the quantity: ");
        int quant = sc.nextInt();
        
        cust.addItem(store, code, quant);
        System.out.println("Item added.");
    }
    
    public void removeItem(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code of the item you would like to remove: ");
        String code = sc.next();
        cust.removeItem(code);
        System.out.println("Item removed.");
    }
    
    public void makePayment() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Cash or Credit? ");
        String payType = sc.next();
        String invoice = cust.makePayment(store, payType);
        System.out.println(invoice);
        
    }
    
    public void ManagerUI() throws IOException{
        Scanner sc = new Scanner(System.in);
        int temp;
        while(isRunning){
            System.out.println("Please select an option: \n "
                    + "1) Add an item \n 2) Remove item \n 3) Exit");
            temp = sc.nextInt();
            
            switch(temp){
                case 1:
                    manageAdd();
                    break;
                case 2:
                    manageRemove();
                    break;
                case 3:
                    isRunning = false;
                    break;
            }
        }
    }
    
    public void viewCatalog() throws IOException{
        ArrayList catalog = cust.getCatalog(store);
        String[] temp;
        String temp_0;
        String delims = "[ ]+";
        System.out.println("Item            Price");
        for (Object item : catalog) {
            temp_0 = item.toString();
            temp = temp_0.split(delims);
            
            
            System.out.println(temp[1] + "           " + temp[2]);
        }
    }
    
    public void manageRemove(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code of the item to be removed: ");
        String code = sc.next();
        
        man.removeItem(code);
        System.out.println("Item removed.");
        
    }
    
    public void manageAdd() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code for the new item: ");
        String code = sc.next();
        System.out.print("Please enter the description of the item: ");
        String description = sc.next();
        System.out.println("Please enter the price of the item: ");
        Float price = sc.nextFloat();
        
        man.addItem(code, description, price);
        System.out.println("Item added");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        POST post = new POST();
        System.out.print("Welcome! Are you: \n 1) Customer \n 2) Manager \n");
        int personType = sc.nextInt();
        
        if(personType == 1){
            post.CustomerUI();
        }else{
            post.ManagerUI();
        
        }
    }
    
}
