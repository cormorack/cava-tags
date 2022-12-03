package cava.tags

class UrlMappings {

    static mappings = {

        "/public/tag/$title?" {
            controller = "public"
            action = "tag"
            params = "$title"
        }

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/" {
            controller = "public"
            action = "index"
        }

        //"/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
