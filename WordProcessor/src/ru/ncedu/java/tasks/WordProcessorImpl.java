package ru.ncedu.java.tasks;

import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordProcessorImpl implements WordProcessor {
    String text = null;
    
    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setSource(String src) {
        if (src == null) {
            throw new IllegalArgumentException();
        }
        text = src;
    }

    @Override
    public void setSourceFile(String srcFile) throws IOException {
        if (srcFile == null) {
            throw new IllegalArgumentException();
        }
        text = new String(Files.readAllBytes(Paths.get(srcFile)));
    }

    @Override
    public void setSource(FileInputStream fis) throws IOException {
        if (fis == null) {
            throw new IllegalArgumentException();
        }
        
        text = new String();
        char c = (char)fis.read();
        while (c != -1) {
            text += c;
        }
    }
    
    private String makePattern(String begin) {
        String pattern = "^";
        
        if (begin != null && !begin.isEmpty()) {
            for (int i = 0; i < begin.length(); i++) {
                String c = begin.substring(i, i + 1);
                pattern += "(" + c.toLowerCase() + "|" + c.toUpperCase() + ")";
            }
            pattern += "[a-zA-Z]*[,.\"]?$";
        }
        else {
            pattern += "[,.\"]?[a-zA-Z]+[,.\"]?$";
        }

        return pattern;
    }

    @Override
    public Set<String> wordsStartWith(String begin) {
        if (text == null) {
            throw new IllegalStateException();
        }
        
        String[] words = text.toLowerCase().split("\\s");       
        Set<String> matches = new HashSet<>();
        
        Pattern pattern = Pattern.compile(makePattern(begin));
        for (String word : words) {
            Matcher matcher = pattern.matcher(word);
            if (matcher.matches()) {
                matches.add(word);
            }
        }
        
        return matches;
    }
}
