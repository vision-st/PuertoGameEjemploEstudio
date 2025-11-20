package com.puertogames.data;

import com.puertogames.model.Videojuego;
import java.util.*;
import java.io.*;
import org.apache.poi.xssf.usermodel.*;

public class GestorVideojuegos {
    private List<Videojuego> lista = new ArrayList<>();

    public void agregar(Videojuego vj) {
        lista.add(vj);
    }

    public void listarTodos() {
        for (Videojuego vj : lista) {
            System.out.println(vj);
        }
    }

    public List<Videojuego> buscarPorGenero(String genero) {
        List<Videojuego> resultado = new ArrayList<>();
        for (Videojuego vj : lista) {
            if (vj.getGenero().equalsIgnoreCase(genero)) {
                resultado.add(vj);
            }
        }
        return resultado;
    }

    public void cargarDesdeExcel(String ruta) {
        try (FileInputStream fis = new FileInputStream(ruta)) {
            XSSFWorkbook libro = new XSSFWorkbook(fis);
            XSSFSheet hoja = libro.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                var fila = hoja.getRow(i);
                String titulo = fila.getCell(0).getStringCellValue();
                String genero = fila.getCell(1).getStringCellValue();
                int precio = (int) fila.getCell(2).getNumericCellValue();
                agregar(new Videojuego(titulo, genero, precio));
            }
            libro.close();
        } catch (Exception e) {
            System.out.println("Error al leer Excel: " + e.getMessage());
        }
    }

    public void cargarDesdeJarExcel(String ruta) {
        // "ruta" será simplemente "videojuegos.xlsx" (el nombre del archivo dentro de resources)
        InputStream is = null;
        XSSFWorkbook libro = null;

        try {
            // ← ESTA ES LA LÍNEA MÁGICA que funciona dentro y fuera del JAR
            is = getClass().getClassLoader().getResourceAsStream(ruta);

            if (is == null) {
                System.err.println("Error al leer Excel: no se encontró '" + ruta + "' dentro del JAR");
                System.err.println("   Asegúrate de que el archivo está en src/main/resources/" + ruta);
                return;
            }

            libro = new XSSFWorkbook(is);
            XSSFSheet hoja = libro.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                XSSFRow fila = hoja.getRow(i);
                if (fila == null) continue; // protección por filas vacías

                String titulo = getStringCellValue(fila.getCell(0));
                String genero  = getStringCellValue(fila.getCell(1));

                // Precio puede venir como número o como texto, así que lo manejamos con seguridad
                int precio = (int) getNumericCellValue(fila.getCell(2));

                agregar(new Videojuego(titulo, genero, precio));
            }

            System.out.println("Excel cargado correctamente desde el JAR (" + ruta + ")");

        } catch (Exception e) {
            System.err.println("Error al leer Excel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerramos todo sin que lance excepciones
            try {
                if (libro != null) libro.close();
                if (is != null) is.close();
            } catch (IOException e) {
                System.err.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }

    // Métodos auxiliares para evitar NullPointer y errores de tipo de celda
    private String getStringCellValue(XSSFCell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()).trim();
            case BLANK -> "";
            default -> cell.toString().trim();
        };
    }

    private double getNumericCellValue(XSSFCell cell) {
        if (cell == null) return 0;
        return switch (cell.getCellType()) {
            case NUMERIC -> cell.getNumericCellValue();
            case STRING -> Double.parseDouble(cell.getStringCellValue().replaceAll("[^\\d.]", ""));
            default -> 0;
        };
    }
}
