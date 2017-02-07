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
public class Customer {
    /**
     * Customer should be able to search the catalog, make a payment and
     * add or remove an item.
     */
    private String name;
    private HashMap cart;
    
    public Customer(String custName){
        name = "Customer";
        cart = new HashMap();
    }
    
    public ArrayList getCatalog(Store store) throws IOException{
        return store.searchCatalog();
    }
    
    public HashMap getCart(){
        return cart;
    }
    
    public void addItem(Store store,String code, int number){
        if(store.isInInventory(code)){
            cart.put(code, number);
        }
    
    }
    
    public void removeItem(String code){
        if(cart.containsKey(code)){
            cart.remove(code);
        }
    }
    
    public void makePayment(Store store, String payType) throws FileNotFoundException, IOException{
        store.updateTransaction(cart, payType, name);
    }

    public static void main(String args[]) throws IOException{
        Customer cust = new Customer("Customer");
        Store store = new Store();
        cust.addItem(store, "PHPH", 3);
        cust.makePayment(store, "Cash");
    }
}
