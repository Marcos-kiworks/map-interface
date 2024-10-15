package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FlatMapDemo {

    public static void main(String[] args) {
        
        /* El método flatmap aplana listas, es muy útil cuando
         * se tienen listas anidadas, y además no tendría en cuenta
         * alguna lista que estuviese vacia.*/

         List<List<String>> listaDeLista = Arrays.asList(
            Arrays.asList("Rubén","Antonio"),
            Arrays.asList("Juan"),
            Arrays.asList("Marcos")
         );

        listaDeLista.stream().flatMap(l -> l.stream()).forEach(System.out::println);

        List<String> lista1 = List.of("Ivan","Juan");
        List<String> lista2 = List.of("Antonio");
        List<String> lista3 = List.of("Rubén","Marcos");

         Stream<List<String>> flujoDeListas = Stream.of(lista1,lista2,lista3);


         System.out.println("Flujo de listas aplanado cortesía de FlatMap");
         flujoDeListas.flatMap(l -> l.stream()).forEach(System.out::println);
    }

}
