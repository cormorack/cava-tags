package cava.tags

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Shared
import spock.lang.Specification
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import groovy.mock.interceptor.MockFor

class SecurityServiceSpec extends Specification implements ServiceUnitTest<SecurityService>, DataTest {

    MockFor secUtil = new MockFor(SecurityUtils)

    @Shared
    Map mockSubject

    void setupSpec() {

        // A map that backs the mock Subject instance.
        mockSubject = [
                isAuthenticated: { -> false },
                isPermitted    : { Object -> true }
        ]

        SecurityUtils.metaClass.static.getSubject = { -> mockSubject as Subject }

        mockDomain User

        User user = new User(
                username: "dil",
                confirm: "dil",
                email: "foo@bar.com",
                passwordHash: "!#@Q@"
        ).save(flush: true)
        assert user != null
    }

    void "test authenticated"() {

        when: "we authenticate the user"
        mockSubject["isAuthenticated"] = { -> true }

        then: "we are logged in"
    }

    /*void "test principal"() {

        when:
        mockSubject["getPrincipal"] = { -> "dil" }
        User user = User.findByUsername("dil")

        then:
        mockSubject.getPrincipal == user.username
    }*/

    /*void "test something"() {

        given:
        secUtil.demand.getSubject { ->
            [login: {authToken -> false}]
        }

        when:
        User user = User.get(1)

        then:
        service.getUser().principal == user.username
    }*/
}
