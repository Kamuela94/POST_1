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
        
    }
    
    public void CustomerUI(){
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
    
    }
    
    public void removeItem(){
    
    }
    
    public void makePayment(){
    
    }
    
    public void ManagerUI(){
        
    }
    
    public void viewCatalog(){
    
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
