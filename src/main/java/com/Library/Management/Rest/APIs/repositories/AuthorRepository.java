package com.Library.Management.Rest.APIs.repositories;

import com.Library.Management.Rest.APIs.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AuthorRepository extends JpaRepository<Author,String> {

}
