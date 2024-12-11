package cn.newbeedaly.easyexcel.excel.common.write.common;

import com.google.common.collect.Lists;

import java.io.ByteArrayOutputStream;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class AbstractExportService<Q extends BaseRequest> implements IExportService<Q>{


    protected String writeExcel(Class<?> clazz, List<?> data) {
        return this.writeExcel(clazz, null, data);
    }

    protected String writeExcel(Class<?> clazz, List<?> data, String... excludeFiled) {
        return this.writeExcel(clazz, null, data, Lists.newArrayList(excludeFiled));
    }

    protected String writeExcel(Class<?> clazz, List<List<String>> head, List<?> data) {
        return this.writeExcel(clazz, head, data, null);
    }

    protected String writeExcel(Class<?> clazz, List<List<String>> head, List<?> data, List<String> excludeFiled) {

        return this.writeExcel(clazz, head, this.title(), data, null);
    }

    protected String writeExcel(Class<?> clazz, List<List<String>> head, String sheetName, List<?> data, List<String> excludeFiled) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        EasyExcelWriter.writeExcel(bos, clazz, sheetName, head, excludeFiled, data);
        return this.uploadToOSS(bos);
    }

    protected String writeExcel(SheetWriteContext... contexts) {
        return this.uploadToOSS(EasyExcelWriter.writeExcel(contexts));
    }

    protected String uploadToOSS(ByteArrayOutputStream bos){
//        try {
//            Files.write(Paths.get("C:\\Users\\Administrator\\Downloads\\OrderExcel.xlsx"), bos.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return "https://www.newbeedaly.cn";
    }

}
