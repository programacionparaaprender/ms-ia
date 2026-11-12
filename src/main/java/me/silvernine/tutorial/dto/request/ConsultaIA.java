package me.silvernine.tutorial.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ConsultaIA {

    @NotBlank
    private String pregunta = "que es la vida";

    @Min(1)
    @JsonProperty("palabras_maximas")
    private int palabrasMaximas = 100;

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public int getPalabrasMaximas() {
        return palabrasMaximas;
    }

    public void setPalabrasMaximas(int palabrasMaximas) {
        this.palabrasMaximas = palabrasMaximas;
    }
}
