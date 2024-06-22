package view;

import DAO.*;
import DAO.components.*;
import DAO.peripherals.*;
import DAO.videoGame.ConsoleDAO;
import controller.Validations;
import model.Product;
import model.components.*;
import model.peripherals.*;
import model.videoGame.Console;

import java.util.Scanner;

public class UpdateProduct {

    public static void updateProduct(Scanner scanner) {
        ProductDAO productDAO = new ProductDAO();

        System.out.print("Ingrese el SKU del producto a actualizar: ");
        int SKU = Validations.getIntInput(scanner, "SKU");

        Product product = productDAO.recover(String.valueOf(SKU));
        if (product == null) {
            System.out.println("No se pudo encontrar el producto con el SKU proporcionado.");
            return;
        }

        boolean updated = false;
        if (product instanceof Console) {
            updateConsole(scanner, (Console) product);
            updated = new ConsoleDAO().update((Console) product);
        } else if (product instanceof CPU) {
            updateCPU(scanner, (CPU) product);
            updated = new CPUDAO().update((CPU) product);
        } else if (product instanceof GPU) {
            updateGPU(scanner, (GPU) product);
            updated = new GPUDAO().update((GPU) product);
        } else if (product instanceof Motherboard) {
            updateMotherboard(scanner, (Motherboard) product);
            updated = new MotherboardDAO().update((Motherboard) product);
        } else if (product instanceof RAM) {
            updateRAM(scanner, (RAM) product);
            updated = new RAMDAO().update((RAM) product);
        } else if (product instanceof Headphones) {
            updateHeadphones(scanner, (Headphones) product);
            updated = new HeadphonesDAO().update((Headphones) product);
        } else if (product instanceof Keyboard) {
            updateKeyboard(scanner, (Keyboard) product);
            updated = new KeyboardDAO().update((Keyboard) product);
        } else if (product instanceof Mouse) {
            updateMouse(scanner, (Mouse) product);
            updated = new MouseDAO().update((Mouse) product);
        } else {
            System.out.println("Tipo de producto no soportado.");
            return;
        }

        if (!updated) {
            System.out.println("No se pudo actualizar el producto con el SKU proporcionado.");
        }
    }

    private static void updateConsole(Scanner scanner, Console console) {
        console.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de la consola"));
        console.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de la consola"));
        console.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de la consola"));
        console.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de la consola"));
        console.setStorageCapacity(Validations.getStringInput(scanner, "Ingrese la nueva capacidad de almacenamiento de la consola"));
        console.setIncludesGames(Validations.getBooleanInput(scanner, "¿La consola incluye juegos?"));
        console.setSpecialEdition(Validations.getBooleanInput(scanner, "¿La consola es edición especial?"));
    }

    private static void updateCPU(Scanner scanner, CPU cpu) {
        cpu.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de la CPU"));
        cpu.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de la CPU"));
        cpu.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de la CPU"));
        cpu.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de la CPU"));
        cpu.setClockSpeed(Validations.getStringInput(scanner, "Ingrese la nueva velocidad de reloj de la CPU"));
        cpu.setCoreCount(Validations.getIntInput(scanner, "Ingrese la nueva cantidad de núcleos de la CPU"));
        cpu.setThreadCount(Validations.getIntInput(scanner, "Ingrese la nueva cantidad de hilos de la CPU"));
        cpu.setSocketType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de socket de la CPU"));
        cpu.setInstalled(Validations.getBooleanInput(scanner, "¿Está instalada la CPU?"));
        cpu.setInstallationTime((float) Validations.getDoubleInput(scanner, "Ingrese el nuevo tiempo de instalación de la CPU"));
        cpu.setInstallationGuideURL(Validations.getStringInput(scanner, "Ingrese la nueva URL de la guía de instalación de la CPU"));
    }

    private static void updateGPU(Scanner scanner, GPU gpu) {
        gpu.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de la GPU"));
        gpu.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de la GPU"));
        gpu.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de la GPU"));
        gpu.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de la GPU"));
        gpu.setMemorySize(Validations.getStringInput(scanner, "Ingrese el nuevo tamaño de memoria de la GPU"));
        gpu.setMemoryType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de memoria de la GPU"));
        gpu.setCoreClock(Validations.getStringInput(scanner, "Ingrese la nueva velocidad de reloj de la GPU"));
        gpu.setInstalled(Validations.getBooleanInput(scanner, "¿Está instalada la GPU?"));
        gpu.setInstallationTime((float) Validations.getDoubleInput(scanner, "Ingrese el nuevo tiempo de instalación de la GPU"));
        gpu.setInstallationGuideURL(Validations.getStringInput(scanner, "Ingrese la nueva URL de la guía de instalación de la GPU"));
    }

    private static void updateMotherboard(Scanner scanner, Motherboard motherboard) {
        motherboard.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de la placa base"));
        motherboard.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de la placa base"));
        motherboard.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de la placa base"));
        motherboard.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de la placa base"));
        motherboard.setFormFactor(Validations.getStringInput(scanner, "Ingrese el nuevo factor de forma de la placa base"));
        motherboard.setChipset(Validations.getStringInput(scanner, "Ingrese el nuevo chipset de la placa base"));
        motherboard.setSocketType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de socket de la placa base"));
        motherboard.setMemoryType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de memoria de la placa base"));
        motherboard.setMaxMemory(Validations.getIntInput(scanner, "Ingrese la nueva memoria máxima de la placa base"));
        motherboard.setInstalled(Validations.getBooleanInput(scanner, "¿Está instalada la placa base?"));
        motherboard.setInstallationTime((float) Validations.getDoubleInput(scanner, "Ingrese el nuevo tiempo de instalación de la placa base"));
        motherboard.setInstallationGuideURL(Validations.getStringInput(scanner, "Ingrese la nueva URL de la guía de instalación de la placa base"));
    }

    private static void updateRAM(Scanner scanner, RAM ram) {
        ram.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de la RAM"));
        ram.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de la RAM"));
        ram.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de la RAM"));
        ram.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de la RAM"));
        ram.setCapacity(Validations.getStringInput(scanner, "Ingrese la nueva capacidad de la RAM"));
        ram.setType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de la RAM"));
        ram.setSpeed(Validations.getStringInput(scanner, "Ingrese la nueva velocidad de la RAM"));
        ram.setFormFactor(Validations.getStringInput(scanner, "Ingrese el nuevo factor de forma de la RAM"));
        ram.setInstalled(Validations.getBooleanInput(scanner, "¿Está instalada la RAM?"));
        ram.setInstallationTime((float) Validations.getDoubleInput(scanner, "Ingrese el nuevo tiempo de instalación de la RAM"));
        ram.setInstallationGuideURL(Validations.getStringInput(scanner, "Ingrese la nueva URL de la guía de instalación de la RAM"));
    }

    private static void updateHeadphones(Scanner scanner, Headphones headphones) {
        headphones.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca de los auriculares"));
        headphones.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo de los auriculares"));
        headphones.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción de los auriculares"));
        headphones.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio de los auriculares"));
        headphones.setType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de auriculares"));
        headphones.setConnectionType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de conexión de los auriculares"));
        headphones.setWireless(Validations.getBooleanInput(scanner, "¿Son inalámbricos los auriculares?"));
        headphones.setWeight((float) Validations.getDoubleInput(scanner, "Ingrese el nuevo peso de los auriculares"));
        headphones.setMicrophone(Validations.getBooleanInput(scanner, "¿Tienen micrófono los auriculares?"));
        headphones.setInputImpedance(Validations.getIntInput(scanner, "Ingrese la nueva impedancia de entrada de los auriculares"));
        headphones.setSensitivity(Validations.getIntInput(scanner, "Ingrese la nueva sensibilidad de los auriculares"));
        headphones.setFrequencyResponse(Validations.getIntInput(scanner, "Ingrese la nueva respuesta de frecuencia de los auriculares"));
        headphones.setMicSensitivity(Validations.getBooleanInput(scanner, "¿Tienen sensibilidad del micrófono los auriculares?"));
        headphones.setMicFrequencyResponse(Validations.getBooleanInput(scanner, "¿Tienen respuesta de frecuencia del micrófono los auriculares?"));
    }

    private static void updateKeyboard(Scanner scanner, Keyboard keyboard) {
        keyboard.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca del teclado"));
        keyboard.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo del teclado"));
        keyboard.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción del teclado"));
        keyboard.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio del teclado"));
        keyboard.setType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de teclado"));
        keyboard.setConnectionType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de conexión del teclado"));
        keyboard.setWireless(Validations.getBooleanInput(scanner, "¿Es inalámbrico el teclado?"));
        keyboard.setMechanical(Validations.getBooleanInput(scanner, "¿Es mecánico el teclado?"));
        keyboard.setSwitchType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de switch del teclado"));
    }

    private static void updateMouse(Scanner scanner, Mouse mouse) {
        mouse.setBrand(Validations.getStringInput(scanner, "Ingrese la nueva marca del mouse"));
        mouse.setModel(Validations.getStringInput(scanner, "Ingrese el nuevo modelo del mouse"));
        mouse.setDescription(Validations.getStringInput(scanner, "Ingrese la nueva descripción del mouse"));
        mouse.setPrice(Validations.getDoubleInput(scanner, "Ingrese el nuevo precio del mouse"));
        mouse.setType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de mouse"));
        mouse.setConnectionType(Validations.getStringInput(scanner, "Ingrese el nuevo tipo de conexión del mouse"));
        mouse.setWireless(Validations.getBooleanInput(scanner, "¿Es inalámbrico el mouse?"));
        mouse.setDpi(Validations.getIntInput(scanner, "Ingrese el nuevo DPI del mouse"));
        mouse.setGamer(Validations.getBooleanInput(scanner, "¿Es para gaming el mouse?"));
    }

}
