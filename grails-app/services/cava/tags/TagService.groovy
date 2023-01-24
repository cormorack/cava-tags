package cava.tags

import grails.gorm.services.Service
import org.hibernate.FetchMode as FM
import org.hibernate.sql.JoinType

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
            //resultTransformer Criteria.DISTINCT_ROOT_ENTITY
            order(args.sort, args.order)
            maxResults(args.max)
            firstResult(args.offset)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(['max':args.max, 'offset':args.offset], query)
    }

}