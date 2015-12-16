package com.project.jarvece.GUI.EcranPairing;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.project.jarvece.Class.Contact;
import com.project.jarvece.GUI.Accueil.F_Accueil;
import com.project.jarvece.GUI.Chat.F_Chat;
import com.project.jarvece.R;
import com.project.jarvece.GUI.Parametre.F_Parametre;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.Session;

import java.util.ArrayList;
import java.util.Set;

public class F_EcranPairing extends CC_Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ImageButton buttonParametre, buttonBack, buttonSearch, buttonConversation;
    private ListView listViewDevice;
    private ArrayList<String> listDevice = new ArrayList<String>();
    private ArrayAdapter<String> arrayAdapterString;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList listDeviceBluetooth = new ArrayList();

	@Override
	public void onCreateView(View convertView) {

        BA = BluetoothAdapter.getDefaultAdapter();

		buttonBack = (ImageButton) convertView.findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(this);

        buttonSearch = (ImageButton) convertView.findViewById(R.id.buttonSearch);
		buttonSearch.setOnClickListener(this);

		buttonParametre = (ImageButton) convertView.findViewById(R.id.buttonParametre);
		buttonParametre.setOnClickListener(this);

		buttonConversation = (ImageButton) convertView.findViewById(R.id.buttonConversation);
		buttonConversation.setOnClickListener(this);

		listViewDevice = (ListView) convertView.findViewById(R.id.listViewBluetooth);
		listViewDevice.setOnItemClickListener(this);
		//For Test, TO DELETE
		listDevice.add("Start research");
		arrayAdapterString = new ArrayAdapter<String>(getActivity(), R.layout.a_textview,R.id.textViewAdapter, listDevice);
		listViewDevice.setAdapter(arrayAdapterString);
	}

	// useless
	@Override
	public void onClick(View view, Integer id) {}

	@Override
	public void refresh() {}

	@Override
	public Integer getLayoutId() {
		return R.layout.f_ecranpairing;
	}

	@Override
	public void onBackPressed() {
	}


	public Contact FindContactByName(String deviceName) {
		for (Contact contact : Session.currentUser.getListContact()) {
			if (contact.pseudo.equals(deviceName)) {
				return contact;
			}
		}
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonParametre:
				changeFragment(new F_Parametre());
				break;
			/*case R.id.buttonConversation:
				int select = listViewDevice.getCheckedItemPosition();
				if(select != -1) {
					String deviceName = listViewDevice.getItemAtPosition(select).toString();
					if (!Session.currentUser.getListContact().contains(deviceName)) {
						Session.currentContact = new Contact(deviceName);
						Session.currentUser.listContact.add(Session.currentContact);
					} else {
						Session.currentContact = FindContactByName(deviceName);
					}
					for (BluetoothDevice bt : pairedDevices)
					{if(bt.getName().equals(deviceName))
						{
							Session.currentDevice = bt;
						}
					}
					changeFragment(new F_Chat());
				}
				break;*/
			case R.id.buttonBack:
				changeFragment(new F_Accueil());
				break;
            case R.id.buttonSearch:
                listDevice.clear();
                listDeviceBluetooth.clear();
                BA = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = BA.getBondedDevices(); //ici je recupère les devices avec qui on est deja paire-é, à modifé plus tard
                if(pairedDevices.size() != 0)
                {
                    for (BluetoothDevice bt : pairedDevices)
                        listDeviceBluetooth.add(bt.getName());
                    ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.a_textview, R.id.textViewAdapter, listDeviceBluetooth);
                    listViewDevice.setAdapter(adapter);
                }
                else
                {
                    listDevice.add("No device found");
                    arrayAdapterString = new ArrayAdapter<String>(getActivity(), R.layout.a_textview,R.id.textViewAdapter, listDevice);
                    listViewDevice.setAdapter(arrayAdapterString);
                }
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Boolean newUser = true;
		int select = listViewDevice.getCheckedItemPosition();
		if(select != -1) {
			String deviceName = listViewDevice.getItemAtPosition(select).toString();
            if(!(deviceName.equals("No device found") || deviceName.equals("Start research"))) {
                for (Contact contact : Session.currentUser.getListContact()) {
                    if (contact.pseudo.equals(deviceName)) {
                        newUser = false;
                        break;
                    }
                }
                if (newUser) {
                    Session.currentContact = new Contact(deviceName);
                    Session.currentUser.listContact.add(Session.currentContact);
                } else {
                    Session.currentContact = FindContactByName(deviceName);
                }
                for (BluetoothDevice bt : pairedDevices) {
                    if (bt.getName().equals(deviceName)) {
                        Session.currentDevice = bt;
                    }
                }
                changeFragment(new F_Chat());
            }
		}
	}
}
