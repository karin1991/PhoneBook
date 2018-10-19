package com.carmely.karin.phonebook;

import java.util.Comparator;

class Sorter implements Comparator<Contact>
{
    int order = -1;

    Sorter(int order)
    {
        this.order = order;
    }

    public int compare(Contact c1, Contact c2)
    {
        if(c1.getName().compareTo(c2.getName()) == 0){return 0;}
        else if (c1.getName().compareTo(c2.getName()) < 0){return order;}
        else return (-1*order);
    }
}
