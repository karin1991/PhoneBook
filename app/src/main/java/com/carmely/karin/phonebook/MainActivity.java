package com.carmely.karin.phonebook;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ContactAdapter.OnClickListener,ContactFragment.ItemDeletedListener,
        OnItemClick {

    private static final int ASCENDING_ORDER = -1;
    private PhoneBook myPhoneBook;
    private RecyclerView recyclerView;
    private  ContactAdapter contactAdapter;
    static List<Contact> cList;
    static List<Contact> searchList;
    private EditText name;
    private EditText number;
    private Button addButton;
    private Button searchButton;
    private SmartSearch smartSearch;
    static Sorter sorter;
    private boolean endOfApp = false;
    DatabaseHelper db;
    private int wasSearch = 0;

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState)
    {
        super.onRestoreInstanceState(saveInstanceState);

    }

    @Override
    public void onClick (String value) {

        String[] contactValue = value.split(",");
        Contact contact = this.myPhoneBook.getContact(cList, contactValue[1], contactValue[2]);

        if(contactValue[0].equals("0"))
        {
            if(contact != null)
            {
                cList.remove(contact);
                db.deleteContact(contact);}

        }
        else if(contactValue[0].equals("1"))
        {
            this.myPhoneBook.getContact(cList, contactValue[1], contactValue[2]).setNumber(contactValue[3]);
            db.upDate(contactValue[3], contactValue[2], contactValue[1]);

        }




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        myPhoneBook = new PhoneBook(MainActivity.this);
        cList = new ArrayList<>();
        searchList = new ArrayList<>();
        smartSearch = new SmartSearch();
        sorter = new Sorter(ASCENDING_ORDER);

        myPhoneBook.setPhonesList(db.getAllContacts());
        cList = db.getAllContacts();
        sortList(ASCENDING_ORDER);
        contactAdapter = new ContactAdapter(myPhoneBook, MainActivity.this, this);

        recyclerView = findViewById(R.id.recycle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(contactAdapter);



        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        addButton = findViewById(R.id.addButton);
        searchButton = findViewById(R.id.searchButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")  && !number.getText().toString().equals(""))
                {
                    Contact contact = new Contact(name.getText().toString(), number.getText().toString());

                    if(contactAdapter.addContact(contact) == 0)
                    {
                        cList.add(contact);
                        sortList(ASCENDING_ORDER);
                        db.addContact(contact);
                    }






                }
                else if(!name.getText().toString().equals("")  && number.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "נא למלא מספר טלפון", Toast.LENGTH_SHORT).show();
                }

                else if(name.getText().toString().equals("")  && !number.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "נא למלא שם איש קשר", Toast.LENGTH_SHORT).show();
                }

                number.setText("");
                name.setText("");
                }


        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wasSearch = 1;
                String searchName = name.getText().toString();
                if(searchName.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "נא לכתוב שם במקום המתאים", Toast.LENGTH_SHORT).show();

                }
                else {
                    for (int i = 0; i < cList.size(); i++) {

                        if(smartSearch.RegexSearch(cList.get(i).getName(), searchName))
                        {
                            searchList.add(cList.get(i));
                            sortList(ASCENDING_ORDER);
                        }
                    }


                    ContactAdapter.myPhoneBook.getPhonesList().removeAll(ContactAdapter.myPhoneBook.getPhonesList());
                    ContactAdapter.myPhoneBook.getPhonesList().addAll(searchList);

                    contactAdapter.notifyDataSetChanged();
                    endOfApp = false;
                    if(ContactAdapter.myPhoneBook.getPhonesList().size() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "איש קשר לא נמצא", Toast.LENGTH_SHORT).show();

                    }

                }

                number.setText("");
                name.setText("");

            }
        });


    }


    public static void sortList(int order)

    {
        Collections.sort(cList, sorter);


    }

    public void onBackPressed() {

        if(endOfApp || wasSearch == 0)
        {
            moveTaskToBack(true);

        }
        else if(wasSearch == 1) {
            ContactAdapter.myPhoneBook.getPhonesList().removeAll(searchList);
            searchList.removeAll(searchList);
            ContactAdapter.myPhoneBook.getPhonesList().addAll(cList);
            ContactAdapter.update();
            contactAdapter.notifyDataSetChanged();
            endOfApp = true;
            wasSearch = 0;
        }

    }



    @Override
    public void onClick(Contact contact)
    {
        ContactFragment fragment = ContactFragment.newInstance(contact);
        getFragmentManager().beginTransaction().add(R.id.activity_base_frame, fragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onItemDeleted(Contact contact)
    {
        contactAdapter.removeContact(contact);
        getSupportFragmentManager().popBackStack();
    }
}
