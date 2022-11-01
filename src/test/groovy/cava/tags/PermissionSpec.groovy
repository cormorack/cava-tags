package cava.tags

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class PermissionSpec extends Specification implements DomainUnitTest<Permission>, DataTest {

    void setupSpec() {

        mockDomain Permission

        User user = new User(
                username: "dilbert",
                confirm: "dilbert",
                email: "foo@bar.com",
                passwordHash: "!#@Q@"
        ).save(flush: true)

        new Permission(permissionsString: 'tag:list,show,edit,delete:1', user: user).save(flush: true)
    }

    void "test domain methods"() {

        given:
        Permission permission = Permission.get(1)

        expect:
        Permission.count == 1
        permission != null
        permission.permissionsString == "tag:list,show,edit,delete:1"
        permission.controller() == "tag"
        permission.actions() == ["list","show","edit","delete"]
        permission.controllerId() == "1"
        permission.user.username == "dilbert"
    }
}
