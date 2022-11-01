package cava.tags

import grails.testing.gorm.DomainUnitTest
import grails.testing.gorm.DataTest
import spock.lang.Specification

class UserRoleAssociationSpec extends Specification implements DomainUnitTest<UserRoleAssociation>, DataTest {

    void setupSpec() {

        mockDomain UserRoleAssociation

        User user = new User(
                username: "dilbert",
                confirm: "dilbert",
                email: "foo@bar.com",
                passwordHash: "!#@Q@"
        ).save(flush: true)

        Role role = new Role(name: "Foo", users: [user]).save(flush:true)

        new UserRoleAssociation(user: user, role: role).save(flush:true)
    }

    void "test domain methods"() {

        given:
        UserRoleAssociation userRoleAssociation = UserRoleAssociation.get(1)

        expect:
        UserRoleAssociation.count == 1
        userRoleAssociation != null
        userRoleAssociation.user.username == "dilbert"
        userRoleAssociation.role.name == "Foo"
    }
}
