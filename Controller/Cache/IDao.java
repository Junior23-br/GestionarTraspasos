package Controller.Cache;

import java.util.List;

public interface IDao <ClaseEntidad, TipoId> {

    public List<ClaseEntidad> obtenerTodos();
}

