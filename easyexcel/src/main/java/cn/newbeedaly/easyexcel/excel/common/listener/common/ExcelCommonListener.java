package cn.newbeedaly.easyexcel.excel.common.listener.common;

import cn.newbeedaly.easyexcel.excel.common.read.common.BaseReadCommon;
import cn.newbeedaly.easyexcel.util.CopyUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.IService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 有个很重要的点 ExcelCommonListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
 */
public class ExcelCommonListener<T extends BaseReadCommon, E> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelCommonListener.class);
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    private final IService<E> service;
    private final Class<E> clazz;
    List<E> list = new LinkedList<>();

    public ExcelCommonListener(IService<E> service, Class<E> clazz) {
        this.service = service;
        this.clazz = clazz;
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        LOGGER.info("解析到一条数据:{}", data);
        list.add(CopyUtils.createBean(data, clazz));
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        service.saveBatch(list);
        LOGGER.info("存储数据库成功！");
    }
}
