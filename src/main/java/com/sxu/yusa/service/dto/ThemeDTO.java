package com.sxu.yusa.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Theme entity.
 */
public class ThemeDTO implements Serializable {

    private Long id;

    private String themeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ThemeDTO themeDTO = (ThemeDTO) o;
        if(themeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ThemeDTO{" +
            "id=" + getId() +
            ", themeName='" + getThemeName() + "'" +
            "}";
    }
}
