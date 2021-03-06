1. 기존 daoexam을 web 프로젝트로 변경하려면
    <packaging>jar</packaging> 를
    <packaging>war</packaging> 로 변경하고 다음을 추가한다.

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- Servlet JSP JSTL -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.1</version>
            <scope>provided</scope>
        </dependency>
        <!-- jsp는 결과를 출력 JSTL & EL(WAS에 있다.) -->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>


메이븐으로 바로 실행하도록 하려면 다음을 추가한다.

            <!-- maven에서 tomcat을 실행한다.(인텔리제이가 없어도 톰캣만 있다면 실행이 가능함) -->
            <plugin>
                <groupId>org.apache.tomcat.maven</groupId>
                <artifactId>tomcat7-maven-plugin</artifactId>
                <version>2.1</version>
                <configuration>
                    <charset>UTF-8</charset>
                    <uriEncoding>UTF-8</uriEncoding>
                    <port>8080</port>
                    <path>/</path>
                </configuration>
            </plugin>



2. 웹 어플리케이션과 관련된 폴더를 생성
src/main/webapp
src/main/webapp/WEB-INF
src/main/webapp/WEB-INF/views  : jsp가 저장되는 폴더



3. 웹 어플리케이션 설정파일도 작성
: src/main/webapp/WEB-INF/web.xml
: web.xml파일에서는 2개의 설정파일을 읽어들인다.
- ApplicationConfig.class(부모설정) : 비지니스 관련된 Bean
- WebMvcContextConfiguration.class : 웹과 관련된 Bean


4.  ApplicationConfig.class
: 기존 dao, service 사용하는 설정


5.  WebMvcContextConfiguration.class
- 보통 웹을 설정할 때에는 WebMvcConfigurerAdapter 를 상속받아서 만든다.(우리는 4.대 기준, 이것을 사용)
- 하지만 Spring 5, Spring Boot 2에서는 WebMvcConfigurer 인터페이스를 구현해서 작성한다.
- WebMvcConfigurerAdapter나 WebMvcConfigurer가 가지고 있는 메소드를 오버라이딩해서, Web과 관련된 설정을 하게 된다.

===보통 아래와 같이 만든다.===
@Configuration  // Java Config
@EnableWebMvc   // 자동으로 Spring MVC에 대한 설정을 해준다.
@ComponentScan(basePackages = { "examples.daoexam.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
 // 필요한 기능이 있다면 Bean을 생성하거나 메소드를 오버라이딩하여 설정
}


// 정적 데이터를 외부에 어떤 path로 제공할 것이냐하는 설정을 한다.(addResourceHandlers)
// src/main/webapp/img/github.gif 가 있을 경우 아래와 같이 설정하면 된다.
// <img src="/images/github.gif"> 아래와 같이 설정했을 경우의 jsp 태그 설정


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
        registry.addResourceHandler("/images/**").addResourceLocations("/img/").setCachePeriod(31556926);
        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
     }

//    /(root) 요청은 WAS가 가지고 있는 DefaultServlet이 처리한다.
//    Dispatcher서블릿이 Controller 를 호출해서 url을 처리하는데 요청받은 url을 처리할 Controller가 없으면
//    DefaultServlet에게 해당 url을 처리해달라고 요청한다.
 @Override
 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
     configurer.enable();
 }
/*
이제까지는 오버라이딩해서 설정을 했는데 이번에는 Bean을 설정해준다.(ViewResolver 설정)
ViewResolver를 설정.(InternalResourceViewResolver를 리턴해준다.)
InternalResourceViewResolver 는 ViewResolver중에 한 가지인데 어떤 JSP를 사용할지 전략을 세워준다.(jsp를 보여주기 위한 resolver)
ex) 사용자가 Controller에서 view name(ex : "list")을 리턴하면, Dispatcher서블릿은
ViewResolver에게 해당 view name을 통하여 어떤 View를 사용할지 물어보게 되어 있다.
"/WEB-INF/views/" + view name + ".jsp" 라는 경로의 jsp를 사용하도록 하겠다고 결정을 해준다. ("프리픽스" + viewname + "서브픽스") -> 다른 폴더에 설정을 할 수도 있다.
이렇게 jsp가 결정이 되면 해당 정보를 이용하여 내부적으로 JstlView라는 객체를 생성하여 jsp를 보여주게 된다.
*/
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

----
이후 WebMvcContextConfiguration 설명, index.jsp 생성, BoardController 생성,