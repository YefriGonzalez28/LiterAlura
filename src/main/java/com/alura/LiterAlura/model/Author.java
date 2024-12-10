package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
        @JsonAlias("name")
        private String name;

        @JsonAlias("birth_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate fechaNacimiento;

        @JsonAlias("death_date")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate fechaFallecimiento;

        // Constructor sin argumentos necesario para JPA
        public Author() {
        }

        public Author(String name, LocalDate fechaNacimiento, LocalDate fechaFallecimiento) {
                this.name = name;
                this.fechaNacimiento = fechaNacimiento;
                this.fechaFallecimiento = fechaFallecimiento;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public LocalDate getFechaNacimiento() {
                return fechaNacimiento;
        }

        public void setFechaNacimiento(LocalDate fechaNacimiento) {
                this.fechaNacimiento = fechaNacimiento;
        }

        public LocalDate getFechaFallecimiento() {
                return fechaFallecimiento;
        }

        public void setFechaFallecimiento(LocalDate fechaFallecimiento) {
                this.fechaFallecimiento = fechaFallecimiento;
        }
}
