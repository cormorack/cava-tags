package cava.tags

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
interface ITagService {

    Tag get(Serializable id)

    Tag findByUrlTitle(String urlTitle)

    List<Tag> list(Map args)

    Long count()

    Tag save(Tag tag)

    Tag save(String title, String urlTitle)

    void delete(Serializable id)
}
