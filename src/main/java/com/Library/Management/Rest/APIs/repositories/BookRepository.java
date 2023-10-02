package com.Library.Management.Rest.APIs.repositories;

import com.Library.Management.Rest.APIs.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book,String> {
    Set<Book> findBooksByAuthorId(String authorId);
    Book findBookByIdAndAuthorId(String bookId,String authorId);
}
