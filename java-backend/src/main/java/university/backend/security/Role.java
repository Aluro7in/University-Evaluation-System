package university.backend.security;

import java.util.EnumSet;
import java.util.Set;

public enum Role {
    ADMIN(EnumSet.allOf(Permission.class)),
    FACULTY(EnumSet.of(Permission.STUDENT_READ, Permission.COURSE_READ, Permission.GRADE_READ, Permission.GRADE_WRITE, Permission.TRANSCRIPT_READ, Permission.ANALYTICS_READ)),
    STUDENT(EnumSet.of(Permission.STUDENT_READ, Permission.COURSE_READ, Permission.TRANSCRIPT_READ)),
    ADVISOR(EnumSet.of(Permission.STUDENT_READ, Permission.COURSE_READ, Permission.TRANSCRIPT_READ, Permission.ANALYTICS_READ));

    private final Set<Permission> permissions;
    Role(Set<Permission> permissions) { this.permissions = Set.copyOf(permissions); }
    public boolean allows(Permission permission) { return permissions.contains(permission); }
    public Set<Permission> permissions() { return permissions; }
}
