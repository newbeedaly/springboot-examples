package cn.newbeedaly.mybatisplus.page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageQueryCondition<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private T t;
    private int page;
    private int size;
    private List<Sort> sortList;

    @Data
    public static class Sort {
        private String column;
        private String orderType;
    }
}
