package com.dsd.game.api;

import com.dsd.game.database.PersistentDatabase;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Validates an email using the javax mail API from Java.
 *
 * @author Joshua
 */
public class EmailValidator {

    /**
     * Validates an email. If it is valid, then it returns true. Else, return
     * false.
     *
     * Source:
     * https://crunchify.com/how-to-validate-email-address-using-java-mail-api/
     *
     * @param _email
     * @return
     */
    public static boolean isValid (String _email) {

        try {
            //  Creates the address
            InternetAddress emailAddress = new InternetAddress(_email);
            emailAddress.validate();
            return true;
        }
        catch (AddressException ex) {
            return false;

        }
    }
}
