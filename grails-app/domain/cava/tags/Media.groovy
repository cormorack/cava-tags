package cava.tags

import grails.compiler.GrailsCompileStatic

//@GrailsCompileStatic
class Media {

    String title, description, url
    Date date = new Date()
    Set<Tag> tags

    enum Type {

        IMAGE ('Image'),
        VIDEO ('Video')

        String name

        Type (String name) {
            this.name = name
        }

        String toString() { name }

        String getKey() { name() }
    }

    Type type

    static hasMany = [tags:Tag]

    static belongsTo = [Tag]

    static constraints = {
        title(blank: false, maxSize: 255)
        description(nullable: true)
        type(nullable: true, maxSize: 55)
        url(blank: false, url: true, maxSize: 255)
    }

    static mapping = {
        id generator:'sequence', params:[sequence:'media_seq']
        description type:'text'
    }

    String toString() {
        return title
    }
}
