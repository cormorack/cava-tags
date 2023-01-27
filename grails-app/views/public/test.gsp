<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <title>Test</title>
    <meta charset="UTF-8" />
    %{--<asset:stylesheet href="styles.css"/>--}%
    <style>
    body {
        font-family: 'Helvetica', Arial, sans-serif;
    }
    a {
        text-decoration: none;
        color: #f66;
    }
    li {
        line-height: 1.5em;
        margin-bottom: 20px;
    }
    .author, .date {
        font-weight: bold;
    }
    </style>
</head>

<body>
    <header>
        <h1>Media Tags</h1>
    </header>

    <div v-scope v-effect="fetchData()">
        <h1>Latest Vue.js Commits</h1>
        <template v-for="branch in branches">
            <input
                    type="radio"
                    :id="branch"
                    :value="branch"
                    name="branch"
                    v-model="currentBranch"
            />
            <label :for="branch">{{ branch }}</label>
        </template>
        %{--<p>vuejs/vue@{{ currentBranch }}</p>--}%
        <ul>
            <li v-for="{ html_url, sha, author, commit } in commits">
                <a :href="html_url" target="_blank" class="commit"
                >{{ sha.slice(0, 7) }}</a
                >
                - <span class="message">{{ truncate(commit.message) }}</span><br />
                by
                <span class="author"
                ><a :href="author.html_url" target="_blank"
                >{{ commit.author.name }}</a
                ></span
                >
                at <span class="date">{{ formatDate(commit.author.date) }}</span>
            </li>
        </ul>
    </div>

    <!-- <script> -->
    <script type="module">

        import { createApp, reactive } from '${assetPath(src: 'petite-vue.es.js')}?module'

        const API_URL = `https://api.github.com/repos/vuejs/vue-next/commits?per_page=3&sha=`

        createApp({
            branches: ['master', 'v2-compat'],
            currentBranch: 'master',
            commits: null,

            truncate(v) {
                const newline = v.indexOf('\n')
                return newline > 0 ? v.slice(0, newline) : v
            },

            formatDate(v) {
                return v.replace(/T|Z/g, ' ')
            },

            fetchData() {
                fetch(`https://api.github.com/repos/vuejs/vue-next/commits?per_page=3&sha=`)
                    .then((res) => res.json())
                    .then((data) => {
                        this.commits = data
                    })
            }
        }).mount()
    </script>
</body>
</html>
