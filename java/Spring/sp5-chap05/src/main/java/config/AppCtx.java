package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import spring.ManualBean;
import spring.MemberDao;
import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.NoProduct;
import spring.VersionPrinter;

@Configuration
//@ComponentScan(basePackages = {"spring"},
//	excludeFilters = @Filter(type = FilterType.REGEX, pattern = "spring\\..*Dao"))
//@ComponentScan(basePackages = {"spring"}, 
//	excludeFilters = @Filter(type = FilterType.ASPECTJ, pattern = "spring.*Dao"))
//@ComponentScan(basePackages = {"spring"},
//	excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = {NoProduct.class, ManualBean.class}))
//@ComponentScan(basePackages = {"spring"},
//	excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MemberDao.class))
//@ComponentScan(basePackages = {"spring"},
//	excludeFilters = {
//			@Filter(type = FilterType.ANNOTATION, classes = ManualBean.class),
//			@Filter(type = FilterType.REGEX, pattern = "spring2\\..*")
//	})
@ComponentScan(basePackages = {"spring", "spring2"})
public class AppCtx {
//	@Bean
//	public MemberPrinter memberPrinter() {
//		return new MemberPrinter();
//	}
	
	@Bean
	@Qualifier("printer")
	public MemberPrinter printer() {
		return new MemberPrinter();
	}
	
//	@Bean
//	@Qualifier("mprinter")
//	public MemberPrinter memberPrinter2() {
//		return new MemberPrinter();
//	}
	
	@Bean
	@Qualifier("summaryPrinter")
	public MemberSummaryPrinter memberPrinter2() {
		return new MemberSummaryPrinter();
	}
	
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
}
