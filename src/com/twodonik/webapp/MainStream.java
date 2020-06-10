package com.twodonik.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStream {

    public static void main(String[] args) {
        int[] val = {12, 2, 3, 8, 7, 53, 4, 4, 1, 2};
        System.out.println(minValue(val));

        List<Integer> integers = oddOrEven(Arrays.asList(12, 34, 2, 546, 1, 2, 3, 1));
        System.out.println(integers);
    }


    static int minValue(int[] values) {
        return IntStream.of(values).filter((x) -> x < 10 && x > 0).sorted().distinct().reduce(0, (n, p) -> n * 10 + p);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        if (integers.stream().mapToInt(Integer::intValue).sum() % 2 == 0) {
            return integers.stream().filter(t -> t % 2 == 0).collect(Collectors.toList());
        } else {
            return integers.stream().filter(t -> t % 2 != 0).collect(Collectors.toList());
        }
    }
}
