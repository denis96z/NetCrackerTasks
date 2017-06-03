package ru.ncedu.java.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckerImpl implements Checker {
    @Override
    public Pattern getPLSQLNamesPattern() {
        String s = "^[a-zA-Z][a-zA-Z0-9_$]{0,29}$";
        return Pattern.compile(s);
    }

    @Override
    public Pattern getHrefURLPattern() {
        String s = "^<\\s*(A|a)\\s+(H|h)(R|r)(E|e)(F|f)\\s*=\\s*" +
                "(([\"](\\s*[a-zA-Z0-9.]*)*[\"])|[a-zA-Z0-9.]*)\\s*>$";
        return Pattern.compile(s);
    }

    @Override
    public Pattern getEMailPattern() {
        String s = "^[a-zA-Z0-9]([a-zA-Z0-9._-]{0,20}[a-zA-Z0-9])?" + 
                "@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9][.])+" +
                "((ru)|(com)|(net)|(org))$";
        return Pattern.compile(s);
    }

    @Override
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {
        if (inputString == null && pattern == null) {
            return true;
        }
        else if (inputString == null || pattern == null) {
            throw new IllegalArgumentException();
        }
        else {
            return pattern.matcher(inputString).matches();
        }
    }

    @Override
    public List<String> fetchAllTemplates(StringBuffer inputString, Pattern pattern) throws IllegalArgumentException {
        List<String> allMatches = new ArrayList<String>();
        
        Matcher matcher = pattern.matcher(inputString);
        while(matcher.find()) {
            allMatches.add(matcher.group());
        }
        
        return allMatches;
    }
    
    public static void main(String[] args) {
        Checker c = new CheckerImpl();
        System.out.println(c.getPLSQLNamesPattern().matcher("a234567_______544$$01234560").matches());
    }
}
