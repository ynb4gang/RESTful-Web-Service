package smth.moySklad.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Getter
@Setter
public class Supply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    private Product product;

    @Min(value = 1)
    private int quantity;

    // getters and setters
}
