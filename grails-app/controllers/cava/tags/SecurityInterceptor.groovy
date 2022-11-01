package cava.tags

class SecurityInterceptor {

    SecurityService securityService

    int order = HIGHEST_PRECEDENCE + 100

    def secureControllers = "tag|role|user|media|permission"

    SecurityInterceptor() {
        match(controller: "(${secureControllers})", action: "*")
        //match(controller: "*", action: "*")
    }

    boolean before() {

        String uri = "${controllerName}:${actionName}${params.id ? ':' + params.id : ''}"

        accessControl {
            securityService.isPermitted(uri)
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
