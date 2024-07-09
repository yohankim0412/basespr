package com.thc.basespr.domain;

import com.thc.basespr.dto.TbfaqDto;
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
public class Tbfaq extends AuditingFields{
    @Setter @Column(nullable = false) private int sequence; // 순서
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false) private String cate;
    @Setter @Column(nullable = false, length=10000) @Lob private String content; // 본문

    protected Tbfaq(){}
    private Tbfaq(int sequence, String title, String cate, String content) {
        this.sequence = sequence;
        this.title = title;
        this.cate = cate;
        this.content = content;
    }
    public static Tbfaq of(int sequence, String title, String cate, String content){
        return new Tbfaq(sequence, title, cate, content);
    }

    public TbfaqDto.CreateResDto toCreateResDto() {
        return TbfaqDto.CreateResDto.builder().id(getId()).build();
    }
}
