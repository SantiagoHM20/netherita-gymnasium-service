package edu.eci.cvds.ECIBienestarGym.embeddables;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalProgressComments {
    private String comment;
    private LocalDate comment_date;
}
