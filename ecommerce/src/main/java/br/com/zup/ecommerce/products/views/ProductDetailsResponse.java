package br.com.zup.ecommerce.products.views;

import br.com.zup.ecommerce.products.Product;
import br.com.zup.ecommerce.products.images.Image;
import br.com.zup.ecommerce.products.questions.Question;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

public class ProductDetailsResponse {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String seller;
    private Set<CharacteristicDetails> characteristics;
    private Set<String> imagesLinks;
    private Set<Map<String, String>> opinions;
    private SortedSet<String> questions;
    private Double averageRate;
    private Integer opinionsQuantity;

    public ProductDetailsResponse(@NotNull @Valid Product product) {

        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.seller = product.getOwner().getUsername();
        this.characteristics = product.mapCharacteristics(CharacteristicDetails::new);
        this.imagesLinks = product.mapImages(Image::getLink);
        this.questions = product.mapQuestions(Question::getTitle);
        OpinionsUtils opinionsUtils = new OpinionsUtils(product.getOpinions());
        this.opinions = opinionsUtils.mapOpinions(opinion -> {
            return Map.of("title", opinion.getTitle(), "description", opinion.getDescription());
        });
        this.averageRate = opinionsUtils.averageRate();
        this.opinionsQuantity = opinionsUtils.totalOpinions();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getSeller() {
        return seller;
    }

    public Set<CharacteristicDetails> getCharacteristics() {
        return characteristics;
    }

    public Set<String> getImagesLinks() {
        return imagesLinks;
    }

    public Set<Map<String, String>> getOpinions() {
        return opinions;
    }

    public SortedSet<String> getQuestions() {
        return questions;
    }

    public Double getAverageRate() {
        return averageRate;
    }

    public Integer getOpinionsQuantity() {
        return opinionsQuantity;
    }
}
