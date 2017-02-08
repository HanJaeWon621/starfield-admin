package kr.co.starfield.common;

public class StatusCode {
	
	public enum Common implements IStatusCode {
		
		WAITING(0, "대기중"),
		USE(1, "사용중"),
		DELETE(9, "삭제");

		private int code;
		private String message;

	      
		private Common(int code, String message) {
			this.code = code;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
	}
	
}
