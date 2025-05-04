package edu.eci.cvds.ECIBienestarGym.embeddables;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalProgressComments {
    private String comment;
    private LocalDate comment_date;
}
