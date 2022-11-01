package cava.tags

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class PermissionServiceSpec extends Specification implements ServiceUnitTest<PermissionService>, DataTest {

    void setupSpec() {

        mockDomain Permission

        User user = new User(
                username: "dilbert",
                confirm: "dilbert",
                email: "foo@bar.com",
                passwordHash: "!#@Q@"
        ).save(flush: true)

        new Permission(permissionsString: 'tag:show:1', user: user).save(flush: true)
    }

    void "test basic service methods"() {

        expect:
        service.count() == 1
        service.list([:]).size() == 1
        service.get(1) != null
        service.delete(1)
        service.count() == 0
    }

}
