package crud.unit.service;
import java.util.List;
import crud.entity.EntityUser;
import crud.repository.RepositoryException;
import crud.service.ServiceResult;
import crud.service.ServiceUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import crud.repository.RepositoryUser;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceGetAllUsers {
    private static final String FIRST_NAME_VALID = "Jawed";
    private static final String LAST_NAME_VALID = "Test";
    private static final String EMAIL_VALID = "valid-email@gmail.com";

    private static final String REPOSITORY_ERROR_PREFIX = "Repository Error: ";
    private static final String UNEXPECTED_ERROR_PREFIX = "Error: ";

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private RepositoryUser repositoryUser;

    @Test
    public void shouldSuccessfully() {
        List<EntityUser> users = List.of(
            new EntityUser(FIRST_NAME_VALID, LAST_NAME_VALID, EMAIL_VALID)
        );

        when(repositoryUser.findAll()).thenReturn(users);

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getAllUsers();

        assertTrue(serviceResult.isSuccess());
        assertEquals(users, serviceResult.getData());

        verify(repositoryUser).findAll();
    }

    @Test
    public void shouldExceptionRepository() {
        when(repositoryUser.findAll())
                .thenThrow(new RepositoryException("Échec de la récupération des utilisateurs."));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getAllUsers();

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(REPOSITORY_ERROR_PREFIX));

        verify(repositoryUser).findAll();
    }

    @Test
    public void shouldUnexpectedException() {
        when(repositoryUser.findAll())
                .thenThrow(new NullPointerException("Erreur inattendue"));

        ServiceResult<List<EntityUser>> serviceResult = serviceUser.getAllUsers();

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(UNEXPECTED_ERROR_PREFIX));

        verify(repositoryUser).findAll();
    }
}