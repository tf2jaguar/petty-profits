package io.github.tf2jaguar.pettyprofits.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zhangguodong
 * @since : 2022/7/3 14:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CList {
    // 市场类型
    private String f13;
    // 股票代码
    private String f12;
    // 股票名称
    private String f14;
}
