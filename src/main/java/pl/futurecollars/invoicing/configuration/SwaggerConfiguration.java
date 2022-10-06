package pl.futurecollars.invoicing.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("pl.futurecollars"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(
            new ApiInfoBuilder()
                .description("Invoice management application.")
                .license("No license available - private!")
                .title("Private Invoicing")
                .contact(
                    new Contact(
                        "Kacper Kropidlowski",
                        "https://github.com/KacperKropidlowski",
                        "kappip@tlen.pl"
                    )
                )
                .build()
        )
        .tags(
            new Tag("invoice-controller", "Controller used to list / add / update / delete invoices."),
            new Tag("tax-calculator-controller", "Controller used to calculate taxes.")
        );
  }
}
