package cava.tags


/**
 * This interceptor class allows access to the AuthInterceptor
 * without being authenticated, so you can login etc.
 */
class AuthInterceptor {

    int order = HIGHEST_PRECEDENCE+100

    boolean before() { true }
    boolean after() { true }
}
