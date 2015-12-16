package com.project.jarvece.GUI.Chat;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.jarvece.BluetoothChatService;
import com.project.jarvece.Class.Contact;
import com.project.jarvece.Class.User;
import com.project.jarvece.GUI.EcranPairing.F_EcranPairing;
import com.project.jarvece.GUI.Historique.F_Historique;
import com.project.jarvece.GUI.Parametre.F_Parametre;
import com.project.jarvece.PARAM.CC_Fragment;
import com.project.jarvece.R;
import com.project.jarvece.Session;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Clément on 15/12/2015.
 */
public class F_Chat extends CC_Fragment implements View.OnClickListener {


    private EditText editTextToSend;
    private Button buttonSend;
    private ImageButton buttonRetour, buttonParametre;
    private Button buttonHistorique;
    private ListView listViewChat;
    private TextView textViewPseudo;

    private String discussion = "";

    private String connectedDeviceName = null;
    private ArrayAdapter<String> conversationArrayAdapter;
    private StringBuffer stringBufferTextToSend;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothChatService chatService = null;
    ArrayList listChat = new ArrayList();
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;


    @Override
    public void onCreateView(View convertView) {

        textViewPseudo = (TextView) convertView.findViewById(R.id.textViewChat);
        textViewPseudo.setText(Session.currentContact.pseudo);
        buttonSend = (Button) convertView.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        buttonRetour = (ImageButton) convertView.findViewById(R.id.buttonBack);
        buttonRetour.setOnClickListener(this);
        buttonParametre = (ImageButton) convertView.findViewById(R.id.buttonParametre);
        buttonParametre.setOnClickListener(this);
        buttonHistorique = (Button) convertView.findViewById(R.id.buttonHistorique);
        buttonHistorique.setOnClickListener(this);

        listViewChat = (ListView) convertView.findViewById(R.id.listViewChat);

        editTextToSend = (EditText) convertView.findViewById(R.id.editTextToSend);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (chatService == null) {
            initChat();
        }

    }

    private void initChat() {
        // Initialize the array adapter for the conversation thread
        //conversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.a_textview);
       // listViewChat.setAdapter(conversationArrayAdapter);
        conversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.a_textview,R.id.textViewAdapter, listChat);
        listViewChat.setAdapter(conversationArrayAdapter);
        // Initialize the compose field with a listener for the return key
        editTextToSend.setOnEditorActionListener(writeListener);
        // Initialize the BluetoothChatService to perform bluetooth connections
        chatService = new BluetoothChatService(getActivity(), mHandler);

        chatService.connect(Session.currentDevice, true);
        //chatService.connected(socket, Session.currentDevice,  socketType);

        // Initialize the buffer for outgoing messages
        stringBufferTextToSend = new StringBuffer("");
    }

    //A SUPPRIMER JE PENSE
    private TextView.OnEditorActionListener writeListener
            = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            // If the action is a key-up event on the return key, send the message
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            return true;
        }
    };

    /**
     * The Handler that gets information back from the BluetoothChatService
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothChatService.Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                           // setStatus(getString(R.string.title_connected_to, connectedDeviceName));
                            conversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                           // setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                           // setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case BluetoothChatService.Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    listChat.add(writeMessage);
                    discussion += writeMessage+"\n";
                    conversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.a_textview_little,R.id.textViewAdapter, listChat);
                    listViewChat.setAdapter(conversationArrayAdapter);
                    break;
                case BluetoothChatService.Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    listChat.add(readMessage);
                    discussion += readMessage+"\n";
                    break;
            }
        }
    };


    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (chatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            chatService.write(send);
            // Reset out string buffer to zero and clear the edit text field
            stringBufferTextToSend.setLength(0);
            editTextToSend.setText(stringBufferTextToSend);
        }
    }


    @Override
    public void onClick(View view, Integer id) {

    }
    @Override
    public void refresh() {

    }
    @Override
    public Integer getLayoutId() {
            return R.layout.f_ecranchat;
    }
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBack:
               int pos =Session.currentUser.listContact.indexOf(Session.currentContact);
                Contact c = Session.currentUser.listContact.get(pos);
                c.setChat(discussion);
                Session.currentContact = c;
                Session.currentUser.listContact.remove(pos);
                Session.currentUser.listContact.add(c);
                cacherClavier();
                changeFragment(new F_EcranPairing());
                break;
            case R.id.buttonHistorique:
                int pos1 =Session.currentUser.listContact.indexOf(Session.currentContact);
                Contact c1 = Session.currentUser.listContact.get(pos1);
                c1.setChat(discussion);
                Session.currentUser.listContact.remove(pos1);
                Session.currentUser.listContact.add(c1);
                cacherClavier();
                Session.currentContact = c1;
                changeFragment(new F_Historique());
                break;
            case R.id.buttonParametre:
                int pos2 =Session.currentUser.listContact.indexOf(Session.currentContact);
                Contact c2 = Session.currentUser.listContact.get(pos2);
                c2.setChat(discussion);
                Session.currentContact = c2;
                Session.currentUser.listContact.remove(pos2);
                Session.currentUser.listContact.add(c2);
                cacherClavier();
                changeFragment(new F_Parametre());
                break;
            case R.id.buttonSend:
                String message = editTextToSend.getText().toString();
                sendMessage(Session.currentUser.pseudo+":  " +message);
                //Afficher message envoyé
               /* listChat.add(Session.currentUser.pseudo+":  " + message);
                conversationArrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.a_textview_little,R.id.textViewAdapter, listChat);
                listViewChat.setAdapter(conversationArrayAdapter);*/
                ///
                break;
        }

    }

    public void cacherClavier()
    {
        InputMethodManager im = (InputMethodManager)this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(editTextToSend.getWindowToken(), 0);
    }

}
