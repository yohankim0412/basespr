package com.thc.basespr.controller;

import com.thc.basespr.domain.Tbgrant;
import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbuserDto;
import com.thc.basespr.security.JwtTokenDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbuserService;
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
@Tag(name = "0-1. 사용자 API 안내",
        description = "사용자 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbuser")
@RestController
public class TbuserRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbuserService tbuserService;
    private final TbgrantService tbgrantService;
    public TbuserRestController(
            TbuserService tbuserService
            ,TbgrantService tbgrantService
    ) {
        this.tbuserService = tbuserService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "회원 가입(KG임시)",
            description = "회원 가입(KG임시) 위한 컨트롤러 (누구나 접근 가능) <br />"
                    + "@param TbuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception username 중복일 경우  <br />"
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/kg")
    public ResponseEntity<JwtTokenDto> tempKgcert(@Valid @RequestBody TbuserDto.CreateReqDto param) {
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "create", null));
        TbuserDto.CreateServDto newParam = (TbuserDto.CreateServDto) TbuserDto.CreateServDto.builder().isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.tempKgcert(newParam));
    }
    @Operation(summary = "회원 가입",
            description = "회원 가입 위한 컨트롤러 (누구나 접근 가능) <br />"
                    + "@param TbuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception username 중복일 경우  <br />"
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<TbuserDto.CreateResDto> signup(@Valid @RequestBody TbuserDto.CreateReqDto param) {
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "create", null));
        TbuserDto.CreateServDto newParam = (TbuserDto.CreateServDto) TbuserDto.CreateServDto.builder().isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.signup(newParam));
    }

    /**/

    @Operation(summary = "사용자 생성",
            description = "사용자 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbuserDto.CreateResDto> create(@RequestBody TbuserDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "create", principalDetails.getTbuser().getId()));
        TbuserDto.CreateServDto newParam = (TbuserDto.CreateServDto) TbuserDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.create(newParam));
    }

    @Operation(summary = "사용자 수정",
            description = "사용자 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbuserDto.CreateResDto> update(@RequestBody TbuserDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "update", principalDetails.getTbuser().getId()));
        TbuserDto.UpdateServDto newParam = (TbuserDto.UpdateServDto) TbuserDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.update(newParam));
    }

    @Operation(summary = "사용자 삭제",
            description = "사용자 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbuserDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.delete(newParam));
    }
    @Operation(summary = "사용자 삭제(일괄)",
            description = "사용자 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbuserDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.deletes(newParam));
    }

    @Operation(summary = "사용자 정보 조회",
            description = "사용자 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbuserDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbuserDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.detail(newParam));
    }

    @Operation(summary = "사용자 목록(전체) 조회",
            description = "사용자 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbuserDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbuserDto.SelectResDto>> list(@Valid TbuserDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "read", principalDetails.getTbuser().getId()));
        TbuserDto.ListServDto newParam = (TbuserDto.ListServDto) TbuserDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.list(newParam));
    }

    @Operation(summary = "사용자 목록(스크롤) 조회",
            description = "사용자 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbuserDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbuserDto.SelectResDto>> mlist(@Valid TbuserDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "read", principalDetails.getTbuser().getId()));
        TbuserDto.MoreListServDto newParam = (TbuserDto.MoreListServDto) TbuserDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.moreList(newParam));
    }

    @Operation(summary = "사용자 목록(페이지) 조회",
            description = "사용자 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbuserDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbuserDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbuser", "read", principalDetails.getTbuser().getId()));
        TbuserDto.PagedListServDto newParam = (TbuserDto.PagedListServDto) TbuserDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.pagedlist(newParam));
    }
}
