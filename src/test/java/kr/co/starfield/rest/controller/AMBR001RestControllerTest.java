package kr.co.starfield.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.common.ErrorCode;
import kr.co.starfield.common.StatusCode;
import kr.co.starfield.model.LikeTenant;
import kr.co.starfield.model.MemberDevice;
import kr.co.starfield.model.vo.SMBR005Vo;
import kr.co.starfield.model.vo.SMBR003Vo;
import kr.co.starfield.service.AMBR001Service;

public class AMBR001RestControllerTest extends BaseTest {

	@Autowired
	AMBR001Service ambr001Service;
	
	String bcnCd;
	String mbrSeq; 
	String tntSeq;
	String dvicId;

	MemberDevice memberDevice;
	SMBR005Vo smbr005vo;
	
	/**
	 * 테스트 초기화
	 * @return
	 */
	@Before
	@Override
	public void setup() throws Exception {
		
		memberDevice = new MemberDevice();
		memberDevice.mbr_seq = null;
		memberDevice.alram_yn = true;
		memberDevice.dvic_id = "AWSSNSRELAYSERVERTEST";
		memberDevice.pltf_type = 1;
		memberDevice.push_token = "1111222211112222111122221111222211112222111122221111222211113333";
		memberDevice.sts = StatusCode.Common.USE.getCode();
		
		smbr005vo = memberDevice.toVo();
		dvicId = memberDevice.dvic_id;
	}
	
	/**
	 * 사용자 디바이스 정보 조회 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testGetMemberDevice() throws Exception { 
		
		ambr001Service.regMemberDevice(smbr005vo);
		
		mockMvc.perform(get("/rest/"+bcnCd+"/members/any/devices/"+dvicId)	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		
				.andExpect(handler().handlerType(AMBR001RestController.class))	
				.andExpect(handler().methodName("getMemberDevice"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * 사용자 디바이스 등록 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testRegMemberDevice() throws Exception {

		memberDevice.mbr_seq = null;
		memberDevice.sts = null;
		
		mockMvc.perform(post("/rest/"+bcnCd+"/members/any/devices/")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(memberDevice)))
				.andDo(print())		
				.andExpect(handler().handlerType(AMBR001RestController.class))	 
				.andExpect(handler().methodName("regMemberDevice"))					
				.andExpect(status().isOk())	 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	
	}
	
	/**
	 * 사용자 디바이스 등록 테스트 (중복)
	 * @return
	 */
	@Test
	@Transactional
	public void testRegMemberDeviceDuplicate() throws Exception {

		memberDevice.mbr_seq = null;
		memberDevice.sts = null;
		memberDevice.dvic_id = "AWSSNSRELAYSERVERTEST";
		
		mockMvc.perform(post("/rest/"+bcnCd+"/members/any/devices/")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(memberDevice)))
				.andDo(print())		
				.andExpect(handler().handlerType(AMBR001RestController.class))	 
				.andExpect(handler().methodName("regMemberDevice"))					
				.andExpect(status().is4xxClientError())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionResult.errCode").value(ErrorCode.Member.MEMBER_DEVICE_DUPLICATE_KEY.getCode()));	
	}
	
	/**
	 * 사용자 디바이스 수정 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testModidfyMemberDevice() throws Exception {
		
		ambr001Service.regMemberDevice(smbr005vo);
		
		
		assertThat(ambr001Service.getMemberDevice(smbr005vo).push_token, is(memberDevice.push_token));
		
		memberDevice.push_token = "1111222211112222111122221111222211112222111122221111222211114444";
		memberDevice.mbr_seq = null;
		memberDevice.dvic_id = null;
		
		mockMvc.perform(put("/rest/"+bcnCd+"/members/any/devices/"+dvicId)
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(new ObjectMapper().writeValueAsString(memberDevice)))
				.andDo(print())	
				.andExpect(handler().handlerType(AMBR001RestController.class))	
				.andExpect(handler().methodName("modifyMemberDevice"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));
		
		assertThat(ambr001Service.getMemberDevice(smbr005vo).push_token, is(memberDevice.push_token));
	}
	
	/**
	 * 사용자 디바이스 삭제 테스트
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteMemberDevice() throws Exception {
		
		ambr001Service.regMemberDevice(smbr005vo);

		assertThat(ambr001Service.getMemberDevice(smbr005vo).sts, is(StatusCode.Common.USE.getCode()));
		
		memberDevice.push_token = "";
		memberDevice.mbr_seq = null;
		memberDevice.dvic_id = null;
		memberDevice.sts = null;
		
		mockMvc.perform(delete("/rest/"+bcnCd+"/members/any/devices/"+dvicId) 
						.header("content-type", "application/json")	// 헤더에 content-type 설정 
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
				.andExpect(handler().handlerType(AMBR001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
				.andExpect(handler().methodName("deleteMemberDevice"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
				.andExpect(status().isOk())	// 응답 받은 상태 코드가 200 인지 확인 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.code").value(0));	// jsonPath를 이용하여 json 응답값 확인
		
		assertThat(ambr001Service.getMemberDevice(smbr005vo).sts, is(StatusCode.Common.DELETE.getCode()));
	}

	/**
	 * 사용자 디바이스 삭제 테스트 (데이터 없음)
	 * @return
	 */
	@Test
	@Transactional
	public void testDeleteMemberDeviceFail() throws Exception {
		
		ambr001Service.regMemberDevice(smbr005vo);
		
		assertThat(ambr001Service.getMemberDevice(smbr005vo).sts, is(StatusCode.Common.USE.getCode()));
		
		memberDevice.push_token = "";
		memberDevice.mbr_seq = null;
		memberDevice.dvic_id = null;
		memberDevice.sts = null;
		
		dvicId = "AAAAAAAAAAAAA";
		
		mockMvc.perform(delete("/rest/"+bcnCd+"/members/any/devices/"+dvicId) 
						.header("content-type", "application/json")	// 헤더에 content-type 설정 
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8"))
				.andDo(print())		// 응답 결과에 대한 모든 정보 출력 
				.andExpect(handler().handlerType(AMBR001RestController.class))	// 요청에 대한 매핑된 MVC컨트롤러클래스 확인 
				.andExpect(handler().methodName("deleteMemberDevice"))					// 요청에 대한 매핑된 MVC컨트롤러메소드 확인
				.andExpect(status().is4xxClientError())	// 응답 받은 상태 코드가 200 인지 확인 
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.exceptionResult.errCode").value(ErrorCode.Member.MEMBER_DEVICE_NOT_FOUND_DATA.getCode()));	// jsonPath를 이용하여 json 응답값 확인
	}
}
