package cava.tags

class Tag {

    String title, urlTitle, description, category
    Date date = new Date()
    Set<Media> media

    static hasMany = [media: Media]

    static constraints = {
        title(blank:false, maxSize:100, unique:true, validator: {
            if (it.matches('^.*[!@#$%^&?/;_()*-+-].*$')) {
                return 'title.illegal.character'
            }
        })
        urlTitle(maxSize:100, unique:true)
        description(nullable:true)
        category(nullable:true, maxSize:100)
    }

    static mapping  = {
        id generator:'sequence', params:[sequence:'tag_seq']
        cache true
        description type:'text'
    }

    String toString() {
        return title
    }
}
