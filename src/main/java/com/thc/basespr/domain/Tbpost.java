package com.thc.basespr.domain;

import com.thc.basespr.dto.TbpostDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Tbpost extends AuditingFields{
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false) private String cate;
    @Setter @Column(nullable = false) private String img; // 프로필 사진
    @Setter @Column(nullable = false, length=2000000) @Lob private String content; // 본문

    @Setter @Column(nullable = false) private String tbuserId;

    protected Tbpost(){}
    private Tbpost(String title, String cate, String img, String content, String tbuserId) {
        this.title = title;
        this.cate = cate;
        this.img = img;
        this.content = content;
        this.tbuserId = tbuserId;
    }
    public static Tbpost of(String title, String cate, String img, String content, String tbuserId){
        return new Tbpost(title, cate, img, content, tbuserId);
    }

    public TbpostDto.CreateResDto toAfterCreateDto() {
        return TbpostDto.CreateResDto.builder().id(getId()).build();
    }
}
