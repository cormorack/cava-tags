package cava.tags

import grails.gorm.PagedResultList
import grails.compiler.GrailsCompileStatic

import static org.springframework.http.HttpStatus.NOT_FOUND

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

        render template: "/public/tags",
                model: [
                    max: params.max,
                    resultCount: tagList.getTotalCount() ?: 0,
                    offset: params.offset,
                    tagList: tagList
                ]
    }

    def tag() {

        String urlTitle = params.title

        if (!urlTitle) {
            notFound()
            return
        }

        Tag tag = Tag.findByUrlTitle(urlTitle)

        if (!tag) {
            notFound()
            return
        }

        render tag
    }

    protected void notFound() {
        render status: NOT_FOUND
    }

    /**
     *
     * @param max
     */
    protected void setParams(Integer max) {

        params.max = Math.min(max ?: 100, 100)

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
