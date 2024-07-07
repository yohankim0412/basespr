package com.thc.basespr.domain;

import com.thc.basespr.dto.TbuserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Tbuser extends AuditingFields{
    @Setter @Column(nullable = false) private String username; // 사용자아이디
    @Setter @Column(nullable = false) private String password; // 비번
    @Setter @Column(nullable = false) private String code;
    @Setter @Column(nullable = false) private String nick;

    @Setter @Column(nullable = true) private String name;
    @Setter @Column(nullable = true) private String phone;
    @Setter @Column(nullable = true) private String img; // 프로필 사진
    @Setter @Column(nullable = true, length=2000000) @Lob private String content; // 본문

    //fetch 타입 바꾸고, toString 순환 참조 수정
    @OneToMany(mappedBy = "tbuser", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<TbuserRoleType> tbuserRoleType = new ArrayList<>();

    //2024-07-03
    //권한 관련한 기능 추가
    public List<TbuserRoleType> getRoleList(){
        if(!this.tbuserRoleType.isEmpty()){
            return tbuserRoleType;
        }
        return new ArrayList<>();
    }

    protected Tbuser(){}
    private Tbuser(String username, String password, String code, String nick) {
        this.username = username;
        this.password = password;
        this.code = code;
        this.nick = nick;
    }
    public static Tbuser of(String username, String password, String code, String nick){
        return new Tbuser(username, password, code, nick);
    }

    public TbuserDto.CreateResDto toCreateResDto() {
        return TbuserDto.CreateResDto.builder().id(getId()).build();
    }
}
