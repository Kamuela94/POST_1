/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post;

import java.io.IOException;

/**
 *
 * @author kamuela94
 */
public class Manager {
    /**
     * Manager should be able to add and remove from the catalog, create or
     * destroy terminals and open the store.
     */
        private Store store;
    public Manager(Store theStore){
        store = theStore;
    }
    
    public void addItem(String code, String description, float price) throws IOException{
        store.addItem(code, description, price);
    }
    
    public void removeItem(String code){
    
    }
    
    public void openStore(){
        store.openStore();
    }
    
    public void closeStore(){
        store.closeStore();
    }
}
