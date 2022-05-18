package org.primefaces.california.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
@Entity
@Table(name = "SALES_DATA")
public class SalesData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Market market;

    @ManyToOne
    private Product product;
    private int amount;
    private int year;
    private int month;

    public SalesData(Market market, Product product, int amount, int year, int month) {
        this.market = market;
        this.product = product;
        this.amount = amount;
        this.year = year;
        this.month = month;
    }
}
