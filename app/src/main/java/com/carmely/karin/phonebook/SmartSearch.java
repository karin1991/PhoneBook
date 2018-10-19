package com.carmely.karin.phonebook;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmartSearch {

    private List myList;


    SmartSearch(List list)
    {
        this.myList = list;
    }

    SmartSearch()
    {}


    public boolean RegexSearch(String sentence, String word)
    {
        Pattern r = Pattern.compile(word);
        Matcher m = r.matcher(sentence);
        if (m.find( )) {
            System.out.println("MATCH");
            return true;
        }

        System.out.println("NO MATCH");
        return false;
    }
}
