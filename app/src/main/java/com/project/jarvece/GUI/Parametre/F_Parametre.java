package com.project.jarvece.GUI.Parametre;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.project.jarvece.Class.User;
import com.project.jarvece.GUI.EcranPairing.F_EcranPairing;
import com.project.jarvece.R;
import com.project.jarvece.GUI.Accueil.F_Accueil;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.Session;

public class F_Parametre extends CC_Fragment implements View.OnClickListener {

	private Button buttonRetour, buttonNewPseudo, ButtonOui;
	private EditText editTextNewPseudo;



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonRetour:
				cacherClavier();
				changeFragment(new F_EcranPairing());
				break;
			case R.id.buttonOui:
				cacherClavier();
				changeFragment(new F_Accueil());
				break;
			case R.id.buttonNewPseudo:
				if (editTextNewPseudo.getText().toString().trim().length() > 0) {

					String pseudo = editTextNewPseudo.getText().toString();
					//User u = Session.currentUser;
					User u = null;
					for (User user : Session.listUser) {
						if (user.login.equals(Session.currentUser.login)) {
							u = new User(user.login,pseudo,user.password);
							Session.listUser.remove(user);
							Session.listUserLogin.remove(user.login);
							break;
						}
					}
					Session.listUser.add(u);
					Session.listUserLogin.add(u.login);
					Session.currentUser = u;
					editTextNewPseudo.setText("");
					cacherClavier();
				}
				break;

		}

	}

	public void cacherClavier()
	{
		InputMethodManager im = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(editTextNewPseudo.getWindowToken(), 0);
	}

	@Override
	public void onCreateView(View convertView) {

		editTextNewPseudo = (EditText) convertView.findViewById(R.id.editTextNewPseudo);
		ButtonOui = (Button) convertView.findViewById(R.id.buttonOui);
		ButtonOui.setOnClickListener(this);
		buttonRetour = (Button) convertView.findViewById(R.id.buttonRetour);
		buttonRetour.setOnClickListener(this);
		buttonNewPseudo = (Button) convertView.findViewById(R.id.buttonNewPseudo);
		buttonNewPseudo.setOnClickListener(this);


	}

	// useless
	@Override
	public void onClick(View view, Integer id) {}

	@Override
	public void refresh() {

	}

	@Override
	public Integer getLayoutId() {
		return R.layout.f_parametre;
	}

	@Override
	public void onBackPressed() {
	//	changeFragment(new F_EcranPairing());
	}

}
