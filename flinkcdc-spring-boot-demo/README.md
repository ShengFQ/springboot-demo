# flinkcdc-spring-boot-demo

[WIKI] https://github.com/apache/flink-cdc/wiki/Flink-CDC-Blog-(ZH)#flink-cdc-%E6%96%87%E7%AB%A0%E8%B5%84%E6%96%99

## flinkcdc定位
Flink CDC 能够连接更多的数据源，包括数据库、消息队列、数据湖、文件格式、SaaS 服务等。同时，我们希望 Flink CDC 不仅仅连接数据源，更希望它是端到端的实时数据集成的解决方案和工具，去打通从数据源、数据管道到数据目标的全套端到端的解决方案

## flinkcdc的优势
在cdc(数据同步)场景中,以往的方案门槛高,稳定性差,性能低.

1.降低了数据集成的门槛
2.全/增量一体化(降低了业务使用数据的门槛)
3.时效性高
4.schema变更
5.稳定性好
6.数据湖无缝集成.

### flinkcdc在企业中的发展
1.替换离线数据集成的方案
2.集成数据湖仓方案
3.将原有的T+1结算提升到秒级,实时性

springboot集成flink-cdc的demo
日期:2024-03-09
功能特性:
1.验证cdc connector的读写性能
    读:是否无锁
2.引擎的能力
    动态加表
3.自动缩容
    会分为历史数据的全量同步阶段和增量 CDC 数据的同步阶段,会自动地把并发度调低，回收空闲的资源，降低成本
4.支持指定位点能力
    用户可以指定 Binlog 的 offset 位点，或者指定开始读取 Binlog 的时间戳，而无需全量加增量的方式读取。
5.At-Least-Once 读取模式
    At-Least-Once 模式可以提供比 Excel-Once 模式更快的读取效率
