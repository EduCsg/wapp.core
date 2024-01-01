package com.wapp.core.models;

import lombok.Data;

@Data
public class SeriesModel {

    private String id;
    private String exerciseDoneId;
    private Integer repetitions;
    private Integer weight;
    private Integer series_order;

}
