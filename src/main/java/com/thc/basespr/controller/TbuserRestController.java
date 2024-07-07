package com.thc.basespr.controller;

import com.thc.basespr.dto.CommonDto;
import com.thc.basespr.dto.TbuserDto;
import com.thc.basespr.service.TbuserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/tbuser")
@RestController
public class TbuserRestController {

    private final TbuserService tbuserService;
    public TbuserRestController(
            TbuserService tbuserService
    ) {
        this.tbuserService = tbuserService;
    }

    @Operation(summary = "회원 가입",
            description = "회원 가입 위한 컨트롤러 (누구나 접근 가능) <br />"
                    + "@param TbuserDto.CreateReqDto <br />"
                    + "@return HttpStatus.CREATED(201) ResponseEntity\\<TbuserDto.CreateResDto\\> <br />"
                    + "@exception username 중복일 경우  <br />"
    )
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<TbuserDto.CreateResDto> signup(@Valid @RequestBody TbuserDto.CreateReqDto params) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.signup(params));
    }

    @PostMapping("")
    public ResponseEntity<TbuserDto.CreateResDto> create(@RequestBody TbuserDto.CreateReqDto param){
        System.out.println("param : " + param);
        return ResponseEntity.status(HttpStatus.CREATED).body(tbuserService.create(param));
    }
    @PutMapping("")
    public ResponseEntity<TbuserDto.CreateResDto> update(@RequestBody TbuserDto.UpdateReqDto param){
        System.out.println(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.update(param));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<TbuserDto.SelectResDto> detail(@Valid CommonDto.SelectReqDto param){
        System.out.println(param);
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.detail(param));
    }
    @GetMapping("/list")
    public ResponseEntity<List<TbuserDto.SelectResDto>> list(@Valid TbuserDto.ListReqDto param){
        return ResponseEntity.status(HttpStatus.OK).body(tbuserService.list(param));
    }
}
