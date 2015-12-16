package com.project.jarvece;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import com.project.jarvece.Class.Contact;
import com.project.jarvece.Class.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Session {
	private static Session instance;
	public static ArrayList<User> listUser = new ArrayList<User>();
	public static ArrayList<String> listUserLogin = new ArrayList<String>();
	public static BluetoothDevice currentDevice;
	public static User currentUser;
	public static Contact currentContact;
	public static String fileName = "Users.txt";

	private Session() {}

	public void openNewSession() {
	}

	public static Session getInstance() {
		if (instance == null) {
			instance = new Session();
		}
		return instance;
	}

	public static void getUsers() {
		listUser.add(new User("login0","Invité","0000"));
		//code XML
	}
	public static void getUsersLogin() {
		for (User user : listUser) {
			listUserLogin.add(user.login);
		}
	}

	public static User getUserByLogin(String login) {
		for (User user : listUser) {
			if (user.login.equals(login)) {
				return user;
			}
		}
		return null;
	}

	public static void WriteSaveUser(Context context){

		//File file = new File(context.getFilesDir(), fileName);
		String nameFile = context.getFilesDir()+"/"+fileName;
		String textToFile = "";
		FileOutputStream outputStream;

		for (User user : listUser) {
			textToFile += user.login+";"+user.pseudo+";"+user.password+";";
			}

		try {
			outputStream = context.openFileOutput(nameFile, context.MODE_PRIVATE);
			outputStream.write(textToFile.getBytes());
			outputStream.close();
		} catch (Exception e) {
			String test = e.toString();
		}

	}

	public static ArrayList NoDoublon(ArrayList list){

		Set set = new HashSet() ;
		set.addAll(list) ;
		ArrayList distinctList = new ArrayList(set) ;
			return distinctList;

	}

	public static void RecupSaveUser(Context context){

		//File file = new File(context.getFilesDir(), filename);
		String nameFile = context.getFilesDir()+"/"+fileName;
		StringBuffer fileContent = new StringBuffer("");
		byte[] buffer = new byte[1024];
		String textToFile = "";
		int c;
		FileInputStream inputStream;

		try {
			inputStream = new FileInputStream(nameFile);
			while((c = inputStream.read(buffer)) != -1)
			{
				fileContent.append(new String(buffer, 0, c));
				StringTokenizer tokens = new StringTokenizer(fileContent.toString(), ";");
				String login = tokens.nextToken();
				String pseudo = tokens.nextToken();
				String password = tokens.nextToken();
				User u = new User(login,pseudo,password);
				Session.listUser.add(u);
				Session.listUserLogin.add(login);

			}
			inputStream.close();
		} catch (Exception e) {
			String test = e.toString();
		}

	}

	}


