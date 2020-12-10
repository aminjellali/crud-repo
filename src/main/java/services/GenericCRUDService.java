package services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import annotations.IsIdentifier;
import entitiesfactory.GenericFactory;
import exceptions.ClassHasNoIdFieldException;
import exceptions.InstanceNotValidException;
import exceptions.NotFoundException;

/**
 * The Class GenericCRUDService.
 *
 * <p>
 * an abstract implementation of all CRUD methods that will be extended by other
 * services.
 *
 * <p>
 * Kindly make sure not to add other specific methods to this generic class;
 * Specific implementations could be added to the specific classes.
 *
 * <p>
 * Note that these implementations could be overridden in the service
 * implementation.
 *
 * @param <ENTITY>      the generic type that will be mapped to the Entity
 *                      object.
 * @param <ENTITY_DTO>  the generic type that will be mapped to the Entity DTO
 *                      object.
 * @param <ENTITY_REPO> the generic Repo reference that will be mapped to the
 *                      Entity associated repository.
 * @param <ID>          the generic type that will be mapped to the Class of the
 *                      entities id; String.
 */
public abstract class GenericCRUDService<ENTITY, ENTITY_DTO, ENTITY_REPO, ID> {

	/**
	 * Generic crud repository of the underlying entity.
	 *
	 * @return the crud repository
	 */
	protected abstract CrudRepository<ENTITY, ID> genericCrudRepo();

	/**
	 * Generic factory of the underlying entity.
	 *
	 * @return the generic factory
	 */
	protected abstract GenericFactory<ENTITY, ENTITY_DTO> genericFactory();

	/**
	 * Gets the Entity by id from the data base.
	 *
	 * @param entityId the entity id
	 * @return the by id
	 * @throws NotFoundException the not found exception
	 */
	public ENTITY getById(ID entityId) throws NotFoundException {
		return genericCrudRepo().findById(entityId).get();
	}

	/**
	 * Persist entity to the database.
	 *
	 * @param dto the dto
	 * @return the entity
	 * @throws NotFoundException         the not found exception
	 * @throws InstanceNotValidException the bean not valid exception
	 */
	public ENTITY persistEntity(ENTITY_DTO dto) throws NotFoundException, InstanceNotValidException {
		ENTITY createdInstance = genericFactory().createInstance(dto);
		return genericCrudRepo().save(createdInstance);
	}

	/**
	 * Delete entity form the database.
	 *
	 * @param entityId the entity id
	 * @throws NotFoundException the not found exception
	 */
	public void deleteEntity(ID entityId) throws NotFoundException {
		genericCrudRepo().deleteById(entityId);
	}

	/**
	 * Update entity an exisitng entity in the data base.
	 *
	 * @param dto               the dto
	 * @return the entity
	 * @throws InstanceNotValidException  the bean not valid exception
	 * @throws NotFoundException          the not found exception
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws ClassHasNoIdFieldException
	 */
	public ENTITY updateEntity(ENTITY_DTO dto) throws InstanceNotValidException, NotFoundException,
			IllegalArgumentException, IllegalAccessException, ClassHasNoIdFieldException {
		ID persistedEntityId = fetchEntityDTOId(dto);
		Optional<ENTITY> persisedEntity = genericCrudRepo().findById(persistedEntityId);
		if (persisedEntity.isPresent()) {
			ENTITY createdInstance = genericFactory().createInstance(dto);
			return genericCrudRepo().save(createdInstance);
		} else {
			throw new NotFoundException("entity with id <" + persistedEntityId + "> does not exist.");
		}
	}

	/**
	 * This function the reflection API to extract the id value of the id based on
	 * the annotation @IsIdenttifier
	 * 
	 * @param dtoInstance
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws ClassHasNoIdFieldException
	 */
	@SuppressWarnings("unchecked")
	public ID fetchEntityDTOId(ENTITY_DTO dtoInstance)
			throws IllegalArgumentException, IllegalAccessException, ClassHasNoIdFieldException {
		Class<ENTITY_DTO> dtoInstanceClass = (Class<ENTITY_DTO>) dtoInstance.getClass();
		Object fieldValue = null;
		for (Field field : Arrays.asList(dtoInstanceClass.getDeclaredFields())) {
			if (field.getAnnotation(IsIdentifier.class) instanceof IsIdentifier) {
				field.setAccessible(true);
				fieldValue = field.get(dtoInstance);
				field.setAccessible(false);
				return (ID) fieldValue;
			}
		}
		throw new ClassHasNoIdFieldException(dtoInstance.getClass());
	}

	/**
	 * Gets the all persisted entities by the provided ids.
	 *
	 * @param entitiesIds the entities ids
	 * @return the all persisted by id
	 */
	public List<ENTITY> getAllPersistedById(List<ID> entitiesIds) {
		List<ENTITY> actualList = new ArrayList<ENTITY>();
		genericCrudRepo().findAllById(entitiesIds).forEach(actualList::add);
		return actualList;
	}
}
