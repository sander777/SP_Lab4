package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static class Reg {
        String pattern;
        String type;
        public Reg(String p, String t) {
            pattern = p;
            type = t;
        }
    }

    static class Matche {
        int start;
        int end;
        String type;

        public Matche(int s, int e, String t) {
            start = s;
            end = e;
            type = t;
        }
    }

    public static void main(String[] args) throws IOException {
        Reg[] rules = {
                new Reg("([0-9]+)", "integer"),
                new Reg("([0-9]+\\.[0-9]*)", "double"),
                new Reg("(0x[0-9]+)", "hex"),
                new Reg("or|not|and", "operator"),
                new Reg("import|as|in|def|return", "keyword"),
        };
        Reg delimiters = new Reg("[\\[\\]()\\{\\},:]+", "delimiters");
        Reg operators = new Reg("[\\+\\-\\\\*/%=<>]+", "operators");
        String out = "";
        String code = Files.readString(Path.of("input.py")).replaceAll("\r", "");
        int i = 0;
        String word = "";
        while (i <= code.length()) {
            if (i == code.length() || Character.toString(code.charAt(i)).matches(delimiters.pattern) || Character.toString(code.charAt(i)).matches(operators.pattern) || code.charAt(i) == ' ') {
                if (word.length() > 0 && !word.equals(" ") && !word.equals("\n")) {
                    String type = "";
                    for (Reg r: rules) {
                        if (word.matches(r.pattern)) {
                            type = r.type;
                        }
                    }
                    if(type.length() == 0) {
                        type = "name";
                    }
                    System.out.println(word + ": " + type);

                }
                if (i < code.length()) {
                    if (Character.toString(code.charAt(i)).matches(delimiters.pattern)) {
                        System.out.println(code.charAt(i) + ": " + "delimiters");
                    }
                    if (Character.toString(code.charAt(i)).matches(operators.pattern)) {
                        System.out.println(code.charAt(i) + ": " + "operators");
                    }
                }
                word = "";
                i++;
            } else {
                if (i < code.length()) {
                    word += code.charAt(i);
                }
                i++;
            }
        }
    }

}


