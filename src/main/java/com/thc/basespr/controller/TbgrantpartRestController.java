package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbgrantpartDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantpartService;
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

//2024-07-09 추가(클래스 처음 추가함)
@Tag(name = "0-1. 접근권한 상세 API 안내",
        description = "접근권한 상세 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbgrantpart")
@RestController
public class TbgrantpartRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbgrantpartService tbgrantpartService;
    private final TbgrantService tbgrantService;
    public TbgrantpartRestController(
            TbgrantpartService tbgrantpartService
            ,TbgrantService tbgrantService
    ) {
        this.tbgrantpartService = tbgrantpartService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "접근권한 상세 토글",
            description = "접근권한 상세 토글 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgrantpartDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/toggle")
    public ResponseEntity<TbgrantpartDto.CreateResDto> toggle(@RequestBody TbgrantpartDto.ToggleReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "create", principalDetails.getTbuser().getId()));
        TbgrantpartDto.ToggleServDto newParam = (TbgrantpartDto.ToggleServDto) TbgrantpartDto.ToggleServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgrantpartService.toggle(newParam));
    }

    /**/

    @Operation(summary = "접근권한 상세 생성",
            description = "접근권한 상세 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbgrantpartDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<TbgrantpartDto.CreateResDto> create(@RequestBody TbgrantpartDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "create", principalDetails.getTbuser().getId()));
        TbgrantpartDto.CreateServDto newParam = (TbgrantpartDto.CreateServDto) TbgrantpartDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbgrantpartService.create(newParam));
    }

    @Operation(summary = "접근권한 상세 수정",
            description = "접근권한 상세 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantpartDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<TbgrantpartDto.CreateResDto> update(@RequestBody TbgrantpartDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        TbgrantpartDto.UpdateServDto newParam = (TbgrantpartDto.UpdateServDto) TbgrantpartDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.update(newParam));
    }

    @Operation(summary = "접근권한 상세 삭제",
            description = "접근권한 상세 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantpartDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("")
    public ResponseEntity<TbgrantpartDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.delete(newParam));
    }
    @Operation(summary = "접근권한 상세 삭제(일괄)",
            description = "접근권한 상세 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantpartDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/list")
    public ResponseEntity<TbgrantpartDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.deletes(newParam));
    }

    @Operation(summary = "접근권한 상세 정보 조회",
            description = "접근권한 상세 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbgrantpartDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbgrantpartDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.detail(newParam));
    }

    @Operation(summary = "접근권한 상세 목록(전체) 조회",
            description = "접근권한 상세 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantpartDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbgrantpartDto.SelectResDto>> list(@Valid TbgrantpartDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantpartDto.ListServDto newParam = (TbgrantpartDto.ListServDto) TbgrantpartDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.list(newParam));
    }

    @Operation(summary = "접근권한 상세 목록(스크롤) 조회",
            description = "접근권한 상세 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbgrantpartDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbgrantpartDto.SelectResDto>> mlist(@Valid TbgrantpartDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantpartDto.MoreListServDto newParam = (TbgrantpartDto.MoreListServDto) TbgrantpartDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.moreList(newParam));
    }

    @Operation(summary = "접근권한 상세 목록(페이지) 조회",
            description = "접근권한 상세 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbgrantpartDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbgrantpartDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbgrant", "read", principalDetails.getTbuser().getId()));
        TbgrantpartDto.PagedListServDto newParam = (TbgrantpartDto.PagedListServDto) TbgrantpartDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbgrantpartService.pagedlist(newParam));
    }
}
