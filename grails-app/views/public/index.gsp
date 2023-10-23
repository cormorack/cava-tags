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
                    <form id="demo" autocomplete="off">
                        <div class="form-group">
                            <label for="term">Search:</label>
                            <input id="term" name="term" class="form-control" type="text" />
                        </div>
                        <div v-effect="fetchData()" class="tagcloud03">
                            <ul v-if="results.length > 0">
                                <li v-for="tag in results" :key="tag.id">
                                    <a v-bind:href="'public/tag/' + tag.urlTitle">{{ tag.title }}<span>{{ tag.media.length }}</span></a>
                                </li>
                            </ul>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <asset:javascript src="petite-vue-iife.js"/>
        <script>

            let query = (getParam('term')!= null) ? getParam('term'): '';
            let max = (getParam('max') != null) ? getParam('max') : 100;
            max = parseInt(max, 10);

            let offset = (getParam('offset') != null) ? getParam('offset') : 0;
            offset = parseInt(offset, 10);

            let serviceURL = location.origin + '${context}';
            let serviceURI = serviceURL + 'public/tags?sort=title&order=asc&max=' + max + '&offset=' + offset;

            if (query) {
                serviceURI = serviceURI  + '&term=' + query;
            }

            function getParam(name) {
                if (name = (new RegExp('[?&]' + encodeURIComponent(name) + '=([^&]*)')).exec(location.search))
                    return decodeURIComponent(name[1]);
            }

            PetiteVue.createApp({
                results: [],
                fetchData() {
                    fetch(serviceURI)
                        .then((res) => res.json())
                        .then((data) => {
                                this.results = data.results
                            }
                        )
                },
            }).mount()

        </script>
    </body>
</html>
