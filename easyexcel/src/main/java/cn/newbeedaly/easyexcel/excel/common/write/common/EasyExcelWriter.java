package cn.newbeedaly.easyexcel.excel.common.write.common;

import cn.newbeedaly.easyexcel.excel.simple.write.WriteOrder;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelWriter {

    public static void writeExcel(OutputStream outputStream, Class<?> clazz, String sheetName, List<?> data) {
        writeExcel(outputStream, clazz, sheetName, null, null, data);
    }

    public static void writeExcel(OutputStream outputStream, Class<?> clazz, String sheetName, List<List<String>> head, List<String> excludeColumnFiled, List<?> data) {
        ExcelWriterBuilder builder = EasyExcel.write(outputStream, clazz).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        builder.autoCloseStream(Boolean.TRUE);
        if (head != null) {
            builder.head(head);
        }
        if (excludeColumnFiled != null) {
            builder.excludeColumnFiledNames(excludeColumnFiled);
        }
        builder.sheet(sheetName).doWrite(data);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static ByteArrayOutputStream writeExcel(SheetWriteContext... contexts) {
        com.alibaba.excel.ExcelWriter writer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            writer = EasyExcel.write(bos).build();
            for (int i = 0; i < contexts.length; i++) {
                SheetWriteContext context = contexts[i];
                // 每次都要创建writeSheet 这里注意必须指定sheetNo 而且sheetName必须不一样
                ExcelWriterSheetBuilder sheetBuilder = EasyExcel.writerSheet(i, context.getSheetName());
                sheetBuilder.registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
                if (context.getHandler() != null) {
                    sheetBuilder.registerWriteHandler(context.getHandler());
                }
                if (context.getHead() instanceof Class) {
                    sheetBuilder.head((Class) context.getHead());
                } else if (context.getHead() instanceof List) {
                    sheetBuilder.head((List<List<String>>) context.getHead());
                } else {
                    throw new IllegalArgumentException("Excel sheet head type is illegal, it must be Class or List<List<String>>");
                }
                // 分页去数据库查询数据 这里可以去数据库查询每一页的数据
                writer.write(context.getData(), sheetBuilder.build());
            }
            return bos;
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ExcelWriterBuilder builder = EasyExcel.write(bos, WriteOrder.class)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy());
        builder.autoCloseStream(Boolean.TRUE);
        List<WriteOrder> dataList = new ArrayList<>();
        dataList.add( WriteOrder.builder().build());
        dataList.add( WriteOrder.builder().build());
        dataList.add( WriteOrder.builder().build());
        builder.sheet("订单表").doWrite(dataList);
        Files.write(Paths.get("C:\\Users\\Administrator\\Downloads\\OrderExcel.xlsx"), bos.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
