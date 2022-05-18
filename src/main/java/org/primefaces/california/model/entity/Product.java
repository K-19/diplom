package org.primefaces.california.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.util.SerializableSupplier;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.persistence.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonAutoDetect
@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nameproduct")
    private String name;
    private String mainType;
    @Column(name = "typeproduct")
    private String type;
    private Double price;
    private Double cost;
    private Double rating;
    private Double ratingMarks;
    private String article;
    private String country;
    private String trademark;
    private String mass;
    private String priceByKg;
    private String priceByLiter;
    private String priceByThing;

    @OneToOne(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private NutritionalValue nutritionalValue;
    private String description;
    private String composition;
    @JsonProperty("img")
    private byte[] img;

    @JsonSerialize(using= org.primefaces.california.util.ByteArraySerializer.class)
    public byte[] getImg() {
        return img;
    }

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesData> salesDataList = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssortmentData> assortmentDataList = new ArrayList<>();

    public void addNutritional(NutritionalValue nutritionalValue) {
        nutritionalValue.setProduct(this);
        this.nutritionalValue = nutritionalValue;
    }

    public void removeNutritional() {
        if (nutritionalValue != null) {
            nutritionalValue.setProduct(null);
            this.nutritionalValue = null;
        }
    }

    public String getPriceString() {
        return price + " BYN";
    }
}
