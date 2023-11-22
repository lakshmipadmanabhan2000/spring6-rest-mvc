package lakshmi.springframewrok.spring6restmvc.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import lakshmi.springframewrok.spring6restmvc.model.Beer;
import lakshmi.springframewrok.spring6restmvc.services.BeerService;
import lakshmi.springframewrok.spring6restmvc.services.BeerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import static org.mockito.Mockito.verify;
//@SpringBootTest
@Slf4j
@WebMvcTest(BeerController.class)
class BeerControllerTest {
	//@Autowired
	//BeerController bc;
	@Autowired
	ObjectMapper om;
	@Autowired
	MockMvc mvc;
	@MockBean
	BeerService bs;
	//mockito should return value
	BeerServiceImpl bsimpl;
	@Captor
	ArgumentCaptor<UUID> ac;
	@Captor
	ArgumentCaptor<Beer> bc;
	@BeforeEach
	void setUp() {
		bsimpl=new BeerServiceImpl();
	}
	@Test
	void patchBeer() throws Exception{
		Beer b=bsimpl.listBeers().get(0);
		Map<String,Object> bMap=new HashMap<>();
		bMap.put("beerName", "Test New Beer");
		System.out.print(bMap);
		mvc.perform(patch(BeerController.BEER_PATH_ID,b.getId())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(bMap)))
		.andExpect(status().isNoContent());
		verify(bs).patchBeer(ac.capture(), bc.capture());
		assertThat(b.getId()).isEqualTo(ac.getValue());
		assertThat(bMap.get("beerName")).isEqualTo(bc.getValue().getBeerName());
		
	}
	@Test
	void update()throws Exception
	{
		Beer b = bsimpl.listBeers().get(0);
		mvc.perform(put(BeerController.BEER_PATH_ID,b.getId()).accept(MediaType.APPLICATION_JSON)
		.contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(b)))
		.andExpect(status().isNoContent());
		verify(bs).updateBeer(any(UUID.class),any(Beer.class));
	}
	@Test
	void deleteBeer()throws Exception{
		Beer b=bsimpl.listBeers().get(0);
		System.out.print(b);
		mvc.perform(delete(BeerController.BEER_PATH_ID,b.getId()).accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isNoContent());
		
		verify(bs).deleteBeer(ac.capture());
		assertThat(b.getId()).isEqualTo(ac.getValue());
	}
	@Test
	void createBeer() throws Exception {
		Beer b=bsimpl.listBeers().get(0);
		b.setVersion(null);
		//b.setId(null);
		given(bs.postABeer(any(Beer.class))).willReturn(bsimpl.listBeers().get(1));
		
		mvc.perform(
				post(BeerController.BEER_PATH)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(om.writeValueAsString(b)))
			.andExpect(status().isCreated())
			.andExpect(header().exists("Location"));
	}
	@Test
	void listBeers() throws Exception {
		given(bs.listBeers()).willReturn(bsimpl.listBeers());
		mvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.length()",is(3)));
	}
	@Test
	void getBeerById() throws Exception {
		//log.debug("Test");
		//System.out.println(bc.getBeerById(UUID.randomUUID()));
		
		Beer testBeer= bsimpl.listBeers().get(0); //get first beer
		//mockito returns first beer for any uuid
		given(bs.getBeerById(testBeer.getId())).willReturn(testBeer);
		
		mvc.perform(get(BeerController.BEER_PATH_ID,testBeer.getId())
		.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id",is(testBeer.getId().toString())))
		.andExpect(jsonPath("$.beerName",is(testBeer.getBeerName())));
	}

}
