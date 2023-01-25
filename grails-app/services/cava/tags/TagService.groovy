package cava.tags

import grails.gorm.services.Service
import org.hibernate.FetchMode as FM

@Service(Tag)
abstract class TagService implements ITagService {

    /**
     * Returns a sorted and paginated List of tags.  Optionally searches for 'term'
     * @param args
     * @return
     */
    List search(Map args) {

        Closure query = {
            if (args.term) {
                or {
                    ilike("title", "%${ args.term }%")
                    ilike("description", "%${ args.term }%")
                }
            }
            fetchMode 'media', FM.JOIN
            order(args.sort, args.order)
            maxResults(args.max)
            firstResult(args.offset)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(['max':args.max, 'offset':args.offset], query)

        return results.unique()
    }

    /**
     *
     * @param title
     * @return
     */
    Tag findByTitle(String title) {

        Tag tag = null

        Closure query = {
            eq("urlTitle", title)
            fetchMode 'media', FM.JOIN
            maxResults(1)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(query)

        if (results.size() > 0) {
            tag = results[0] as Tag
        }
    }

}