package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbqnafileDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbqnafileService;
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

//2024-07-10 추가(클래스 처음 추가함)
@Tag(name = "2-3. 1:1문의 첨부파일 API 안내",
        description = "1:1문의 첨부파일 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbqnafile")
@RestController
public class TbqnafileRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbqnafileService tbqnafileService;
    private final TbgrantService tbgrantService;
    public TbqnafileRestController(
            TbqnafileService tbqnafileService
            ,TbgrantService tbgrantService
    ) {
        this.tbqnafileService = tbqnafileService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "1:1문의 첨부파일 생성",
            description = "1:1문의 첨부파일 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbqnafileDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbqnafileDto.CreateResDto> create(@RequestBody TbqnafileDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "create", principalDetails.getTbuser().getId()));
        TbqnafileDto.CreateServDto newParam = (TbqnafileDto.CreateServDto) TbqnafileDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbqnafileService.create(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 수정",
            description = "1:1문의 첨부파일 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnafileDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("")
    public ResponseEntity<TbqnafileDto.CreateResDto> update(@RequestBody TbqnafileDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        TbqnafileDto.UpdateServDto newParam = (TbqnafileDto.UpdateServDto) TbqnafileDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.update(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 삭제",
            description = "1:1문의 첨부파일 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnafileDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbqnafileDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.delete(newParam));
    }
    @Operation(summary = "1:1문의 첨부파일 삭제(일괄)",
            description = "1:1문의 첨부파일 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnafileDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/list")
    public ResponseEntity<TbqnafileDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.deletes(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 정보 조회",
            description = "1:1문의 첨부파일 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnafileDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbqnafileDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.detail(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 목록(전체) 조회",
            description = "1:1문의 첨부파일 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbqnafileDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbqnafileDto.SelectResDto>> list(@Valid TbqnafileDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnafileDto.ListServDto newParam = (TbqnafileDto.ListServDto) TbqnafileDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.list(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 목록(스크롤) 조회",
            description = "1:1문의 첨부파일 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbqnafileDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbqnafileDto.SelectResDto>> mlist(@Valid TbqnafileDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnafileDto.MoreListServDto newParam = (TbqnafileDto.MoreListServDto) TbqnafileDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.moreList(newParam));
    }

    @Operation(summary = "1:1문의 첨부파일 목록(페이지) 조회",
            description = "1:1문의 첨부파일 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnafileDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbqnafileDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnafileDto.PagedListServDto newParam = (TbqnafileDto.PagedListServDto) TbqnafileDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnafileService.pagedlist(newParam));
    }
}
