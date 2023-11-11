package com.vinsguru.sec09;

import java.util.stream.IntStream;

public class MapMulti {

    public static void main(String[] args) {

//        IntStream.rangeClosed(1, 5)
//                 .mapMulti((a, b) -> {
//                     for (int i = 0; i < a; i++) {
//                         b.accept(a);
//                     }
//                 })
//                 .forEach(System.out::println);

        IntStream.rangeClosed(1, 5)
                .flatMap(i -> IntStream.rangeClosed(1, i).map(a -> i))
                 .forEach(System.out::println);



    }

}
