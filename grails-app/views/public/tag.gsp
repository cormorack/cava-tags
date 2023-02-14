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
                        <h3>{{ results.title }}</h3>
                        <shiro:isLoggedIn>
                            <br /><a :href="'/tag/edit/' + results.id" class="btn btn-primary btn-sm">Edit</a>
                        </shiro:isLoggedIn>
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
                        <h4>Tags</h4>
                        <ul>
                            <li v-for="tag in results.tags">
                                <a v-bind:href="tag.urlTitle">{{ tag.title }}</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
        %{--<asset:javascript src="petite-vue.es.js" />--}%
    %{--<script src="https://unpkg.com/petite-vue" defer init></script>--}%
    <script type="module">

            /*import { createApp } from '${assetPath(src: 'petite-vue.es.js')}?module'*/
            import { createApp } from 'https://unpkg.com/petite-vue@0.2.2/dist/petite-vue.es.js?module'

            createApp({
                results: [],
                fetchData() {
                    fetch(location.origin + '/public/findByTitle?title=' + '${tag}')
                        .then((res) => res.json())
                        .then((data) => {
                            this.results = data.results
                        }
                    )
                }
            }).mount();

        </script>
    </body>
</html>


