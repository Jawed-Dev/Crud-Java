package crud.unit.service;

import crud.dto.DtoResponse;
import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.param.ParamsUser;
import crud.entity.EntityUser;
import crud.service.ServiceUser;
import crud.validation.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import crud.dao.DaoUser;
import crud.validation.ValidationUser;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceAddUser {

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private DaoUser daoUser;

    @Mock
    private ValidationUser validationUser;

    @Mock
    private MapperUser mapperUser;


    @Test
    public void shouldAddUserValidWhenAllDataAreValid() {
        String firstName = "Jawed";
        String lastName = "Test";
        String email = "valid-email@gmail.com";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(true));
        when(mapperUser.dtoToEntityUser(dtoUser))
                .thenReturn(mockedEntityUser);
        when(daoUser.addUser(mockedEntityUser))
                .thenReturn(mockedEntityUser);

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(mapperUser).dtoToEntityUser(dtoUser);
        verify(daoUser).addUser(Mockito.any(EntityUser.class));

        assertNotNull(entityUser);
        assertEquals(entityUser.getFirstName(), firstName);
        assertEquals(entityUser.getLastName(), lastName);
        assertEquals(entityUser.getEmail(), email);
    }


    @Test
    public void shouldAddUserInvalidByEmail() {
        String firstName = "Jawed";
        String lastName = "Test";
        String email = "invalid-email@";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_INVALID));

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(daoUser, never()).addUser(mockedEntityUser);
        verify(mapperUser, never()).dtoToEntityUser(dtoUser);

        assertNull(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByEmailAlreadyUsed() {
        String firstName = "Jawed";
        String lastName = "Test";
        String email = "valid-email@gmail.com";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_EMAIL_USED));

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(daoUser, never()).addUser(mockedEntityUser);
        verify(mapperUser, never()).dtoToEntityUser(dtoUser);

        assertNull(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByFirstName() {
        String firstName = "Jawed1234";
        String lastName = "Test";
        String email = "valid-email@gmail.com";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FIRSTNAME_INVALID));

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(daoUser, never()).addUser(mockedEntityUser);
        verify(mapperUser, never()).dtoToEntityUser(dtoUser);

        assertNull(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByLastName() {
        String firstName = "Jawed";
        String lastName = "Test1234";
        String email = "valid-email@gmail.com";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_LASTNAME_INVALID));

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(daoUser, never()).addUser(mockedEntityUser);
        verify(mapperUser, never()).dtoToEntityUser(dtoUser);

        assertNull(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByRequestDaoThrowException() {
        String firstName = "Jawed";
        String lastName = "Test";
        String email = "valid-email@gmail.com";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(true));
        when(mapperUser.dtoToEntityUser(dtoUser))
                .thenReturn(mockedEntityUser);
        when(daoUser.addUser(mockedEntityUser))
                .thenReturn(null);

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(mapperUser).dtoToEntityUser(dtoUser);
        verify(daoUser).addUser(mockedEntityUser);

        assertNull(entityUser);
    }

    @Test
    public void shouldAddUserInvalidByAllDataEmpty() {
        String firstName = "";
        String lastName = "";
        String email = "";
        DtoUser dtoUser = new DtoUser(firstName,lastName, email);
        EntityUser mockedEntityUser = new EntityUser(firstName, lastName, email);

        when(validationUser.validateAddUser(dtoUser))
                .thenReturn(new ValidationResult(false, ValidationUser.ERR_FORMS_EMPTY));

        EntityUser entityUser = serviceUser.addUser(dtoUser);

        verify(validationUser).validateAddUser(dtoUser);
        verify(daoUser, never()).addUser(mockedEntityUser);
        verify(mapperUser, never()).dtoToEntityUser(dtoUser);

        assertNull(entityUser);
    }
}
