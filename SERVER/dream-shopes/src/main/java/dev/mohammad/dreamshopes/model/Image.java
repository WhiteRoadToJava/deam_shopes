package dev.mohammad.dreamshopes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filename;
    private String fileType;
    @Lob
    private Blob image;
    private String downloadUrl;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
