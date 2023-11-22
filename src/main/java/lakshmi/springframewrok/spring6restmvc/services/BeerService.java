package lakshmi.springframewrok.spring6restmvc.services;

import java.util.List;
import java.util.UUID;

import lakshmi.springframewrok.spring6restmvc.model.Beer;

public interface BeerService {
	public Beer getBeerById(UUID id);

	List<Beer> listBeers();

	public Beer postABeer(Beer beer);

	public void updateBeer(UUID id,Beer beer);

	public void deleteBeer(UUID id);

	public void patchBeer(UUID id, Beer beer);
}
