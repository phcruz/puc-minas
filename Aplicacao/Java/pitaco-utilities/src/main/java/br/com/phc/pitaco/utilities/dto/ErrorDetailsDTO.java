package br.com.phc.pitaco.utilities.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ErrorDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uniqueId;
	private String informationCode;
	private Integer statusCode;
	private String message;
	private long time;
	private Map<String, String> args = new HashMap<>();

}
