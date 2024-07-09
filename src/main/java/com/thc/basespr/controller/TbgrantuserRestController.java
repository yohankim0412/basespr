package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbgrantuserDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbgrantuserService;
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

//2024-07-09 추가(클래스 처음 추가함)
@Tag(name = "0-1. 접근권한 사용자 API 안내",
        description = "접근권한 사용자 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbgrantuser")
@RestController
public class TbgrantuserRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantuserService tbgrantuserService;
    private final TbgrantService tbgrantService;
    public TbgrantuserRestController(
            TbgrantuserService tbgrantuserService
            ,TbgrantService tbgrantService
    ) {
        this.tbgrantuserService = tbgrantuserService;
        this.tbgrantService = tbgrantService;
    }


    /**/

    @Operation(summary = "접근권한 사용자 생성",
            description = "접근권한 사용자 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgrantuserDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbgrantuserDto.CreateResDto> create(@RequestBody TbgrantuserDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantuserDto.CreateServDto newParam = (TbgrantuserDto.CreateServDto) TbgrantuserDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgrantuserService.create(newParam));
    }

    @Operation(summary = "접근권한 사용자 수정",
            description = "접근권한 사용자 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantuserDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbgrantuserDto.CreateResDto> update(@RequestBody TbgrantuserDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantuserDto.UpdateServDto newParam = (TbgrantuserDto.UpdateServDto) TbgrantuserDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.update(newParam));
    }

    @Operation(summary = "접근권한 사용자 삭제",
            description = "접근권한 사용자 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantuserDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbgrantuserDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.delete(newParam));
    }
    @Operation(summary = "접근권한 사용자 삭제(일괄)",
            description = "접근권한 사용자 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbgrantuserDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.deletes(newParam));
    }

    @Operation(summary = "접근권한 사용자 정보 조회",
            description = "접근권한 사용자 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantuserDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbgrantuserDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.detail(newParam));
    }

    @Operation(summary = "접근권한 사용자 목록(전체) 조회",
            description = "접근권한 사용자 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantuserDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbgrantuserDto.SelectResDto>> list(@Valid TbgrantuserDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantuserDto.ListServDto newParam = (TbgrantuserDto.ListServDto) TbgrantuserDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.list(newParam));
    }

    @Operation(summary = "접근권한 사용자 목록(스크롤) 조회",
            description = "접근권한 사용자 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantuserDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbgrantuserDto.SelectResDto>> mlist(@Valid TbgrantuserDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantuserDto.MoreListServDto newParam = (TbgrantuserDto.MoreListServDto) TbgrantuserDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.moreList(newParam));
    }

    @Operation(summary = "접근권한 사용자 목록(페이지) 조회",
            description = "접근권한 사용자 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantuserDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbgrantuserDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantuserDto.PagedListServDto newParam = (TbgrantuserDto.PagedListServDto) TbgrantuserDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantuserService.pagedlist(newParam));
    }
}
