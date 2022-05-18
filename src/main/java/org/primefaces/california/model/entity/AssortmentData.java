package org.primefaces.california.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect
@Entity
@Table(name = "ASSORTMENT_DATA")
public class AssortmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Market market;

    @ManyToOne
    private Product product;
    private int amount;

    public AssortmentData(Market market, Product product, int amount) {
        this.market = market;
        this.product = product;
        this.amount = amount;
    }
}
