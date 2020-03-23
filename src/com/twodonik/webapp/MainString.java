package com.twodonik.webapp;

public class MainString {
    public static void main(String[] args) {
        String[] line = new String[]{"1", "2", "3", "4", "5"};
        StringBuilder sb = new StringBuilder();
        for (String s : line) {
            sb.append(s + ", ");
        }
        System.out.println(sb.toString());
    }
}
