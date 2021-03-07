package br.com.zup.ecommerce.products;

import br.com.zup.ecommerce.products.categories.Category;
import br.com.zup.ecommerce.products.characteristics.Characteristic;
import br.com.zup.ecommerce.products.characteristics.CharacteristicRequest;
import br.com.zup.ecommerce.products.images.Image;
import br.com.zup.ecommerce.products.opinions.Opinion;
import br.com.zup.ecommerce.products.questions.Question;
import br.com.zup.ecommerce.users.User;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.jsonwebtoken.lang.Assert.isTrue;
import static io.jsonwebtoken.lang.Assert.notNull;


@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private final LocalDateTime registrationDate = LocalDateTime.now();

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Positive
    private Integer quantity;

    @NotBlank
    @Length(max = 1000)
    private String description;

    @NotNull
    @Valid
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    private Set<Characteristic> characteristics = new HashSet<>();

    @NotNull
    @ManyToOne
    @Valid
    private User owner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
    private Set<Opinion> opinions = new HashSet<>();

    @OneToMany(mappedBy = "product")
    @OrderBy("title asc")
    private SortedSet<Question> questions = new TreeSet<>();

    @Deprecated
    public Product() {
    }

    public Product(@NotBlank String name, @NotNull @Positive BigDecimal price,
                   @NotNull @Positive Integer quantity, @NotBlank @Length(max = 1000) String description,
                   @NotNull @Valid Category category, @NotNull @Valid Collection<CharacteristicRequest> characteristics, @NotNull @Valid User owner) {

        notNull(name, "o nome do produto não pode ser nulo");
        isTrue(StringUtils.hasLength(name), "o nome do produto não pode estar vazio");
        notNull(price, "o preço do produto não pode ser nulo");
        isTrue(price.intValue() >= 0, "o preço do produto não pode ser menor que 0");
        notNull(quantity, "a quantidade em estoque do produto não pode ser nula");
        isTrue(quantity >= 0, "a quantidade em estoque do produto não pode ser menor que 0");
        notNull(description, "a descrição do produto não pode ser nula");
        isTrue(StringUtils.hasLength(description), "a descrição do produto não pode estar vazia");
        isTrue(description.length() <= 1000, "a descrição do produto não pode ter mais que 1000 caracteres");
        notNull(category, "a categoria do produto não pode ser nula");
        notNull(characteristics, "as caracteristicas do produto não pode ser nula");
        notNull(owner, "o dono do produto não pode ser nulo");

        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        this.owner = owner;
        this.characteristics.addAll(characteristics.stream().map(characteristic -> characteristic.toModel(this)).collect(Collectors.toSet()));
        isTrue(this.characteristics.size() >= 3, "o produto precisa ter 3 ou mais características");
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Set<Characteristic> getCharacteristics() {
        return characteristics;
    }

    public User getOwner() {
        return owner;
    }

    public Set<Image> getImages() {
        return images;
    }

    public Set<Opinion> getOpinions() {
        return opinions;
    }

    public SortedSet<Question> getQuestions() {
        return questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean belongsTo(User owner) {
        return this.owner.equals(owner);
    }

    public void addImages(Set<String> links) {
        Set<Image> images = links.stream().map(link -> new Image(this, link)).collect(Collectors.toSet());
        this.images.addAll(images);
    }

    public void addOpinions(Opinion opinion) {
        this.opinions.add(opinion);
    }

    public <T> Set<T> mapCharacteristics(Function<Characteristic, T> mapperFunction) {
        return this.characteristics.stream().map(mapperFunction).collect(Collectors.toSet());
    }

    public <T> Set<T> mapImages(Function<Image, T> mapperFunction) {
        return this.images.stream().map(mapperFunction).collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapQuestions(Function<Question, T> mapperFunction) {
        return this.questions.stream().map(mapperFunction).collect(Collectors.toCollection(TreeSet::new));
    }

    public boolean manageStock(@Positive Integer quantity) {
        Assert.isTrue(this.quantity > 0, "a quantidade em estoque do produto deve ser maior que 0");

        if (quantity <= this.quantity) {
            this.quantity -= quantity;
            return true;
        }

        return false;
    }
}
