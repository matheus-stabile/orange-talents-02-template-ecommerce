package br.com.zup.ecommerce.products.views;

import br.com.zup.ecommerce.products.opinions.Opinion;

import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OpinionsUtils {

    private Set<Opinion> opinions;

    public OpinionsUtils(Set<Opinion> opinions) {
        this.opinions = opinions;
    }

    public <T> Set<T> mapOpinions(Function<Opinion, T> mapperFunction) {
        return this.opinions.stream().map(mapperFunction).collect(Collectors.toSet());
    }

    public Double averageRate() {
        Set<Integer> rates = mapOpinions(Opinion::getRate);
        OptionalDouble possibleAverageRate = rates.stream().mapToInt(rate -> rate).average();
        return possibleAverageRate.orElse(0.0);
    }

    public Integer totalOpinions() {
        return this.opinions.size();
    }
}


