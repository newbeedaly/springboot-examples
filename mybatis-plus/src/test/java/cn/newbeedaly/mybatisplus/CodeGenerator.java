package cn.newbeedaly.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CodeGenerator {

    /**
     * 基础配置
     */
    private static final String outputDir = System.getProperty("user.dir") + "/code-generator/src/main/java";
    private static final String author = "newbeedaly";
    /**
     * 数据库配置
     */
    private static final DbType dbType = DbType.MYSQL;
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "root";
    private static final String password = "mysql123";
    private static final String url = "jdbc:mysql://localhost:3306/mybatisplus?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8";
    private static final String[] tables = {"mp_user"};
    /**
     * 生成包路径
     */
    private static final String packageParent = "cn.newbeedaly.springbootmybatisplus";
    private static final String entity = "dao.po";
    private static final String mapper = "dao.mapper";
    private static final String mapperXml = "dao.mapper";
    private static final String service = "service";
    private static final String serviceImpl = "service.impl";
    private static final String controller = "controller";
    private static final String moduleName = "user";

    @Test
    void codeGenerator() {
        generator();
    }

    public static void generator() {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //代码生成存放位置
        gc.setOutputDir(outputDir);
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(false);
        gc.setOpen(false);
        gc.setAuthor(author);
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceImplName("%sService");
        gc.setServiceName("I%sService");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(dbType);
        dsc.setDriverName(driverName);
        dsc.setUsername(userName);
        dsc.setPassword(password);
        dsc.setUrl(url);
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setInclude(tables);
        strategy.setSuperEntityColumns(new String[]{});
        strategy.setTablePrefix("t_", "mp_");// 此处可以修改为您的表前缀

        mpg.setStrategy(strategy);
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageParent);
        // 代码生成包路径
        pc.setEntity(entity);
        pc.setMapper(mapper);
        pc.setXml(mapperXml);
        pc.setService(service);
        pc.setServiceImpl(serviceImpl);
        pc.setController(controller);
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 ${cfg.packageMy} 设置值
        InjectionConfig cfg = new InjectionConfig() {
             public void initMap() {
                 Map<String, Object> map = new HashMap<String, Object>();
                 map.put("packageDao", "cn.newbeedaly.springbootmybatisplus."+moduleName+".dao");
                 this.setMap(map);
             }
         };
         mpg.setCfg(cfg);
        // 自定义模板生成
        List<FileOutConfig> focList = new ArrayList<FileOutConfig>();
        focList.add(new FileOutConfig("/templates/dao.java.vm" ) {
           @Override
           public String outputFile(TableInfo tableInfo) {
              // 自定义输入文件名称
              return outputDir + "/cn/newbeedaly/springbootmybatisplus/"+moduleName+"/dao/" + tableInfo.getEntityName() + "Dao.java";
           }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig tc = new TemplateConfig();
        tc.setEntity("templates/entity.java.vm");
        tc.setMapper("templates/mapper.java.vm");
        tc.setXml("templates/mapper.xml.vm");
        tc.setServiceImpl("templates/serviceImpl.java.vm");
        tc.setService("templates/service.java.vm");
        tc.setController("templates/controller.java.vm");
        mpg.setTemplate(tc);
        // 执行生成
        mpg.execute();
    }


}
