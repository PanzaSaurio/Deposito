-- Inserción de datos en la tabla product
INSERT INTO `product` (`SKU`, `brand`, `model`, `description`, `price`) VALUES
(1001, 'Sony', 'PlayStation 5', 'Consola de videojuegos de última generación', 499.99),
(1002, 'Microsoft', 'Xbox Series X', 'Consola de videojuegos de última generación', 499.99),
(2001, 'Intel', 'Core i9-11900K', 'Procesador de alto rendimiento', 549.99),
(2002, 'AMD', 'Ryzen 9 5900X', 'Procesador de alto rendimiento', 499.99),
(3001, 'NVIDIA', 'GeForce RTX 3080', 'Tarjeta gráfica de alta gama', 699.99),
(3002, 'AMD', 'Radeon RX 6800 XT', 'Tarjeta gráfica de alta gama', 649.99),
(4001, 'ASUS', 'ROG Strix Z590-E', 'Placa base para juegos', 349.99),
(4002, 'MSI', 'MPG B550 Gaming Edge', 'Placa base para juegos', 199.99),
(5001, 'Corsair', 'Vengeance LPX 16GB', 'Memoria RAM DDR4', 89.99),
(5002, 'G.Skill', 'Trident Z RGB 16GB', 'Memoria RAM DDR4', 99.99),
(6001, 'Sony', 'WH-1000XM4', 'Auriculares inalámbricos con cancelación de ruido', 349.99),
(6002, 'Bose', 'QuietComfort 35 II', 'Auriculares inalámbricos con cancelación de ruido', 299.99),
(7001, 'Logitech', 'G915 TKL', 'Teclado mecánico inalámbrico', 229.99),
(7002, 'Razer', 'BlackWidow V3 Pro', 'Teclado mecánico inalámbrico', 229.99),
(8001, 'Logitech', 'G502 HERO', 'Ratón para juegos', 79.99),
(8002, 'Razer', 'DeathAdder V2', 'Ratón para juegos', 69.99);

-- Inserción de datos en la tabla console
INSERT INTO `console` (`id_Product`, `storageCapacity`, `includesGames`, `isSpecialEdition`) VALUES
(1, '825GB', 1, 0),
(2, '1TB', 1, 0);

-- Inserción de datos en la tabla cpu
INSERT INTO `cpu` (`id_Product`, `clockSpeed`, `coreCount`, `threadCount`, `socketType`, `isInstalled`, `installationTime`, `installationGuideURL`) VALUES
(3, '3.5GHz', 8, 16, 'LGA1200', 0, 0, ''),
(4, '3.7GHz', 12, 24, 'AM4', 0, 0, '');

-- Inserción de datos en la tabla gpu
INSERT INTO `gpu` (`id_Product`, `memorySize`, `memoryType`, `coreClock`, `isInstalled`, `installationTime`, `installationGuideURL`) VALUES
(5, '10GB', 'GDDR6X', '1.71GHz', 0, 0, ''),
(6, '16GB', 'GDDR6', '1.82GHz', 0, 0, '');

-- Inserción de datos en la tabla motherboard
INSERT INTO `motherboard` (`id_Product`, `formFactor`, `chipset`, `socketType`, `memoryType`, `maxMemory`, `isInstalled`, `installationTime`, `installationGuideURL`) VALUES
(7, 'ATX', 'Z590', 'LGA1200', 'DDR4', 128, 0, 0, ''),
(8, 'ATX', 'B550', 'AM4', 'DDR4', 128, 0, 0, '');

-- Inserción de datos en la tabla ram
INSERT INTO `ram` (`id_Product`, `capacity`, `type`, `speed`, `formFactor`, `isInstalled`, `installationTime`, `installationGuideURL`) VALUES
(9, '16GB', 'DDR4', '3200MHz', 'DIMM', 0, 0, ''),
(10, '16GB', 'DDR4', '3600MHz', 'DIMM', 0, 0, '');

-- Inserción de datos en la tabla peripheral
INSERT INTO `peripheral` (`id_Product`, `type`, `connectionType`, `isWireless`) VALUES
(11, 'Auriculares', 'Bluetooth', 1),
(12, 'Auriculares', 'Bluetooth', 1),
(13, 'Teclado', 'Bluetooth', 1),
(14, 'Teclado', 'Bluetooth', 1),
(15, 'Ratón', 'USB', 0),
(16, 'Ratón', 'USB', 0);

-- Inserción de datos en la tabla headphones
INSERT INTO `headphones` (`id_Peripheral`, `type`, `weight`, `microphone`, `inputImpedance`, `sensitivity`, `frequencyResponse`, `micSensitivity`, `micFrequencyResponse`) VALUES
(1, 'Over-ear', 254, 1, 32, 105, 20000, 1, 1),
(2, 'Over-ear', 235, 1, 32, 115, 20000, 1, 1);

-- Inserción de datos en la tabla keyboard
INSERT INTO `keyboard` (`id_Peripheral`, `switchType`, `isMechanical`) VALUES
(3, 'GL Tactile', 1),
(4, 'Razer Green', 1);

-- Inserción de datos en la tabla mouse
INSERT INTO `mouse` (`id_Peripheral`, `dpi`, `isGamer`) VALUES
(5, 16000, 1),
(6, 20000, 1);

-- Inserción de datos en la tabla inventory
INSERT INTO `inventory` (`id_Product`, `stock`) VALUES
(1, 50),
(2, 50),
(3, 30),
(4, 30),
(5, 20),
(6, 20),
(7, 15),
(8, 15),
(9, 40),
(10, 40),
(11, 25),
(12, 25),
(13, 10),
(14, 10),
(15, 35),
(16, 35);
