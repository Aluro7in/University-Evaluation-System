package university.backend.notification;

import java.time.Instant;

public record Notification(
        String recipient,
        String type,
        String title,
        String message,
        Instant createdAt,
        boolean read
) {
    public Notification markRead() { return new Notification(recipient, type, title, message, createdAt, true); }
}
