package cava.tags

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Permission {

    String permissionsString
    User user

    static constraints = {
        permissionsString(nullable: false, blank: false)
        user(nullable:false)
    }

    static mapping = {
        cache true
        id generator:'sequence', params:[sequence:'perm_seq']
    }

    static transients = ['controller', 'controllerId']

    String controller() {
        this.permissionsString?.substring(0, permissionsString?.indexOf(":")) ?: ""
    }

    String controllerId() {

        String cId = ""

        if (this.permissionsString.lastIndexOf(":") != -1) {
            cId = this.permissionsString.substring(this.permissionsString.lastIndexOf(":") + 1)
        }
        return cId
    }

    List actions() {

        List a = []

        if (this.permissionsString) {
            a = this.permissionsString?.split(':')[1]?.split(',')?.toList()
        }
    }

    /*String toString() {
        return "${user}: ${permissionsString}"
    }*/
}
