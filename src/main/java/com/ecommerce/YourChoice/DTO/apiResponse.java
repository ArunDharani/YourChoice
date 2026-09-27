package com.ecommerce.YourChoice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class apiResponse {
    public String message;
    public Boolean status;
}
