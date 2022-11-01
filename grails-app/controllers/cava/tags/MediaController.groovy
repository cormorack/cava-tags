package cava.tags

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MediaController {

    MediaService mediaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond mediaService.list(params), model:[mediaCount: mediaService.count()]
    }

    def show(Long id) {
        respond mediaService.get(id)
    }

    def create() {
        respond new Media(params)
    }

    def save(Media media) {
        if (media == null) {
            notFound()
            return
        }

        try {
            mediaService.save(media)
        } catch (ValidationException e) {
            respond media.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'media.label', default: 'Media'), media.id])
                redirect media
            }
            '*' { respond media, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond mediaService.get(id)
    }

    def update(Media media) {
        if (media == null) {
            notFound()
            return
        }

        try {
            mediaService.save(media)
        } catch (ValidationException e) {
            respond media.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'media.label', default: 'Media'), media.id])
                redirect media
            }
            '*'{ respond media, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        mediaService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'media.label', default: 'Media'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'media.label', default: 'Media'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
