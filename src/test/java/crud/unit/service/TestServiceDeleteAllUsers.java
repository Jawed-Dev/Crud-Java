package crud.unit.service;
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
public class TestServiceDeleteAllUsers {
    private static final String REPOSITORY_ERROR_PREFIX = "Repository Error: ";
    private static final String UNEXPECTED_ERROR_PREFIX = "Error: ";

    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private RepositoryUser repositoryUser;

    @Test
    public void shouldSuccessfully() {
        int numberOfDeletedUsers = 3;
        when(repositoryUser.deleteAll()).thenReturn(numberOfDeletedUsers);

        ServiceResult<Integer> serviceResult = serviceUser.deleteAllUsers();

        assertTrue(serviceResult.isSuccess());
        assertEquals(numberOfDeletedUsers, serviceResult.getData());

        verify(repositoryUser).deleteAll();
    }

    @Test
    public void shouldExceptionRepository() {
        when(repositoryUser.deleteAll())
                .thenThrow(new RepositoryException("Échec de la suppression des utilisateurs."));

        ServiceResult<Integer> serviceResult = serviceUser.deleteAllUsers();

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(REPOSITORY_ERROR_PREFIX));

        verify(repositoryUser).deleteAll();
    }

    @Test
    public void shouldUnexpectedException() {
        when(repositoryUser.deleteAll())
                .thenThrow(new NullPointerException("Erreur inattendue"));

        ServiceResult<Integer> serviceResult = serviceUser.deleteAllUsers();

        assertFalse(serviceResult.isSuccess());
        assertNull(serviceResult.getData());
        assertTrue(serviceResult.getErrorMessage().contains(UNEXPECTED_ERROR_PREFIX));

        verify(repositoryUser).deleteAll();
    }
}