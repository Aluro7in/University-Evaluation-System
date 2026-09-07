package university.backend.business;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/** Lightweight domain audit trail used to explain important academic changes. */
public class AuditTrailService {
    public record Event(String actor, String action, String entity, String entityId, Instant at, String detail) {}
    private final List<Event> events = new ArrayList<>();

    public Event record(String actor, String action, String entity, String entityId, String detail) {
        if (actor == null || actor.isBlank()) throw new IllegalArgumentException("Actor is required");
        if (action == null || action.isBlank()) throw new IllegalArgumentException("Action is required");
        Event event = new Event(actor, action, entity, entityId, Instant.now(), detail == null ? "" : detail);
        events.add(event);
        return event;
    }

    public List<Event> forEntity(String entity, String entityId) {
        return events.stream().filter(e -> e.entity().equals(entity) && e.entityId().equals(entityId)).toList();
    }

    public List<Event> snapshot() { return List.copyOf(events); }

    public long count(String action) { return events.stream().filter(e -> e.action().equals(action)).count(); }
}
