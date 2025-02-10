package com.booking.controller.model.v0;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultMessage <T> implements Serializable {
    private String message;
    private T data;
    private int code;
    private boolean success;





}
