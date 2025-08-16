package com.kavie12.customer_system_api.dto.response.paginate;

import com.kavie12.customer_system_api.dto.response.CustomerResponseDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerPaginateResponseDto {
    private List<CustomerResponseDto> dataList;
    private int dataCount;
}
