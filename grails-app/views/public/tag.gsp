<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Vue-Petite Itunes Search</title>
    <meta charset="UTF-8" />
    <asset:stylesheet href="tags.css"/>
</head>

<body>
<header>
    <h1>Media Tags</h1>
</header>

<main>
    <section id="resultsbox" v-scope="results()"></section>
</main>

<template id="results">
    <h3>{{ store.results.title }}</h3>
    <div class="tagcloud03">
        <ul>
            <li v-for="media in store.results.media" :key="media.id">
                <a v-bind:href="media.url">{{ media.title }}</a>
            </li>
        </ul>
    </div>
</template>

<!-- <script> -->
<script type="module">

    import { createApp, reactive } from '${assetPath(src: 'petite-vue.es.js')}?module'

    const store = reactive({
        results: ""
    });

    const results = function() {

        fetch(location.origin + '/public/findByTitle?title=' + '${tag}')
            .then((res) => res.json())
            .then((data) => {
                this.store.results = data.results
            }
        )
        return {
            $template: "#results"
        };
    };

    createApp({
        results,
        store
    }).mount();

</script>
</body>
</html>
