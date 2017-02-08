package kr.co.starfield.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonCode {
	
	private static Logger ll = LoggerFactory.getLogger(CommonCode.class);
	
	public enum Common {
		SUCCESS(1, "완료"),
		FAIL(2, "실패");
		
		private int code;
		private String message;
	      
		private Common(int code, String message) {
			this.code = code;
			this.message = message;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public static Common getEnum(int code) throws BaseException {
			for(Common common : Common.values()) {
			    if(common.code == code) {
			      return common;
			    }
			}
			throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
		}

	}
	
	public enum PlatformType {
		PLATFORM_TYPE_IOS(1, "IOS"),
		PLATFORM_TYPE_ANDROID(2, "ANDROID");
		
		private int code;
		private String message;
	      
		private PlatformType(int code, String message) {
			this.code = code;
			this.message = message;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public int getCode() {
			return this.code;
		}
		
		public static PlatformType getEnum(int code) throws BaseException {
			for(PlatformType playformType : PlatformType.values()) {
			    if(playformType.code == code) {
			      return playformType;
			    }
			}
			throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
		}

	}
	
	public static class Tenant {
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum TenantType implements ICommonCode {
			STARFILED("1", "일반(스타필드)", true, 1),
			DEPT("2", "백화점", false, 2);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private TenantType(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(TenantType.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<TenantType> {
		        public TypeHandler() {
		            super(TenantType.class);
		        }
		    }
			
			public static List<TenantType> getList() {
				return new ArrayList<TenantType>(Arrays.asList(TenantType.values()));
			}
			
			@JsonCreator
			public static TenantType getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(TenantType searchOption : TenantType.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum OperationStatus implements ICommonCode {

			DATA_UNFINISHED("0", "데이터 미완", true, 1),
			OPERATION("1", "정상운영", false, 2),
			CLOSED("2", "퇴점", false, 3),
			REPARATION("3", "공사중", false, 4);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			
			private OperationStatus(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(OperationStatus.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<OperationStatus> {
		        public TypeHandler() {
		            super(OperationStatus.class);
		        }
		    }
			
			public static List<OperationStatus> getList() {
				return new ArrayList<OperationStatus>(Arrays.asList(OperationStatus.values()));
			}
			
			@JsonCreator
			public static OperationStatus getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(OperationStatus searchOption : OperationStatus.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}

	}
	
	public static class Facility {
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum FacilityType implements ICommonCode {
			CONVENIENCE("Y", "편의시설", true, 1),
			NORMAL("N", "일반시설", false, 2);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private FacilityType(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(FacilityType.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<FacilityType> {
		        public TypeHandler() {
		            super(FacilityType.class);
		        }
		    }
			
			public static List<FacilityType> getList() {
				return new ArrayList<FacilityType>(Arrays.asList(FacilityType.values()));
			}
			
			@JsonCreator
			public static FacilityType getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(FacilityType searchOption : FacilityType.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
	}
	
	public static class Push {
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum SearchOption implements ICommonCode {
			REG_TERM("1", "등록일", true, 1),
			SEND_TERM("2", "발송일", false, 2);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private SearchOption(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(SearchOption.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<SearchOption> {
		        public TypeHandler() {
		            super(SearchOption.class);
		        }
		    }
			
			public static List<SearchOption> getList() {
				return new ArrayList<SearchOption>(Arrays.asList(SearchOption.values()));
			}
			
			@JsonCreator
			public static SearchOption getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(SearchOption searchOption : SearchOption.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum SendType implements ICommonCode {
			IMMEDIATELY("1", "즉시발송", true, 1),
			RESERVATION("2", "예약발송", false, 2);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			
			private SendType(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(SendType.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<SendType> {
		        public TypeHandler() {
		            super(SendType.class);
		        }
		    }
			
			public static List<SendType> getList() {
				return new ArrayList<SendType>(Arrays.asList(SendType.values()));
			}
			
			@JsonCreator
			public static SendType getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(SendType searchOption : SendType.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum SendYn implements ICommonCode {
			COMPLETE("Y", "발송완료", true, 1),
			REQUEST("R", "발송요청", false, 2),
			ERROR("E", "발송오류", false, 3),
			BEFORE("N", "발송전", false, 4);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			
			private SendYn(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(SendYn.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<SendYn> {
		        public TypeHandler() {
		            super(SendYn.class);
		        }
		    }
			
			public static List<SendYn> getList() {
				return new ArrayList<SendYn>(Arrays.asList(SendYn.values()));
			}
			
			@JsonCreator
			public static SendYn getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(SendYn searchOption : SendYn.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				return null;
			}
		}
	}
	
	public static class Event {
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum SearchOption implements ICommonCode {
			NOTICE_TERM("1", "게시기간", true, 1),
			EVENT_TERM("2", "이벤트기간", false, 2),
			EVT_PICK_PALN_DT("3", "당첨자 발표일", false, 3);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private SearchOption(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(SearchOption.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<SearchOption> {
		        public TypeHandler() {
		            super(SearchOption.class);
		        }
		    }
			
			public static List<SearchOption> getList() {
				return new ArrayList<SearchOption>(Arrays.asList(SearchOption.values()));
			}
			
			@JsonCreator
			public static SearchOption getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(SearchOption searchOption : SearchOption.values()) {
				    if(searchOption.codeCd.equals(codeCd)) {
				      return searchOption;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum EventType implements ICommonCode {
			NOTICE_TYPE("1", "공지형", true, 1),
			GIFT_PRVC_TYPE("2", "경품(개인정보)", false, 2),
			GIFT_TYPE("3", "경품", false, 3),
			STAMP_TYPE("9", "스탬프", false, 9);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			
			private EventType(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}

			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			@MappedTypes(EventType.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<EventType> {
		        public TypeHandler() {
		            super(EventType.class);
		        }
		    }
			
			public static List<EventType> getList() {
				return new ArrayList<EventType>(Arrays.asList(EventType.values()));
			}
			
			@JsonCreator
			public static EventType getEnum(String codeCd) throws BaseException {
				for(EventType eventType : EventType.values()) {
				    if(eventType.codeCd.equals(codeCd)) {
				      return eventType;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum EventStatus implements ICommonCode {
			EVENT_BEFORE("1", "진행전", false, 1, "gray"),
			EVENT_ING("2", "진행중", false, 2, "red"),
			EVENT_END("3", "종료", false, 3, "green");
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private String cssClass;
			
			private EventStatus(String codeCd, String codeNm, boolean defaultValue, int sortOrder, String cssClass) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
				this.cssClass = cssClass;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			public String getCssClass() {
				return this.cssClass;
			}
			
			@MappedTypes(EventStatus.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<EventStatus> {
		        public TypeHandler() {
		            super(EventStatus.class);
		        }
		    }
			
			public static List<EventStatus> getList() {
				return new ArrayList<EventStatus>(Arrays.asList(EventStatus.values()));
			}
			
			@JsonCreator
			public static EventStatus getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				for(EventStatus eventStatus : EventStatus.values()) {
				    if(eventStatus.codeCd.equals(codeCd)) {
				      return eventStatus;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}

		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum EventDvi implements ICommonCode {
			BLAND("B", "브랜드", false, 1),
			STARFILED("S", "스타필드", false, 2);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private EventDvi(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}

			@MappedTypes(EventDvi.class)
		    public static class TypeHandler extends CodeEnumTypeHandler<EventDvi> {
		        public TypeHandler() {
		            super(EventDvi.class);
		        }
		    }
			
			public static List<EventDvi> getList() {
				return new ArrayList<EventDvi>(Arrays.asList(EventDvi.values()));
			}
			
			@JsonCreator
			public static EventDvi getEnum(@JsonProperty("codeCd") String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(EventDvi eventDiv : EventDvi.values()) {
				    if(eventDiv.codeCd.equals(codeCd)) {
				      return eventDiv;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
	}
	
	public static class Scenario {
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum TgtMbr implements ICommonCode {
			ALL("0", "전체", false, 0),
			MEMBER("1", "회원", false, 1),
			NONM_EMBERS("2", "비회원", false, 2),
			ATTENTION_MEMBER("3", "관심등록고객", false, 3),
			VIP_MEMBER("4", "vip회원", false, 4);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private TgtMbr(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}

			public static List<TgtMbr> getList() {
				return new ArrayList<TgtMbr>(Arrays.asList(TgtMbr.values()));
			}
			
			public static TgtMbr getEnum(String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(TgtMbr tgtmbr : TgtMbr.values()) {
				    if(tgtmbr.codeCd.equals(codeCd)) {
				      return tgtmbr;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum TgtSex implements ICommonCode {
			SEX_ALL("A", "전체", false, 1),
			SEX_WOMAN("F", "여성", false, 2),
			SEX_MAN("M", "남성", false, 3);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private TgtSex(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}

			public static List<TgtSex> getList() {
				return new ArrayList<TgtSex>(Arrays.asList(TgtSex.values()));
			}
			
			public static TgtSex getEnum(String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(TgtSex tgtsex : TgtSex.values()) {
				    if(tgtsex.codeCd.equals(codeCd)) {
				      return tgtsex;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum StayZone implements ICommonCode {
			MALLALL("1", "전체", false, 1),
			AQUAFIELD("2", "아쿠아필드", false, 2),
			MEGABOX("3", "메가박스", false, 3),
			BANDINLUNIS("4", "반디엔루니스", false, 4),
			FBZONE("5", "F&B존", false, 5),
			EVTZONE("6", "이벤트존1", false, 6);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
			private int sortOrder;
			
			private StayZone(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}

			public static List<StayZone> getList() {
				return new ArrayList<StayZone>(Arrays.asList(StayZone.values()));
			}
			
			public static StayZone getEnum(String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(StayZone stayzone : StayZone.values()) {
				    if(stayzone.codeCd.equals(codeCd)) {
				      return stayzone;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
		
		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum VisitTime {
			DIRECT_INPUT("0", "직접입력", false, 1),
			M30("30", "30분 경과", false, 2), 
			H1("60", "1시간 경과", false, 3),
			H1M30("90", "1시간30분 경과", false, 4),
			H2("120", "2시간 경과", false, 5),
			H2M30("150", "2시간30분 경과", false, 6),
			H3("180", "3시간 경과", false, 7);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
		     
			private int sortOrder;
			private VisitTime(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}

			public static List<VisitTime> getList() {
				return new ArrayList<VisitTime>(Arrays.asList(VisitTime.values()));
			}
			
			public static VisitTime getEnum(String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(VisitTime visittime : VisitTime.values()) {
				    if(visittime.codeCd.equals(codeCd)) {
				      return visittime;
				    }
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}

		@JsonFormat(shape = JsonFormat.Shape.OBJECT)
		public enum TgtAge implements ICommonCode {
			AGE_ALL("20,30,40,50", "전체", false, 1),
			AGE_20("20", "20대", false, 2),
			AGE_30("30", "30대", false, 3),
			AGE_40("40", "40대", false, 4),
			AGE_50("50", "50대", false, 5);
			
			private String codeCd;
			private String codeNm;
			private boolean defaultValue;
			
			private int sortOrder;
			private TgtAge(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
				this.codeCd = codeCd;
				this.codeNm = codeNm;
				this.defaultValue = defaultValue;
				this.sortOrder = sortOrder;
			}
			
			public String getCodeCd() {
				return this.codeCd;
			}
			
			public String getCodeNm() {
				return this.codeNm;
			}
			
			public boolean isDefaultValue() {
				return this.defaultValue;
			}			
			public int getSortOrder() {
				return this.sortOrder;
			}
			
			public static List<TgtAge> getList() {
				return new ArrayList<TgtAge>(Arrays.asList(TgtAge.values()));
			}
			
			public static TgtAge getEnum(String codeCd) throws BaseException {
				if (codeCd == null) {
					return null;
				}
				for(TgtAge tgtage : TgtAge.values()) {
					if(tgtage.codeCd.equals(codeCd)) {
						return tgtage;
					}
				}
				throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
			}
		}
	}
	
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum AdminLogin implements ICommonCode {
		
		SUCESS("101", "정상", false, 1),
		NOT_FOUND("102", "정보 없음", false, 2),
		ACCOUNT_LOCK("103", "사용자 계정중지", false, 3),
		ACCOUNT_LOCK_PASS_FAIL("104", "계정 잠김(패스워드 3번실패)", false, 4),
		MATCH_FAIL_PASS("105", "패스워드 미일치", false, 5),
	    OLD_ACCOUNT_PASS("106", "비밀번호 3개월 미변경", false, 6),
	    STAT_STAND_BY("107", "계정 미등록 상태", false, 7);
		
		private String codeCd;
		private String codeNm;
		private boolean defaultValue;
		
		private int sortOrder;
		private AdminLogin(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
			this.codeCd = codeCd;
			this.codeNm = codeNm;
			this.defaultValue = defaultValue;
			this.sortOrder = sortOrder;
		}
		
		public String getCodeCd() {
			return this.codeCd;
		}
		
		public String getCodeNm() {
			return this.codeNm;
		}
		
		public boolean isDefaultValue() {
			return this.defaultValue;
		}			
		public int getSortOrder() {
			return this.sortOrder;
		}
		
		public static List<AdminLogin> getList() {
			return new ArrayList<AdminLogin>(Arrays.asList(AdminLogin.values()));
		}
		
		public static AdminLogin getEnum(String codeCd) throws BaseException {
			if (codeCd == null) {
				return null;
			}
			for(AdminLogin adminLogin : AdminLogin.values()) {
				if(adminLogin.codeCd.equals(codeCd)) {
					return adminLogin;
				}
			}
			throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
		}
	}
	
	@JsonFormat(shape = JsonFormat.Shape.OBJECT)
	public enum AdminChangePass implements ICommonCode {
		
		SUCESS("101", "정상", false, 1),
		NOT_FOUND("102", "정보 없음", false, 2),
		MATCH_FAIL_ORIGIN_PASS("103", "기존 패스워드 미일치", false, 3),
		SAME_PRE_PASSWORD("104", "이전 패스워드와 동일", false, 4),
		SAME_THREE_MONTHS_PRE_PASSWORD("105", "3개월내 패스워드와 동일", false, 5);
		
		private String codeCd;
		private String codeNm;
		private boolean defaultValue;
		
		private int sortOrder;
		private AdminChangePass(String codeCd, String codeNm, boolean defaultValue, int sortOrder) {
			this.codeCd = codeCd;
			this.codeNm = codeNm;
			this.defaultValue = defaultValue;
			this.sortOrder = sortOrder;
		}
		
		public String getCodeCd() {
			return this.codeCd;
		}
		
		public String getCodeNm() {
			return this.codeNm;
		}
		
		public boolean isDefaultValue() {
			return this.defaultValue;
		}			
		public int getSortOrder() {
			return this.sortOrder;
		}
		
		public static List<AdminChangePass> getList() {
			return new ArrayList<AdminChangePass>(Arrays.asList(AdminChangePass.values()));
		}
		
		public static AdminChangePass getEnum(String codeCd) throws BaseException {
			if (codeCd == null) {
				return null;
			}
			for(AdminChangePass adminChangePass : AdminChangePass.values()) {
				if(adminChangePass.codeCd.equals(codeCd)) {
					return adminChangePass;
				}
			}
			throw new BaseException(ErrorCode.Common.NOT_FOUND_CODE);
		}
	}
}
