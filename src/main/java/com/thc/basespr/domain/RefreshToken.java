package com.thc.basespr.domain;

import com.thc.basespr.dto.RefreshTokenDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//2024-07-03 추가(클래스 처음 추가함)
@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "createdAt")
        ,@Index(columnList = "modifiedAt")
})
@Entity
public class RefreshToken extends AuditingFields {

    @Setter @Column(nullable = false, length = 2000) private String content;
    @Setter @Column(nullable = false) private String tbuserId;

    protected RefreshToken(){}
    private RefreshToken(String content, String tbuserId) {
        this.content = content;
        this.tbuserId = tbuserId;
    }
    public static RefreshToken of(String content, String tbuserId) {
        return new RefreshToken(content, tbuserId);
    }

}
