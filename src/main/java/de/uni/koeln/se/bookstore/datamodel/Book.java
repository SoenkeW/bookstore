package de.uni.koeln.se.bookstore.datamodel;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name ="name")
    private String name;
    @Column(name ="author")
    private String author;
    @Column(name ="year")
    private Integer year;
    @Column(name ="price")
    private Double price;

}

