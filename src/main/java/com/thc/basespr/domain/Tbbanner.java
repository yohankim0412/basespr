package com.thc.basespr.domain;

import com.thc.basespr.dto.TbbannerDto;
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
public class Tbbanner extends AuditingFields{
    @Setter @Column(nullable = false) private int sequence; // 순서
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length=10000) @Lob private String content; // 본문
    @Setter @Column(nullable = false) private String img;

    protected Tbbanner(){}
    private Tbbanner(int sequence, String title, String content, String img) {
        this.sequence = sequence;
        this.title = title;
        this.content = content;
        this.img = img;
    }
    public static Tbbanner of(int sequence, String title, String content, String img){
        return new Tbbanner(sequence, title, content, img);
    }

    public TbbannerDto.CreateResDto toCreateResDto() {
        return TbbannerDto.CreateResDto.builder().id(getId()).build();
    }
}
