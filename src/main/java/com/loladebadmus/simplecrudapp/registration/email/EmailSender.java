package com.loladebadmus.simplecrudapp.registration.email;

public interface EmailSender {
    void send(String to, String email);
}
