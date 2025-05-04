package edu.eci.cvds.ECIBienestarGym.embeddables;


import edu.eci.cvds.ECIBienestarGym.enums.ExerciseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    @Field("name")
    private String name;
    private int repetitions;
    private int sets;
    private int duration;
    private ExerciseType type;


}
