package com.api.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    @NotBlank(message = "cardNumber is required")
    @Pattern(regexp = "\\d+", message = "cardNumber must contain only digits")
    private String cardNumber;

    @NotBlank(message = "cvv is required")
    @Size(min = 3, max = 3, message = "cvv must be 3 digits")
    private String cvv;
}
