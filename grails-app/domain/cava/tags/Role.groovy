package cava.tags

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Role {

    String name

    Set<User> users
    Set<String> permissions

    static hasMany = [permissions: String, users: User]

    static belongsTo = User

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        cache true
        id generator:'sequence', params:[sequence:'perm_seq']
    }

    String toString() {
        return "${name}"
    }

    /*Set<User> getUsers() {

        UserRoleAssociation.findAllByRole(this).collect { it.user } as Set
    }*/

    String controller(String permissionsString) {

        permissionsString?.substring(0, permissionsString?.indexOf(":")) ?: ""
    }

    String controllerId(String permissionsString) {

        String newString = ""

        if (permissionsString.lastIndexOf(":") != -1) {
            newString = permissionsString.substring(permissionsString.lastIndexOf(":") +1)
        }
        return newString
    }

    List actions(String permissionsString) {

        List a = []

        if (permissionsString) {
            a = permissionsString?.split(':')[1]?.split(',')?.toList()
        }
    }
}
