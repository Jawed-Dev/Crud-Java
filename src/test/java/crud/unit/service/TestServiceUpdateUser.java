package crud.unit.service;

import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.entity.EntityUser;
import crud.repository.RepositoryException;
import crud.service.ServiceResult;
import crud.service.ServiceUser;
import crud.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import crud.repository.RepositoryUser;
import crud.validation.ValidationUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceUpdateUser {
    private static final String FIRST_NAME_VALID = "Jawed";
    private static final String LAST_NAME_VALID = "Test";
    private static final String EMAIL_VALID = "valid-email@gmail.com";
    private static final String NEW_EMAIL_VALID = "new-email@gmail.com";

    private static final String REPOSITORY_ERROR_PREFIX = "Repository Error: ";
    private static final String UNEXPECTED_ERROR_PREFIX = "Error: ";

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private RepositoryUser repositoryUser;

    @Mock
    private ValidationUser validationUser;

    @Mock
    private MapperUser mapperUser;

    @Test
    public void shouldSuccessfully() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);
        EntityUser entityUser = new EntityUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        int idUser = 1;

        when(validationUser.validateUpdateUser(dtoUser)).thenReturn(new ValidationResult(true));
        when(mapperUser.dtoToEntityUser(dtoUser)).thenReturn(entityUser);
        when(repositoryUser.getIdByEmail(EMAIL_VALID)).thenReturn(idUser);
        when(repositoryUser.update(entityUser)).thenReturn(entityUser);

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertTrue(serviceResult.isSuccess());
        assertEquals(entityUser, serviceResult.getData());

        verify(repositoryUser).update(entityUser);
    }

    @Test
    public void shouldInvalidByEmailNotUsed() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_NOT_USED));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_EMAIL_NOT_USED));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldInvalidByNewEmailAlreadyUsed() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_USED));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_EMAIL_USED));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldInvalidByAllDataEmpty() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FORMS_EMPTY));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_FORMS_EMPTY));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldInvalidByEmailInvalid() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_INVALID));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_EMAIL_INVALID));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldInvalidByFirstNameInvalid() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FIRSTNAME_INVALID));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_FIRSTNAME_INVALID));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldInvalidByLastNameInvalid() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        dtoUser.setNewEmail(NEW_EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_LASTNAME_INVALID));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_LASTNAME_INVALID));

        verify(repositoryUser, never()).update(any());
    }

    @Test
    public void shouldExceptionRepository() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        EntityUser entityUser = new EntityUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        int idUser = 1;

        when(validationUser.validateUpdateUser(dtoUser)).thenReturn(new ValidationResult(true));
        when(mapperUser.dtoToEntityUser(dtoUser)).thenReturn(entityUser);
        when(repositoryUser.getIdByEmail(EMAIL_VALID)).thenReturn(idUser);
        when(repositoryUser.update(entityUser))
                .thenThrow(new RepositoryException("Échec de la mise à jour de l'utilisateur."));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(REPOSITORY_ERROR_PREFIX));

        verify(repositoryUser).update(entityUser);
    }

    @Test
    public void shouldUnexpectedException() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);

        when(validationUser.validateUpdateUser(dtoUser))
                .thenThrow(new NullPointerException("Erreur inattendue"));

        ServiceResult<EntityUser> serviceResult = serviceUser.updateUser(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(UNEXPECTED_ERROR_PREFIX));

        verify(repositoryUser, never()).update(any());
    }
}