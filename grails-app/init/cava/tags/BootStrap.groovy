package cava.tags

import grails.gorm.transactions.Transactional
import org.apache.shiro.authc.credential.PasswordService
import javax.servlet.ServletContext

class BootStrap {

    PasswordService credentialMatcher
    TagService tagService

    def init = { servletContext ->

        createUsers()
        String path = servletContext.getRealPath("/")
        tagService.createTagsFromText(path)
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

        /*(1..< 20).each {
            Tag tag = new Tag(title: "${it} tag", urlTitle: "${it}-tag")
            tag.addToMedia(media)
            tag.addToMedia(media2)
            tag.save(flush:true)
        }

        Tag tag1 = Tag.findByTitle("1 tag")
        Tag tag2 = Tag.findByTitle("2 tag")

        if (tag1 != null && tag2 != null) {

            TagAssociation tagAssociation = new TagAssociation(tag: tag1, otherTag: tag2).save(flush:true)
            TagAssociation otherTagAssociation = new TagAssociation(tag: tag2, otherTag: tag1).save(flush:true)
        }*/
    }
}
