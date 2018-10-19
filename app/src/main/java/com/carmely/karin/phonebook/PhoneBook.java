package com.carmely.karin.phonebook;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PhoneBook {

    private List<Contact> phonesList;
    private Context context;


    public int getSize()
    {
        return this.phonesList.size();
    }

    PhoneBook(Context context)
    {

        this.phonesList = new ArrayList<>();
        this.context = context;
    }

    public int addContact(Contact newContact)
    {
        if(checkIfExist(newContact) == 0)
        {
            this.phonesList.add(newContact);
            return 0;

        }
        Toast.makeText(context, "איש קשר קיים", Toast.LENGTH_SHORT).show();
        return 1;


    }

    public void setPhonesList(List<Contact> list)
    {
        this.phonesList = list;
    }

    private int checkIfExist(Contact contact)
    {
        for(int i = 0; i < this.phonesList.size(); i++)
        {
            if(this.phonesList.get(i).getName().equals(contact.getName()) &&
                    this.phonesList.get(i).getNumber().equals(contact.getNumber()))
            {
                return 1;
            }
        }
        return 0;
    }

    public Contact getContact(List<Contact> list, String name, String number)
    {
        for(Contact contact:list)
        {
            if(contact.getName().equals(name) && contact.getNumber().equals(number))
            {

                return contact;

            }

        }

        return null;


    }

    public List<Contact> getPhonesList()
    {
        return this.phonesList;
    }
}
