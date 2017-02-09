/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post;

import java.io.IOException;
import java.util.*;
import java.text.*;

/**
 *
 * @author kamuela94
 */
public class POST {

    private Customer cust;
    private Manager man;
    private Store store;
    private boolean isRunning;

    public POST() throws IOException {
        cust = new Customer("Customer");
        store = new Store();
        man = new Manager(store);
        isRunning = true;
    }

    public void CustomerUI() throws IOException {
        Scanner sc = new Scanner(System.in);
        int temp;
        while (isRunning) {
            System.out.println("Please select an option: \n 1) View Catalog \n 2) Add Item"
                    + "\n 3) Remove Item \n 4) Make Payment \n 5) Show Cart \n 6) Exit");
            temp = sc.nextInt();

            switch (temp) {
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
                    showCart();
                    break;
                case 6:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Sorry, that is not a valid option. Please try again.");
                    break;
            }
        }
    }

    public void addItem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please Enter your product code: ");
        String code = sc.next();
        System.out.print("Please Enter the quantity: ");
        int quant = sc.nextInt();

        if (cust.addItem(store, code, quant)) {
            System.out.println("Item added.");
        } else {
            System.out.println("Item could not be added");
        }
    }

    public void removeItem() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code of the item you want to add: ");
        String code = sc.next();
        if (cust.removeItem(code)) {
            System.out.println("Item removed.");
        } else {
            System.out.println("Item can not be removed.");
        }
    }

    public void makePayment() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Cash or Credit? ");
        String payType = sc.next();

        float tender = 0;
        int cardNum = 0;
        boolean temp = true;
        while (temp) {
            switch (payType) {
                case "Cash":
                    System.out.println("Please enter your tender");
                    tender = sc.nextFloat();
                    temp = false;
                    break;
                case "Credit":
                    System.out.println("Please enter your 5 digit card number.");
                    cardNum = sc.nextInt();
                    temp = false;
                    break;
                default:
                    System.out.println("Sorry, that is an invalid option, please try again.");
                    break;
            }
        }
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        String[][] invoice = cust.makePayment(store, payType, cardNum);
        System.out.printf("%-10s", cust.getName());
        System.out.print(ft.format(dNow)+"\n");
        
        int index = -1;
        
        for (String[] code : invoice) {
            System.out.printf("%-10s%-5s%-8s%s\n", code[0], code[1], code[2], code[3]);
            index++;
        }
        System.out.println("------");
        System.out.printf("%-10s%s\n","Total", invoice[index][3]);
        System.out.print("Amount Tendered: ");
        float result = tender - Float.parseFloat(invoice[index][3]);
        if(payType.equals("Cash")){
            System.out.print(tender + "\nAmount Returned: " + result + "\n");
        }else if(payType.equals("Credit")){
            System.out.print("Credit Card " + cardNum + "\nAmount Returned: 0.00\n");
        }
        
    }
    
    public void showCart(){
        HashMap cart = cust.getCart();
        
        System.out.printf("%-5s%s\n", "Item", "Quantity");      
        Iterator it = cart.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            System.out.printf("%-5s%s\n", pair.getKey(), pair.getValue());
            
            
        }
    }
    
    public void ManagerUI() throws IOException {
        Scanner sc = new Scanner(System.in);
        int temp;
        while (isRunning) {
            System.out.println("Please select an option: \n "
                    + "1) Add an item \n 2) Remove item \n 3) Exit");
            temp = sc.nextInt();

            switch (temp) {
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

    public void viewCatalog() throws IOException {
        ArrayList catalog = cust.getCatalog(store);
        String[] temp;
        String temp_0;
        String delims = "[ ]+";
        String code = "Code";
        String tem = "Item";
        String price = "Price";
        System.out.printf("%-10s%-25s%s\n", code, tem, price);
        for (Object item : catalog) {
            temp_0 = item.toString();
            temp = temp_0.split(delims);

            System.out.printf("%-10s%-25s%s\n", temp[0], temp[1], temp[2]);
        }
    }

    public void manageRemove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code of the item to be removed: ");
        String code = sc.next();
        man.removeItem(code);
        System.out.println("Item removed.");
    }

    public void manageAdd() throws IOException {
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
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        POST post = new POST();
        int personType;
        boolean startUI = true;
        while (startUI) {
            System.out.print("Welcome! Are you: \n 1) Customer \n 2) Manager \n");
            personType = sc.nextInt();
            switch (personType) {
                case 1:
                    post.CustomerUI();
                    startUI = false;
                    break;
                case 2:
                    post.ManagerUI();
                    startUI = false;
                    break;
                default:
                    System.out.println("Sorry, that is not a valid option. Please try again.");
                    break;
            }
        }
    }

}
