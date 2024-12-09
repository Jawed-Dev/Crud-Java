package crud.unit.service;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceDeleteUser {
    @InjectMocks
    private ServiceUser serviceUser;

    @Mock
    private DaoUser daoUser;

    @Mock
    private ValidationUser validationUser;


}
