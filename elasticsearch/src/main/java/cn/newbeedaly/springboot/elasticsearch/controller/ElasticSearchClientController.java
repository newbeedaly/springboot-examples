package cn.newbeedaly.springboot.elasticsearch.controller;

import cn.newbeedaly.springboot.elasticsearch.domain.client.User;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/es/client")
public class ElasticSearchClientController {

    private static final String INDEX = "newbeedaly-index";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /*----------------------------简单操作----------------------------*/

    /**
     * 创建索引
     */
    @PostMapping("/createIndex")
    public void createIndex() throws IOException {
        CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(c -> c.index(INDEX));
        log.info("创建索引状态: {}", createIndexResponse.acknowledged());
        log.info("已确认的分片: {}", createIndexResponse.shardsAcknowledged());
        log.info("索引名称: {}", createIndexResponse.index());
    }

    /**
     * 查看索引
     */
    @PostMapping("/getIndex")
    public void getIndex() throws IOException {
        // 查看索引是否存在
        BooleanResponse booleanResponse = elasticsearchClient.indices().exists(i -> i.index(INDEX));
        log.info("查看索引是否存在: {}", booleanResponse.value());
        // 查看索引
        GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(i -> i.index(INDEX));
        Map<String, IndexState> result = getIndexResponse.result();
        result.forEach(
                (k, v) -> log.info("key = {},value = {}", k, v)
        );
        // 查看全部索引
        IndicesResponse indicesResponse = elasticsearchClient.cat().indices();
        indicesResponse.valueBody().forEach(
                info -> log.info("health:{}, status:{}, uuid:{} ", info.health(), info.status(), info.uuid())
        );
    }

    /**
     * 删除索引
     */
    @PostMapping("/deleteIndex")
    public void deleteIndex() throws IOException {
        DeleteIndexResponse deleteIndexResponse = elasticsearchClient.indices().delete(i -> i.index(INDEX));
        log.info("删除索引状态: {}", deleteIndexResponse.acknowledged());
    }

    /**
     * 添加文档
     */
    @PostMapping("/addDocument")
    public void addDocument() throws IOException {
        // 方法一
        User user = User.builder().id("1").name("newbeedaly").age(29).sex("男").build();
        IndexResponse indexResponse = elasticsearchClient.index(d -> d.index(INDEX).id(user.getId()).document(user));
        log.info("添加文档: {}", indexResponse.version());
        // 方法二
        IndexRequest.Builder<User> indexReqBuilder = new IndexRequest.Builder<>();
        indexReqBuilder.index(INDEX);
        indexReqBuilder.id(user.getId());
        indexReqBuilder.document(user);
        IndexResponse indexResponseTwo = elasticsearchClient.index(indexReqBuilder.build());
        log.info("添加文档: {}", indexResponseTwo.version());
    }

    /**
     * 查询文档
     */
    @GetMapping("/getDocument")
    public void getDocument() throws IOException {
        GetResponse<User> getResponse = elasticsearchClient.get(s -> s.index(INDEX).id("1"), User.class);
        log.info("getResponse: {}", getResponse.source());
        if (getResponse.found()) {
            User user = getResponse.source();
            log.info("user name = {}", user);
        }
        // 判断文档是否存在
        BooleanResponse booleanResponse = elasticsearchClient.exists(s -> s.index(INDEX).id("1"));
        log.info("判断Document是否存在: {}", booleanResponse.value());
    }

    /**
     * 更新文档
     */
    @PostMapping("/updateDocument")
    public void updateDocument() throws IOException {
        // 构建需要修改的内容，这里使用了Map
        User user = User.builder().name("newbee").build();
        // 构建修改文档的请求
        UpdateResponse<User> response = elasticsearchClient.update(e -> e.index(INDEX).id("1").doc(user), User.class);
        // 打印请求结果
        log.info(String.valueOf(response.result()));
    }

    /**
     * 删除文档
     */
    @PostMapping("/deleteDocument")
    public void deleteDocument() throws IOException {
        DeleteResponse deleteResponse = elasticsearchClient.delete(s -> s.index(INDEX).id("1"));
        log.info("删除文档操作结果:{}", deleteResponse.result());
    }

    /**
     * 批量添加文档
     */
    @PostMapping("/batchAddDocument")
    public void batchAddDocument() throws IOException {
        // 方法一 use BulkOperation
        List<User> users = new ArrayList<>();
        User.builder().id("11").name("阿旺").age(29).sex("男").build();
        User.builder().id("12").name("刘菲").age(31).sex("女").build();
        User.builder().id("13").name("冬梅").age(27).sex("女").build();
        List<BulkOperation> bulkOperations = new ArrayList<>();
        users.forEach(u ->
                bulkOperations.add(BulkOperation.of(b -> b.index(c -> c.id(u.getId()).document(u))))
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(s -> s.index(INDEX).operations(bulkOperations));
        bulkResponse.items().forEach(i -> log.info("i = {}", i.result()));
        log.error(" bulkResponse.errors = {}", bulkResponse.errors());

        // 方法二 use BulkRequest
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (User user : users) {
            br.operations(op -> op.index(idx -> idx.index(INDEX).id(user.getId()).document(user)));
        }
        BulkResponse result = elasticsearchClient.bulk(br.build());
        // Log errors, if any
        if (result.errors()) {
            log.error("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    log.error(item.error().reason());
                }
            }
        }
    }

    /**
     * 批量删除文档
     */
    @PostMapping("/batchDeleteDocument")
    public void batchDeleteDocument() throws IOException {
        // 方法一 use BulkOperation
        List<String> list = Arrays.asList("11", "12", "13");
        List<BulkOperation> bulkOperations = new ArrayList<>();
        list.forEach(a ->
                bulkOperations.add(BulkOperation.of(b ->
                        b.delete(c -> c.id(a))
                ))
        );
        BulkResponse bulkResponse = elasticsearchClient.bulk(a -> a.index(INDEX).operations(bulkOperations));
        bulkResponse.items().forEach(a ->
                log.info("result = {}", a.result())
        );
        log.error("bulkResponse.errors() = {}", bulkResponse.errors());

        // 方法二 use BulkRequest
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (String s : list) {
            br.operations(op -> op.delete(c -> c.id(s)));
        }
        BulkResponse bulkResponseTwo = elasticsearchClient.bulk(br.build());
        bulkResponseTwo.items().forEach(a ->
                log.info("result = {}", a.result())
        );
        log.error("bulkResponse.errors() = {}", bulkResponseTwo.errors());
    }



    /*------------------------------复杂操作--------------------------*/

    /**
     * 条件查询方式
     */
    @GetMapping("/search")
    public void search() throws IOException {
        String searchText = "newbee";
        SearchResponse<User> response = elasticsearchClient.search(s -> s.index(INDEX)
                        .query(q -> q
                                // 在众多可用的查询变体中选择一个。我们在这里选择匹配查询（全文搜索）
                                .match(t -> t
                                        // name配置匹配查询：我们在字段中搜索一个词
                                        .field("name")
                                        .query(searchText)
                                )
                        ),
                // 匹配文档的目标类
                User.class
        );
        TotalHits total = response.hits().total();
        log.info("There are " + total.value() + " results");
        for (Hit<User> hit : response.hits().hits()) {
            User user = hit.source();
            log.info("Found user " + user);
        }
    }

    /**
     * 嵌套搜索查询
     */
    @GetMapping("/searchNested")
    public void searchNested() throws IOException {
        String searchText = "newbeedaly";
        int maxAge = 28;
        // byName、byMaxAge：分别为各个条件创建查询
        Query byName = MatchQuery.of(m -> m
                .field("name")
                .query(searchText)
        )
                //MatchQuery是一个查询变体，我们必须将其转换为 Query 联合类型
                ._toQuery();
        Query byMaxAge = RangeQuery.of(m -> m
                .field("age")
                // Elasticsearch 范围查询接受大范围的值类型。我们在这里创建最高价格的 JSON 表示。
                .gte(JsonData.of(maxAge))
        )._toQuery();
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .bool(b -> b
                                        // 搜索查询是结合了文本搜索和最高价格查询的布尔查询
                                        .must(byName)
                                        // .should(byMaxAge)
                                        .must(byMaxAge)
                                )
                        ),
                User.class
        );
        List<Hit<User>> hits = response.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
    }

    /**
     * 模板化搜索
     * 模板化搜索是存储的搜索，可以使用不同的变量运行它。搜索模板让您无需修改应用程序代码即可更改搜索。
     * 在运行模板搜索之前，首先必须创建模板。这是一个返回搜索请求正文的存储脚本，通常定义为 Mustache 模板
     */
    @GetMapping("/templatedSearch")
    public void templatedSearch() throws IOException {
        // 事先创建搜索模板
        elasticsearchClient.putScript(r -> r
                // 要创建的模板脚本的标识符
                .id("query-script")
                .script(s -> s
                        .lang("mustache")
                        .source("{\"query\":{\"match\":{\"{{field}}\":\"{{value}}\"}}}")
                ));
        // 开始使用模板搜索
        String field = "name";
        String value = "liuyifei";
        SearchTemplateResponse<User> response = elasticsearchClient.searchTemplate(r -> r
                        .index("users")
                        // 要使用的模板脚本的标识符
                        .id("query-script")
                        // 模板参数值
                        .params("field", JsonData.of(field))
                        .params("value", JsonData.of(value)),
                User.class
        );

        List<Hit<User>> hits = response.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
    }

    /**
     * 分页+排序条件搜索
     */
    @GetMapping("/paginationSearch")
    public void paginationSearch() throws IOException {
        int maxAge = 20;
        Query byMaxAge = RangeQuery.of(m -> m
                .field("age")
                .gte(JsonData.of(maxAge))
        )._toQuery();
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .bool(b -> b
                                        .must(byMaxAge)
                                )
                        )
                        //分页查询，从第0页开始查询4个document
                        .from(0)
                        .size(4)
                        //按age降序排序
                        .sort(f -> f.field(o -> o.field("age")
                                .order(SortOrder.Desc))),
                User.class
        );
        List<Hit<User>> hits = response.hits().hits();
        for (Hit<User> hit : hits) {
            User user = hit.source();
            assert user != null;
            log.info("Found userId " + user.getId() + ", name " + user.getName());
        }
    }

    /**
     * 过滤字段
     *
     * @throws IOException ioexception
     */
    @GetMapping("/filterFieldSearch")
    public void filterFieldSearch() throws IOException {
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .matchAll(m -> m)
                        )
                        .sort(f -> f
                                .field(o -> o
                                        .field("age")
                                        .order(SortOrder.Desc)
                                )
                        )
                        .source(source -> source
                                .filter(f -> f
                                        .includes("name", "id")
                                        .excludes(""))),
                User.class
        );
        List<Hit<User>> hits = response.hits().hits();
        List<User> userList = new ArrayList<>(hits.size());
        for (Hit<User> hit : hits) {
            User user = hit.source();
            userList.add(user);
        }
        log.info("过滤字段后：{}", userList);
    }

    /**
     * 模糊查询
     *
     * @throws IOException ioexception
     */
    @GetMapping("/fuzzyQuerySearch")
    public void fuzzyQuerySearch() throws IOException {
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                // 模糊查询
                                .fuzzy(f -> f
                                        // 需要判断的字段名称
                                        .field("name")
                                        // 需要模糊查询的关键词
                                        // 目前文档中没有liuyi这个用户名
                                        .value("liuyi")
                                        // fuzziness代表可以与关键词有误差的字数，可选值为0、1、2这三项
                                        .fuzziness("2")
                                )
                        )
                        .source(source -> source
                                .filter(f -> f
                                        .includes("name", "id")
                                        .excludes(""))),
                User.class
        );
        List<Hit<User>> hits = response.hits().hits();
        List<User> userList = new ArrayList<>(hits.size());
        for (Hit<User> hit : hits) {
            User user = hit.source();
            userList.add(user);
        }
        log.info("过滤字段后：{}", userList);
    }

    /**
     * 高亮查询
     *
     * @throws IOException ioexception
     */
    @GetMapping("/highlightQueryQuery")
    public void highlightQueryQuery() throws IOException {
        SearchResponse<User> response = elasticsearchClient.search(s -> s
                        .index("users")
                        .query(q -> q
                                .term(t -> t
                                        .field("name")
                                        .value("zhaosi"))
                        )
                        .highlight(h -> h
                                .fields("name", f -> f
                                        .preTags("<font color='red'>")
                                        .postTags("</font>")))
                        .source(source -> source
                                .filter(f -> f
                                        .includes("name", "id")
                                        .excludes(""))),
                User.class
        );
        List<Hit<User>> hits = response.hits().hits();
        List<User> userList = new ArrayList<>(hits.size());
        for (Hit<User> hit : hits) {
            User user = hit.source();
            userList.add(user);
            for (Map.Entry<String, List<String>> entry : hit.highlight().entrySet()) {
                System.out.println("Key = " + entry.getKey());
                entry.getValue().forEach(System.out::println);
            }
        }
        log.info("模糊查询结果：{}", userList);
    }


    /**
     * 获取最大年龄用户
     */
    @GetMapping("/getMaxAgeUserTest")
    public void getMaxAgeUserTest() throws IOException {
        SearchResponse<Void> response = elasticsearchClient.search(b -> b
                        .index("users")
                        .size(0)
                        .aggregations("maxAge", a -> a
                                .max(MaxAggregation.of(s -> s
                                        .field("age"))
                                )
                        ),
                Void.class
        );
        MaxAggregate maxAge = response.aggregations()
                .get("maxAge")
                .max();
        log.info("maxAge.value:{}", maxAge.value());
    }

    /**
     * 年龄分组
     *
     * @throws IOException ioexception
     */
    @GetMapping("/groupingTest")
    public void groupingTest() throws IOException {
        SearchResponse<Void> response = elasticsearchClient.search(b -> b
                        .index("users")
                        .size(0)
                        .aggregations("groupName", a -> a
                                .terms(TermsAggregation.of(s -> s
                                        .field("age")))
                        ),
                Void.class
        );
        LongTermsAggregate longTermsAggregate = response.aggregations()
                .get("groupName")
                .lterms();
        log.info("multiTermsAggregate:{}", longTermsAggregate.buckets());
    }

    /**
     * 性别分组
     *
     * @throws IOException ioexception
     */
    @GetMapping("/groupBySexTest")
    public void groupBySexTest() throws IOException {
        SearchResponse<Void> response = elasticsearchClient.search(b -> b
                        .index("users")
                        .size(0)
                        .aggregations("groupSex", a -> a
                                .terms(TermsAggregation.of(s -> s
                                        // 分组聚合查询的字符串sex类型是text类型。当使用到 term 查询的时候，由于是精准匹配，所以查询的关键字在es上的类型，必须是keyword而不能是text。
                                        .field("sex.keyword")))
                        ),
                Void.class
        );
        StringTermsAggregate stringTermsAggregate = response.aggregations()
                .get("groupSex")
                .sterms();
        log.info("stringTermsAggregate:{}", stringTermsAggregate.buckets());
    }

}
