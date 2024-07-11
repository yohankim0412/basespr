package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.dto.TbbannerDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbgrantService;
import com.thc.basespr.service.TbbannerService;
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
@Tag(name = "2-5. 배너 API 안내",
        description = "배너 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbbanner")
@RestController
public class TbbannerRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbbannerService tbbannerService;
    private final TbgrantService tbgrantService;
    public TbbannerRestController(
            TbbannerService tbbannerService
            ,TbgrantService tbgrantService
    ) {
        this.tbbannerService = tbbannerService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "배너 순서변경",
            description = "배너 순서변경 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.SequenceReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbbannerDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/sequence")
    public ResponseEntity<TbbannerDto.CreateResDto> sequence(@RequestBody TbbannerDto.SequenceReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "update", principalDetails.getTbuser().getId()));
        TbbannerDto.SequenceServDto newParam = (TbbannerDto.SequenceServDto) TbbannerDto.SequenceServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbbannerService.sequence(newParam));
    }
    /**/
    @Operation(summary = "배너 생성",
            description = "배너 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbbannerDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbbannerDto.CreateResDto> create(@RequestBody TbbannerDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "create", principalDetails.getTbuser().getId()));
        TbbannerDto.CreateServDto newParam = (TbbannerDto.CreateServDto) TbbannerDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbbannerService.create(newParam));
    }

    @Operation(summary = "배너 수정",
            description = "배너 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbbannerDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbbannerDto.CreateResDto> update(@RequestBody TbbannerDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "update", principalDetails.getTbuser().getId()));
        TbbannerDto.UpdateServDto newParam = (TbbannerDto.UpdateServDto) TbbannerDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.update(newParam));
    }

    @Operation(summary = "배너 삭제",
            description = "배너 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbbannerDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbbannerDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.delete(newParam));
    }
    @Operation(summary = "배너 삭제(일괄)",
            description = "배너 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbbannerDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbbannerDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.deletes(newParam));
    }

    @Operation(summary = "배너 정보 조회",
            description = "배너 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbbannerDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbbannerDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.detail(newParam));
    }

    @Operation(summary = "배너 목록(전체) 조회",
            description = "배너 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbbannerDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public ResponseEntity<List<TbbannerDto.SelectResDto>> list(@Valid TbbannerDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "read", principalDetails.getTbuser().getId()));
        TbbannerDto.ListServDto newParam = (TbbannerDto.ListServDto) TbbannerDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.list(newParam));
    }

    @Operation(summary = "배너 목록(스크롤) 조회",
            description = "배너 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbbannerDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbbannerDto.SelectResDto>> mlist(@Valid TbbannerDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "read", principalDetails.getTbuser().getId()));
        TbbannerDto.MoreListServDto newParam = (TbbannerDto.MoreListServDto) TbbannerDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.moreList(newParam));
    }

    @Operation(summary = "배너 목록(페이지) 조회",
            description = "배너 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbbannerDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbbannerDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbbanner", "read", principalDetails.getTbuser().getId()));
        TbbannerDto.PagedListServDto newParam = (TbbannerDto.PagedListServDto) TbbannerDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbbannerService.pagedlist(newParam));
    }
}
