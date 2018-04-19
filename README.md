# springBoot-bo

spring-boot前后端分离 后台管理系统
----


基于spring-boot开发的后台管理系统，实现以下功能：<br>
1.实现前后端分离，前端数据请求全部通过ajax等方式异步请求数据；<br>
2.后台view层基于restController实现restful借口，整合swagger2生成restful api；<br>
3.持久层基于 spring data jpa，关系型数据库采用mysql<br>
4.非关系型数据采用elasticSearch存储和查询数据，整合ELK，使用org.elasticsearch等第三方类包可直接向elastic插入数据，也可以对elastic中的数据进行分析和清洗后再插入；kibana先绘制视图和仪表盘，通过iframe内嵌到管理系统轻松绘制报表<br>
5.前端基于angularJS+bootstrap开发，目前功能比较简单，后续将会陆续完善<br>
6.实现基于junit测试用例测试restful接口<br>




