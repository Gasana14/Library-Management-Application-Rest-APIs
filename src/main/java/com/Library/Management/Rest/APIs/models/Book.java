package com.Library.Management.Rest.APIs.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "books")
public class Book  {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "book_id",unique = true)
    private String id;
    private String title;
    private String isbn;
    private float price;
    private String category;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
