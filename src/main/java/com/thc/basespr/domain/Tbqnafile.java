package com.thc.basespr.domain;

import com.thc.basespr.dto.TbqnafileDto;
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
public class Tbqnafile extends AuditingFields{
    @Setter @Column(nullable = false) private String tbqnaId;
    @Setter @Column(nullable = false) private String cate;
    @Setter @Column(nullable = false) private String url;

    protected Tbqnafile(){}
    private Tbqnafile(String tbqnaId, String cate, String url) {
        this.tbqnaId = tbqnaId;
        this.cate = cate;
        this.url = url;
    }
    public static Tbqnafile of(String tbqnaId, String cate, String url){
        return new Tbqnafile(tbqnaId, cate, url);
    }

    public TbqnafileDto.CreateResDto toCreateResDto() {
        return TbqnafileDto.CreateResDto.builder().id(getId()).build();
    }
}
