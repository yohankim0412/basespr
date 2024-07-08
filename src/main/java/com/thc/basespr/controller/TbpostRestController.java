package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpostDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbpostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "2-5. 게시글 API 안내",
        description = "게시글 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbpost")
@RestController
public class TbpostRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbpostService tbpostService;
    public TbpostRestController(
            TbpostService tbpostService
    ) {
        this.tbpostService = tbpostService;
    }

    @Operation(summary = "게시글 작성",
            description = "게시글 작성 컨트롤러 (모두 접근 가능) <br />"
                    + "@param TbpostDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbpostDto.CreateResDto\\> <br />"
                    + "@exception 정보 없음 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbpostDto.CreateResDto> create(@RequestBody TbpostDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("tbuserId : " + principalDetails.getTbuser().getId());
        String tbuserId = principalDetails.getTbuser().getId();
        param = TbpostDto.CreateServDto.builder().tbuserId(tbuserId).reqTbuserId(tbuserId).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbpostService.create(param));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbpostDto.CreateResDto> update(@RequestBody TbpostDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        param = TbpostDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpostService.update(param));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbpostDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        param = CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpostService.detail((CommonDto.SelectServDto)param));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public ResponseEntity<List<TbpostDto.SelectResDto>> list(@Valid TbpostDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        TbpostDto.ListServDto param2 = (TbpostDto.ListServDto) TbpostDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpostService.list(param2));
    }
    @GetMapping("/mlist")
    public ResponseEntity<List<TbpostDto.SelectResDto>> mlist(@Valid TbpostDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("mlist : " + param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpostService.moreList(param));
    }
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbpostDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("plist : " + param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpostService.pagedlist(param));
    }
}
