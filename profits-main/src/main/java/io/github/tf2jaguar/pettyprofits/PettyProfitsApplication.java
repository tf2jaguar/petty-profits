package io.github.tf2jaguar.pettyprofits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "io.github.tf2jaguar.pettyprofits.dao")
public class PettyProfitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PettyProfitsApplication.class, args);
    }

}
