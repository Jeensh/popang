package com.poscodx.popang.service;

import com.poscodx.popang.domain.OrderItem;
import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.Reply;
import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.ReplyDTO;
import com.poscodx.popang.repository.OrderItemRepository;
import com.poscodx.popang.repository.ProductRepository;
import com.poscodx.popang.repository.ReplyRepository;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void writeReview(ReplyDTO replyDTO, String userId, Long productId, Long orderItemId){
        Reply reply = new Reply();
        User user = userRepository.findUserById(userId);
        Product product = productRepository.findProductById(productId);
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);
        reply.setContent(replyDTO.getContent());
        reply.setUser(user);
        reply.setScore(replyDTO.getScore());
        reply.setProduct(product);
        reply.setUploadDate(nowTimeStamp);
        OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
        orderItem.setReview(2);
        replyRepository.save(reply);

        // 상품 평점 수정
        product.setScore((product.getScore() + reply.getScore()) / (product.getReplyList().size()));
        productRepository.save(product);
    }
}
