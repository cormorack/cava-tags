package cava.tags

import grails.gorm.transactions.Transactional
import org.apache.shiro.authc.credential.PasswordService

class BootStrap {

    PasswordService credentialMatcher

    def init = { servletContext ->
        createUsers()
    }
    def destroy = {
    }

    @Transactional
    void createUsers() {

        String password = "123dilbert@"

        User normalUser = new User(
                username: "dilbert",
                confirm: password,
                email: "foo@bar.com",
                passwordHash: credentialMatcher.encryptPassword(password)
        ).save()

        assert normalUser.validate()
        assert credentialMatcher.passwordsMatch(password, normalUser.passwordHash)

        Role userRole = new Role(name: "Admin").save()
        userRole.addToPermissions("*:*:*")

        UserRoleAssociation.create(normalUser, userRole)

        Permission permission = new Permission(permissionsString: "tag:index", user: normalUser).save()
        Permission permission1 = new Permission(permissionsString: "tag:show:1", user: normalUser).save()

        Media media = new Media(title: "Media 1", url: "https://interactiveoceans.washington.edu/img_2170/").save()
        Media media2 = new Media(title: "Media 2", url: "https://interactiveoceans.washington.edu/img_8704/").save()

        Tag tag = new Tag(title: "Test Tag", urlTitle: "test-tag")
        tag.addToMedia(media)
        tag.addToMedia(media2)
        tag.save()
    }
}
