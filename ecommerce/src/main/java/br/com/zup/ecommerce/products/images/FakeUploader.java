package br.com.zup.ecommerce.products.images;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class FakeUploader implements Uploader {
    @Override
    public Set<String> send(List<MultipartFile> images) {
        return images.stream().map(image -> "http://bucket.io/" + image.getOriginalFilename().replace(" ", "_")).collect(Collectors.toSet());
    }
}
