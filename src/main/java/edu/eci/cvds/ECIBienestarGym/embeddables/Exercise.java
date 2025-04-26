package edu.eci.cvds.ECIBienestarGym.embeddables;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
public class Exercise {
    @Field("name")
    private String name;
    private int repetitions;
    private int sets;

}
