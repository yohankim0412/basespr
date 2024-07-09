package com.thc.basespr.service.impl;

import com.thc.basespr.domain.Tbfaq;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbfaqDto;
import com.thc.basespr.exception.NoAuthorizationException;
import com.thc.basespr.exception.NoMatchingDataException;
import com.thc.basespr.mapper.TbfaqMapper;
import com.thc.basespr.repository.TbfaqRepository;
import com.thc.basespr.service.TbfaqService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//2024-07-09 추가(클래스 처음 추가함)
@Service
public class TbfaqServiceImpl implements TbfaqService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbfaqRepository tbfaqRepository;
    private final TbfaqMapper tbfaqMapper;
    public TbfaqServiceImpl(
            TbfaqRepository tbfaqRepository
            , TbfaqMapper tbfaqMapper
    ) {
        this.tbfaqRepository = tbfaqRepository;
        this.tbfaqMapper = tbfaqMapper;
    }


    public TbfaqDto.CreateResDto sequence(TbfaqDto.SequenceServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }

        Tbfaq tbfaq = tbfaqRepository.findById(param.getId())
                .orElseThrow(() -> new NoMatchingDataException(""));
        int nowSequence = tbfaq.getSequence();

        int targetSequence = nowSequence;
        if("up".equals(param.getWay())){
            targetSequence++;
        } else {
            targetSequence--;
        }

        logger.info("!!!!!" + targetSequence);

        if(targetSequence == 0 || targetSequence > tbfaqMapper.pagedListCount(new TbfaqDto.PagedListServDto())){
            return null;
        } else {
            //잠시 순번에서 제외
            update(TbfaqDto.UpdateServDto.builder().id(tbfaq.getId()).sequence(-1).isAdmin(param.isAdmin()).build());
            //바꾸고자 하는 순번의 정보에 이전 순번 저장
            Tbfaq targetTbfaq = tbfaqRepository.findBySequence(targetSequence);
            update(TbfaqDto.UpdateServDto.builder().id(targetTbfaq.getId()).sequence(nowSequence).isAdmin(param.isAdmin()).build());
            //다시 수정하고 리턴
            return update(TbfaqDto.UpdateServDto.builder().id(tbfaq.getId()).sequence(targetSequence).isAdmin(param.isAdmin()).build());
        }
    }

    /**/

    public TbfaqDto.CreateResDto create(TbfaqDto.CreateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        param.setSequence(tbfaqMapper.pagedListCount(new TbfaqDto.PagedListServDto()) + 1);
        return tbfaqRepository.save(param.toEntity()).toCreateResDto();
    }
    public TbfaqDto.CreateResDto update(TbfaqDto.UpdateServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        Tbfaq tbfaq = tbfaqRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            tbfaq.setDeleted(param.getDeleted());
        }
        if(param.getSequence() != null){
            tbfaq.setSequence(param.getSequence());
        }
        if(param.getTitle() != null) {
            tbfaq.setTitle(param.getTitle());
        }
        if(param.getCate() != null) {
            tbfaq.setCate(param.getCate());
        }
        if(param.getContent() != null) {
            tbfaq.setContent(param.getContent());
        }
        return tbfaqRepository.save(tbfaq).toCreateResDto();
    }
    public TbfaqDto.CreateResDto delete(CommonDto.DeleteServDto param){
        TbfaqDto.UpdateServDto newParam = TbfaqDto.UpdateServDto.builder().id(param.getId()).deleted("Y").build();
        return update(newParam);
    }
    public TbfaqDto.CreateResDto deletes(CommonDto.DeletesServDto param){
        int count = 0;
        for(String each : param.getIds()){
            TbfaqDto.UpdateServDto newParam = TbfaqDto.UpdateServDto.builder().id(each).deleted("Y").build();
            TbfaqDto.CreateResDto result = update(newParam);
            if(!(result.getId()).isEmpty()) {
                count++;
            }
        }
        return TbfaqDto.CreateResDto.builder().id(count + "").build();
    }
    public TbfaqDto.SelectResDto detail(CommonDto.SelectServDto param){
        //권한 확인
        if(!param.isAdmin()){ throw new NoAuthorizationException(""); }
        TbfaqDto.SelectResDto selectDto = tbfaqMapper.detail(param.getId());
        return selectDto;
    }

    public List<TbfaqDto.SelectResDto> list(TbfaqDto.ListServDto param){
        List<TbfaqDto.SelectResDto> list = tbfaqMapper.list(param);
        return addListDetails(list, CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public List<TbfaqDto.SelectResDto> moreList(TbfaqDto.MoreListServDto param){
        param.init();
        return addListDetails(tbfaqMapper.moreList(param), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build());
    }

    public CommonDto.PagedListResDto<TbfaqDto.SelectResDto> pagedlist(TbfaqDto.PagedListServDto param){
        CommonDto.PagedListResDto<TbfaqDto.SelectResDto> returnDto = new CommonDto.PagedListResDto<>();
        TbfaqDto.PagedListServDto newParam = new TbfaqDto.PagedListServDto();
        newParam.init(tbfaqMapper.pagedListCount(param), param);
        return returnDto.init(addListDetails(tbfaqMapper.pagedList(newParam), CommonDto.DetailServDto.builder().reqTbuserId(param.getReqTbuserId()).isAdmin(param.isAdmin()).build()), newParam);
    }

    public List<TbfaqDto.SelectResDto> addListDetails(List<TbfaqDto.SelectResDto> a_list, CommonDto.DetailServDto detailServDto){
        List<TbfaqDto.SelectResDto> result_list = new ArrayList<>();
        for(TbfaqDto.SelectResDto each : a_list){
            result_list.add(detail(CommonDto.SelectServDto.builder().id(each.getId()).reqTbuserId(detailServDto.getReqTbuserId()).isAdmin(detailServDto.isAdmin()).build()));
        }
        return result_list;
    }

}
