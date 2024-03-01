package com.poscodx.popang.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reply_image")
public class ReplyImage {
    @Id
    @GeneratedValue
    private Long id;
    private String imageAddress;
    private Long priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;
}
