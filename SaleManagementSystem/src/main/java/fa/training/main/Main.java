package fa.training.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean isCont = true;
        SaleManagement saleManagement = new SaleManagement();

        while (isCont) {
            saleManagement.displayMenu();
            String answer = scanner.nextLine().trim();
            switch (answer) {
                case "1":
                    boolean isContListAll = true;
                    while (isContListAll) {
                        saleManagement.displayMenuListAll();
                        String answerListAll = scanner.nextLine().trim();
                        switch (answerListAll) {
                            case "1":
                                saleManagement.getAllCustomer();
                                break;
                            case "2":
                                saleManagement.getAllEmployee();
                                break;
                            case "3":
                                saleManagement.getAllLineitem();
                                break;
                            case "4":
                                saleManagement.getAllOrders();
                                break;
                            case "5":
                                saleManagement.getAllProduct();
                                break;
                            case "6":
                                isContListAll = false;
                                break;
                        }
                    }
                    break;
                case "2":
                    saleManagement.getAllOrderByCustomerId();
                    break;
                case "3":
                    saleManagement.getAllItemByOrderId();
                    break;
                case "4":
                    saleManagement.computeOrderTotal();
                    break;
                case "5":
                    saleManagement.addCustomer();
                    break;
                case "6":
                    saleManagement.deleteCustomer();
                    break;
                case "7":
                    saleManagement.updateCustomer();
                    break;
                case "8":
                    saleManagement.addOrder();
                    break;
                case "9":
                    saleManagement.addLineItem();
                    break;
                case "10":
                    saleManagement.updateOrderTotal();
                    break;
                case "0":
                    System.out.println("-------------------Exit program------------------");
                    isCont = false;
                    break;
            }
        }
    }
}
