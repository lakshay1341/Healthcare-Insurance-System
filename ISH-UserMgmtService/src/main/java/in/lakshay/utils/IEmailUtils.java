package in.lakshay.utils;

/**
 * Interface for email utilities
 */
public interface IEmailUtils {
    
    /**
     * Send an email message
     * 
     * @param to the recipient email address
     * @param subject the email subject
     * @param body the email body
     * @return true if the email was sent successfully, false otherwise
     */
    boolean sendEmailMessage(String to, String subject, String body);
}
