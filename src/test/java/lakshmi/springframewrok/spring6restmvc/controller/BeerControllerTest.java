package lakshmi.springframewrok.spring6restmvc.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
@SpringBootTest
@Slf4j
class BeerControllerTest {
	@Autowired
	BeerController bc;

	@Test
	void getBeerById() {
		log.debug("Test");
		System.out.println(bc.getBeerById(UUID.randomUUID()));
	}

}
