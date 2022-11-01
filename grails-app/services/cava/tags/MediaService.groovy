package cava.tags

import grails.gorm.services.Service

@Service(Media)
interface MediaService {

    Media get(Serializable id)

    List<Media> list(Map args)

    Long count()

    void delete(Serializable id)

    Media save(Media media)

}