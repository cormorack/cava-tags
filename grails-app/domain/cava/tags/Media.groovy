package cava.tags

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Media {

    String title, description, url
    Date date = new Date()
    Set<Tag> tags

    static hasMany = [tags:Tag]

    static belongsTo = [Tag]

    static constraints = {
        title(blank: false, maxSize: 255)
        description(nullable: true)
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
