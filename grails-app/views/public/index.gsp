<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Vue-Petite Itunes Search</title>
    <meta charset="UTF-8" />
    <asset:stylesheet href="styles.css"/>
</head>

<body>
<header>
    <h1>Media Tags</h1>
</header>

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
    <ul v-if="store.results.length > 0">
        <li v-for="tag in store.results" :key="tag.id">
            <div>
                <h4><a v-bind:href="'public/tag/' + tag.urlTitle">{{ tag.title }}</a></h4>
            </div>
        </li>
    </ul>
</template>

<!-- <script> -->
<script type="module">

    import { createApp, reactive } from '${assetPath(src: 'petite-vue.es.js')}?module'

    const store = reactive({
        term: "",
        results: "",
        msg: ""
    });

    const searchBox = function () {
        return {
            $template: "#search",
            async search() {
                if (store.term.length > 1) {
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
