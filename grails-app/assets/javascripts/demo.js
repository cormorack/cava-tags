PetiteVue.createApp({
  results: [],
  fetchData() {
    fetch(location.origin + '/public/tags?sort=title&order=asc&max=100')
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
    newEntries = ($.object.isObjectValues(this.filter)) ? $.array.filtered(newEntries, this.filter) : newEntries
    this.pageInfo = $.array.pageInfo(newEntries, this.pageActive, this.limitPerPage)
    this.totalPages = $.array.pages(newEntries, this.limitPerPage)
    newEntries = $.array.sortBy(newEntries, this.sort.column, this.sort.by)
    newEntries = $.array.paginate(newEntries, this.pageActive, this.limitPerPage)
    return newEntries
  },
  get renderPagination() {
    return $.array.pagination(this.totalPages, this.pageActive, 2)
  },
  checks() {
    return Array.from(this.filteredEntries).filter(i => i.checked).map(i => i.id)
  }
}).mount()