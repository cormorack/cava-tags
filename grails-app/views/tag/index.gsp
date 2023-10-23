<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="public" />
        <asset:stylesheet src="application.css"/>
        <g:set var="entityName" value="${message(code: 'tag.label', default: 'Tag')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div class="container">
        <div class="row">
            <h3 class="text-center">Media Tags</h3>
            <div class="col">
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                </g:if>
                %{--<f:table collection="${tagList}" properties="['title', 'tags']"/>--}%
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Title</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tagList}" var="tag">
                        <tr>
                            <td>
                                <g:link action="show" id="${tag.id}">
                                    ${tag.title}
                                </g:link>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <div class="pagination">
                    <g:paginate total="${tagCount ?: 0}" />
                </div>
            </div>
        </div>
    </div>
        %{--<a href="#list-tag" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-tag" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${tagList}" properties="['title', 'description', 'category', 'tags']"/>

            <div class="pagination">
                <g:paginate total="${tagCount ?: 0}" />
            </div>
        </div>--}%
    </body>
</html>