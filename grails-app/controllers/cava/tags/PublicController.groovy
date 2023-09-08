package cava.tags

import grails.converters.JSON
import grails.gorm.PagedResultList
import grails.compiler.GrailsCompileStatic
//import grails.util.Holders
import static org.springframework.http.HttpStatus.NOT_FOUND

@GrailsCompileStatic
class PublicController {

    //Holders config = Holders.config
    TagService tagService
    private final String contentType = "application/json;charset=UTF-8"

    def index() {params:params}

    def test() {}

    def index1() {

        String context = grailsApplication.config.getProperty('server.servlet.context-path') ?: ""
        [params:params, context: context]
    }

    /**
     *
     * @param max
     */
    def tags(Integer max) {

        setParams(max)

        PagedResultList tagList = tagService.search(params) as PagedResultList

        response.setContentType(contentType)

        render template: "/public/tags",
                model: [
                    max: params.max,
                    resultCount: tagList.getTotalCount() ?: 0,
                    offset: params.offset.toString().toInteger(),
                    tagList: tagList
                ]
    }

    /**
     *
     * @return
     */
    def tag() {

        if (!params.title) {
            notFound()
            return
        }

        [tag: params.title]
    }

    /**
     *
     */
    def findByTitle() {

        response.setContentType(contentType)

        String urlTitle = params.title

        if (!urlTitle) {
            notFound()
            return
        }

        Tag tag = tagService.findByUrlTitle(urlTitle)

        if (!tag) {
            notFound()
            return
        }

        render template: "/public/tag", model: [tag: tag]
    }

    protected void notFound() {

        if (response.contentType != contentType) {
            render status: NOT_FOUND
        }
        else {
            Map m = ['status': NOT_FOUND.toString()]
            render m as JSON
        }
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
