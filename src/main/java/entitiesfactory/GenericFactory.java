package entitiesfactory;

import exceptions.InstanceNotValidException;

/**
 * A factory for creating Generic objects.
 *
 * @author amin.jellali
 * @param <ENTITY> the generic type
 * @param <ENTIY_DTO> the generic type
 */
public abstract class GenericFactory<ENTITY, ENTIY_DTO> {

  /**
   * Creates a new Generic object.
   *
   * <p>Note that when implementing this function make sure to call the DTO validation method and
   * throw BeanNotValidException in case the dto is not valid.
   *
   * @param dto the dto
   * @return the valid entity
   * @throws InstanceNotValidException the bean not valid exception
   */
  public abstract ENTITY createInstance(ENTIY_DTO dto) throws InstanceNotValidException;
}
