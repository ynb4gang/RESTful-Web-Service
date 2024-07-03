package smth.moySklad;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import smth.moySklad.model.Product;
import smth.moySklad.model.Sale;
import smth.moySklad.model.Supply;
import smth.moySklad.repository.ProductRepository;
import smth.moySklad.repository.SaleRepository;
import smth.moySklad.repository.SupplyRepository;
import smth.moySklad.service.ProductService;
import smth.moySklad.service.SaleService;
import smth.moySklad.service.SupplyService;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MoySkladApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SupplyService supplyService;

    @MockBean
    private SupplyRepository supplyRepository;

    @MockBean
    private SaleService saleService;

    @MockBean
    private  SaleRepository saleRepository;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product();
        product.setName("Test Product");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void testProductNameNotBlank() {
        Product product = new Product();
        product.setName("");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    public void testProductNameSize() {
        Product product = new Product();
        product.setName("Very long product name exceeding 50 characters");

        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 50", violations.iterator().next().getMessage());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        Optional<Product> found = productService.getProductById(1L);
        assertEquals("Test Product", found.get().getName());
    }

    @Test
    public void testSaveProduct() {
        Product product = new Product();
        product.setName("Test Product");

        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getId());
        assertEquals("Test Product", savedProduct.getName());
    }

    @Test
    public void testCreateSupply() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Supply supply = new Supply();
        supply.setDocumentName("Supply Document");
        supply.setProduct(product);
        supply.setQuantity(10);

        when(supplyService.createSupply(any(Supply.class))).thenReturn(supply);

        mockMvc.perform(MockMvcRequestBuilders.post("/supplies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supply)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentName").value("Supply Document"));

        verify(supplyService, times(1)).createSupply(any(Supply.class));
    }

    @Test
    public void testSupplyDocumentNameSize() {
        Supply supply = new Supply();
        supply.setDocumentName("Very long supply document name exceeding 255 characters");

        Set<ConstraintViolation<Supply>> violations = validator.validate(supply);
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 255", violations.iterator().next().getMessage());
    }


    @Test
    public void testCreateSale() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");

        Sale sale = new Sale();
        sale.setDocumentName("Sale Document");
        sale.setProduct(product);
        sale.setQuantity(5);
        sale.setPurchaseCost(BigDecimal.valueOf(10.5));

        when(saleService.createSale(any(Sale.class))).thenReturn(sale);

        mockMvc.perform(MockMvcRequestBuilders.post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sale)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.documentName").value("Sale Document"));

        verify(saleService, times(1)).createSale(any(Sale.class));
    }

    @Test
    public void testSaleDocumentNameSize() {
        Sale sale = new Sale();
        sale.setDocumentName("Very long sale document name exceeding 255 characters");

        Set<ConstraintViolation<Sale>> violations = validator.validate(sale);
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 255", violations.iterator().next().getMessage());
    }
}