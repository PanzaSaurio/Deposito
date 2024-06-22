package view;

import DAO.peripherals.*;
import DAO.videoGame.ConsoleDAO;
import DAO.components.*;
import model.peripherals.*;
import model.videoGame.Console;
import model.components.*;
import controller.Validations;

import java.util.Scanner;


public class CreateProduct {

    public static void createConsole(Scanner scanner) {
        ConsoleDAO consoleDAO = new ConsoleDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String storageCapacity = Validations.getStringInput(scanner, "Capacidad de almacenamiento");
        boolean includesGames = Validations.getBooleanInput(scanner, "Incluye juegos");
        boolean isSpecialEdition = Validations.getBooleanInput(scanner, "Es edición especial");

        Console console = new Console(SKU, brand, model, description, price, storageCapacity, includesGames, isSpecialEdition);
        consoleDAO.create(console);
        System.out.println("Console creada con éxito.");
    }

    public static void createCPU(Scanner scanner) {
        CPUDAO cpuDAO = new CPUDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String clockSpeed = Validations.getStringInput(scanner, "Velocidad de reloj");
        int coreCount = Validations.getIntInput(scanner, "Cantidad de núcleos");
        int threadCount = Validations.getIntInput(scanner, "Cantidad de hilos");
        String socketType = Validations.getStringInput(scanner, "Tipo de socket");
        boolean installed = Validations.getBooleanInput(scanner, "Instalado");
        float installationTime = (float) Validations.getDoubleInput(scanner, "Tiempo de instalación");
        String installationGuideURL = Validations.getStringInput(scanner, "URL de la guía de instalación");

        CPU cpu = new CPU(SKU, brand, model, description, price, clockSpeed, coreCount, threadCount, socketType, installed, installationTime, installationGuideURL);
        cpuDAO.create(cpu);
        System.out.println("CPU creada con éxito.");
    }

    public static void createGPU(Scanner scanner) {
        GPUDAO gpuDAO = new GPUDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String memorySize = Validations.getStringInput(scanner, "Tamaño de memoria");
        String memoryType = Validations.getStringInput(scanner, "Tipo de memoria");
        String coreClock = Validations.getStringInput(scanner, "Velocidad del reloj");
        boolean installed = Validations.getBooleanInput(scanner, "Instalado");
        float installationTime = (float) Validations.getDoubleInput(scanner, "Tiempo de instalación");
        String installationGuideURL = Validations.getStringInput(scanner, "URL de la guía de instalación");

        GPU gpu = new GPU(SKU, brand, model, description, price, memorySize, memoryType, coreClock, installed, installationTime, installationGuideURL);
        gpuDAO.create(gpu);
        System.out.println("GPU creada con éxito.");
    }

    public static void createMotherboard(Scanner scanner) {
        MotherboardDAO motherboardDAO = new MotherboardDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String formFactor = Validations.getStringInput(scanner, "Factor de forma");
        String chipset = Validations.getStringInput(scanner, "Chipset");
        String socketType = Validations.getStringInput(scanner, "Tipo de socket");
        String memoryType = Validations.getStringInput(scanner, "Tipo de memoria");
        int maxMemory = Validations.getIntInput(scanner, "Memoria máxima");
        boolean installed = Validations.getBooleanInput(scanner, "Instalado");
        float installationTime = (float) Validations.getDoubleInput(scanner, "Tiempo de instalación");
        String installationGuideURL = Validations.getStringInput(scanner, "URL de la guía de instalación");

        Motherboard motherboard = new Motherboard(SKU, brand, model, description, price, formFactor, chipset, socketType, memoryType, maxMemory, installed, installationTime, installationGuideURL);
        motherboardDAO.create(motherboard);
        System.out.println("Motherboard creada con éxito.");
    }

    public static void createRAM(Scanner scanner) {
        RAMDAO ramDAO = new RAMDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String capacity = Validations.getStringInput(scanner, "Capacidad");
        String type = Validations.getStringInput(scanner, "Tipo");
        String speed = Validations.getStringInput(scanner, "Velocidad");
        String formFactor = Validations.getStringInput(scanner, "Factor de forma");
        boolean installed = Validations.getBooleanInput(scanner, "Instalado");
        float installationTime = (float) Validations.getDoubleInput(scanner, "Tiempo de instalación");
        String installationGuideURL = Validations.getStringInput(scanner, "URL de la guía de instalación");

        RAM ram = new RAM(SKU, brand, model, description, price, capacity, type, speed, formFactor, installed, installationTime, installationGuideURL);
        ramDAO.create(ram);
        System.out.println("RAM creada con éxito.");
    }

    public static void createHeadphones(Scanner scanner) {
        HeadphonesDAO headphonesDAO = new HeadphonesDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String typeHeadphones = Validations.getStringInput(scanner, "Tipo de auriculares");
        String connectionType = Validations.getStringInput(scanner, "Tipo de conexión");
        boolean isWireless = Validations.getBooleanInput(scanner, "Es inalámbrico");
        float weight = (float) Validations.getDoubleInput(scanner, "Peso");
        boolean microphone = Validations.getBooleanInput(scanner, "Micrófono");
        int inputImpedance = Validations.getIntInput(scanner, "Impedancia de entrada");
        int sensitivity = Validations.getIntInput(scanner, "Sensibilidad");
        int frequencyResponse = Validations.getIntInput(scanner, "Respuesta de frecuencia");
        boolean micSensitivity = Validations.getBooleanInput(scanner, "Sensibilidad del micrófono");
        boolean micFrequencyResponse = Validations.getBooleanInput(scanner, "Respuesta de frecuencia del micrófono");

        Headphones headphones = new Headphones(SKU, brand, model, description, price, typeHeadphones, connectionType, isWireless, typeHeadphones, weight, microphone, inputImpedance, sensitivity, frequencyResponse, micSensitivity, micFrequencyResponse);
        headphonesDAO.create(headphones);
        System.out.println("Auriculares creados con éxito.");
    }

    public static void createKeyboard(Scanner scanner) {
        KeyboardDAO keyboardDAO = new KeyboardDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String type = Validations.getStringInput(scanner, "Tipo de teclado");
        String connectionType = Validations.getStringInput(scanner, "Tipo de conexión");
        boolean isWireless = Validations.getBooleanInput(scanner, "Es inalámbrico");
        boolean isMechanical = Validations.getBooleanInput(scanner, "Es mecánico");
        String switchType = Validations.getStringInput(scanner, "Tipo de switch");

        Keyboard keyboard = new Keyboard(SKU, brand, model, description, price, type, connectionType, isWireless, isMechanical, switchType);
        keyboardDAO.create(keyboard);
        System.out.println("Teclado creado con éxito.");
    }

    public static void createMouse(Scanner scanner) {
        MouseDAO mouseDAO = new MouseDAO();

        int SKU = Validations.getIntInput(scanner, "SKU");
        String brand = Validations.getStringInput(scanner, "Marca");
        String model = Validations.getStringInput(scanner, "Modelo");
        String description = Validations.getStringInput(scanner, "Descripción");
        double price = Validations.getDoubleInput(scanner, "Precio");
        String type = Validations.getStringInput(scanner, "Tipo de mouse");
        String connectionType = Validations.getStringInput(scanner, "Tipo de conexión");
        boolean isWireless = Validations.getBooleanInput(scanner, "Es inalámbrico");
        int dpi = Validations.getIntInput(scanner, "DPI");
        boolean isGamer = Validations.getBooleanInput(scanner, "Es para gaming");

        Mouse mouse = new Mouse(SKU, brand, model, description, price, type, connectionType, isWireless, dpi, isGamer);
        mouseDAO.create(mouse);
        System.out.println("Mouse creado con éxito.");
    }



}
