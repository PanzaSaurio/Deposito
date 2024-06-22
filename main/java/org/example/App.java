package org.example;

import view.Menu;

public class App
{
    public static void main( String[] args )
    {
        System.out.println("Bienvenido al sistema de gestión de inventario!");

        Menu menu = new Menu();
        menu.viewMenu();


    }
}
