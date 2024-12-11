package cn.newbeedaly.easyexcel.controller;

import cn.newbeedaly.easyexcel.excel.common.write.common.ApiResult;
import cn.newbeedaly.easyexcel.excel.simple.listener.ExcelOrderListener;
import cn.newbeedaly.easyexcel.excel.simple.read.ReadOrder;
import cn.newbeedaly.easyexcel.excel.simple.write.WriteOrder;
import cn.newbeedaly.easyexcel.order.service.IOrderService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * easyexcel controller
 *
 * @author newbeedaly
 * @since 2021-8-18
 */
@Api(tags = "easyexcel")
@RequestMapping("/easyexcel/simple")
@RestController
public class EasyExcelSimpleController {

    @Autowired
    private IOrderService orderService;

    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link WriteOrder}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @ApiOperation(value = "下载", notes = "简单下载")
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("订单", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), WriteOrder.class).sheet("订单表").doWrite(orderService.page(new Page<>(1, 100)).getRecords());
    }

    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link ReadOrder}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ReadOrder}
     * <p>3. 直接读即可
     */
    @ApiOperation(value = "上传", notes = "简单上传")
    @PostMapping(value = "/upload")
    public ApiResult<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ReadOrder.class, new ExcelOrderListener(orderService)).sheet().doRead();
        return ApiResult.<String>builder().msg("成功").build();
    }


}
