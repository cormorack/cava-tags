package cava.tags

import grails.gorm.PagedResultList
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class PublicController {

    TagService tagService

    def index() {}

    /**
     *
     * @param max
     */
    def tags(Integer max) {

        setParams(max)

        PagedResultList tagList = tagService.search(params) as PagedResultList

        response.setContentType("application/json;charset=UTF-8")

        render template: "tags", model: [
                max: params.max,
                resultCount: tagList.getTotalCount() ?: 0,
                offset: params.offset,
                tagList: tagList ]
    }

    /**
     *
     * @param max
     */
    protected void setParams(Integer max) {

        params.max = Math.min(max ?: 10, 100)

        if (!params.sort) {
            params.sort = "date"
        }
        if (!params.order) {
            params.order = "desc"
        }
        if (!params.offset) {
            params.offset = 0
        }
    }
}
