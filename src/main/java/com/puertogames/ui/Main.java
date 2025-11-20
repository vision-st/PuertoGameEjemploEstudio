package com.puertogames.ui;

import com.puertogames.data.GestorVideojuegos;
import com.puertogames.model.Videojuego;

public class Main {
    public static void main(String[] args) {
        GestorVideojuegos gestor = new GestorVideojuegos();
        gestor.cargarDesdeExcel("videojuegos.xlsx"); //funciona en el ide pero no en el jar
        gestor.cargarDesdeJarExcel("videojuegos.xlsx"); // funciona para el ide y el jar

        System.out.println(" Todos los videojuegos:");
        gestor.listarTodos();

        System.out.println("\n Videojuegos de Aventura:");
        for (Videojuego vj : gestor.buscarPorGenero("Aventura")) {
            System.out.println(vj);
        }
    }
}
