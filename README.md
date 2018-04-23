# 说明

## 1.依赖引用

```xml
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.0.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.M9</spring-cloud.version>
		<elasticsearch.version>6.2.2</elasticsearch.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-config</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- transport查询引用，采用TCP端口9300 -->
		<dependency>
			<groupId>org.elasticsearch.client</groupId>
			<artifactId>transport</artifactId>
			<version>${elasticsearch.version}</version>
		</dependency>
		<dependency>
			<groupId>cn.hycun.spring</groupId>
			<artifactId>spring-boot-starter-elasticsearch</artifactId>
			<version>1.0.0</version>
		</dependency>

		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
			<version>2.9.4</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-annotations</artifactId>
			<version>2.9.4</version>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.9.4</version>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.apache.commons</groupId>
			<artifactId>commons-lang3</artifactId>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

	<repositories>
		<repository>
			<id>spring-milestones</id>
			<name>Spring Milestones</name>
			<url>https://repo.spring.io/milestone</url>
			<snapshots>
				<enabled>false</enabled>
			</snapshots>
		</repository>
	</repositories>
```

## 2.Spring属性配置

application.properties

```
spring.extend.elastic.clusterName=elasticsearch
spring.extend.elastic.host=localhost
spring.extend.elastic.port=9300
```

## 3.应用实例

```java
@RestController
public class TestController {
    @Autowired
    private TransportClient client;

    private final static ObjectMapper mapper = new ObjectMapper();
    private AtomicInteger no = new AtomicInteger(1000);

    private final static String TEST_INDEX = "es-docs";
    private final static String TEST_TYPE = "cargo";

    @RequestMapping("/add")
    @ResponseBody
    public IndexResponse add() throws JsonProcessingException {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        User user=new User(String.valueOf(no), RandomStringUtils.randomAlphabetic(6), new Date());

        String userStr = mapper.writeValueAsString(user);
        return client.prepareIndex(TEST_INDEX, TEST_TYPE).setSource(userStr, XContentType.JSON).get();
    }
}
```


