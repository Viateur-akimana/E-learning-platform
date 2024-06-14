    package org.example.restapi.service;

    import org.example.restapi.model.Book;
    import org.example.restapi.model.Library;
    import org.example.restapi.model.Publisher;
    import org.example.restapi.repository.BookRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class BookService {

        @Autowired
        private BookRepository bookRepository;

        @Autowired
        private PublisherService publisherService;

        @Autowired
        private LibraryService libraryService;

        public List<Book> getAllBooks() {
            return bookRepository.findAll();
        }

        public Optional<Book> getBookById(Long id) {
            return bookRepository.findById(id);
        }

        public Book saveBook(Book book) {
            return bookRepository.save(book);
        }

        public Book saveOrUpdateBook(Book book) {
            if (book.getPublisher() == null || book.getPublisher().getName() == null) {
                throw new IllegalArgumentException("Publisher name cannot be null");
            }
            if (book.getLibrary() == null || book.getLibrary().getName() == null) {
                throw new IllegalArgumentException("Library name cannot be null");
            }

            String publisherName = book.getPublisher().getName();
            String libraryName = book.getLibrary().getName();

            Publisher publisher = publisherService.getOrCreatePublisherByName(publisherName);

            Library library = libraryService.getOrCreateLibraryByName(libraryName);

            book.setPublisher(publisher);
            book.setLibrary(library);

            return bookRepository.save(book);
        }

        public void deleteBook(Long id) {
            bookRepository.deleteById(id);
        }
    }
