package io.github.tf2jaguar.pettyprofits.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 14:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EfResult<T> {
    private Integer rc;
    private Integer rt;
    private Integer svr;
    private Integer lt;
    private Integer full;
    private Integer dlmkts;
    private T data;
}
