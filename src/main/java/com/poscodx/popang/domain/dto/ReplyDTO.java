package com.poscodx.popang.domain.dto;

import com.poscodx.popang.domain.Product;
import com.poscodx.popang.domain.Reply;
import com.poscodx.popang.domain.ReplyImage;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReplyDTO {
    private String id;
    private String content;
    private Timestamp uploadDate;
    private Long score;
    private Long productId;
    private ProductDTO product;
    private ReplyDTO parentReply;
    private String userId;
    private String userName;
    private List<ReplyDTO> replyList = new ArrayList<>();
    private List<ReplyImage> imageList = new ArrayList<>();

    public void setDTOByEntity(Reply reply){
        id = reply.getId();
        content = reply.getContent();
        uploadDate = reply.getUploadDate();
        score = reply.getScore();
        userId = reply.getUser().getId();
        userName = reply.getUser().getName();
        if(reply.getReplyList() != null)
            replyList = reply.getReplyList().stream().map(r -> {
                ReplyDTO dto = new ReplyDTO();
                dto.setDTOByEntity(r);
                return dto;
            }).toList();
    }
}
