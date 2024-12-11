package cn.newbeedaly.easyexcel.excel.common.write.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * create by newbeedaly
 * since 2021-08-24
 */
@Slf4j
@SuppressWarnings({"rawtypes", "unchecked"})
@Service
public class DataExportService implements IDataExportService, InitializingBean, ApplicationContextAware {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    private static final Semaphore SEMAPHORE = new Semaphore(5);

    private Map<String, IExportService> BEAN_MAP;
    private ApplicationContext applicationContext;

    @SneakyThrows
    @Override
    public ApiResult<String> exportFile(CommonExportRequest request) {
        IExportService exportService = BEAN_MAP.get(request.getExportBean());
        Class<BaseRequest> paramClass = exportService.paramClass();
        BaseRequest params = new ObjectMapper().readValue(request.getRequestParams(), paramClass);
        BeanUtils.copyProperties(request, params);
        return this.export(params, request.getExportBean(), paramClass);
    }

    private <T extends BaseRequest> ApiResult<String> export(T request, String beanName, Class<?> clazz) {
        try {
            SEMAPHORE.acquire();
            IExportService exportService = BEAN_MAP.get(beanName);
            // 可以记录导出日志表
            log.info("数据导出中");
            // 异步进行导出操作并更新导出结果
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> exportService.makefile(request), EXECUTOR_SERVICE);
            future.whenComplete((link, t) -> {
                if (t == null) {
                    log.info("导出成功,下载链接为{}", link);
                } else {
                    String error;
                    StackTraceElement[] stackTrace = t.getCause().getStackTrace();
                    if (stackTrace == null || stackTrace.length < 1) {
                        error = t.getCause().getMessage();
                    } else {
                        error = stackTrace[0].toString();
                    }
                    log.info("导出失败,出现异常{}", StringUtils.truncate(error, 250));
                }
            });
            return ApiResult.<String>builder().msg("请至‘数据下载’中下载数据").build();
        } catch (InterruptedException e) {
            return ApiResult.<String>builder().msg("任务过多,请稍后重试").build();
        } catch (Exception e) {
            return ApiResult.<String>builder().msg("导出失败").build();
        } finally {
            SEMAPHORE.release();
        }
    }

    @Override
    public void afterPropertiesSet() {
        BEAN_MAP = applicationContext.getBeansOfType(IExportService.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
