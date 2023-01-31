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

        Media media = new Media(title: "Media 1", url: "https://interactiveoceans.washington.edu/img_2170/", type: Media.Type.VIDEO).save()
        Media media2 = new Media(title: "Media 2", url: "https://interactiveoceans.washington.edu/img_8704/", type: Media.Type.IMAGE).save()

        (1..< 20).each {
            Tag tag = new Tag(title: "${it} tag", urlTitle: "${it}-tag")
            tag.addToMedia(media)
            tag.addToMedia(media2)
            tag.save()
        }

        Tag tag1 = Tag.get(1)
        Tag tag2 = Tag.get(2)

        TagAssociation tagAssociation = new TagAssociation(tag1, tag2).save()

    }
}
