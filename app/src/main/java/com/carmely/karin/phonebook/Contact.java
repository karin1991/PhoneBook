package com.carmely.karin.phonebook;

public class Contact {

    private String name;
    private String number;


    Contact(String name, String number)
    {
        this.name = name;
        this.number = number;
    }
    Contact(){}

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }
}
