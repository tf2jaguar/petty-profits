package io.github.tf2jaguar.pettyprofits.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 14:41
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CListResponse {
    private Integer total;
    private List<CList> diff;
}
