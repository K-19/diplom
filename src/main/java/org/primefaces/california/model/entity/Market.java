package org.primefaces.california.model.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonAutoDetect
@Entity
@Table(name = "MARKETS")
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String city;
    private String address;

    @Column(name = "typemarket")
    private String type;

    public String getType() {
        return StringUtils.capitalize(type.toLowerCase());
    }

    private String workTime;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesData> salesDataList = new ArrayList<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssortmentData> assortmentDataList = new ArrayList<>();
}
