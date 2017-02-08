package kr.co.starfield.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import kr.co.starfield.model.Facility;
import kr.co.starfield.model.FacilityDetail;
import kr.co.starfield.service.AFCT001Service;

public class AFCT001RestControllerTest extends BaseTest {

	@Autowired
	AFCT001Service afct001Service;
	
	Facility facility;
	FacilityDetail facilityDetail;
	
	
	@Override
	public void setup() throws Exception {
		
		
		facility = new Facility();
		facility.bcn_cd = "01";
		facility.conv_faci_seq = "CF201607122333042133";
		facility.sts = 1;
		
		facilityDetail = new FacilityDetail();
		facilityDetail.sts = 1;
		
	}
	
	/**
	 * 모든 편의시설 동기화
	 * @return
	 */
	@Test
	public void testRedisSyncAll() throws Exception {
		afct001Service.syncRedisFacilitySuite();
	}
	
	/**
	 * 편의시설 동기화
	 * @return
	 */
	@Test
	public void testRedisSync() throws Exception {
		afct001Service.syncRun("01");
	}

	/**
	 * 편의시설 디테일 시퀀스 단일조회 및 존아이디 단일조회
	 * @return
	 */
	@Test
	public void facilityDetailRedisSync() throws Exception {
		afct001Service.syncFacilityDetail("01");
	}

	/**
	 * 편의시설 노출여부Y 리스트 동기화
	 * @return
	 */
	@Test
	public void facilityExpRedisSync() throws Exception {
		afct001Service.syncFacilityExp("01");
	}
	
	/**
	 * 편의시설 노출여부N 층별 리스트 동기화
	 * @return
	 */
	@Test
	public void facilityFloorRedisSync() throws Exception {
		afct001Service.syncFacilityFloor("01");
	}
	
	/**
	 * facility 목록 조회
	 * @return
	 */
	@Test
	//@Transactional
	public void testGetFacilityList() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + facility.bcn_cd + "/facilitys")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						//.param("q", "blog_titl=title")
						.param("offset", "0")
						.param("limit", "10"))
				.andDo(print())		
				.andExpect(handler().handlerType(AFCT001RestController.class))	
				.andExpect(handler().methodName("getFacilityList"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * facilityDetail 목록 조회
	 * @return
	 */
	@Test
//	@Transactional
	public void testGetFacilityDetailList() throws Exception { 
		
		mockMvc.perform(get("/admin/rest/" + facility.bcn_cd + "/facilitys/" + facility.conv_faci_seq + "/facility")	
						.header("content-type", "application/json")	
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						//.param("q", "tag_nm=tag")
						.param("offset", "0")
						.param("limit", "10"))
				.andDo(print())		
				.andExpect(handler().handlerType(AFCT001RestController.class))	
				.andExpect(handler().methodName("getFacilityDetailList"))					
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
}
