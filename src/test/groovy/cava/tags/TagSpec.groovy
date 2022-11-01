package cava.tags

import grails.testing.gorm.DataTest
import spock.lang.Specification

class TagSpec extends Specification implements DataTest {

    void setupSpec() {
        mockDomain Tag
    }

    void "test basic persistence mocking"() {

        setup:
        new Tag(title: 'Test', urlTitle: 'test-tag').save()

        expect:
        Tag.count() == 1
    }

    void 'test title and urlTitle cannot be null'() {

        when:
        Tag tag = new Tag(title: null, urlTitle: null)

        then:
        !tag.validate(['title'])
        tag.errors['title'].code == 'nullable'
        !tag.validate(['urlTitle'])
        tag.errors['urlTitle'].code == 'nullable'
    }

    void 'test title and urlTitle cannot be empty'() {

        when:
        Tag tag = new Tag(title: '', urlTitle: '')

        then:
        !tag.validate(['title'])
        !tag.validate(['urlTitle'])
    }

    void 'test that title and urlTitle can have a maximum of 100 characters'() {

        setup:
        Tag tag = new Tag(title: 'test', urlTitle: 'test')

        when: 'for a string of 101 characters'

        String str = 'a' * 101
        tag.title = str
        tag.urlTitle = str

        then: 'title validation fails'

        !tag.validate(['title'])
        tag.errors['title'].code == 'maxSize.exceeded'

        !tag.validate(['urlTitle'])
        tag.errors['urlTitle'].code == 'maxSize.exceeded'

        when: 'for a string of 100 characters'

        str = 'a' * 100
        tag.title = str
        tag.urlTitle = str

        then: 'title validation passes'

        tag.validate(['title'])
        tag.validate(['urlTitle'])
    }

    void 'test that urlTitle cannot contain illegal characters'() {

        when:
        Tag tag = new Tag(title: '', urlTitle: '')
        List chars = ['!', '@', '#', '$', '%', '^', '&', '?', '/', ';', '(', ')', '*', '+']

        then:
        for (String c in chars) {
            tag.urlTitle = c
            !tag.validate(['urlTitle'])
        }
    }
}
