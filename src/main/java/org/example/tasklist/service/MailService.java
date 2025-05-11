package org.example.tasklist.service;

import org.example.tasklist.model.MailType;
import org.example.tasklist.model.User;

import java.util.Properties;

public interface MailService {

    void sendEmail(User user, MailType type, Properties props);
}
