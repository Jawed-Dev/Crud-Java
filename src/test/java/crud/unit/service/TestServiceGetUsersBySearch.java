package crud.unit.service;
import java.util.List;
import crud.dto.DtoUser;
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
public class TestServiceGetUsersBySearch {
    private static final String FIRST_NAME_VALID = "Jawed";
    private static final String LAST_NAME_VALID = "Test";
    private static final String EMAIL_VALID = "valid-email@gmail.com";

    private static final String FIRST_NAME_INVALID = "Jawed1234";
    private static final String LAST_NAME_INVALID = "Test1234";
    private static final String EMAIL_INVALID = "invalid-email@";

    private static final String REPOSITORY_ERROR_PREFIX = "Repository Error: ";
    private static final String UNEXPECTED_ERROR_PREFIX = "Error: ";

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private RepositoryUser repositoryUser;

    @Mock
    private ValidationUser validationUser;

    @Test
    public void shouldSuccessfully() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);
        List<EntityUser> users = List.of(
                new EntityUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID)
        );

        when(validationUser.validateGetUsersBySearch(dtoUser)).thenReturn(new ValidationResult(true));
        when(repositoryUser.findByFilters(dtoUser)).thenReturn(users);

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertTrue(serviceResult.isSuccess());
        assertEquals(users, serviceResult.getData());

        verify(repositoryUser).findByFilters(dtoUser);
    }

    @Test
    public void shouldInvalidByAllDataEmpty() {
        DtoUser dtoUser = new DtoUser(null, null, null);

        when(validationUser.validateGetUsersBySearch(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FORMS_EMPTY));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_FORMS_EMPTY));

        verify(repositoryUser, never()).findByFilters(any());
    }

    @Test
    public void shouldInvalidByEmailInvalid() {
        DtoUser dtoUser = new DtoUser(null, null, EMAIL_INVALID);

        when(validationUser.validateGetUsersBySearch(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_INVALID));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_EMAIL_INVALID));

        verify(repositoryUser, never()).findByFilters(any());
    }

    @Test
    public void shouldInvalidByFirstNameInvalid() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_INVALID, null, null);

        when(validationUser.validateGetUsersBySearch(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FIRSTNAME_INVALID));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_FIRSTNAME_INVALID));

        verify(repositoryUser, never()).findByFilters(any());
    }

    @Test
    public void shouldInvalidByLastNameInvalid() {
        DtoUser dtoUser = new DtoUser(null, LAST_NAME_INVALID, null);

        when(validationUser.validateGetUsersBySearch(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_LASTNAME_INVALID));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(ValidationUser.ERR_LASTNAME_INVALID));

        verify(repositoryUser, never()).findByFilters(any());
    }

    @Test
    public void shouldExceptionRepository() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);

        when(validationUser.validateGetUsersBySearch(dtoUser)).thenReturn(new ValidationResult(true));
        when(repositoryUser.findByFilters(dtoUser))
                .thenThrow(new RepositoryException("Échec de la recherche des utilisateurs."));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(REPOSITORY_ERROR_PREFIX));

        verify(repositoryUser).findByFilters(dtoUser);
    }

    @Test
    public void shouldUnexpectedException() {
        DtoUser dtoUser = new DtoUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID);

        when(validationUser.validateGetUsersBySearch(dtoUser))
                .thenThrow(new NullPointerException("Erreur inattendue"));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getUsersBySearch(dtoUser);

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(UNEXPECTED_ERROR_PREFIX));

        verify(repositoryUser, never()).findByFilters(any());
    }
}