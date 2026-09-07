package university.backend.notification;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** In-process notification service used by academic alerts and grade events. */
public class NotificationService {
    private final List<Notification> outbox = new ArrayList<>();

    public Notification send(String recipient, String type, String title, String message) {
        Notification notification = new Notification(recipient, type, title, message, Instant.now(), false);
        outbox.add(notification);
        return notification;
    }

    public List<Notification> pending(String recipient) {
        return outbox.stream().filter(n -> n.recipient().equals(recipient) && !n.read()).toList();
    }

    public List<Notification> snapshot() { return List.copyOf(outbox); }

    public int unreadCount(String recipient) { return pending(recipient).size(); }
}
