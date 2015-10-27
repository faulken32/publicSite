package com.infinity.config;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.infinity"})
@Import({SecurityConfig.class})
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "com.infinity.repository")

public class MvcConfiguration extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(MvcConfiguration.class);

    private static final List<String> DEFAULT_TILES_DEFINITIONS = new LinkedList<>();

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.auto}")
    private String auto;

    static {

        DEFAULT_TILES_DEFINITIONS.add("/WEB-INF/tiles.xml");
        DEFAULT_TILES_DEFINITIONS.add("/WEB-INF/view.xml");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

//    @Bean(name = "multipartResolver")
//    public StandardServletMultipartResolver multipartResolver() {
//
//        return new StandardServletMultipartResolver();
//    }
//    
    @Bean
    public UrlBasedViewResolver viewResolver() {

        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
//        urlBasedViewResolver.setCache(false);
        urlBasedViewResolver.setViewClass(TilesView.class);

        return urlBasedViewResolver;
    }

    @Bean(name = "dataSource")
    public BasicDataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", this.auto);
        properties.setProperty("hibernate.show_sql", "true");
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPersistenceUnitName("PU");

        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.infinity");
        factory.setDataSource(dataSource());
        factory.setJpaProperties(properties);
        factory.afterPropertiesSet();

        return factory;

    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) throws SQLException {

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(emf);

        return jpaTransactionManager;

    }

//    @Bean
//    public AuthSuccesHandler authSuccesHandler() {
//
//        return new AuthSuccesHandler();
//    }

//    /**
//     *
//     * @return
//     */
//    @Bean
//    public ResourceBundleViewResolver resourceBundleViewResolver(){
//    
//        ResourceBundleViewResolver resourceBundleViewResolver = new ResourceBundleViewResolver();
//        resourceBundleViewResolver.setBasename("views");
//        return resourceBundleViewResolver; 
//    }
    @Bean
    public TilesConfigurer tilesConfigurer() {

        TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer.setDefinitions(DEFAULT_TILES_DEFINITIONS.toArray(new String[DEFAULT_TILES_DEFINITIONS.size()]));

        return tilesConfigurer;
    }
}
