package com.poscodx.popang.service;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.ProductCategory;
import com.poscodx.popang.domain.ProductImage;
import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.ProductDTO;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.repository.CategoryRepository;
import com.poscodx.popang.repository.ProductImageRepository;
import com.poscodx.popang.repository.ProductRepository;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public ProductDTO findById(Long id){
        ProductDTO dto = new ProductDTO();
        Product product = productRepository.findProductById(id);
        dto.setCategoryCode(product.getCategory().getCode());
        dto.setImageList(product.getImageList());
        dto.setDTOByEntity(product);
        return dto;
    }

    public Page<ProductDTO> findAllBySeller(String sellerId, Pageable pageable) {
        User seller = userRepository.findUserById(sellerId);
        return productRepository.findAllProductBySellerOrderByUploadDateDesc(seller, pageable)
                .map(p -> {
                    ProductDTO pd = new ProductDTO();
                    pd.setDTOByEntity(p);
                    pd.setImageList(p.getImageList());
                    return pd;
                });
    }

    @Transactional
    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    @Transactional
    public void editProduct(ProductDTO productDTO, String sellerId, List<String> images) {
        Product product = productRepository.findProductById(productDTO.getId());

        ProductCategory category = categoryRepository.findByCode(productDTO.getCategoryCode());
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setDescriptionDetail(productDTO.getDescriptionDetail());

        // 이미지 삭제
        product.getImageList().clear();

        // 이미지 넣기
        if (images != null) {
            for (String addrss : images) {
                ProductImage pi = new ProductImage();
                pi.setProduct(product);
                pi.setImageAddress(addrss);
                product.getImageList().add(pi);
            }
        }

        productRepository.save(product);
    }

    @Transactional
    public void addProduct(ProductDTO productDTO, String sellerId, List<String> images) {
        //        id - 입력 필요 x
//        seller_id - auth로 처리
//        category_code - 만들어 놓고 입력해야됨
//        name - 입력 필요함
//        price - 입력 필요함
//        stock - 입력 필요함
//        description - 입력 필요함
//        description_detail - 입력 필요함
//        upload_date - 서버에서 처리
        Product product = new Product();

        User seller = userRepository.findUserById(sellerId);
        product.setSeller(seller);
        ProductCategory category = categoryRepository.findByCode(productDTO.getCategoryCode());
        product.setCategory(category);
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setDescriptionDetail(productDTO.getDescriptionDetail());
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);
        product.setUploadDate(nowTimeStamp);

        // 이미지 넣기
        if (images != null) {
            for (String addrss : images) {
                ProductImage pi = new ProductImage();
                pi.setProduct(product);
                pi.setImageAddress(addrss);
                product.getImageList().add(pi);
            }
        }

        productRepository.save(product);
    }

}
