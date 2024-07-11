package com.thc.basespr.domain;

import com.thc.basespr.dto.TbgrantuserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//2024-07-08 추가(클래스 처음 추가함)
//어드민 접속 권한 소유 사용자 관련 클래스
@Getter
@Table(
        indexes = {
                @Index(columnList = "deleted")
                ,@Index(columnList = "process")
                ,@Index(columnList = "createdAt")
                ,@Index(columnList = "modifiedAt")
                ,@Index(columnList = "tbgrantId")
        }
        ,uniqueConstraints = {@UniqueConstraint(
        name = "UQ_tbgrantuser_tbgrant_tbuser"
        ,columnNames = {"tbgrantId", "tbuserId"}
        )}
)
@Entity
public class Tbgrantuser extends AuditingFields{

    @Setter @Column(nullable = false) private String tbgrantId;
    @Setter @Column(nullable = false) private String tbuserId;

    protected Tbgrantuser(){}
    private Tbgrantuser(String tbgrantId, String tbuserId) {
        this.tbgrantId = tbgrantId;
        this.tbuserId = tbuserId;
    }
    public static Tbgrantuser of(String tbgrantId, String tbuserId){
        return new Tbgrantuser(tbgrantId, tbuserId);
    }

    public TbgrantuserDto.CreateResDto toCreateResDto() {
        return TbgrantuserDto.CreateResDto.builder().id(getId()).build();
    }
}
