package com.Library.Management.Rest.APIs.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "author_id",unique = true)
    private String id;
    private String name;
    private Integer age;
    private String address;
    @OneToMany(mappedBy = "author",fetch = FetchType.LAZY)
    Set<Book> books;
}
