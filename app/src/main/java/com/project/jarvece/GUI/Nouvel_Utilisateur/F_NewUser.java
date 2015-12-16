package com.project.jarvece.GUI.Nouvel_Utilisateur;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.jarvece.Class.User;
import com.project.jarvece.GUI.EcranPairing.F_EcranPairing;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.R;
import com.project.jarvece.Session;

/**
 * Created by Clément on 15/12/2015.
 */
public class F_NewUser extends CC_Fragment implements View.OnClickListener {

    Button buttonSave;
    EditText editTextLogin, editTextPseudo, editTextPassword;
    String login,pseudo,password;

    @Override
    public void onCreateView(View convertView) {

        buttonSave = (Button) convertView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(this);

        editTextLogin= (EditText) convertView.findViewById(R.id.editTextLogin);
        editTextPseudo= (EditText) convertView.findViewById(R.id.editTextPseudo);
        editTextPassword= (EditText) convertView.findViewById(R.id.editTextPassword);

    }

    @Override
    public void onClick(View view, Integer id) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public Integer getLayoutId() {
        return R.layout.f_newuser;
    }
    @Override
    public void onBackPressed() {

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSave:
                if(editTextLogin.getText().toString().trim().length() > 0) {
                    if(!Session.listUserLogin.contains(login))
                    {
                        if(editTextPassword.getText().toString().trim().length() > 0) {
                            if (editTextPseudo.getText().toString().trim().length() > 0) {

                                login = editTextLogin.getText().toString();
                                pseudo = editTextPseudo.getText().toString();
                                password = editTextPassword.getText().toString();
                                User u = new User(login,pseudo,password);//this.getActivity().getFilesDir().toString(),password);
                                Session.listUser.add(u);
                                Session.listUserLogin.add(login);
                                Session.currentUser = u;
                                Session.WriteSaveUser(this.getActivity());
                                changeFragment(new F_EcranPairing());
                            }
                        }
                    }
                }

                break;

        }

    }
}
