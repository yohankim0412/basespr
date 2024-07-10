package com.thc.basespr.domain;

import com.thc.basespr.dto.TbqnaDto;
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
public class Tbqna extends AuditingFields{
    @Setter @Column(nullable = false) private String tbuserId;
    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length=10000) @Lob private String content; // 본문
    @Setter @Column(nullable = true, length=10000) @Lob private String answer; // 본문

    protected Tbqna(){}
    private Tbqna(String tbuserId, String title, String content, String answer) {
        this.tbuserId = tbuserId;
        this.title = title;
        this.content = content;
        this.answer = answer;
    }
    public static Tbqna of(String tbuserId, String title, String content, String answer){
        return new Tbqna(tbuserId, title, content, answer);
    }

    public TbqnaDto.CreateResDto toCreateResDto() {
        return TbqnaDto.CreateResDto.builder().id(getId()).build();
    }
}
