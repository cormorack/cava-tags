<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-role" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-role" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.role}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.role}" var="error">
                    <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.role}" method="PUT">
                <g:hiddenField name="version" value="${this.role?.version}" />
                <fieldset class="form">
                    <div class="fieldcontain required">
                        <label for="name">Name
                            <span class="required-indicator">*</span>
                        </label>
                        <g:textField
                                name="name"
                                required=""
                                id="name"
                                class="w-input admin-text-field value ${hasErrors(bean:role, field:'name','errors')}"
                                value="${role?.name}"
                        />
                    </div>
                    <div class="fieldcontain">
                        <label for="permissions">Exiting Permissions</label>
                        <g:if test="${role?.permissions}">
                            <g:each in="${role.permissions}" var="perm" status="i">
                                <g:textField
                                        name="permissions"
                                        class="w-input admin-text-field value ${hasErrors(bean:role, field:'permissions','errors')}"
                                        value="${perm}"
                                /> Remove  <g:checkBox name="permissionsToRemove" value="${perm}" checked="${false}" />
                            </g:each>
                        </g:if>
                        <g:else>
                            <g:textField
                                    name="permissions"
                                    class="w-input admin-text-field value ${hasErrors(bean:role, field:'permissions','errors')}"
                                    value=""
                            />
                        </g:else>
                    </div>
                    <div class="fieldcontain">
                        <label for="newPermission">Add a New Permission:</label>
                        <g:textField
                                name="newPermission"
                                class="w-input admin-text-field value"
                                value=""
                        />
                    </div>
                    <div class="fieldcontain">
                        <label for="users">Users</label>
                        <g:select
                                name="users"
                                from="${cava.tags.User.list()}"
                                multiple="multiple"
                                optionKey="id"
                                size="5"
                                value="${users}"
                                class="w-select select-field"
                        />
                    </div>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
