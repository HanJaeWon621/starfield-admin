package kr.co.starfield.common;

public class ErrorCode {
	
	public static class Severity {
		/**
		 *  0 : 단순 로그(사용하지 않음)
		 */
		public final static int LEVEL_0 = 0;
		/**
		 * 1 : 서비스에 지장 없으나 프로그램오류 (복구가능한 단순 조회 등의 실패)
		 */
		public final static int LEVEL_1 = 1;
		/**
		 * 2 : 서비스의 일시적인 장애 (트래픽 과부하로 인한 인터페이스 조회 실패 등)
		 */
		public final static int LEVEL_2 = 2;
		/**
		 * 3 : 서비스가 중단 될 수 있는 장애 (지속적인 커넥션 실패 등)
		 */
		public final static int LEVEL_3 = 3;
		/**
		 * 4 : 주요한 시스템 인프라 장애 (DB접속오류, 서버중단, 주요 인터페이스 연동실패 등)
		 */
		public final static int LEVEL_4 = 4;
		
	}
	
	public enum Common implements IErrorCode{
		
		CATEGORY_NOT_FOUND_DATA(11001, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		CATEGORY_TO_JSON_FOR_SET_REDIS_ERROR(11002, "카테고리 데이터 변환 중 오류 발생(Category Object->Json)", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		NOT_FOUND_CODE(11701, "없는 코드 입니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		MUST_REQUIRE_PARAMETER_EMPTY(11003, "필수 파라미터 정보 누락", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		PARSER_DATE_PARSE_EXCEPTION(11996, "날짜 포맷이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		Q_PARSER_NUMBER_FORMAT_EXCEPTION(11997, "숫자 포맷이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		Q_PARSER_DATE_PARSE_EXCEPTION(11998, "날짜 포맷이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		UNKNOWN(11999, "알수 없는 오류입니다.", HttpStatusCode.HTTP_UNKNOWN, Severity.LEVEL_1);

		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Common(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum File implements IErrorCode {
		
		MIME_TYPE_NOT_MATCH_BY_IMG(12001, "업로드 파일의 content(mime) type이 image가 아닙니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		NOT_FOUND_UPLOAD_TARGET(12002, "업로드 대상 파일을 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		UN_KNOWN_EXCEPTION_WHEN_IMG_UPLOAD(12003, "이미지 업로드 중 알 수 없는 에러 발생", HttpStatusCode.HTTP_UNKNOWN, Severity.LEVEL_1),
		UN_KNOWN_EXCEPTION_WHEN_TRUN_IMG_FILE_RESOURCE(12004, "이미지 파일 자원 반납 중 알 수 없는 에러 발생", HttpStatusCode.HTTP_UNKNOWN, Severity.LEVEL_1),
		
		MIME_TYPE_NOT_MATCH_BY_EXCEL(12005, "업로드 파일의 content(mime) type이 exceldl 아닙니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		LOCALE_SYNC_FAIL_REDIS(12006, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private File(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Auth implements IErrorCode{
		
		AUTH_FAIL(12001, "인증에 실패하였습니다.", HttpStatusCode.HTTP_UNAUTHORIZED, Severity.LEVEL_1),
		ADM_LOGIN_PARAM_EMPTY(12002, "관리자 아이디 또는 비밀번호 정보가 누락되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		ADM_CHANGE_PASSWORD_PARAM_EMPTY(12003, "기존 비밀번호 또는 새로운 비밀번호 정보가 누락되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		ADM_SESSION_UNUSUAL(12004, "관리자 세션이 비정상 입니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		ADM_CHANGE_INFO_COMPULSORY_DATA_EMPTY(12005, "관리자 변경 필수값 정보가 누락되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		ADM_ID_EMPTY(12006, "관리자 아이디 정보가 누락되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		PERMISSION_DENIED(12007, "권한이 없어 허가 거부되었습니다.", HttpStatusCode.HTTP_UNAUTHORIZED, Severity.LEVEL_0),
		ACCESS_IP_PERMISSION_DENIED(12008, "접근권한이 없는 IP입니다.", HttpStatusCode.HTTP_UNAUTHORIZED, Severity.LEVEL_0);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Auth(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Operation implements IErrorCode{
		OPERATION_NOT_FOUND_DATA(21101, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		OPERATION_SYNC_DATA_PARSING_FAILED(21102, "데이터 parsing에 실패하였습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		OPERATION_SYNC_FAIL_REDIS(21103, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		HOLIDAY_NOT_FOUND_DATA(21111, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		HOLIDAY_SYNC_FAIL_REDIS(22112, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		STARFIELD_HOLIDAY_NOT_FOUND_DATA(21121, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		STARFIELD_HOLIDAY_SYNC_FAIL_REDIS(22122, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		TENANT_HOLIDAY_NOT_FOUND_DATA(21131, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		TENANT_HOLIDAY_SYNC_FAIL_REDIS(22132, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		APPVER_SELECTED_SIZE_ERROR(22143, "앱버전이 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Operation(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}

	public enum Contents implements IErrorCode{
		MAIN_BRAND_NOT_FOUND_DATA(21201, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		RECOMMEND_TAG_NOT_FOUND_DATA(21211, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		RECOMMEND_KEYWORD_NOT_FOUND_DATA(21221, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		QNA_NOT_FOUND_DATA(21231, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		BANNER_GROUP_NOT_FOUND_DATA(21241, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		BANNER_NOT_FOUND_DATA(21242, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		BANNER_GROUP_SELECTED_SIZE_ERROR(21243, "배너 그룹이 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		BANNER_SELECTED_SIZE_ERROR(21244, "배너가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		BANNER_SYNC_FAIL_REDIS(21245, "배너 동기화 실패", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		WHATNEW_GROUP_NOT_FOUND_DATA(21246, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		WHATNEW_GROUP_SELECTED_SIZE_ERROR(21247, "왓츠뉴 그룹이 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		WHATNEW_NOT_FOUND_DATA(21248, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		WHATNEW_SELECTED_SIZE_ERROR(21249, "왓츠뉴가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Contents(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Tenant implements IErrorCode{
		TENANT_NOT_FOUND_DATA(21601, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		TENANT_TO_JSON_FOR_SET_REDIS_ERROR(21602, "카테고리 데이터 변환 중 오류 발생(Category Object->Json)", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		FLOORINFO_TO_JSON_FOR_SET_REDIS_ERROR(21603, "층별 테넌트 데이터 변환 중 오류 발생(FloorInfo Object->Json)", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		TENANT_SELECTED_SIZE_ERROR(21604, "테넌트가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		TENANT_DATE_PARSE_ERROR(21605, "DATE 형식이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Tenant(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Board implements IErrorCode{
		NOTICE_NOT_FOUND_DATA(21301, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		NOTICE_SYNC_FAIL_REDIS(21310, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		FAQ_NOT_FOUND_DATA(21302, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		FAQ_SYNC_FAIL_REDIS(21311, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		NEWS_NOT_FOUND_DATA(21303, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		NEWS_SYNC_FAIL_REDIS(21312, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		NOTICE_SELECTED_SIZE_ERROR(21321, "공지사항이 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		NEWS_SELECTED_SIZE_ERROR(21322, "뉴스가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		BLOG_SELECTED_SIZE_ERROR(21322, "블로그가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		THEME_SELECTED_SIZE_ERROR(21322, "테마가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Board(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}

	public enum Event implements IErrorCode{
		EVENT_NOT_FOUND_DATA(21500, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_0),
		EVENT_DATE_PARSE_ERROR(21501, "DATE 형식이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		EVENT_SELECTED_SIZE_ERROR(21502, "이벤트가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		EVENT_APPLY_SIZE_ERROR(21503, "이벤트가 응모자 수가 추천자 수보다 적습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Event(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Push implements IErrorCode{
		PUSH_NOT_FOUND_DATA(21600, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_0),
		PUSH_DATE_PARSE_ERROR(21601, "DATE 형식이 잘못되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		PUSH_SELECTED_SIZE_ERROR(21602, "푸시가 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		PUSH_SELECTED_SIZE_ERROR_FOR_MODIFY(21604, "이미 발송된 푸시는 수정이 불가합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		PUSH_SELECTED_SIZE_ERROR_FOR_DELETE(21605, "이미 발송된 푸시는 삭제가 불가합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0),
		PUSH_ALREADY_SEND(21603, "푸시가 이미 발송되었습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_0);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Push(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum SNS implements IErrorCode{
		BLOG_NOT_FOUND_DATA(22101, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		BLOG_TAG_NOT_FOUND_DATA(22102, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		BLOG_SYNC_FAIL_REDIS(22113, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		INSTAGRAM_NOT_FOUND_DATA(22121, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		INSTAGRAM_TAG_NOT_FOUND_DATA(22122, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		INSTAGRAM_SYNC_DATA_PARSING_FAILED(22123, "데이터 parsing에 실패하였습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		INSTAGRAM_SYNC_FAIL_REDIS(22124, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		THEME_NOT_FOUND_DATA(22131, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		THEME_IMAGE_NOT_FOUND_DATA(22132, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		THEME_DETAIL_NOT_FOUND_DATA(22133, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		THEME_TAG_NOT_FOUND_DATA(22134, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		THEME_SYNC_FAIL_REDIS(22135, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private SNS(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	

	public enum Facility implements IErrorCode{
		FACILITY_NOT_FOUND_DATA(22301, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		FACILITY_SYNC_DATA_PARSING_FAILED(22302, "데이터 parsing에 실패하였습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		FACILITY_SYNC_FAIL_REDIS(22303, "데이터를 redis에 반영하지 못했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		FACILITY_SELECTED_SIZE_ERROR(22304, "편의시설이 하나 이상 선택되어야 합니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Facility(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}

	public enum Member implements IErrorCode{
		MEMBER_DEVICE_NOT_FOUND_DATA(23201, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_OK, Severity.LEVEL_1),
		MEMBER_DEVICE_DUPLICATE_KEY(23202, "중복된 디바이스 입니다.", HttpStatusCode.HTTP_OK, Severity.LEVEL_1),
		MEMBER_DEVICE_ID_INVALID_DATA(23203, "DEVICE_ID는 필수 입니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		MEMBER_NOT_FOUND_DATA(23210, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_OK, Severity.LEVEL_4),
		MEMBER_REG_FAILED(23211, "회원 등록이 실패했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_4),
		MEMBER_DUPLICATE_REG(23212, "중복된 회원 등록입니다.", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		LIKE_EVENT_NOT_FOUND_DATA(23220, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		LIKE_TENANT_NOT_FOUND_DATA(23230, "요청하신 데이터를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		MEMBER_CUST_ID_FOUND_DATA(23204, "CUST_ID는 필수 입니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		LOCATION_INFO_NOT_FOUND_DATA(23301, "개인 위치정보 관리 정보를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_2);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Member(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	
	public enum Coupon implements IErrorCode{

		COUPON_CUSTID_NOT_FOUND_DATA(29001, "요청하신 회원 정보를 찾을 수 없습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		COUPON_NOT_FOUND_DATA(29012, "요청하신 쿠폰 정보를 찾을 수 없습니다.", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_DATA_DELETE_FAILED(29002, "쿠폰 삭제 실패", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		TENANT_COUPON_DATA_FAILED(29003, "테넌트 쿠폰 리스트 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_TENANT_DATA_FAILED(29004, "쿠폰이 있는 테넌트 리스트 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_USED_DATA_FAILED(29005, "사용한 쿠폰 리스트 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_USED_CK_DATA_FAILED(29006, "쿠폰이 사용여부 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_DETAIL_DATA_FAILED(29007, "쿠폰 상세 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_ISSU_FAILED(29008, "쿠폰 다운로드 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		MY_COUPON_DATA_FAILED(29009, "쿠폰 보관함 리스트 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_DATA_FAILED(29010, "쿠폰 리스트 조회 실패", HttpStatusCode.HTTP_RESOURCE_NOT_FOUND, Severity.LEVEL_1),
		COUPON_USE_FAILED(29011, "쿠폰 사용 실패", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);

		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Coupon(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum AwsSnsRelayHttpClient implements IErrorCode{
		AWS_SNS_RELAY_URI_ENCODING_EXCEPTION(42001, "인코딩 중 오류가 발생했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		AWS_SNS_RELAY_JSON_PARSE_EXCEPTION(42002, "JSON 변환 중 오류가 발생했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		AWS_SNS_RELAY_IO_EXCEPTION(42003, "JSON 변환 중 오류가 발생했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		AWS_SNS_RELAY_URI_SYNTAX_EXCEPTION(42004, "잘못된 URI입니다.", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		AWS_SNS_RELAY_CLIENT_PROTOCOL_EXCEPTION(42005, "잘못된 URI입니다.", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		AWS_SNS_RELAY_HTTP_CLIENT_IO_EXCEPTION(42006, "IO 오류가 발생했습니다.", HttpStatusCode.HTTP_INTERNAL_SERVER_ERROR, Severity.LEVEL_1),
		AWS_SNS_RELAY_HTTP_CLIENT_UNKNOWN_EXCEPTION(42099, "오류가 발생했습니다.", HttpStatusCode.HTTP_UNKNOWN, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private AwsSnsRelayHttpClient(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}
	
	public enum Scenario implements IErrorCode{

		SCENARIO_REG_FAILED(39001, "시나리오 등록 실패", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_4),
		TOKEN_NOT_FOUND_DATA(39002, "access_token 조회 실패", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1),
		SCENARIO_JSON_PARSE_EXCEPTION(39003, "JSON 변환 중 오류가 발생했습니다.", HttpStatusCode.HTTP_FIELD_VALIDATION_FAIL, Severity.LEVEL_1);
		
		private int code;
		private String message;
		private HttpStatusCode httpStatusCode;
		private int severityCode;
	      
		private Scenario(int code, String message, HttpStatusCode httpStatusCode, int severityCode) {
			this.code = code;
			this.message = message;
			this.httpStatusCode = httpStatusCode;
			this.severityCode = severityCode;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public int getSeverityCode() {
			return this.severityCode;
		}
		
		public HttpStatusCode getHttpStatusCode() {
			return this.httpStatusCode;
		}
	}

	public enum HttpStatusCode {
		HTTP_OK(200),
		HTTP_FIELD_VALIDATION_FAIL(400),
		HTTP_UNAUTHORIZED(401),
		HTTP_RESOURCE_NOT_FOUND(404),
		HTTP_INTERNAL_SERVER_ERROR(500),
		HTTP_UNKNOWN(520);
		
		int code;
		
		private HttpStatusCode(int code) {
			this.code = code;
		}
		
		public int getCode() {
			return this.code;
		}
	}
}
