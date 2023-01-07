package org.sid.banquetechcodec.services;

import org.sid.banquetechcodec.security.AbstractEmailContext;

import javax.mail.MessagingException;

public interface EmailService {
    void sendMail(final AbstractEmailContext email) throws MessagingException;
}
