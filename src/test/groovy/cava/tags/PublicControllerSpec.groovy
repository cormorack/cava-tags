package cava.tags

import grails.gorm.PagedResultList
import org.grails.datastore.mapping.query.Query
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class PublicControllerSpec extends Specification implements ControllerUnitTest<PublicController> {

    void "test the tags action"() {

        /*given:
        views['/public/_tags.gson'] = "'{\"max\":10,\"offset\":0, \"resultCount\":0,\"results\":[]}"
        List results = ["10", "0", "0", []]
        Map args = [max: 10, offset: 0, sort: 'date', order: 'desc']
        Query query
        PagedResultList pagedResultList = new PagedResultList(query)
        controller.tagService = Stub(TagService) {
            search(_) >> pagedResultList
        }

        when: 'json request is sent'
        controller.tags(10)
        request.method = 'GET'

        then: 'OK status code is set'

        //model.tagList
        //response.status == 200
        //response.contentType == "application/json;charset=UTF-8"
        //response.json == '{"max":10,"offset":0, "resultCount":0,"tagList":[]}'*/
    }
}
