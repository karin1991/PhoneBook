package com.carmely.karin.phonebook;



import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.TextView;


import java.util.Collections;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private static final int ASCENDING_ORDER = -1;
    static PhoneBook myPhoneBook;

    private Context context;
    private OnItemClick mCallback;
    private static Sorter sorter;




    public int addContact(Contact newContact)
    {
        int val = myPhoneBook.addContact(newContact);
        sortList(ASCENDING_ORDER);
        notifyDataSetChanged();

        return val;
    }



    public void removeContact(Contact contact)
    {
        for(int i = 0 , size = myPhoneBook.getPhonesList().size(); i < size; i++)
        {
            if(contact.equals(myPhoneBook.getPhonesList().get(i)))
            {

                myPhoneBook.getPhonesList().remove(i);
                notifyItemRemoved(i);
                return;
            }
        }
        throw new IllegalArgumentException("");
    }



    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        EditText number;
        ImageButton deleteButton;
        ImageButton editButton;



        public MyViewHolder(View view)
        {
            super(view);
            name = view.findViewById(R.id.nameContact);
            number = view.findViewById(R.id.phoneNum);
            deleteButton = view.findViewById(R.id.deleteButton);
            editButton = view.findViewById(R.id.editButton);

        }



    }
    public static void sortList(int order)

    {
        Collections.sort(myPhoneBook.getPhonesList(), sorter);


    }




    interface OnClickListener
    {
        void onClick(Contact contact);
    }

    public static void update()
    {
        sortList(ASCENDING_ORDER);

    }



    public ContactAdapter(PhoneBook phoneBook, Context context, OnItemClick listener)
    {
        myPhoneBook = phoneBook;
        this.context = context;
        this.mCallback = listener;
        sorter = new Sorter(ASCENDING_ORDER);
        sortList(ASCENDING_ORDER);
        this.notifyDataSetChanged();

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position)
    {
        final Contact contact = myPhoneBook.getPhonesList().get(position);


        holder.name.setText(contact.getName());
        holder.number.setText(contact.getNumber());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick("0,"+ holder.name.getText().toString() + "," + holder.number.getText().toString());
                removeContact(myPhoneBook.getPhonesList().get(holder.getAdapterPosition()));


            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick("1," +contact.getName() + "," + contact.getNumber() +"," + holder.number.getText().toString());



            }
        });

    }



    @Override
    public int getItemCount()
    {
        return myPhoneBook.getPhonesList().size();
    }






}
