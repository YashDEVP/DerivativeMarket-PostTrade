package com.derivativemarket.posttrade.org;

import com.derivativemarket.posttrade.org.entities.CompanyEntity;
import com.derivativemarket.posttrade.org.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrgApplicationTests {

	@Autowired
	private JwtService jwtService;

	@Test
	void contextLoads() {
		CompanyEntity companyEntity=new CompanyEntity(4L,"rooshad@derivativemarkit.com","password123","ABC.OPS","5040","ABC Capital");
		String token=jwtService.generateToken(companyEntity);

		System.out.println(token);

		Long id=jwtService.getUserIdFromToken(token);

		System.out.println(id);
}

}

