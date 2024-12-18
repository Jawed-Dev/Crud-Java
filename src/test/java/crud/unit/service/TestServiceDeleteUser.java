package crud.unit.service;
import crud.dto.DtoUser;
import crud.mapper.MapperUser;
import crud.entity.EntityUser;
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
public class TestServiceDeleteUser {
    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private RepositoryUser repositoryUser;

    @Mock
    private ValidationUser validationUser;

    @Mock
    private MapperUser mapperUser;




}
