package cava.tags

import grails.gorm.PagedResultList
import grails.gorm.transactions.Transactional
import org.grails.orm.hibernate.HibernateDatastore
import grails.test.hibernate.HibernateSpec
import org.springframework.test.annotation.Rollback
import org.springframework.transaction.PlatformTransactionManager
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification


class TagServiceSpec extends HibernateSpec  {

    @Shared
    TagService tagService

    /*@Shared
    @AutoCleanup
    HibernateDatastore hibernateDatastore

    @Shared
    PlatformTransactionManager transactionManager*/

    void setupSpec() {
        /*hibernateDatastore = new HibernateDatastore(Tag)
        transactionManager = hibernateDatastore.getTransactionManager()
        tagService = this.hibernateDatastore.getService(TagService)*/
        tagService = hibernateDatastore.getService(TagService)
    }

    /*@Transactional
    def setup() {
        new Tag(title: "Test Tag", urlTitle: "test-tag").save()
    }*/

    //@Rollback
    void "test find tag by title"() {

        given:
        new Tag(title: "Test Tag", urlTitle: "test-tag").save(flush:true)

        when:
        Tag tag = tagService.findByUrlTitle("test-tag")

        then:
        tag.title == "Test Tag"
        tag.urlTitle == "test-tag"

        cleanup:
        tagService.delete(tag.id)
    }

    void "test save tag"() {

        when:
        Tag tag = tagService.save("Test Tag", "test-tag").save(flush:true)

        then:
        tag.title == "Test Tag"
        tag.urlTitle == "test-tag"
        tagService.count() == old(tagService.count()) + 1

        cleanup:
        tagService.delete(tag.id)
    }

    void "test search"() {

        given:
        new Tag(title: "Test Tag", urlTitle: "test-tag").save(flush:true)
        PagedResultList tags = null
        Map params = [max: 1, offset: 0, sort: 'date', order: 'desc']

        when:
        tags = tagService.search(params + [term: 'foo'])

        then:
        tags.getTotalCount() == 0

        when:
        tags = tagService.search(params + [term: 'test'])

        then:
        tags.getTotalCount() == 1

        cleanup:
        tagService.delete(tags.get(0).id)
    }
}
