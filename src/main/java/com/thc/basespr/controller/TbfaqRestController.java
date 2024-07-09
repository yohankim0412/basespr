package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbfaqDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbfaqService;
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
@Tag(name = "2-1. FAQ API 안내",
        description = "FAQ 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbfaq")
@RestController
public class TbfaqRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbfaqService tbfaqService;
    private final TbgrantService tbgrantService;
    public TbfaqRestController(
            TbfaqService tbfaqService
            ,TbgrantService tbgrantService
    ) {
        this.tbfaqService = tbfaqService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "FAQ 순서변경",
            description = "FAQ 순서변경 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.SequenceReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbfaqDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/sequence")
    public ResponseEntity<TbfaqDto.CreateResDto> sequence(@RequestBody TbfaqDto.SequenceReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "update", principalDetails.getTbuser().getId()));
        TbfaqDto.SequenceServDto newParam = (TbfaqDto.SequenceServDto) TbfaqDto.SequenceServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbfaqService.sequence(newParam));
    }
    /**/
    @Operation(summary = "FAQ 생성",
            description = "FAQ 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbfaqDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbfaqDto.CreateResDto> create(@RequestBody TbfaqDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "create", principalDetails.getTbuser().getId()));
        TbfaqDto.CreateServDto newParam = (TbfaqDto.CreateServDto) TbfaqDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbfaqService.create(newParam));
    }

    @Operation(summary = "FAQ 수정",
            description = "FAQ 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbfaqDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbfaqDto.CreateResDto> update(@RequestBody TbfaqDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "update", principalDetails.getTbuser().getId()));
        TbfaqDto.UpdateServDto newParam = (TbfaqDto.UpdateServDto) TbfaqDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.update(newParam));
    }

    @Operation(summary = "FAQ 삭제",
            description = "FAQ 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbfaqDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbfaqDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.delete(newParam));
    }
    @Operation(summary = "FAQ 삭제(일괄)",
            description = "FAQ 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbfaqDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbfaqDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.deletes(newParam));
    }

    @Operation(summary = "FAQ 정보 조회",
            description = "FAQ 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbfaqDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbfaqDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.detail(newParam));
    }

    @Operation(summary = "FAQ 목록(전체) 조회",
            description = "FAQ 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbfaqDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<List<TbfaqDto.SelectResDto>> list(@Valid TbfaqDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "read", principalDetails.getTbuser().getId()));
        TbfaqDto.ListServDto newParam = (TbfaqDto.ListServDto) TbfaqDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.list(newParam));
    }

    @Operation(summary = "FAQ 목록(스크롤) 조회",
            description = "FAQ 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbfaqDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbfaqDto.SelectResDto>> mlist(@Valid TbfaqDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "read", principalDetails.getTbuser().getId()));
        TbfaqDto.MoreListServDto newParam = (TbfaqDto.MoreListServDto) TbfaqDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.moreList(newParam));
    }

    @Operation(summary = "FAQ 목록(페이지) 조회",
            description = "FAQ 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbfaqDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbfaqDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbfaq", "read", principalDetails.getTbuser().getId()));
        TbfaqDto.PagedListServDto newParam = (TbfaqDto.PagedListServDto) TbfaqDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbfaqService.pagedlist(newParam));
    }
}
