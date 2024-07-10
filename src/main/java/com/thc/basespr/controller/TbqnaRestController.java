package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbqnaDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbqnaService;
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
@Tag(name = "2-3. 1:1문의 API 안내",
        description = "1:1문의 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbqna")
@RestController
public class TbqnaRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbqnaService tbqnaService;
    private final TbgrantService tbgrantService;
    public TbqnaRestController(
            TbqnaService tbqnaService
            ,TbgrantService tbgrantService
    ) {
        this.tbqnaService = tbqnaService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "1:1문의 생성",
            description = "1:1문의 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbqnaDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbqnaDto.CreateResDto> create(@RequestBody TbqnaDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "create", principalDetails.getTbuser().getId()));
        TbqnaDto.CreateServDto newParam = (TbqnaDto.CreateServDto) TbqnaDto.CreateServDto.builder().tbuserId(principalDetails.getTbuser().getId()).reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbqnaService.create(newParam));
    }

    @Operation(summary = "1:1문의 수정",
            description = "1:1문의 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnaDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbqnaDto.CreateResDto> update(@RequestBody TbqnaDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        TbqnaDto.UpdateServDto newParam = (TbqnaDto.UpdateServDto) TbqnaDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.update(newParam));
    }

    @Operation(summary = "1:1문의 삭제",
            description = "1:1문의 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnaDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbqnaDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.delete(newParam));
    }
    @Operation(summary = "1:1문의 삭제(일괄)",
            description = "1:1문의 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnaDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbqnaDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.deletes(newParam));
    }

    @Operation(summary = "1:1문의 정보 조회",
            description = "1:1문의 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbqnaDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbqnaDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.detail(newParam));
    }

    @Operation(summary = "1:1문의 목록(전체) 조회",
            description = "1:1문의 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbqnaDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<TbqnaDto.SelectResDto>> list(@Valid TbqnaDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnaDto.ListServDto newParam = (TbqnaDto.ListServDto) TbqnaDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.list(newParam));
    }

    @Operation(summary = "1:1문의 목록(스크롤) 조회",
            description = "1:1문의 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbqnaDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbqnaDto.SelectResDto>> mlist(@Valid TbqnaDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnaDto.MoreListServDto newParam = (TbqnaDto.MoreListServDto) TbqnaDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.moreList(newParam));
    }

    @Operation(summary = "1:1문의 목록(페이지) 조회",
            description = "1:1문의 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbqnaDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbqnaDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbqna", "read", principalDetails.getTbuser().getId()));
        TbqnaDto.PagedListServDto newParam = (TbqnaDto.PagedListServDto) TbqnaDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbqnaService.pagedlist(newParam));
    }
}
