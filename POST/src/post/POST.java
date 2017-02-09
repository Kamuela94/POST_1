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
    /**
     * When POST is create, classes and variables are initialized.
     */
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

    public boolean CustomerUI() throws IOException {
        /**
         * If the Store is closed, the customer cannot enter the store and the catalog is shown.
         * isPostOn() checks to see if the store is open.
         */
        if (!store.isPostOn()) {
            System.out.println("I'm sorry, the store is not open at the moment, please wait for a manager to open the store.");
            System.out.println("Here is our catalog if you would like to browse while you wait.");
            viewCatalog();
            return true;
        } else {
        /**
         * If the Store is open, the user is prompted to make a choice.
         */
            Scanner sc = new Scanner(System.in);
            int temp;
        /**
         * The loop runs while the User does not select Exit Program(6) or Quit User(7)
         */
            while (isRunning) {
                System.out.println("Please select an option: \n 1) View Catalog \n 2) Add Item"
                        + "\n 3) Remove Item \n 4) Make Payment \n 5) Show Cart \n 6) Exit Program"
                        + "\n 7) Quit User");
                temp = sc.nextInt();

                /**
                 * After the user chooses, a Method is called based on their choice.
                 */
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
                    case 7:
                        return true;
                    default:
                        System.out.println("Sorry, that is not a valid option. Please try again.");
                        break;
                }
            }
            return false;
        }
    }

    /**
     * addItem() asks the user for a code and quantity, then adds it to the Customers cart if it was a valid
     * code, or rejects it if the code was not valid.
     */
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

    /**
     * removeItem() removes an item from the Customers cart if the code exists, or rejects if the given 
     * code is not in the cart.
     */
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

    /**
     * makePayment() takes the items in the Customers cart, adds them up and then
     * prints out an invoice for the transaction.
     * @throws IOException 
     */
    public void makePayment() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Cash or Credit? ");
        String payType = sc.next();

        float tender = 0;
        int cardNum = 0;
        boolean temp = true;
        /**
         * Loop runs while the Customer does not choose Cash or Credit.
         */
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
        /**
         * Gets the current date from the computer for the invoice
         */
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        
        /**
         * String is a 2D array that contains x amount of rows and 4 columns. 
         * x is equal to the amount of items in the cart.
         */
        String[][] invoice = cust.makePayment(store, payType, cardNum);
        System.out.printf("%-10s", cust.getName());
        System.out.print(ft.format(dNow) + "\n");

        //index will tell me the number of rows in invoice
        int index = -1;
        
        
        /**
         * System prints out the code, quantity, price and subtotal of each item. 
         */
        for (String[] code : invoice) {
            System.out.printf("%-10s%-5s%-8s%s\n", code[0], code[1], code[2], code[3]);
            index++;
        }
        
        /**
         * total is then printed out
         */
        System.out.println("------");
        System.out.printf("%-10s%s\n", "Total", invoice[index][3]);
        System.out.print("Amount Tendered: ");
        
        //Note that result isn't used in the case that a credit card is used
        float result = tender - Float.parseFloat(invoice[index][3]);
        if (payType.equals("Cash")) {
            System.out.print(tender + "\nAmount Returned: " + result + "\n");
        } else if (payType.equals("Credit")) {
            System.out.print("Credit Card " + cardNum + "\nAmount Returned: 0.00\n");
        }

    }

    public void showCart() {
        HashMap cart = cust.getCart();
        System.out.print(cust.getName());
        System.out.printf("%-5s%s\n", "Item", "Quantity");
        Iterator it = cart.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            System.out.printf("%-5s%s\n", pair.getKey(), pair.getValue());

        }
    }

    public boolean ManagerUI() throws IOException {
        Scanner sc = new Scanner(System.in);
        int temp;
        while (isRunning) {
            System.out.println("Please select an option: \n "
                    + "1) Open Store \n 2) Close Store \n 3) Add an item \n 4) Remove item \n 5) Exit Program \n 6) Quit User");
            temp = sc.nextInt();

            switch (temp) {
                case 1:
                    store.openStore();
                    break;
                case 2:
                    store.closeStore();
                    break;
                case 3:
                    manageAdd();
                    break;
                case 4:
                    manageRemove();
                    break;
                case 5:
                    isRunning = false;
                    break;
                case 6:
                    return true;
                default:
                    System.out.println("I'm sorry, that is not a valid option");
            }
        }
        return false;
    }

    public void viewCatalog() throws IOException {
        ArrayList catalog = cust.getCatalog(store);
        String[] temp;
        String temp_0;
        //regex expression delims says that the items are divided based on white space.
        String delims = "[ ]+";
        String code = "Code";
        String tem = "Item";
        String price = "Price";
        System.out.printf("%-10s%-25s%s\n", code, tem, price);
        //Each item in catalog is printed
        for (Object item : catalog) {
            temp_0 = item.toString();
            temp = temp_0.split(delims);

            System.out.printf("%-10s%-25s%s\n", temp[0], temp[1], temp[2]);
        }
    }

    /**
     * Item is removed from the catalog
     */
    public void manageRemove() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the code of the item to be removed: ");
        String code = sc.next();
        man.removeItem(code);
        System.out.println("Item removed.");
    }

    /**
     * Item is added to the catalog.
     * @throws IOException 
     */
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
                    startUI = post.CustomerUI();
                    break;
                case 2:
                    startUI = post.ManagerUI();
                    break;
                default:
                    System.out.println("Sorry, that is not a valid option. Please try again.");
                    break;
            }
        }
    }

}
