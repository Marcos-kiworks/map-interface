package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        // Determinar la frecuencia de una palabra en un array o lista de palabras

        List<String> palabras = Arrays.asList("Antonio", "Antonio", "Juan", "Antonio", "Marcos", "Ruben");
        Map<String, Long> m = new HashMap<>();

        /* Para rellenar el mapa m primero utilizaremos una sentencia for mejorado */
        for (String palabra : palabras) {

            Long frecuenciaOcurrencia = m.get(palabra);

            m.put(palabra, frecuenciaOcurrencia == null ? 1L : ++frecuenciaOcurrencia);
        }

        System.err.println(m);

        /* Lo anterior está bien pero es antiguo, actualmente se puede obtener el
         * mismo resultado con operaciones de agregado, es decir, métodos de la clase Stream,
         * tuberias, lambdas, metodos
        * por referencia, en fin, PROGRAMACION FUNCIONAL)
         */
        Map<String, Long> m2 = palabras.stream()
                .collect(Collectors.groupingBy(palabra -> palabra, Collectors.counting()));

        System.out.println(m2);

        /* Ejemplos tipos de creación de mapas, colecciones map Interface */

 /* Ejemplo 1 : Recorrer la coleccion de personas y obtener
         * un mapa que agrupe las personas por género
         */
 /* Siempre que se recorra una lista del mismo tipo que el valor del mapa*/
 /* No hay que hacer absolutamente nada para obtener el valor del mapa*/
        List<Persona> personas = Persona.getPersonas();

        Map<Genero, List<Persona>> personasPorGenero = personas.stream()
                .collect(Collectors.groupingBy(persona -> persona.getGenero()));

        System.out.println("Colección de personas agrupadas por género");
        System.out.println(personasPorGenero);

        /* Ejemplo 2: Recorrer la lista de personas y obtener una nueva 
         * colección que agrupe nombres de personas, separados por punto y coma, por género
         * 
         */

        Map<Genero, String> nombresPorGenero = personas.stream()
            .collect(Collectors.groupingBy(Persona::getGenero, Collectors.mapping(Persona::getNombre, Collectors.joining(";"))));

    }
}
