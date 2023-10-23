<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Data Table with Petite-Vue</title>
    <asset:stylesheet href="bootstrap5.2.css"/>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css'>
    <meta name="layout" content="public" />
</head>

<body>
    <div v-effect="fetchData()" v-scope class="m-3">
        <h3>Media Tags</h3>
        <div class="d-flex justify-content-between my-3">
            <div>
                <input type="search" v-model="search" class="form-control" placeholder="Search here..." @click.prevent="pageActive = 1">
            </div>
        </div>
        <table class="table table-bordered table-striped">
            <thead>
            <tr>
                %{--<th scope="col" rowspan="2">#</th>--}%
                <th v-for="(col, index) in columns" :key="index" scope="col">
                    <div class="d-flex justify-content-between">
                        <div>{{ col.text }}</div>
                        <div>
                            <template v-if="sort.column === col.name && sort.by === 'asc'">
                                <i class="fas fa-sort-up" @click="sort = {column: col.name, by: 'desc'}"></i>
                            </template>
                            <template v-else-if="sort.column === col.name && sort.by === 'desc'">
                                <i class="fas fa-sort-down" @click="sort = {column: col.name, by: ''}"></i>
                            </template>
                            <template v-else>
                                <i class="fas fa-sort" @click="sort = {column: col.name, by: 'asc'}"></i>
                            </template>
                        </div>
                    </div>
                </th>
            </tr>
            <tr>
                <th v-for="(col2, index) in columns" :key="index" scope="col">
    %{--
                    <input type="search" v-model="filter[col2.name]" class="form-control" :placeholder="`Filter ${col2.name}...`" @click.prevent="pageActive = 1">
    --}%
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(entry, index) in filteredEntries" :key="index">
    %{--
                <td><input type="checkbox" class="form-check-input" @click="entry.checked = $el.checked ? true : false" v-effect="$el.checked = entry.checked"></td>
    --}%
                <td v-for="(column) in columns">
                    <a v-if="column.name === 'title'" v-bind:href="'tag/' + entry.urlTitle">{{ entry[column.name] }}</a>
                    <p v-if="column.name === 'description'">{{ entry[column.name] }}</p>
                    <ul v-if="column.name === 'media'">
                        <li v-for="media in entry.media">
                            <a v-bind:href="media.url">{{ media.title }}</a>
                            <span v-show="media.type == 'VIDEO'">
                                <i class="bi bi-camera-video"></i>
                            </span>
                            <span v-show="media.type == 'IMAGE'">
                                <i class="bi bi-image"></i>
                            </span>
                        </li>
                    </ul>
                    <ul v-if="column.name === 'tags'">
                        <li v-for="tag in entry.tags">
                            <a v-bind:href="'tag/' + tag.urlTitle">{{ tag.title }}</a>
                        </li>
                    </ul>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="d-flex justify-content-between align-items-stretch">
            <div class="d-flex align-items-center" style="margin-top: -17px;">
                <div class="me-2">Show</div>
                <select class="form-select" v-model="limitPerPage" style="width: 80px">
                    <option :value="5">5</option>
                    <option :value="10">10</option>
                    <option :value="25">25</option>
                    <option :value="50">50</option>
                    <option :value="100">100</option>
                </select>
                <div class="ms-2">from {{ pageInfo.from }} to {{ pageInfo.to }} of {{ pageInfo.of }} entries</div>
            </div>
            <div>
                <ul class="pagination">
                    <li class="page-item">
                        <a class="page-link text-dark" href="#" @click.prevent="pageActive = 1">&#171;</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link text-dark" href="#" @click.prevent="pageActive = (pageActive > 1) ? Number(pageActive) - 1 : pageActive">&#8249;</a>
                    </li>
                    <li v-for="(page, index) in renderPagination" :key="index" :class="['page-item', {disabled: isNaN(page) === true, active: page === pageActive}]">
                        <a :class="['page-link', {'text-dark': page !== pageActive}]" href="#" @click.prevent="pageActive = page">{{ page }}</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link text-dark" href="#" @click.prevent="pageActive = (pageActive < totalPages) ? pageActive + 1 : pageActive">&#8250;</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link text-dark" href="#" @click.prevent="pageActive = totalPages">&#187;</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <asset:javascript src="petite-vue-iife.js"/>
    <asset:javascript src="alga-umd.js"/>
    <script>

        let query = getParam('q');
        let max = (getParam('max') != null) ? getParam('max') : 50;
        max = parseInt(max, 10);

        let offset = (getParam('offset') != null) ? getParam('offset') : 0;
        offset = parseInt(offset, 10);

        let serviceURL = location.origin + '${context}';
        let serviceURI = serviceURL + 'public/tags?sort=title&order=asc&max=' + max + '&offset=' + offset;

        if (query) {
            serviceURI = serviceURI  + '&q=' + query;
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
            columns: [
                {name: 'title', text:'Title'},
                {name: 'description', text: 'Description'},
                {name: 'media', text: 'Media'},
                {name: 'tags', text: 'Tags'},
                {name: 'date', text: 'Date'}
            ],
            filter: {
                title: '',
                description: ''
            },
            pageActive: 1, //or currentPage
            limitPerPage: 10, //or currentEntries
            pageInfo: {}, //or show
            totalPages: 1, //or allPages
            search: '',
            sort: {
                column: 'id',
                by: 'asc'
            },
            get filteredEntries() {

                let newEntries = this.results
                if (this.search.length >= 2) {
                    newEntries = $.array.search(this.results, this.search)
                }
                newEntries = ( $.object.isObjectValues( this.filter)) ? $.array.filtered( newEntries, this.filter) : newEntries
                this.pageInfo = $.array.pageInfo( newEntries, this.pageActive, this.limitPerPage)
                this.totalPages = $.array.pages( newEntries, this.limitPerPage)
                newEntries = $.array.sortBy( newEntries, this.sort.column, this.sort.by)
                newEntries = $.array.paginate( newEntries, this.pageActive, this.limitPerPage)
                return newEntries
            },
            get renderPagination() {
                return $.array.pagination( this.totalPages, this.pageActive, 2)
            },
            checks() {
                return Array.from( this.filteredEntries).filter( i => i.checked).map( i => i.id)
            }
        }).mount()

    </script>
    </body>
</html>
