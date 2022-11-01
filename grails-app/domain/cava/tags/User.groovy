package cava.tags

class User implements Serializable {

    String username, password, passwordHash, email, confirm
    boolean confirmed = false
    Date date = new Date()
    Set<Role> roles

    static constraints = {
        username(nullable: false, blank: false, unique: true, maxSize:50)
        email(blank:false, email:true, maxSize:50)
        passwordHash(nullable: false, maxSize: 255)
        confirm(blank: false, maxSize: 30)
        password(bindable:true, nullable:true, size:10..30, validator:{ val, obj ->
            if (!val) {
                return true
            }
            if (val != obj.confirm) {
                return 'user.password.error.dontmatch'
            }
            if (obj.username.equals(val)) {
                return 'user.password.error.unique'
            }
            //if(obj.password.equals(val)) return 'user.password.error.same'
            if (!val.matches('^.*\\p{Alpha}.*$') || !val.matches('^.*\\p{Digit}.*$') || !val.matches('^.*[!@#$%^&].*$')) {
                return 'user.password.error.username'
            }
        })
    }

    static hasMany = [roles: Role]

    static transients = ['confirm', 'password']

    static mapping = {
        id generator:'sequence', params:[sequence:'user_seq']
        cache true
        table "`user`"
        roles cache:true
    }

    String toString() {
        return username
    }

    boolean equals(def o) {

        if ( is ( o)) {
            return true
        }
        if (o instanceof User) {
            return id == o.id
        }
        return false
    }

    /*Set<Role> getRoles() {
        UserRoleAssociation.findAllByUser(this, [readOnly:true]).collect { it.role } as Set
    }*/

    Set<Permission> getPermissions() {
        Permission.findAllByUser(this) as Set<Permission>
    }

    void deleteAllAssociations() {

        UserRoleAssociation.removeAllByUser(this)
        executeUpdate("DELETE FROM Permission WHERE user=:user", ['user': this])
    }
}
