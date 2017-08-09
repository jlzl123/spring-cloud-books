package cn.lsh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorData {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		List<String> warnings=new ArrayList<String>();
		File configFile=new File("src/main/resources/generator/generatorConfig.xml");
		ConfigurationParser parser=new ConfigurationParser(warnings);
		Configuration config=parser.parseConfiguration(configFile);
		DefaultShellCallback callback=new DefaultShellCallback(true);
		MyBatisGenerator generator=new MyBatisGenerator(config, callback, warnings);
		generator.generate(null);
	}

}
