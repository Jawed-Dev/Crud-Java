package crud.unit.service;

import crud.entity.EntityUser;
import crud.service.ServiceUser;
import crud.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import crud.dao.DaoUser;
import crud.validation.ValidationUser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddUser {

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private DaoUser daoUser;

    @Mock
    private ValidationUser validationUser;


    @Test
    public void shouldAddUserValidWhenAllDataAreValid() {
        EntityUser entityUser = new EntityUser("Jawed", "Test", "valid-email@gmail.com");
        when(validationUser.validateAddUser(entityUser)).thenReturn(new ValidationResult(true));
        when(daoUser.addUser(entityUser)).thenReturn(true);
        boolean result = serviceUser.addUser(entityUser);
        assertTrue(result);
        verify(validationUser).validateAddUser(entityUser);
        verify(daoUser).addUser(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByEmail() {
        EntityUser entityUser = new EntityUser("Jawed", "Test", "invalid-email@");
        when(validationUser.validateAddUser(entityUser)).thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_INVALID));
        boolean result = serviceUser.addUser(entityUser);
        assertFalse(result);
        verify(validationUser).validateAddUser(entityUser);
        verify(daoUser, never()).addUser(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByFirstName() {
        EntityUser entityUser = new EntityUser("Jawed1234", "Test", "valid-email@gmail.com");
        when(validationUser.validateAddUser(entityUser)).thenReturn(new ValidationResult(false, ValidationUser.ERR_FIRSTNAME_INVALID));
        boolean result = serviceUser.addUser(entityUser);
        assertFalse(result);
        verify(validationUser).validateAddUser(entityUser);
        verify(daoUser, never()).addUser(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByLastName() {
        EntityUser entityUser = new EntityUser("Jawed", "Test1234", "valid-email@gmail.com");
        when(validationUser.validateAddUser(entityUser)).thenReturn(new ValidationResult(false, ValidationUser.ERR_LASTNAME_INVALID));
        boolean result = serviceUser.addUser(entityUser);
        assertFalse(result);
        verify(validationUser).validateAddUser(entityUser);
        verify(daoUser, never()).addUser(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByRequestDaoThrowException() {
        EntityUser entityUser = new EntityUser("Jawed", "Test", "valid-email@gmail.com");
        when(validationUser.validateAddUser(entityUser)).thenReturn(new ValidationResult(true));
        when(daoUser.addUser(entityUser)).thenThrow(new RuntimeException("Error"));
        boolean result = serviceUser.addUser(entityUser);
        assertFalse(result);
        verify(validationUser).validateAddUser(entityUser);
        verify(daoUser).addUser(entityUser);
    }
}
