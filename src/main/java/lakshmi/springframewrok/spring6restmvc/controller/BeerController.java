package lakshmi.springframewrok.spring6restmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lakshmi.springframewrok.spring6restmvc.model.Beer;
import lakshmi.springframewrok.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@AllArgsConstructor
@Controller
@Slf4j
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	private final BeerService beerService;
	@PatchMapping("{beerId}")
	public ResponseEntity patchBeer(@RequestBody Beer beer,@PathVariable("beerId") UUID id) {
		beerService.patchBeer(id, beer);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@DeleteMapping("{beerId}")
	public ResponseEntity deletebeer(@PathVariable("beerId") UUID id ) {
		beerService.deleteBeer(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PutMapping("{beerId}")
	public ResponseEntity updateBeer(@PathVariable("beerId") UUID id,@RequestBody Beer beer) {
		beerService.updateBeer(id,beer); 
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	@PostMapping
	public ResponseEntity postCall(@RequestBody Beer beer) {
		Beer b=beerService.postABeer(beer);
		HttpHeaders h=new HttpHeaders();
		h.add("LOCATION", "/api/v1/beer/"+beer.getId().toString());
		return new ResponseEntity(h,HttpStatus.CREATED);
		//return b and return type Beer could also be done
	}
	@RequestMapping(method=RequestMethod.GET)
	public List<Beer>getListOfBeers() {
		return beerService.listBeers();
	}
	@RequestMapping(method=RequestMethod.GET, value="{beerId}")
	public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
		log.debug("Beer Controller getByid");
		return beerService.getBeerById(beerId);
		
	}
}
