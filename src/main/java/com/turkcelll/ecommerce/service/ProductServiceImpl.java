package com.turkcelll.ecommerce.service;

import com.turkcelll.ecommerce.dto.ProductRequestDto;
import com.turkcelll.ecommerce.dto.ProductResponseDto;
import com.turkcelll.ecommerce.entity.Category;
import com.turkcelll.ecommerce.entity.Product;
import com.turkcelll.ecommerce.mapper.ProductMapper;
import com.turkcelll.ecommerce.repository.CategoryRepository;
import com.turkcelll.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto dto) {
        Product product = ProductMapper.toEntity(dto);

        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Kategori eşleştirilmedi veya geçersiz kategori ID!");
        }

        // Kategorinin veritabanında mevcut olup olmadığını kontrol et
        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz kategori ID!"));

        product.setCategory(category); // Kategoriyi ayarla

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toDto(savedProduct);
    }


    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı!"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        // Kategori güncelleme
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Kategori bulunamadı!"));
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return ProductMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı!"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .toList();
    }

    @Override
    public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı!"));
        return ProductMapper.toDto(product);
    }
}
