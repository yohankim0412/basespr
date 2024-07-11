package com.thc.basespr.domain;

import com.thc.basespr.dto.TbuserDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Table(indexes = {
        @Index(columnList = "deleted")
        ,@Index(columnList = "process")
        ,@Index(columnList = "createdAt")
        ,@Index(columnList = "modifiedAt")
})
@Entity
public class Tbuser extends AuditingFields{
    @Setter @Column(nullable = false, unique = true) private String username; // 사용자아이디
    @Setter @Column(nullable = false) private String password; // 비번
    @Setter @Column(nullable = false) private String code;
    @Setter @Column(nullable = false) private String nick;

    @Setter @Column(nullable = true) private String name;
    @Setter @Column(nullable = true) private String phone;
    @Setter @Column(nullable = true) private String birth;
    @Setter @Column(nullable = true) private String gender;

    @Setter @Column(nullable = true) private String route;
    @Setter @Column(nullable = true) private String img; // 프로필 사진
    @Setter @Column(nullable = true) private String brief; //자기소개(간단)
    @Setter @Column(nullable = true, length=2000000) @Lob private String content; // 본문

    @Setter @Column(nullable = false) private Boolean popup;
    @Setter @Column(nullable = false) private Boolean agreethird;

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
    private Tbuser(String username, String password, String code, String nick
    , String name, String phone, String birth, String gender, String route) {
        this.username = username;
        this.password = password;
        this.code = code;
        this.nick = nick;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.gender = gender;
        this.route = route;
        this.popup = true;
        this.agreethird = true;
    }
    public static Tbuser of(String username, String password, String code, String nick
    , String name, String phone, String birth, String gender, String route){
        return new Tbuser(username, password, code, nick, name, phone, birth, gender, route);
    }

    public TbuserDto.CreateResDto toCreateResDto() {
        return TbuserDto.CreateResDto.builder().id(getId()).build();
    }
}
