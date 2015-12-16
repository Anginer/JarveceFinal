package com.project.jarvece.GUI.Historique;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.jarvece.Class.Contact;
import com.project.jarvece.GUI.Chat.F_Chat;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.R;
import com.project.jarvece.Session;

import java.util.ArrayList;

/**
 * Created by Clément on 16/12/2015.
 */
public class F_Historique extends CC_Fragment implements OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton buttonBack;
    private TextView textViewDiscussion,textViewHistorique;
    private ListView listViewHistoriqueChat;
    private ArrayList<String> listContact = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterString;
    @Override
    public void onCreateView(View convertView) {

        buttonBack = (ImageButton) convertView.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);

        textViewDiscussion = (TextView) convertView.findViewById(R.id.textViewDiscussion);
        textViewHistorique = (TextView) convertView.findViewById(R.id.textViewHistorique);

        listViewHistoriqueChat = (ListView) convertView.findViewById(R.id.listViewHistoriqueChat);
        listViewHistoriqueChat.setOnItemClickListener(this);
        Session.currentUser.listContact = Session.NoDoublon(Session.currentUser.getListContact());
        for(Contact contact : Session.currentUser.listContact)
        {
            listContact.add(contact.pseudo);
        }
        arrayAdapterString = new ArrayAdapter<String>(getActivity(), R.layout.a_textview_little_center,R.id.textViewAdapter, listContact);
        listViewHistoriqueChat.setAdapter(arrayAdapterString);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //position de l'item cliqué : position
        listViewHistoriqueChat.setVisibility(View.GONE);
        textViewDiscussion.setVisibility(View.VISIBLE);
        textViewDiscussion.setText(Session.currentUser.listContact.get(position).getChat());
        textViewHistorique.setText(Session.currentUser.listContact.get(position).getPseudo());

    }

    @Override
    public void onClick(View view, Integer id) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public Integer getLayoutId() {
        return R.layout.f_historique;
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBack:
                changeFragment(new F_Chat());
                break;
        }

    }

}
