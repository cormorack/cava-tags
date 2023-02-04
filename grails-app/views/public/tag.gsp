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
                            <br /><a :href="'/tag/edit/' + results.id">Edit</a>
                        </shiro:isLoggedIn>
                        <div class="tagcloud03">
                            <ul>
                                <li v-for="tag in results.tags" :key="tag.id">
                                    {{ tag.title }}
                                </li>
                            </ul>
                        </div>
                        <div class="tagcloud03">
                            <ul>
                                <li v-for="media in results.media" :key="media.id">
                                    <a v-bind:href="media.url">{{ media.title }}</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
        <script type="module">

            import { createApp } from '${assetPath(src: 'petite-vue.es.js')}?module'

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


