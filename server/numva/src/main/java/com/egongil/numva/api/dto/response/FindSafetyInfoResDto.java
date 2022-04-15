package com.egongil.numva.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FindSafetyInfoResDto {
    Long id;
    String name;
    String memo;
    String safetyNumber;
}
