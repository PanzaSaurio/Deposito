package view;

import DAO.ProductDAO;
import DAO.components.CPUDAO;

import java.util.Scanner;


public class Menu {

    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void viewMenu() {
        int opcion;
        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Crear Producto");
            System.out.println("2. Eliminar Producto");
            System.out.println("3. Modificar Producto");
            System.out.println("4. Mostrar Productos");
            System.out.println("5. Inventario");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    CreateProduct(scanner);
                    break;
                case 2:
                    DeleteProduct.deleteProduct(scanner);
                    break;
                case 3:
                    UpdateProduct.updateProduct(scanner);
                    break;
                case 4:
                    listProduct();
                    break;
                case 5:
                    inventoryMenu();
                    break;
                case 0:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 0);
    }

    public void CreateProduct(Scanner scanner) {
        int opcion;
        do {
            System.out.println("Menú de opciones:");
            System.out.println("1. Crear Console");
            System.out.println("2. Crear CPU");
            System.out.println("3. Crear GPU");
            System.out.println("4. Crear Motherboard");
            System.out.println("5. Crear RAM");
            System.out.println("6. Crear Headphones");
            System.out.println("7. Crear Keyboard");
            System.out.println("8. Crear Mouse");
            System.out.println("0. Volver al Menu Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    CreateProduct.createConsole(scanner);
                    break;
                case 2:
                    CreateProduct.createCPU(scanner);
                    break;
                case 3:
                    CreateProduct.createGPU(scanner);
                    break;
                case 4:
                    CreateProduct.createMotherboard(scanner);
                    break;
                case 5:
                    CreateProduct.createRAM(scanner);
                    break;
                case 6:
                    CreateProduct.createHeadphones(scanner);
                    break;
                case 7:
                    CreateProduct.createKeyboard(scanner);
                    break;
                case 8:
                    CreateProduct.createMouse(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal.....");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 0);
    }

    public void listProduct() {
        int opcion;
        do {
            System.out.println("Opciones de listado:");
            System.out.println("1. Mostrar todos los productos");
            System.out.println("2. Mostrar consolas");
            System.out.println("3. Mostrar componentes de PC");
            System.out.println("4. Mostrar periféricos");
            System.out.println("5. Mostrar por SKU");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    ListProduct.listAllProducts();
                    break;
                case 2:
                    ListProduct.listConsoles();
                    break;
                case 3:
                    ListProduct.listComponentsPC();
                    break;
                case 4:
                    ListProduct.listPeripherals();
                    break;
                case 5:
                    ListProduct.listProductSKU(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menu principal.....");
                    return;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 0);
    }


    public void inventoryMenu() {
        int opcion;
        do {
            System.out.println("Menú de Inventario:");
            System.out.println("1. Listar Inventario");
            System.out.println("2. Buscar Stock por SKU");
            System.out.println("3. Modificar Stock por SKU");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    InventoryCRUD.listInventory();
                    break;
                case 2:
                    InventoryCRUD.searchStockBySKU(scanner);
                    break;
                case 3:
                    InventoryCRUD.updateStockBySKU(scanner);
                    break;
                case 0:
                    System.out.println("Volviendo al menú principal.....");
                    return;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 0);

    }



}
