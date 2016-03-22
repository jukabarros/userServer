package api.model;

/**
 * Classe que representa um erro
 * @author juccelino.barros
 *
 */
public class Error {
	
	private int code;
	
	private String message;

	public Error(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Erro [code=" + code + ", message=" + message + "]";
	}

}
