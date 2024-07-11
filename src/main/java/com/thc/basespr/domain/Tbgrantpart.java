package com.thc.basespr.domain;

import com.thc.basespr.dto.TbgrantpartDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//2024-07-08 추가(클래스 처음 추가함)
//어드민 접속 권한 상세 관련 클래스
@Getter
@Table(
        indexes = {
            @Index(columnList = "deleted")
            ,@Index(columnList = "process")
            ,@Index(columnList = "createdAt")
            ,@Index(columnList = "modifiedAt")
            ,@Index(columnList = "tbgrantId")
        }
)
@Entity
public class Tbgrantpart extends AuditingFields{

    @Setter @Column(nullable = false) private String tbgrantId;
    @Setter @Column(nullable = false) private String cate;
    @Setter @Column(nullable = false, length = 10000) private String content;

    protected Tbgrantpart(){}
    private Tbgrantpart(String tbgrantId, String cate, String content) {
        this.tbgrantId = tbgrantId;
        this.cate = cate;
        this.content = content;
    }
    public static Tbgrantpart of(String tbgrantId, String cate, String content){
        return new Tbgrantpart(tbgrantId, cate, content);
    }

    public TbgrantpartDto.CreateResDto toCreateResDto() {
        return TbgrantpartDto.CreateResDto.builder().id(getId()).build();
    }
}
