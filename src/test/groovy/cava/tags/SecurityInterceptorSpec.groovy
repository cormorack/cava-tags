package cava.tags

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class SecurityInterceptorSpec extends Specification implements InterceptorUnitTest<SecurityInterceptor> {

    void "Test security interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"tag")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
