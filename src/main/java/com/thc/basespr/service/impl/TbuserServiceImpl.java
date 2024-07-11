package com.thc.basespr.service.impl;

import com.thc.basespr.domain.RoleType;
import com.thc.basespr.domain.Tbuser;
import com.thc.basespr.domain.TbuserRoleType;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbuserDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbuserMapper;
import com.thc.basespr.repository.RoleTypeRepository;
import com.thc.basespr.repository.TbuserRepository;
import com.thc.basespr.repository.TbuserRoleTypeRepository;
import com.thc.basespr.security.JwtTokenDto;
import com.thc.basespr.service.AuthService;
import com.thc.basespr.service.TbuserService;
import com.thc.basespr.util.ExternalProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//2024-07-08 추가(클래스 처음 추가함)
@Service
public class TbuserServiceImpl implements TbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbuserMapper tbuserMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleTypeRepository roleTypeRepository;
    private final TbuserRoleTypeRepository tbuserRoleTypeRepository;
    private final AuthService authService;
    private final ExternalProperties externalProperties;
    public TbuserServiceImpl(
            TbuserRepository tbuserRepository
            ,TbuserMapper tbuserMapper
            ,BCryptPasswordEncoder bCryptPasswordEncoder
            ,RoleTypeRepository roleTypeRepository
            ,TbuserRoleTypeRepository tbuserRoleTypeRepository
            ,AuthService authService
            ,ExternalProperties externalProperties
    ) {
        this.tbuserRepository = tbuserRepository;
        this.tbuserMapper = tbuserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleTypeRepository = roleTypeRepository;
        this.tbuserRoleTypeRepository = tbuserRoleTypeRepository;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }

    public JwtTokenDto getToken(String id){
        String refreshToken = authService.createRefreshToken(id);
        JwtTokenDto jwtTokenDto = authService.issueAccessToken(refreshToken);
        jwtTokenDto.setRefreshToken(externalProperties.getTokenPrefix() + jwtTokenDto.getRefreshToken());
        jwtTokenDto.setAccessToken(externalProperties.getTokenPrefix() + jwtTokenDto.getAccessToken());
        return jwtTokenDto;
    }

    public JwtTokenDto tempKgcert(TbuserDto.CreateServDto param){
        TbuserDto.CreateResDto createResDto = null;
        //01099998888_19781121_M_홍길동
        String birth = param.getBirth();
        birth = birth.replaceAll("-","");
        String phone = param.getPhone();
        phone = phone.replaceAll("-","");
        String tempUsername = phone + "_" + birth + "_" + param.getGender() + "_" + param.getName();
        param.setUsername(tempUsername);
        param.setPassword(tempUsername);

        Tbuser tbuser = tbuserRepository.findByUsername(param.getUsername());
        if(tbuser == null){
            param.setRoute("KGCERT");
            createResDto = create(param);
        } else {
            createResDto = TbuserDto.CreateResDto.builder().id(tbuser.getId()).build();
        }
        return getToken(createResDto.getId());
    }

    public TbuserDto.CreateResDto signup(TbuserDto.CreateServDto param){
        param.setRoute("direct");
        return create(param);
    }

    /**/
    public TbuserDto.CreateResDto create(TbuserDto.CreateServDto param){

        //닉네임은 그냥 자동 생성..
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,12);
        param.setCode(code);
        param.setNick(code);

        //비번 암호화를 위한 코드
        String rawPassword = param.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        param.setPassword(encPassword);

        //사용자 등록 완료!
        Tbuser tbuser = tbuserRepository.save(param.toEntity());

        //권한은 그냥 ROLE_USER 로 강제 저장함!(임시코드)
        RoleType roleType = roleTypeRepository.findByTypeName("ROLE_USER");
        TbuserRoleType tbuserRoleType = TbuserRoleType.of(tbuser, roleType);
        tbuserRoleTypeRepository.save(tbuserRoleType);
        //

        return tbuser.toCreateResDto();
    }
    public TbuserDto.CreateResDto update(TbuserDto.UpdateServDto param){
        Tbuser tbuser = tbuserRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));

        logger.info("!!!!" + param.getPopup());
        //권한 확인
        if(!param.isAdmin() && !(param.getReqTbuserId()).equals(tbuser.getId())){ throw new NoAuthorizationException(""); }

        if(param.getDeleted() != null) {
            tbuser.setDeleted(param.getDeleted());
        }
        if(param.getNick() != null) {
            tbuser.setNick(param.getNick());
        }
        if(param.getImg() != null) {
            tbuser.setImg(param.getImg());
        }
        if(param.getBrief() != null) {
            tbuser.setBrief(param.getBrief());
        }
        if(param.getContent() != null) {
            tbuser.setContent(param.getContent());
        }
        if(param.getPopup() != null) {
            tbuser.setPopup(param.getPopup());
        }
        if(param.getAgreethird() != null) {
            tbuser.setAgreethird(param.getAgreethird());
        }
        return tbuserRepository.save(tbuser).toCreateResDto();
    }
    public TbuserDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbuserDto.UpdateServDto newParam = TbuserDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
        return update(newParam);
    }
    public TbuserDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbuserDto.UpdateServDto newParam = TbuserDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).reqTbuserId(param.getReqTbuserId()).build();
            TbuserDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbuserDto.CreateResDto.builder().id(count + "").build();
    }
    public TbuserDto.SelectResDto detail(CommonDto.SelectServDto param){
        //내가 쓴 글 조희
        if("my".equals(param.getId())){
            param.setId(param.getReqTbuserId());
        }
        TbuserDto.SelectResDto selectDto = tbuserMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));

        return selectDto;
    }

    public List<TbuserDto.SelectResDto> list(TbuserDto.ListServDto param){
        List<TbuserDto.SelectResDto> list = tbuserMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListServDto param){
        param.init();
        return addListDetails(tbuserMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbuserDto.SelectResDto> pagedlist(TbuserDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbuserDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbuserDto.PagedListServDto newParam = new TbuserDto.PagedListServDto();
        newParam.init(tbuserMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbuserMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbuserDto.SelectResDto> addListDetails(List<TbuserDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbuserDto.SelectResDto> result_list = new ArrayList<>();
        for(TbuserDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
