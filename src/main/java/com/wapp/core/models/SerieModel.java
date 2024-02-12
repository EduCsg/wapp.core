package com.wapp.core.models;

import lombok.Data;

@Data
public class SerieModel {

    private String id;
    private String exerciseDoneId;
    private Integer repetitions;
    private Integer weight;
    private Integer serieOrder;
    private String description;

}
