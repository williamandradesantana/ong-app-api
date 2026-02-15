package io.github.williamandradesantana.ong_app_api.modules.roles.data.dto;

import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class RoleRequestDTO extends RepresentationModel<RoleRequestDTO> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RoleRequestDTO that = (RoleRequestDTO) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(name);
        return result;
    }
}
