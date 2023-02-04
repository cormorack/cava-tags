package cava.tags

class Tag {

    String title, urlTitle, description, category
    Date date = new Date()
    User user
    Set<Media> media
    Set<Tag> tags

    static hasMany = [media: Media, tags: Tag]

    static mappedBy = [tags: 'tags']

    static belongsTo = [User]

    static constraints = {
        title(blank:false, maxSize:100, unique:true, validator: {
            if (it.matches('^.*[!@#$%^&?/;_()*-+-].*$')) {
                return 'title.illegal.character'
            }
        })
        urlTitle(maxSize:100, unique:true)
        description(nullable:true)
        category(nullable:true, maxSize:100)
        user(nullable: true)
    }

    static mapping  = {
        id generator:'sequence', params:[sequence:'tag_seq']
        cache true
        description type:'text'
        tags joinTable: [name: "tag_tag"]
    }

    String toString() {
        return title
    }

    /*Set<Tag> getTags() {

        TagAssociation.findAllByTag(this).collect { it.otherTag } as Set
        *//*def perms = TagAssociation.findAll(
                "from TagAssociation as ta left outer join fetch ta.role.permissions where ta.user = :userInstance",
                [story: userInstance],
                [readOnly:true]
        ).collect { it.role.permissions }.unique() as Set*//*
    }*/
}
