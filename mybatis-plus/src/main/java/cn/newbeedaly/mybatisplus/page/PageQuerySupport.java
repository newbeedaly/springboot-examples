package cn.newbeedaly.mybatisplus.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PageQuerySupport {

    public static <T> QueryWrapper<T> processingSort(List<PageQueryCondition.Sort> sortList) {
        QueryWrapper<T> queryWrapper = Wrappers.query();
        sortList = Optional.ofNullable(sortList).orElse(Lists.newArrayList());
        List<String> ascColumnList = sortList.stream()
                .filter(s -> StringUtils.equalsIgnoreCase(s.getOrderType(), "asc"))
                .map(PageQueryCondition.Sort::getColumn)
                .collect(Collectors.toList());
        List<String> descColumnList = sortList.stream()
                .filter(s -> StringUtils.equalsIgnoreCase(s.getOrderType(), "desc"))
                .map(PageQueryCondition.Sort::getColumn)
                .collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(ascColumnList)){
            queryWrapper.orderByAsc(String.join(",", ascColumnList));
        }
        if(!CollectionUtils.isEmpty(descColumnList)) {
            queryWrapper.orderByDesc(String.join(",", descColumnList));
        }
        return queryWrapper;
    }


    public static <T> PageQueryResult<T> buildPageResult(Page<T> page) {
        return PageQueryResult.<T>builder()
                .current(page.getCurrent())
                .size(page.getSize())
                .pages(page.getPages())
                .dataList(page.getRecords())
                .build();
    }
}
