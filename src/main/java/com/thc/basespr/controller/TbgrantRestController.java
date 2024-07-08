package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
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

//2024-07-08 추가(클래스 처음 추가함)
@Tag(name = "0-1. 접근권한 API 안내",
        description = "접근권한 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbgrant")
@RestController
public class TbgrantRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantService tbgrantService;
    public TbgrantRestController(
            TbgrantService tbgrantService
    ) {
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "접근권한 작성",
            description = "접근권한 작성 컨트롤러 (모두 접근 가능) <br />"
                    + "@param TbgrantDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 정보 없음 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbgrantDto.CreateResDto> create(@RequestBody TbgrantDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("tbuserId : " + principalDetails.getTbuser().getId());
        String tbuserId = principalDetails.getTbuser().getId();
        param = TbgrantDto.CreateServDto.builder().reqTbuserId(tbuserId).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgrantService.create(param));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbgrantDto.CreateResDto> update(@RequestBody TbgrantDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        param = TbgrantDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.update(param));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbgrantDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        param = CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.detail((CommonDto.SelectServDto)param));
    }
    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public ResponseEntity<List<TbgrantDto.SelectResDto>> list(@Valid TbgrantDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("reqTbuserId : " + principalDetails.getTbuser().getId());
        TbgrantDto.ListServDto param2 = (TbgrantDto.ListServDto) TbgrantDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.list(param2));
    }
    @GetMapping("/mlist")
    public ResponseEntity<List<TbgrantDto.SelectResDto>> mlist(@Valid TbgrantDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("mlist : " + param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.moreList(param));
    }
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbgrantDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        logger.info("plist : " + param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.pagedlist(param));
    }
}
