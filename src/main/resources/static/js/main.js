function getIndex(list, id){
        for(var i =0; i<list.length; i++){
            if(list[i].id === id){
                return i;
            }
        }
        return -1;
}

var bookApi = Vue.resource('/book{/id}');

Vue.component('book-form',{
    props: ['books','bookAttr'],
    data: function(){
    return {
        name:'',
        price:'',
        genre:'',
        id: ''
    }
},
    watch:{
        bookAttr: function(newVal,odVal){
            this.name = newVal.name;
            this.price = newVal.price;
            this.genre = newVal.genre;
            this.id = newVal.id;
        }
    },
    template: '<div>'+
            '<input type="text" name="name" placeholder="Name" v-model="name" />'+

            '<input type="number" name="price" min="0" placeholder="Price" v-model="price" />'+
                        '<select name="genre" v-model="genre">'+
                             '<option disabled>Genre</option>'+
                             '<option value="ADVENTURE">adventure</option>'+
                             '<option value="DETECTIVE">detective</option>'+
                             '<option value="DRAMA">drama</option>'+
                             '<option value="FANTASY">fantasy</option>'+
                             '<option value="HORROR">horror</option>'+
                        '</select>'+
            '<input type="button" value="Create" @click="create" />'+
          '</div>',

    methods: {
    create: function(){
            var book = {name: this.name, price: this.price, genre: this.genre};
            if(this.id){
                bookApi.update({id: this.id}, book).then(result =>
                    result.json().then(data =>{
                            var index = getIndex(this.books, data.id);
                            this.books.splice(index,1,data);
                            this.name = ''
                            this.price = ''
                            this.genre = ''
                            this.id = ''
                    })
                )
            }else{
            bookApi.save({}, book).then(result =>
                        result.json().then(data => {
                        this.books.push(data);
                        this.name = ''
                        this.price = ''
                        this.genre = ''
                        })
                        )
            }
    }
    }
});

Vue.component('book-row',{
props: ['book','editMethod','books'],
template: '<div>'+
            '<b>{{book.id}}.</b> {{book.name}} <i>{{book.genre.toLowerCase()}}</i> ({{book.price}} $)'+
            '<span style="position: absolute; right: -7%">' +
                 '<input type="button" value="Edit" @click="edit" />' +
                 '<input type="button" value="X" @click="del" />' +
            '</span>' +
          '</div>',
  methods: {
  edit: function(){
    this.editMethod(this.book);
  },
  del: function() {
              bookApi.remove({id: this.book.id}).then(result => {
                  if (result.ok) {
                      this.books.splice(this.books.indexOf(this.book), 1)
                  }
              })
          }
  }
});

Vue.component('book-list', {
props: ['books'],
data: function(){
   return {
   book: null
   }
},
  template:
  '<div style="position: relative; width: 300px;">' +
    '<book-form :books="books" :bookAttr="book" />'+
    '<book-row v-for="book in books" :key="book.id" '+
    ':book="book" :editMethod="editMethod" :books="books" />'+
  '</div>',
  created: function(){
  bookApi.get().then(result =>
  result.json().then(data =>
  data.forEach(book => this.books.push(book))))
  },
  methods: {
  editMethod: function(book){
  this.book=book;
  }
  }
});

var app = new Vue({
  el: '#app',
  template: '<book-list :books="books" />',
  data: {
    books: []
  }
});