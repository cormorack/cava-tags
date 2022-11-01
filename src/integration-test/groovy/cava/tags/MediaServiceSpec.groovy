package cava.tags

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class MediaServiceSpec extends Specification {

    MediaService mediaService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Media(...).save(flush: true, failOnError: true)
        //new Media(...).save(flush: true, failOnError: true)
        //Media media = new Media(...).save(flush: true, failOnError: true)
        //new Media(...).save(flush: true, failOnError: true)
        //new Media(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //media.id
    }

    void "test get"() {
        setupData()

        expect:
        mediaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Media> mediaList = mediaService.list(max: 2, offset: 2)

        then:
        mediaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        mediaService.count() == 5
    }

    void "test delete"() {
        Long mediaId = setupData()

        expect:
        mediaService.count() == 5

        when:
        mediaService.delete(mediaId)
        sessionFactory.currentSession.flush()

        then:
        mediaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Media media = new Media()
        mediaService.save(media)

        then:
        media.id != null
    }
}
