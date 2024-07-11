package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbpopupDto;
import com.thc.basespr.dto.TbgrantDto;
import com.thc.basespr.security.PrincipalDetails;
import com.thc.basespr.service.TbpopupService;
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
@Tag(name = "2-5. 팝업 API 안내",
        description = "팝업 관련 기능 정의한 RestController.")
@RequestMapping("/api/tbpopup")
@RestController
public class TbpopupRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TbpopupService tbpopupService;
    private final TbgrantService tbgrantService;
    public TbpopupRestController(
            TbpopupService tbpopupService
            ,TbgrantService tbgrantService
    ) {
        this.tbpopupService = tbpopupService;
        this.tbgrantService = tbgrantService;
    }

    @Operation(summary = "팝업 순서변경",
            description = "팝업 순서변경 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.SequenceReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbpopupDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/sequence")
    public ResponseEntity<TbpopupDto.CreateResDto> sequence(@RequestBody TbpopupDto.SequenceReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "update", principalDetails.getTbuser().getId()));
        TbpopupDto.SequenceServDto newParam = (TbpopupDto.SequenceServDto) TbpopupDto.SequenceServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbpopupService.sequence(newParam));
    }
    /**/
    @Operation(summary = "팝업 생성",
            description = "팝업 생성 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbpopupDto.CreateResDto\\> <br />"
                    + "@exception 필수 파라미터 누락하였을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PostMapping("")
    public ResponseEntity<TbpopupDto.CreateResDto> create(@RequestBody TbpopupDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "create", principalDetails.getTbuser().getId()));
        TbpopupDto.CreateServDto newParam = (TbpopupDto.CreateServDto) TbpopupDto.CreateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbpopupService.create(newParam));
    }

    @Operation(summary = "팝업 수정",
            description = "팝업 수정 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbpopupDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @PutMapping("")
    public ResponseEntity<TbpopupDto.CreateResDto> update(@RequestBody TbpopupDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "update", principalDetails.getTbuser().getId()));
        TbpopupDto.UpdateServDto newParam = (TbpopupDto.UpdateServDto) TbpopupDto.UpdateServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.update(newParam));
    }

    @Operation(summary = "팝업 삭제",
            description = "팝업 삭제 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.DeleteReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbpopupDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("")
    public ResponseEntity<TbpopupDto.CreateResDto> delete(@RequestBody CommonDto.DeleteReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeleteServDto newParam = (CommonDto.DeleteServDto) CommonDto.DeleteServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.delete(newParam));
    }
    @Operation(summary = "팝업 삭제(일괄)",
            description = "팝업 삭제(일괄) 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.UpdateReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbpopupDto.CreateResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/list")
    public ResponseEntity<TbpopupDto.CreateResDto> deletes(@RequestBody CommonDto.DeletesReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "update", principalDetails.getTbuser().getId()));
        CommonDto.DeletesServDto newParam = (CommonDto.DeletesServDto) CommonDto.DeletesServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.deletes(newParam));
    }

    @Operation(summary = "팝업 정보 조회",
            description = "팝업 정보 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.SelectReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<TbpopupDto.SelectResDto\\> <br />"
                    + "@exception 해당 자료 없을 때 등 <br />"
    )
    @PreAuthorize("hasRole('USER')")
    //@PreAuthorize("permitAll()")
    @GetMapping("")
    public ResponseEntity<TbpopupDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "read", principalDetails.getTbuser().getId()));
        CommonDto.SelectServDto newParam = (CommonDto.SelectServDto) CommonDto.SelectServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.detail(newParam));
    }

    @Operation(summary = "팝업 목록(전체) 조회",
            description = "팝업 목록(전체) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.ListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbpopupDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public ResponseEntity<List<TbpopupDto.SelectResDto>> list(@Valid TbpopupDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "read", principalDetails.getTbuser().getId()));
        TbpopupDto.ListServDto newParam = (TbpopupDto.ListServDto) TbpopupDto.ListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.list(newParam));
    }

    @Operation(summary = "팝업 목록(스크롤) 조회",
            description = "팝업 목록(스크롤) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.MoreListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<TbpopupDto.SelectResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/mlist")
    public ResponseEntity<List<TbpopupDto.SelectResDto>> mlist(@Valid TbpopupDto.MoreListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "read", principalDetails.getTbuser().getId()));
        TbpopupDto.MoreListServDto newParam = (TbpopupDto.MoreListServDto) TbpopupDto.MoreListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.moreList(newParam));
    }

    @Operation(summary = "팝업 목록(페이지) 조회",
            description = "팝업 목록(페이지) 조회 컨트롤러 (사용자만 접근 가능) <br />"
                    + "@param TbpopupDto.PagedListReqDto <br />"
                    + "@return HttpStatus.OK(200) ResponseEntity\\<List<CommonDto.PagedListResDto>\\> <br />"
                    + "@exception - <br />"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/plist")
    public ResponseEntity<CommonDto.PagedListResDto> plist(@Valid TbpopupDto.PagedListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        boolean isAdmin = tbgrantService.access(new TbgrantDto.AccessReqDto("tbpopup", "read", principalDetails.getTbuser().getId()));
        TbpopupDto.PagedListServDto newParam = (TbpopupDto.PagedListServDto) TbpopupDto.PagedListServDto.builder().reqTbuserId(principalDetails.getTbuser().getId()).isAdmin(isAdmin).build().afterBuild(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbpopupService.pagedlist(newParam));
    }
}
