package com.project.jarvece.Class;

/**
 * Created by Clément on 15/12/2015.
 */
public class Contact {

    public String pseudo;
    public String chat;

    public Contact(String pseudo)
    {
        this.pseudo=pseudo;
        this.chat = "";
    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = this.chat + chat;
    }





}
