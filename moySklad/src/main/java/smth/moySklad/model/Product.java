package smth.moySklad.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is required")
    @Size(max = 255, message = "Name must be less than or equal to 255 characters")
    private String name;

    @Size(max = 4096, message = "Description must be less than or equal to 4096 characters")
    private String description;

    @Min(value = 0, message = "Price cannot be less than 0")
    private Double price = 0.0;

    private boolean available = false;

}
