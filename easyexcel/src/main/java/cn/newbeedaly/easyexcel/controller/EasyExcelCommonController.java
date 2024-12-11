package cn.newbeedaly.easyexcel.controller;

import cn.newbeedaly.easyexcel.excel.common.listener.common.ExcelCommonListener;
import cn.newbeedaly.easyexcel.excel.common.read.order.ReadOrderCommon;
import cn.newbeedaly.easyexcel.excel.common.write.common.ApiResult;
import cn.newbeedaly.easyexcel.excel.common.write.common.CommonExportRequest;
import cn.newbeedaly.easyexcel.excel.common.write.common.DataExportService;
import cn.newbeedaly.easyexcel.order.dao.po.Order;
import cn.newbeedaly.easyexcel.order.service.IOrderService;
import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * easyexcel common controller 通用上传（分批保存），下载（异步）控制器
 *
 * @author newbeedaly
 * @since 2021-8-19
 */
@Api(tags = "easyexcel")
@RequestMapping("/easyexcel/common")
@RestController
public class EasyExcelCommonController {

    @Autowired
    private DataExportService dataExportService;

    @Autowired
    private IOrderService orderService;

    /**
     * Excel文件下载
     */
    @ApiOperation(value = "下载", notes = "通用下载")
    @GetMapping("/download")
    public ApiResult<String> download(CommonExportRequest request) {
        return dataExportService.exportFile(request);
    }

    /**
     * Excel文件上传
     */
    @ApiOperation(value = "上传", notes = "通用上传")
    @PostMapping(value = "/upload")
    public ApiResult<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), ReadOrderCommon.class, new ExcelCommonListener<>(orderService, Order.class)).sheet().doRead();
        return ApiResult.<String>builder().msg("成功").build();
    }


}
