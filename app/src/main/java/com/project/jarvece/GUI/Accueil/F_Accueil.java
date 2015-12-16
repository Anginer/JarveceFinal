package com.project.jarvece.GUI.Accueil;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.jarvece.GUI.EcranPairing.F_EcranPairing;
import com.project.jarvece.GUI.Nouvel_Utilisateur.F_NewUser;
import com.project.jarvece.R;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.Session;

public class F_Accueil extends CC_Fragment implements View.OnClickListener {
	Button buttonAccueil;
	TextView textViewCreate, textViewForgot;
	EditText editTextLogin,editTextPassword;
	String login,password;
	@Override
	public void onCreateView(View convertView) {
		Session.getUsers();
		Session.getUsersLogin();

		buttonAccueil = (Button) convertView.findViewById(R.id.buttonAccueil);
		buttonAccueil.setOnClickListener(this);

		textViewCreate = (TextView) convertView.findViewById(R.id.textViewCreate);
		textViewCreate.setOnClickListener(this);

		textViewForgot = (TextView) convertView.findViewById(R.id.textViewForgot);
		textViewForgot.setOnClickListener(this);

		editTextLogin= (EditText) convertView.findViewById(R.id.editTextLogin);
		editTextPassword= (EditText) convertView.findViewById(R.id.editTextPassword);
		Session.WriteSaveUser(this.getActivity());
		Session.RecupSaveUser(this.getActivity());
	}

	@Override
	public void onClick(View view, Integer id) {

	}

	@Override
	public void refresh() {

	}

	@Override
	public Integer getLayoutId() {

		return R.layout.f_accueil;
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.buttonAccueil:

				if(editTextLogin.getText().toString().trim().length() > 0) {

					if(editTextPassword.getText().toString().trim().length() > 0){

					login = editTextLogin.getText().toString();
					password = editTextPassword.getText().toString();

					if (Session.listUserLogin.contains(login)) {

						if (Session.getUserByLogin(login).password.equals(password)) {

							//ALORS ON EST DANS LE SYSTEME WAIIII
							Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivityForResult(turnOn, 0);
							//
							Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
							discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
							startActivity(discoverableIntent);

							Session.currentUser = Session.getUserByLogin(login);
							changeFragment(new F_EcranPairing());
						}else{Toast.makeText(this.getActivity(),"Mot de passe erroné", Toast.LENGTH_SHORT).show();}
					}else{Toast.makeText(this.getActivity(),"Login inconnu", Toast.LENGTH_SHORT).show();}
				}else{Toast.makeText(this.getActivity(),"Veuillez remplir le Password", Toast.LENGTH_SHORT).show();}
				}else{Toast.makeText(this.getActivity(),"Veuillez remplir le Login", Toast.LENGTH_SHORT).show();	}

			break;
			case R.id.textViewCreate:
				changeFragment(new F_NewUser());
				break;
			case R.id.textViewForgot:
				Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
				startActivity(discoverableIntent);
				Session.currentUser = Session.listUser.get(0);
				changeFragment(new F_EcranPairing());
				break;
		}
	}


}
