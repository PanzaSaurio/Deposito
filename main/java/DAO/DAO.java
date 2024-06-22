package DAO;

import model.Product;

import java.util.List;

public interface DAO <T>{

    void create (T t);
    List<T> list ();
    T recover (String SKU);
    boolean update(T t);
    boolean delete(String SKU);
    String getProductTypeBySKU (String SKU);
    T recoverById(int id_Product);


}
