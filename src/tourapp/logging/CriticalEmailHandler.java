package tourapp.logging;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class CriticalEmailHandler extends Handler {

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;
    private final String fromAddress;
    private final String toAddress;
    private final boolean useTls;
    private final boolean useSsl;

    public CriticalEmailHandler(String smtpHost,
                                int smtpPort,
                                String username,
                                String password,
                                String fromAddress,
                                String toAddress,
                                boolean useTls,
                                boolean useSsl) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.useTls = useTls;
        this.useSsl = useSsl;
    }

    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        // Шлемо тільки CRITICAL/SEVERE
        if (record.getLevel().intValue() < Level.SEVERE.intValue()) {
            return;
        }

        String subject = "[TourApp] Critical error: " + record.getMessage();
        String body = buildBody(record);

        try {
            sendEmail(subject, body);
        } catch (Throwable t) { // ЛОВИМО ВСЕ, включно з Error (NoClassDefFoundError і т.п.)
            System.err.println("Failed to send critical error e-mail: " + t.getMessage());
        }
    }

    private String buildBody(LogRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("A critical error has occurred in TourApp.\n\n");
        sb.append("Message: ").append(record.getMessage()).append("\n");
        sb.append("Logger: ").append(record.getLoggerName()).append("\n");
        sb.append("Level: ").append(record.getLevel()).append("\n");
        sb.append("Thread: ").append(record.getThreadID()).append("\n\n");

        if (record.getThrown() != null) {
            sb.append("Stack trace:\n");
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.flush();
            sb.append(sw.toString());
        }

        return sb.toString();
    }

    private void sendEmail(String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");

        if (useTls) {
            props.put("mail.smtp.starttls.enable", "true");
        }
        if (useSsl) {
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.trust", smtpHost);
        }

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    @Override
    public void flush() {
        // nothing
    }

    @Override
    public void close() throws SecurityException {
        // nothing
    }
}
