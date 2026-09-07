package university.backend.security;

import java.util.Objects;

/** Central authorization rules for Java REST operations. */
public class AuthorizationService {
    public boolean can(Role role, Permission permission) {
        return role != null && permission != null && role.allows(permission);
    }

    public void require(Role role, Permission permission) {
        Objects.requireNonNull(role, "role");
        Objects.requireNonNull(permission, "permission");
        if (!can(role, permission)) {
            throw new SecurityException("Role " + role + " is not allowed to perform " + permission);
        }
    }

    public boolean canEditGrades(Role role) { return can(role, Permission.GRADE_WRITE); }
    public boolean canManageCourses(Role role) { return can(role, Permission.COURSE_WRITE); }
    public boolean canReadAnalytics(Role role) { return can(role, Permission.ANALYTICS_READ); }
}
