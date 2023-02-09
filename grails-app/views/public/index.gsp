<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

    <head>
        <meta name="layout" content="public" />
    </head>

    <body>
        <div class="container">
            <div class="row">
                <h3 class="text-center">Media Tags</h3>
                <div class="col">
                    <main>
                        <section id="searchbox" v-scope="searchBox()"></section>
                        <section id="resultsbox" v-scope="results()"></section>
                    </main>
                    <template id="search">
                        <form @submit.prevent="search">
                            <input
                                    type="text"
                                    v-model="store.term"
                                    placeholder="search for a tag"
                            />
                        </form>
                    </template>
                    <template id="results">
                        <h3>{{ store.msg }}</h3>
                        <div class="tagcloud03">
                            <ul v-if="store.results.length > 0">
                                <li v-for="tag in store.results" :key="tag.id">
                                    <a v-bind:href="'public/tag/' + tag.urlTitle">{{ tag.title }}<span>{{ tag.media.length }}</span></a>
                                </li>
                            </ul>
                        </div>
                    </template>
                </div>
            </div>
        </div>

        <script type="module">

            /*import { createApp, reactive } from '${assetPath(src: 'petite-vue.es.js')}?module'*/
            import { createApp, reactive } from 'https://unpkg.com/petite-vue@0.2.2/dist/petite-vue.es.js?module'

            const store = reactive({
                term: "",
                results: "",
                msg: ""
            });

            const searchBox = function () {
                return {
                    $template: "#search",
                    async search() {
                        if (store.term.length > 0) {
                            store.msg = "loading...";
                            let resp = await fetch(
                                location.origin + '/public/tags?term=' + store.term
                            );
                            let tags = await resp.json();
                            store.results = tags.results;
                            store.msg = 'Results for: ' + store.term;
                            if (tags.results.length == 0) {
                                store.msg = 'There are no results for: ' + store.term;
                            }
                            if (tags.results.length > 1) {
                                store.term = "";
                            }
                        } else {
                            store.msg = "Please type something";
                        }
                        //console.log(store.results);
                    }
                };
            };

            const results = function () {
                fetch(location.origin + '/public/tags')
                    .then((res) => res.json())
                    .then((data) => {
                        this.store.results = data.results
                    })
                return {
                    $template: "#results"
                };
            };

            createApp({
                searchBox,
                results,
                store
            }).mount();

        </script>
    </body>
</html>
