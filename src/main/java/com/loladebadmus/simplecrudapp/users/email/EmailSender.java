package com.loladebadmus.simplecrudapp.users.email;

public interface EmailSender {
    void send(String to, String email);
}
