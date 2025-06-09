package core.pojo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Core implements Serializable {
	private static final long serialVersionUID = 1457755989409740329L;
	private boolean successful;
	private String message;
	private List<?> data;
}