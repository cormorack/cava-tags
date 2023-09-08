package cava.tags

class TagAssociation implements Serializable {

    Tag tag
    Tag otherTag

    static mapping = {
        table 'tag_tag'
        version false
        otherTag column:'tags__id'
        id composite: ['tag', 'otherTag']
        cache true
    }

    static boolean exists(Tag tag, Tag otherTag) {

        TagAssociation tagAssociation = TagAssociation.findByTagAndOtherTag(tag, otherTag)
        if (tagAssociation) {
            return true
        }
        return false
    }

    static TagAssociation create(Tag tag, Tag otherTag) {

        TagAssociation tagAssociation = new TagAssociation(tag: tag, otherTag: otherTag)
        tagAssociation.save(flush:true)

        TagAssociation otherAssociation = new TagAssociation(tag: otherTag, otherTag: tag)
        otherAssociation.save(flush:true)

        return tagAssociation
    }

    static boolean remove(Tag tag, Tag otherTag) {

        TagAssociation tagAssociation = TagAssociation.findByTagAndOtherTag(tag, otherTag)

        if (tagAssociation !=null && tagAssociation.delete(flush: true)) {
            return true
        }
        return false
    }

    static void removeAll(Tag tag) {
        executeUpdate("DELETE FROM TagAssociation WHERE tag=:tag", [tag: tag])
        executeUpdate("DELETE FROM TagAssociation WHERE otherTag=:tag", [tag: tag])
    }

    boolean equals(other) {

        if (!(other instanceof TagAssociation)) {
            return false
        }
        return other.tag.id == tag.id && other.otherTag.id == otherTag.id
    }
}
