<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>CodePen - Data Table with Petite-Vue</title>
    <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.0.2/css/bootstrap.min.css'>
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

    <script src='https://unpkg.com/petite-vue@0.3.0/dist/petite-vue.iife.js'></script>
    <script src='https://unpkg.com/alga-js@0.0.8/dist/alga-umd.js'></script>
    <asset:javascript src="demo.js"/>
    </body>
</html>
