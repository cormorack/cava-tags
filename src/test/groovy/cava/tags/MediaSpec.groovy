package cava.tags

import grails.testing.gorm.DataTest
import spock.lang.Specification
import spock.lang.Unroll

class MediaSpec extends Specification implements DataTest {

    void setupSpec() {
        mockDomain Media
    }

    void "test basic persistence mocking"() {
        setup:
        new Media(title: 'Test', url: 'https://foo.com').save()

        expect:
        Media.count() == 1
    }

    void 'test title cannot be null'() {
        when:
        Media media = new Media(title: null, url: null)

        then:
        !media.validate(['title'])
        media.errors['title'].code == 'nullable'
    }

    void 'test title cannot be empty'() {
        when:
        Media media = new Media(title: '', url: '')

        then:
        !media.validate(['title'])
    }

    void 'test that title can have a maximum of 255 characters'() {

        setup:
        Media media = new Media(title: 'test', url: 'test')

        when: 'for a string of 256 characters'
        String str = 'a' * 256
        media.title = str

        then: 'title validation fails'
        !media.validate(['title'])
        media.errors['title'].code == 'maxSize.exceeded'

        when: 'for a string of 255 characters'

        str = 'a' * 255
        media.title = str

        then: 'title validation passes'

        media.validate(['title'])
    }

    @Unroll('Media.validate() with url: #value should have returned #expected with errorCode: #expectedErrorCode')
    void "test url validation"() {

        setup:
        Media media = new Media(title: 'test', url: 'test')

        when:
        media.url = value
        String longerValue = value + 'a' * 255

        then:
        expected == media.validate(['url'])
        media.errors['url']?.code == expectedErrorCode

        where:

        value                  | expected | expectedErrorCode
        null                   | false    | 'nullable'
        ''                     | false    | 'blank'
        'https://foo.com'      | true     |  null
        'foo.com'              | false    | 'url.invalid'
        'https://foo.com/'+'a'*255 | false    | 'maxSize.exceeded'
    }
}
