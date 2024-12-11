package cn.newbeedaly.springboot.jpa.common;

import lombok.Data;

@Data
public class PageQuery {
    private int page;
    private int size;
}
