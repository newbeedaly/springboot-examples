package cn.newbeedaly.easyexcel.excel.common.write.common;

import com.alibaba.excel.write.handler.WriteHandler;
import lombok.Getter;

import java.util.List;

@Getter
public class SheetWriteContext<H, T> {
    private final String sheetName;
    private final H head;
    private final List<T> data;
    private final WriteHandler handler;

    public SheetWriteContext(String sheetName, H head, List<T> data, WriteHandler handler) {
        this.sheetName = sheetName;
        this.head = head;
        this.data = data;
        this.handler = handler;
    }
}
