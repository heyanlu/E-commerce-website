package com.qiuzhitech.onlineshopping_06.db;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GeneratorSqlmap {
    public void generator() throws Exception {
        List<String> warnings = new ArrayList<>();
        // Specify the reverse engineering configuration file
        File configFile = new File("src/main/resources/generatorConfig.xml");

        // Parse the configuration file
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        // Initialize MyBatis Generator
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);

        // Generate the code
        myBatisGenerator.generate(null);

        // Output warnings, if any
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }

    public static void main(String[] args) {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


