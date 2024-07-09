package com.thc.basespr.domain;

import com.thc.basespr.dto.TbnoticeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//2024-07-09 추가(클래스 처음 추가함)
@Getter
@Table(indexes = {
        @Index(columnList = "deleted")
        ,@Index(columnList = "process")
        ,@Index(columnList = "createdAt")
        ,@Index(columnList = "modifiedAt")
})
@Entity
public class Tbnotice extends AuditingFields{
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false) private String cate;
    @Setter @Column(nullable = false, length=10000) @Lob private String content; // 본문

    protected Tbnotice(){}
    private Tbnotice(String title, String cate, String content) {
        this.title = title;
        this.cate = cate;
        this.content = content;
    }
    public static Tbnotice of(String title, String cate, String content){
        return new Tbnotice(title, cate, content);
    }

    public TbnoticeDto.CreateResDto toCreateResDto() {
        return TbnoticeDto.CreateResDto.builder().id(getId()).build();
    }
}
