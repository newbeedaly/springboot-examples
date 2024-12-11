package cn.newbeedaly.mybatisplus.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageQueryResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private long current;
    private long pages;
    private long size;
    private long total;
    private List<T> dataList;

}
