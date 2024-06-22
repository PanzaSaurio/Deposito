package model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private Map<Integer, Integer> products;

    public Inventory(){
        products = new HashMap<>();
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void addProduct(int id_Product, int initialStock) {
        if (products.containsKey(id_Product)) {
            System.out.println("El producto ya existe en el inventario.");
        } else {
            products.put(id_Product, initialStock);
            //System.out.println("Producto agregado: ID " + id_Product);
        }
    }

    public void updateStock(int id_Product, int quantity) {
        if (products.containsKey(id_Product)) {
            int newStock = products.get(id_Product) + quantity;
            products.put(id_Product, newStock);
            System.out.println("Se actualizó el stock del producto ID " + id_Product + ". Nuevo stock: " + newStock);
        } else {
            System.out.println("El producto con el ID " + id_Product + " no fue encontrado.");
        }
    }

    public void listProducts() {
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Integer id_Product = entry.getKey();
            Integer stock = entry.getValue();
            System.out.println("Producto ID: " + id_Product + ", Stock: " + stock);
        }
    }

    public Integer getStock(int id_Product) {
        return products.get(id_Product);
    }
}
