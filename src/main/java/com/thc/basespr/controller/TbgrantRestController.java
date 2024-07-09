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

    @Operation(summary = "접근권한 생성",
            description = "접근권한 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbgrantDto.CreateResDto> create(@RequestBody TbgrantDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "create", principalDetails.getTbuser().getId()));
        TbgrantDto.CreateServDto newParam = (TbgrantDto.CreateServDto) TbgrantDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgrantService.create(newParam));
    }

    @Operation(summary = "접근권한 수정",
            description = "접근권한 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbgrantDto.CreateResDto> update(@RequestBody TbgrantDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantDto.UpdateServDto newParam = (TbgrantDto.UpdateServDto) TbgrantDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.update(newParam));
    }

    @Operation(summary = "접근권한 삭제",
            description = "접근권한 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbgrantDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.delete(newParam));
    }
    @Operation(summary = "접근권한 삭제(일괄)",
            description = "접근권한 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbgrantDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.deletes(newParam));
    }

    @Operation(summary = "접근권한 정보 조회",
            description = "접근권한 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbgrantDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.detail(newParam));
    }

    @Operation(summary = "접근권한 목록(전체) 조회",
            description = "접근권한 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbgrantDto.SelectResDto>> list(@Valid TbgrantDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantDto.ListServDto newParam = (TbgrantDto.ListServDto) TbgrantDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.list(newParam));
    }

    @Operation(summary = "접근권한 목록(스크롤) 조회",
            description = "접근권한 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbgrantDto.SelectResDto>> mlist(@Valid TbgrantDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantDto.MoreListServDto newParam = (TbgrantDto.MoreListServDto) TbgrantDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.moreList(newParam));
    }

    @Operation(summary = "접근권한 목록(페이지) 조회",
            description = "접근권한 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbgrantDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantDto.PagedListServDto newParam = (TbgrantDto.PagedListServDto) TbgrantDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantService.pagedlist(newParam));
    }
}
