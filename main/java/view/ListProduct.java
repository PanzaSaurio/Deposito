package view;

import java.util.Scanner;
import java.util.List;
import DAO.ProductDAO;
import model.Product;


public class ListProduct {

    public static void listProductSKU(Scanner scanner ){

        ProductDAO productDAO = new ProductDAO();

        System.out.print("Ingrese el SKU del producto: ");
        String SKU = scanner.nextLine();
        Product producto = productDAO.recover(SKU);
        if (producto != null) {
            System.out.println(producto.toString());
        } else {
            System.out.println("No se encontró el producto con el SKU proporcionado.");
        }

    }

    public static void listPeripherals(){
        ProductDAO productDAO = new ProductDAO();

        List<Product> perifericos = productDAO.listPeripherals();
        perifericos.forEach(System.out::println);
    }

    public static void listComponentsPC() {
        ProductDAO productDAO = new ProductDAO();

        List<Product> componentesPC = productDAO.listComponents();
        componentesPC.forEach(System.out::println);
    }


    public static void listConsoles (){
        ProductDAO productDAO = new ProductDAO();

        List<Product> consolas = productDAO.listConsoles();
        consolas.forEach(System.out::println);
    }

    public static void listAllProducts() {
        ProductDAO productDAO = new ProductDAO();

        List<Product> todosLosProductos = productDAO.listAllProducts();
        todosLosProductos.forEach(System.out::println);
    }



}
