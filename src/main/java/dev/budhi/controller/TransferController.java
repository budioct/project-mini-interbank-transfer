package dev.budhi.controller;

import dev.budhi.controller.handler.RestResponse;
import dev.budhi.dto.AccountDTO;
import dev.budhi.service.AccountService;
import dev.budhi.utilitis.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class TransferController {

    private final AccountService services;

    @PostMapping(
            value = "/transfer",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public RestResponse.object<AccountDTO.AccountResponse> transfer(@RequestBody AccountDTO.AccountRequest request) {

        AccountDTO.AccountResponse accountResp = services.transfer(request);

        return RestResponse.object.<AccountDTO.AccountResponse>builder()
                .status_code(Constants.OK)
                .message(Constants.TRANSFER_MESSAGE)
                .data(accountResp)
                .build();
    }

}
