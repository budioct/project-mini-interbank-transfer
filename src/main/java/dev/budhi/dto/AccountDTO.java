package dev.budhi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import dev.budhi.entity.Account;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class AccountDTO {

    @Getter
    @Setter
    @Builder
    @JsonPropertyOrder({"fromBank", "fromAccount", "fromAmount", "toBank", "toAccount", "toAmount"})
    public static class AccountResponse {
        @JsonProperty("from_bank")
        String fromBank;
        @JsonProperty("from_account")
        String fromAccount;
        @JsonProperty("balance_from_sender")
        BigDecimal fromAmount;
        @JsonProperty("to_bank")
        String toBank;
        @JsonProperty("to_account")
        String toAccount;
        @JsonProperty("balance_from_recipient")
        BigDecimal toAmount;
    }

    @Getter
    @Setter
    @Builder
    public static class AccountRequest {
        @JsonProperty("from_bank")
        @NotBlank
        String fromBank;
        @JsonProperty("from_account")
        @NotBlank
        @Size(min = 10, max = 10, message = "account number must be 10 digits")
        String fromAccount;
        @JsonProperty("to_bank")
        @NotBlank
        String toBank;
        @JsonProperty("to_account")
        @NotBlank
        @Size(min = 10, max = 10, message = "account number must be 10 digits")
        String toAccount;
        @DecimalMin(value = "0.0", inclusive = false)
        @Digits(integer=10, fraction=2)
        BigDecimal amount;
    }

    public static AccountResponse toAccountResponse(Account fromAcc, Account toAcc) {
        return AccountResponse.builder()
                .fromBank(fromAcc.getBank())
                .fromAccount(fromAcc.getAccountNumber())
                .fromAmount(fromAcc.getBalance())
                .toBank(toAcc.getBank())
                .toAccount(toAcc.getAccountNumber())
                .toAmount(toAcc.getBalance())
                .build();
    }

}
