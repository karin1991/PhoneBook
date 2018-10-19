package com.carmely.karin.phonebook;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactFragment extends Fragment{

    @BindView(R.id.nameContact)
    TextView name;
    @BindView(R.id.phoneNum)
    EditText phoneNum;
    ItemDeletedListener listener;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name", name.toString());
        outState.putString("number", phoneNum.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        if ((saveInstanceState != null)
                && (saveInstanceState.getString("name") != null) && (saveInstanceState.getString("number") != null))
        {
            name.setText(saveInstanceState
                    .getString("name"));

            phoneNum.setText(saveInstanceState
                    .getString("number"));
        }

        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof  ItemDeletedListener)
        {
            this.listener = (ItemDeletedListener) context;
        }
    }

    public static ContactFragment newInstance(Contact contact)
    {
        ContactFragment contactFragment = new ContactFragment();
        Bundle bundle = new Bundle();

        bundle.putString("contact", contact.toString());
        contactFragment.setArguments(bundle);

        return contactFragment;
    }



    public interface ItemDeletedListener
    {
        void onItemDeleted(Contact contact);
    }

}
