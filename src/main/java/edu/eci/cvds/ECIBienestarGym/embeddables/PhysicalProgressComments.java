package edu.eci.cvds.ECIBienestarGym.embeddables;


import lombok.Data;

import java.time.LocalDate;


@Data
public class PhysicalProgressComments {
    private String comment;
    private LocalDate comment_date;
}
