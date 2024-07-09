package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbnoticeDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbnoticeService;
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
@Tag(name = "2-1. 공지사항 API 안내",
        description = "공지사항 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbnotice")
@RestController
public class TbnoticeRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbnoticeService tbnoticeService;
    private final TbgrantService tbgrantService;
    public TbnoticeRestController(
            TbnoticeService tbnoticeService
            ,TbgrantService tbgrantService
    ) {
        this.tbnoticeService = tbnoticeService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "공지사항 생성",
            description = "공지사항 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbnoticeDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbnoticeDto.CreateResDto> create(@RequestBody TbnoticeDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "create", principalDetails.getTbuser().getId()));
        TbnoticeDto.CreateServDto newParam = (TbnoticeDto.CreateServDto) TbnoticeDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbnoticeService.create(newParam));
    }

    @Operation(summary = "공지사항 수정",
            description = "공지사항 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbnoticeDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbnoticeDto.CreateResDto> update(@RequestBody TbnoticeDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "update", principalDetails.getTbuser().getId()));
        TbnoticeDto.UpdateServDto newParam = (TbnoticeDto.UpdateServDto) TbnoticeDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.update(newParam));
    }

    @Operation(summary = "공지사항 삭제",
            description = "공지사항 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbnoticeDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbnoticeDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.delete(newParam));
    }
    @Operation(summary = "공지사항 삭제(일괄)",
            description = "공지사항 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbnoticeDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbnoticeDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.deletes(newParam));
    }

    @Operation(summary = "공지사항 정보 조회",
            description = "공지사항 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbnoticeDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbnoticeDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.detail(newParam));
    }

    @Operation(summary = "공지사항 목록(전체) 조회",
            description = "공지사항 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbnoticeDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbnoticeDto.SelectResDto>> list(@Valid TbnoticeDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "read", principalDetails.getTbuser().getId()));
        TbnoticeDto.ListServDto newParam = (TbnoticeDto.ListServDto) TbnoticeDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.list(newParam));
    }

    @Operation(summary = "공지사항 목록(스크롤) 조회",
            description = "공지사항 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbnoticeDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbnoticeDto.SelectResDto>> mlist(@Valid TbnoticeDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "read", principalDetails.getTbuser().getId()));
        TbnoticeDto.MoreListServDto newParam = (TbnoticeDto.MoreListServDto) TbnoticeDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.moreList(newParam));
    }

    @Operation(summary = "공지사항 목록(페이지) 조회",
            description = "공지사항 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbnoticeDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbnoticeDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbnotice", "read", principalDetails.getTbuser().getId()));
        TbnoticeDto.PagedListServDto newParam = (TbnoticeDto.PagedListServDto) TbnoticeDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbnoticeService.pagedlist(newParam));
    }
}
