package cava.tags

import grails.gorm.transactions.Transactional
import org.apache.commons.lang.RandomStringUtils
import org.apache.shiro.SecurityUtils
import org.apache.shiro.authc.credential.PasswordService

@Transactional(readOnly = true)
class SecurityService {

    PasswordService credentialMatcher

    def getUser() {

        String subject = SecurityUtils.getSubject()?.principal
        User currentUser = User.find { username == subject }
    }

    /**
     *
     * @param role
     * @return
     */
    boolean hasRole(String role) {

        SecurityUtils.subject.hasRole(role)
    }

    /**
     *
     * @return
     */
    boolean isLoggedIn() {

        SecurityUtils.subject.isAuthenticated()
    }

    /**
     *
     */
    void logout() {

        SecurityUtils.subject?.logout()
    }

    /**
     *
     * @param permString
     * @return
     */
    boolean isPermitted(String permString) {

        SecurityUtils.subject.isPermitted(permString)
    }

    /**
     *
     * @param password
     * @param user
     * @return
     */
    def encryptPassword(String password, User user) {

        user.passwordHash = credentialMatcher.encryptPassword(password)
    }

    /**
     *
     * @return
     */
    String getRandomString() {

        String randomString = RandomStringUtils.random(60, true, true)
    }

    /**
     *
     * @param object
     * @param objectId
     * @param user
     */
    @Transactional
    void removePermission(object, objectId, user) {

        def permission = permissionQuery(object, objectId, user.id)

        if (permission) {
            permission.delete()
        }
    }

    /**
     *
     * @param permString
     * @param user
     */
    @Transactional
    void addPermission(permString, user) {

        if (!permissionExists(permString, user)) {

            Permission permission = new Permission(permissionsString: permString, user: user).save(flush: true)
        }
    }

    /**
     *
     * @param permString
     * @param user
     * @return
     */
    boolean permissionExists(permString, user) {

        Permission exists = Permission.findByPermissionsStringAndUser(permString, user)

        return exists != null
    }

    /**
     *
     * @param object
     * @param objectId
     * @param userId
     */
    def findPermission(object, objectId, userId) {

        def permission = permissionQuery(object, objectId, userId)
    }

    /**
     *
     * @param object
     * @param objectId
     * @param userId
     */
    def permissionQuery(object, objectId, userId) {

        def query = Permission.withCriteria(uniqueResult:true) {

            ilike("permissionsString", "%:${objectId}")
            ilike("permissionsString", "${object}:%")
            user {
                eq("id", userId.toLong())
            }
        }
    }

}
