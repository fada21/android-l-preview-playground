android-l-preview-playground
============================
Testing new widgets, material theme and tools available in Android-L preview. I've been given assignment to make a simple app while applying for a role. It's the best opportunity to try out new toys provided in L-preview version.

## I'm playing with
 - RecyclerView with LinearLayoutManager
 - CardView
 - Material theme
 - ...

## Android Programming Task
### Overview:
Your task is to complete an implementation of a Library page for a typical Android eReader application.
The library should present to the user books grouped by categories.
### Specification (refer to design overleaf): 
 - A descriptive header should be present – “Library” 
 - The books are to be visually grouped into the following categories: ‘Crime’, ‘Comedy’, ‘History’ and ‘Thriller’
 - The page should show a list of books for each category (with books being a consistent, readable size)
  - Each book should show its title, the author name and a thumbnail image as a background 
  - By default, thecategory lists should be collapsed, showing one row of books for each category
  - There should be an option to expand each category list (individually) to reveal all the books from that category 
  - A clickable element should allow the expansion and collapse of this view 
 - Each category  should be titled and include a counter that reflects the number of books in that category –  ie. Crime(3)

### Extra: You  should populate your library with sample books.
The URLs given below reveal JSON objects that you shouldquery in your application. The categories should contain the following number of books respectively (3, 10, 20, 5) Categories: Crime, Comedy, History, Thriller
 - https://www.googleapis.com/books/v1/volumes?q=crime
 - https://www.googleapis.com/books/v1/volumes?q=comedy
 - https://www.googleapis.com/books/v1/volumes?q=history
 - https://www.googleapis.com/books/v1/volumes?q=thriller

Final query will look like this:
 - https://www.googleapis.com/books/v1/volumes?q=crime&fields=items(volumeInfo(title,authors,imageLinks(thumbnail)))&maxResults=1
