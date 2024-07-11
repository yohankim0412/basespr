package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbbanner;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbbannerDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbbannerMapper;
import com.thc.basespr.repository.TbbannerRepository;
import com.thc.basespr.service.TbbannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public class TbbannerServiceImpl implements TbbannerService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbbannerRepository tbbannerRepository;
    private final TbbannerMapper tbbannerMapper;
    public TbbannerServiceImpl(
            TbbannerRepository tbbannerRepository
            , TbbannerMapper tbbannerMapper
    ) {
        this.tbbannerRepository = tbbannerRepository;
        this.tbbannerMapper = tbbannerMapper;
    }


    public TbbannerDto.CreateResDto sequence(TbbannerDto.SequenceServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }

        Tbbanner tbbanner = tbbannerRepository.findById(param.getId()).orElseThrow(() -> new NoMatchingDataException(""));
        int nowSequence = tbbanner.getSequence(); // 현재 순번을 알아본다. // 2

        int targetSequence = nowSequence; // 2
        if("up".equals(param.getWay())){
            targetSequence++; // 3!!
        } else {
            targetSequence--; // 1
        }
        if(targetSequence == 0 || targetSequence > tbbannerMapper.pagedListCount(new TbbannerDto.PagedListServDto())){
            return null;
        } else {
            //잠시 순번에서 제외
            update(TbbannerDto.UpdateServDto.builder().id(tbbanner.getId()).sequence(-1).isAdmin(param.isAdmin()).build()); // 2-> -1
            //바꾸고자 하는 순번의 정보에 이전 순번 저장
            Tbbanner targetTbbanner = tbbannerRepository.findBySequence(targetSequence); // 현재 3번에 위치한 애 정보 가져오기
            update(TbbannerDto.UpdateServDto.builder().id(targetTbbanner.getId()).sequence(nowSequence).isAdmin(param.isAdmin()).build()); // 3번애 있는 애를 2번으로 업데이트
            //다시 수정하고 리턴
            return update(TbbannerDto.UpdateServDto.builder().id(tbbanner.getId()).sequence(targetSequence).isAdmin(param.isAdmin()).build()); //2번에 있는 애를 3번으로 업데이트
        }
    }

    /**/

    public TbbannerDto.CreateResDto create(TbbannerDto.CreateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        param.setSequence(tbbannerMapper.pagedListCount(new TbbannerDto.PagedListServDto()) + 1);
        return tbbannerRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbbannerDto.CreateResDto update(TbbannerDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbbanner tbbanner = tbbannerRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbbanner.setDeleted(param.getDeleted());
        }
        if(param.getSequence() != null){
            tbbanner.setSequence(param.getSequence());
        }
        if(param.getTitle() != null) {
            tbbanner.setTitle(param.getTitle());
        }
        if(param.getContent() != null) {
            tbbanner.setContent(param.getContent());
        }
        if(param.getImg() != null) {
            tbbanner.setImg(param.getImg());
        }
        return tbbannerRepository.save(tbbanner).toCreateResDto();
    }
    public TbbannerDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbbannerDto.UpdateServDto newParam = TbbannerDto.UpdateServDto.builder().id(param.getId()).deleted("Y").isAdmin(param.isAdmin()).build();
        return update(newParam);
    }
    public TbbannerDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbbannerDto.UpdateServDto newParam = TbbannerDto.UpdateServDto.builder().id(each).deleted("Y").isAdmin(param.isAdmin()).build();
            TbbannerDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbbannerDto.CreateResDto.builder().id(count + "").build();
    }
    public TbbannerDto.SelectResDto detail(CommonDto.SelectServDto param){
        TbbannerDto.SelectResDto selectDto = tbbannerMapper.detail(param.getId());
        if(selectDto == null){ throw new NoMatchingDataException(""); }
        //날짜 표기 추가
        String createdAt = selectDto.getCreatedAt();
        selectDto.setCreatedAtOnlyDate(createdAt.substring(0, 19));
        return selectDto;
    }

    public List<TbbannerDto.SelectResDto> list(TbbannerDto.ListServDto param){
        List<TbbannerDto.SelectResDto> list = tbbannerMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbbannerDto.SelectResDto> moreList(TbbannerDto.MoreListServDto param){
        param.init();
        return addListDetails(tbbannerMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbbannerDto.SelectResDto> pagedlist(TbbannerDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbbannerDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbbannerDto.PagedListServDto newParam = new TbbannerDto.PagedListServDto();
        newParam.init(tbbannerMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbbannerMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbbannerDto.SelectResDto> addListDetails(List<TbbannerDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbbannerDto.SelectResDto> result_list = new ArrayList<>();
        for(TbbannerDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
