package cava.tags

import grails.gorm.services.Service

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
            order(args.sort, args.order)
            maxResults(args.max)
            firstResult(args.offset)
            setReadOnly true
        }

        List results = Tag.createCriteria().list(['max':args.max, 'offset':args.offset], query)
    }

}