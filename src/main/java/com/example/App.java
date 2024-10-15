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

        System.out.println(nombresPorGenero);

        /* Un Map Interface no es realmente una colección porque no hereda de la interfaz Collection
         * pero se puede tratar como tal utilizando las llamadas vistas de colección ( Collection Views ),
         * para en cada momento acceder a las claves del mapa, a los valores o a ambos.
         * 
         * https://docs.oracle.com/javase/tutorial/collections/interfaces/map.html
         */

 /* Recorrer el mapa de personas agrupadas por Género, para mostrar solamente
          * las personas que tengan un salario superior a los 4000 euros
         */

 /* Utilizando una sentencia for mejorado */
        for (Map.Entry<Genero, List<Persona>> entry : personasPorGenero.entrySet()) {
            Genero key = entry.getKey();
            List<Persona> value = entry.getValue();

            System.out.println(" Del Género " + key);
            System.out.println(" Personas con salario superior a 4000 euros");

            for (Persona persona : value) {
                if (persona.getSalario() > 4000) {
                    System.out.println(persona);
                }
            }
        }

        /* Utilizando operaciones de agregado */
        System.out.println(" Utilizando operaciones de agregado ");

        personasPorGenero.entrySet().stream().forEach(entry -> {
            System.out.println(" Del Género " + entry.getKey());
            System.out.println(" Personas con salario superior a 4000 euros ");

            entry.getValue().stream().filter(p -> p.getSalario() > 4000).forEach(System.out::println);
        });

        /* Ejercicio 1: Crear una colección que agrupe Personas por Género y edad de la persona*/

 /* Posteriormente recorrer la colección obtenida y mostrar solamente las personas 
           * del género HOMBRE, que tengan un salario superior a la media */

 /* http://www.java2s.com/Tutorials/Java/Java_Stream/0220__Java_Stream_Map.htm */
        Map<Genero, Map<Long, List<Persona>>> personasGéneroEdad = personas.stream()
                .collect(Collectors.groupingBy(Persona::getGenero,
                        Collectors.groupingBy(Persona::edad)));

        /* Recuperar el salario promedio */
        final Double salarioMedio = personas.stream().mapToDouble(Persona::getSalario).average().orElseThrow(() -> new RuntimeException());
        /* Recorrer la colección personasGeneroEdad */
        personasGéneroEdad.entrySet().stream().forEach(entry1 -> {
            System.out.println(" Genero : " + entry1.getKey());
            Map<Long, List<Persona>> entry2 = entry1.getValue();

            entry2.entrySet().stream().forEach(entry -> {
                entry.getValue().stream().filter(persona -> persona.getGenero().equals(Genero.HOMBRE)
                        && persona.getSalario() > salarioMedio).forEach(System.out::println);
            });
        });

        /* Recorrer la colección personasGeneroEdad otra forma*/
        personasGéneroEdad.entrySet().stream().forEach(entry3 -> {
            Genero genero = entry3.getKey();
            System.out.println(" Género :" + genero);

            Map<Long, List<Persona>> entry4 = entry3.getValue();

            // var persons = entry4.values();
            // persons.stream().flatMap(lista -> lista.stream())
            // .filter(persona -> persona.getGenero().equals(Genero.HOMBRE) && persona.getSalario() > salarioMedio)
            // .forEach(System.out::println);

                /* Distinta forma, mismo resultado */

            entry4.entrySet().stream().forEach(entry -> {
                List<Persona> persons = entry.getValue();
                persons.stream().filter(p -> p.getGenero().equals(Genero.HOMBRE)
                        && p.getSalario() > salarioMedio)
                        .forEach(System.out::println);
            });
        });

    }
}
