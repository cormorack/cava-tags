package cava.tags

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class UserRoleAssociation implements Serializable {

    User user
    Role role

    static mapping = {
        table 'user_roles'
        version false
        id composite: ['user', 'role']
    }

    static UserRoleAssociation create(User user, Role role) {

        UserRoleAssociation userRoleAssociation = new UserRoleAssociation(user: user, role: role)
        userRoleAssociation.save(flush:true)
        return userRoleAssociation
    }

    static boolean remove(User user, Role role) {

        UserRoleAssociation userRoleAssociation = findByUserAndRole(user, role)

        if (userRoleAssociation && userRoleAssociation.delete(flush:true)) {
            return true
        }
        return false
    }

    static boolean exists(User user, Role role) {

        UserRoleAssociation userRoleAssociation = findByUserAndRole(user, role)

        if (userRoleAssociation) {
            return true
        }
        return false
    }

    static List findRolesByUser(User user) {

        List roles = findAllByUser(user, [readOnly:true])
    }

    static List findUsersByRole(Role role) {

        List users = findAllByRole(role, [readOnly:true])
    }

    static List findUsersByRoles(List roles) {

        List users = []

        for (String r in roles) {
            if (r) {
                Role role = Role.findByName(r)
                if (role) {
                    UserRoleAssociation ura = findByRole(role)
                    if (ura) {
                        users.add(ura)
                    }
                }
            }
        }
    }

    static void removeAllByUser(User user) {

        executeUpdate("DELETE FROM UserRoleAssociation WHERE user=:user", [user: user])
    }

    static void removeAllByRole(Role role) {

        executeUpdate("DELETE FROM UserRoleAssociation WHERE role=:role", [role: role])
    }

    boolean equals(other) {

        if (!(other instanceof UserRoleAssociation)) {
            return false
        }
        return other.user.id == user.id && other.role.id == role.id
    }
}
