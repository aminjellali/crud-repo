package controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import exceptions.InstanceNotValidException;
import exceptions.NotFoundException;
import services.GenericCRUDService;

/**
 * The Class GenericController is an abstract that contains and encapsulates the
 * common CRUD endpoints and that will extended by all other CRUD controllers.
 *
 * <p>
 * Note: all child controllers could implement other request mappings.
 *
 * @author amin.jellali
 * @since 1.0.0
 * @param <ENTITY>     the generic type that refers to the entity that will be
 *                     processed by the implemented controller.
 * @param <ENTITY_DTO> the generic type that refers to the entity DTO that will
 *                     be processed by the implemented controller.
 */
public abstract class GenericController<ENTITY, ENTITY_DTO, ENTITY_REPO, ID> {

	/**
	 * is an abstract method used to return the service instance associated to the
	 * child controller to be used in this context.
	 *
	 * @return the generic CRUD service
	 */
	protected abstract GenericCRUDService<ENTITY, ENTITY_DTO, ENTITY_REPO, ID> genericCrudService();

	/**
	 * Implementation of the generic method that is going to persist the provided
	 * entity to the data base.
	 *
	 * <p>
	 * Will call the persist function of the GenericCrudService and process the
	 * return values to return the corresponding response entity.
	 *
	 * @param dto the DTO of the underlying class.
	 * @return the response entity with the associated HttpStatus.
	 */
	@PostMapping(value = "/add-entity")
	public ResponseEntity<?> persistEntityToDataBase(@RequestBody ENTITY_DTO dto) {
		try {
			return new ResponseEntity<ENTITY>(genericCrudService().persistEntity(dto), HttpStatus.OK);
		} catch (InstanceNotValidException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(
					"Erreur Fatal: \nDate: " + new Date().toString() + "\n" + "Message: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Implementation of the generic method that is going to get an entity from the
	 * data base based on the provided id.
	 *
	 * <p>
	 * Will call the getById function of the GenericCrudService and process the
	 * return values to return the corresponding response entity.
	 * 
	 * @param entityId id
	 * @return ResponseEntity
	 */
	@GetMapping(value = "/get-entity-by-id")
	public ResponseEntity<?> getEntityById(@RequestParam ID entityId) {
		try {
			return new ResponseEntity<ENTITY>(genericCrudService().getById(entityId), HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(
					"Erreur Fatal: \nDate: " + new Date().toString() + "\n" + "Message: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Implementation of the generic method that is going to delete an entity from
	 * the data base based on the provided id.
	 *
	 * <p>
	 * Will call the deleteEntity function of the GenericCrudService and process the
	 * return values to return the corresponding response entity.
	 * 
	 * @param entityId id
	 * @return ResponseEntity
	 */
	@DeleteMapping(value = "/delete-entity-by-id")
	public ResponseEntity<?> deleteEntityById(@RequestParam ID entityId) {
		try {
			genericCrudService().deleteEntity(entityId);
			return new ResponseEntity<ENTITY>(HttpStatus.OK);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(
					"Erreur Fatal: \nDate: " + new Date().toString() + "\n" + "Message: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Implementation of the generic method that is going to update an existing
	 * entity in the data base based the provided entity DTO.
	 *
	 * <p>
	 * Will call the update function of the GenericCrudService and process the
	 * return values to return the corresponding response entity.
	 * 
	 * @param entityId id
	 * @param dto dto
	 * @return ResponseEntity
	 */
	@PutMapping(value = "/update-entity")
	public ResponseEntity<?> updateEntity(@RequestParam ID entityId, @RequestBody ENTITY_DTO dto) {
		try {
			return new ResponseEntity<ENTITY>(genericCrudService().updateEntity(dto), HttpStatus.OK);
		} catch (InstanceNotValidException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (NotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(
					"-Erreur Fatal- \nDate: " + new Date().toString() + "\n" + "Message: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
