package com.project.jarvece.Class;

import java.util.ArrayList;

/**
 * Created by Clément on 15/12/2015.
 */
public class User {

    public String login;
    public String pseudo;
    public String password;
    public  ArrayList<Contact> listContact;// = new ArrayList<Contact>();

    public User(String login, String pseudo, String password)
    {
        this.login=login;
        this.pseudo=pseudo;
        this.password=password;
        this.listContact = new ArrayList<Contact>();
    }

    public User() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
       this.login = login;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Contact> getListContact() {
        return listContact;
    }

    public void setListContact(Contact contact) {
        listContact.add(contact);
    }


}
