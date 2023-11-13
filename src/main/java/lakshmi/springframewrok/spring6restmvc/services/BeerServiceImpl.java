package lakshmi.springframewrok.spring6restmvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lakshmi.springframewrok.spring6restmvc.model.Beer;
import lakshmi.springframewrok.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
	private Map<UUID,Beer> beerMap;
	public BeerServiceImpl() {
		this.beerMap=new HashMap();
		Beer b1=Beer.builder()
				.id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
		Beer b2=Beer.builder()
				.id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
		Beer b3=Beer.builder()
				.id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
		beerMap.put(b1.getId(), b1);
		beerMap.put(b2.getId(), b2);
		beerMap.put(b3.getId(), b3);
		
	}
	@Override
	public List<Beer> listBeers(){
		return new ArrayList<>(beerMap.values());
	}
	public Beer getBeerById(UUID id) {
		log.debug("FROM SERVICE");
		return beerMap.get(id);
	}
	public Beer postABeer(Beer beer) {
		Beer b=Beer.builder()
				.id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
		beerMap.put(b.getId(), b);
		return b;
	}
	public void updateBeer(UUID id,Beer beer) {
		Beer b=beerMap.get(id);
		b.setBeerName(beer.getBeerName());
		b.setPrice(beer.getPrice());
		b.setUpc(beer.getUpc());
		b.setQuantityOnHand(beer.getQuantityOnHand());
		beerMap.put(b.getId(), b);
	}
	public void deleteBeer(UUID id) {
		beerMap.remove(id);
	}
	public void patchBeer(UUID id, Beer beer) {
		Beer b=beerMap.get(id);
		if(beer.getPrice()!=null)
			b.setPrice(beer.getPrice());
		if(beer.getUpc()!=null)
			b.setUpc(beer.getUpc());
		if(beer.getQuantityOnHand()!=null)
			b.setQuantityOnHand(beer.getQuantityOnHand());
		if(beer.getBeerName()!=null)
			b.setBeerName(beer.getBeerName());
		if(beer.getVersion()!=null)
			b.setVersion(beer.getVersion());
		if(beer.getBeerStyle()!=null)
			b.setBeerStyle(beer.getBeerStyle());
		
	}
	
}
