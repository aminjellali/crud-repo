package main;

import org.springframework.data.repository.CrudRepository;

import entitiesfactory.GenericFactory;
import services.GenericCRUDService;

public class CrudService extends GenericCRUDService<Person, Person, Person, Integer> {

	@Override
	protected CrudRepository<Person, Integer> genericCrudRepo() {
		return null;
	}

	@Override
	protected GenericFactory<Person, Person> genericFactory() {
		return null;
	}

}