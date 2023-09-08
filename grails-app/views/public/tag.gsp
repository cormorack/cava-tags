<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Tag Detail</title>
        <meta name="layout" content="public" />
    </head>

    <body>
        <div class="container">
            <div class="row">
                <h3 class="text-center">Media Tags</h3>
                <div class="col">
                    <div v-scope v-effect="fetchData()" class="tag">
                        <h3>{{ results.title }}
                        <shiro:isLoggedIn>
                             <a :href="'/tag/edit/' + results.id" class="btn btn-primary btn-sm">Edit</a>
                        </shiro:isLoggedIn>
                        </h3>
                        <h4>Media</h4>
                        <ul>
                            <li v-for="media in results.media">
                                <a v-bind:href="media.url">{{ media.title }}</a>
                                <span v-show="media.type == 'VIDEO'">
                                    <i class="bi bi-camera-video"></i>
                                </span>
                                <span v-show="media.type == 'IMAGE'">
                                    <i class="bi bi-image"></i>
                                </span>
                            </li>
                        </ul>
                        <p>{{ results.description }}</p>
                        <h4>Associated Tags</h4>
                        <ul>
                            <li v-for="tag in results.tags">
                                <a v-bind:href="tag.urlTitle">{{ tag.title }}</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <asset:javascript src="bootstrap5.2.min.js" />
        <script type="module">

            import { createApp } from '${assetPath(src: 'petite-vue.es.js')}?module'

            createApp({
                results: [],
                fetchData() {
                    fetch(location.origin + '/public/findByTitle?title=' + '${tag}')
                        .then((res) => res.json())
                        .then((data) => {
                            this.results = data
                        }
                    )
                }
            }).mount();
        </script>
    </body>
</html>


