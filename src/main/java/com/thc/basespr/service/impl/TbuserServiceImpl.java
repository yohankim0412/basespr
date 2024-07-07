package com.thc.basespr.service.impl;

import com.thc.basespr.domain.RoleType;
import com.thc.basespr.domain.Tbuser;
import com.thc.basespr.domain.TbuserRoleType;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbuserDto;
import com.thc.basespr.mapper.TbuserMapper;
import com.thc.basespr.repository.TbuserRepository;
import com.thc.basespr.repository.RoleTypeRepository;
import com.thc.basespr.repository.TbuserRoleTypeRepository;
import com.thc.basespr.service.TbuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TbuserServiceImpl implements TbuserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserRepository tbuserRepository;
    private final TbuserMapper tbuserMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleTypeRepository roleTypeRepository;
    private final TbuserRoleTypeRepository tbuserRoleTypeRepository;
    public TbuserServiceImpl(
            TbuserRepository tbuserRepository
            ,TbuserMapper tbuserMapper
            ,BCryptPasswordEncoder bCryptPasswordEncoder
            ,RoleTypeRepository roleTypeRepository
            ,TbuserRoleTypeRepository tbuserRoleTypeRepository
    ) {
        this.tbuserRepository = tbuserRepository;
        this.tbuserMapper = tbuserMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleTypeRepository = roleTypeRepository;
        this.tbuserRoleTypeRepository = tbuserRoleTypeRepository;
    }

    public TbuserDto.CreateResDto signup(TbuserDto.CreateReqDto param){
        //닉네임은 그냥 자동 생성..
        String code = UUID.randomUUID().toString().replace("-", "").substring(0,12);
        param.setCode(code);
        param.setNick(code);
        return create(param);
    }
    /**/
    public TbuserDto.CreateResDto create(TbuserDto.CreateReqDto param){

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
    public TbuserDto.CreateResDto update(TbuserDto.UpdateReqDto param){
        System.out.println(param);
        Tbuser tbuser = tbuserRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbuser.setDeleted(param.getDeleted());
        }
        if(param.getNick() != null) {
            tbuser.setNick(param.getNick());
        }
        if(param.getPhone() != null) {
            tbuser.setPhone(param.getPhone());
        }
        if(param.getImg() != null) {
            tbuser.setImg(param.getImg());
        }
        if(param.getContent() != null) {
            tbuser.setContent(param.getContent());
        }
        return tbuserRepository.save(tbuser).toCreateResDto();
    }
    public TbuserDto.SelectResDto detail(CommonDto.SelectReqDto param){
        TbuserDto.SelectResDto selectDto = tbuserMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbuserDto.SelectResDto> list(TbuserDto.ListReqDto param){
        List<TbuserDto.SelectResDto> list = tbuserMapper.list(param);
        return addListDetails(list);
    }

    public List<TbuserDto.SelectResDto> moreList(TbuserDto.MoreListReqDto param){
        param.afterBuild();
        logger.info(param.getCursor());
        return addListDetails(tbuserMapper.moreList(param));
    }

    public CommonDto.PagedListResDto<TbuserDto.SelectResDto> pagedlist(TbuserDto.PagedListReqDto param){
        CommonDto.PagedListResDto<TbuserDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbuserDto.PagedListServDto newParam = new TbuserDto.PagedListServDto();
        newParam.afterBuild(tbuserMapper.pagedListCount(param), param);
        return returnDto.afterBuild(addListDetails(tbuserMapper.pagedList(newParam)), newParam);
    }

    public List<TbuserDto.SelectResDto> addListDetails(List<TbuserDto.SelectResDto> a_list){
        List<TbuserDto.SelectResDto> result_list = new ArrayList<>();
        for(TbuserDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectReqDto.builder().id(each.getId()).build()));
        }
        return result_list;
    }

}
