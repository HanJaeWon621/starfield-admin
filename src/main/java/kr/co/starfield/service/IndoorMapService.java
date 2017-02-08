package kr.co.starfield.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.starfield.mapper.IndoorMapMapper;
import kr.co.starfield.model.Bcn;
import kr.co.starfield.model.Block;
import kr.co.starfield.model.Floor;
import kr.co.starfield.model.ListResult;
import kr.co.starfield.model.MapFile;
import kr.co.starfield.model.Paging;
import kr.co.starfield.model.Poi;
import kr.co.starfield.model.SimpleResult;
import kr.co.starfield.model.TenantPoi;
import kr.co.starfield.model.Tnt;

/**
 * REST 서비스 클래스
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

@Service
public class IndoorMapService {

	@Autowired
	private IndoorMapMapper mapper;

	private static final Logger ll = LoggerFactory.getLogger(IndoorMapMapper.class);

	/**
	 * Poi 리스트
	 * 
	 * @param Poi
	 * @return
	 */
	public ListResult<Poi> getPois(Poi vo) {

		ListResult<Poi> result = new ListResult<>();

		List<Poi> cpList = mapper.getPois(vo);
		result.list = cpList;
		int totCnt = cpList.size();
		result.tot_cnt = totCnt;
		return result;
	}

	/**
	 * 테넌트 리스트
	 * 
	 * @param Poi
	 * @return
	 */
	public ListResult<Tnt> getTenents(Tnt vo) {

		ListResult<Tnt> result = new ListResult<>();

		List<Tnt> cpList = mapper.getTenents(vo);
		result.list = cpList;
		int totCnt = cpList.size();
		result.tot_cnt = totCnt;
		return result;
	}

	/**
	 * 지점 리스트
	 * @param Floor
	 * @return 
	 */
	public ListResult<Bcn> getBcns(Bcn vo) {
		
		ListResult<Bcn> result = new ListResult<>();
		
		List<Bcn> cpList = mapper.getBcns(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	/**
	 * 지점 상세
	 * @param 
	 * @return 
	 */
	public Bcn getBcn(Bcn vo) {
	
		Bcn ss = mapper.getBcn(vo);
		//result.list = cpList;
	
		return ss;
	}
	/**
	 * 층 등록
	 * @param Floor
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regBcn(Bcn vo) {
		
		SimpleResult result = new SimpleResult();
		int existCnt = mapper.getBcnExistChk(vo);
		if(existCnt>0){
			System.out.println("체크>>>"+existCnt);
			result.code =-1;
		}else{
			System.out.println("체크>>>"+existCnt);
			mapper.regBcn(vo);
			result.extra =vo.bcn_seq;
		}
	 	
	 	return result;
	}
	/**
	 * 지점 수정
	 * @param Bcn
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyBcn(Bcn vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyBcn(vo);
	 	
	 	
	 	result.extra =vo.bcn_seq;
	 	return result;
	}

	/**
	 * 지점 삭제
	 * @param Bcn
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteBcn(Bcn vo) {
		
		SimpleResult result = new SimpleResult();
	 	
	 	int existCnt = mapper.getBcnFloorExistChk(vo);
	 	if(existCnt>0){
			System.out.println("체크>>>"+existCnt);
			result.code =-1;
		}else{
			System.out.println("체크>>>"+existCnt);
			mapper.deleteBcn(vo);
			result.extra =vo.bcn_seq;
		}
	 	return result;
	}
	/**
	 * 층 리스트
	 * @param Floor
	 * @return 
	 */
	public ListResult<Floor> getFloors(Floor vo) {
		
		ListResult<Floor> result = new ListResult<>();
		
		List<Floor> cpList = mapper.getFloors(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	
	/**
	 * 층 상세
	 * @param Floor
	 * @return 
	 */
	public Floor getFloor(Floor vo) {
	
		Floor ss = mapper.getFloor(vo);
		//result.list = cpList;
	
		return ss;
	}
	/**
	 * 층 상세
	 * @param Floor
	 * @return 
	 */
	public Floor getFloorMapFile(Floor vo) {
	
		Floor ss = mapper.getFloorMapFile(vo);
		//result.list = cpList;
	
		return ss;
	}
	/**
	 * 층 등록
	 * @param Floor
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regFloor(Floor vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regFloor(vo);
	 	
	 	result.extra =vo.fl_seq;
	 	return result;
	}
	
	/**
	 * 층 수정
	 * @param Floor
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyFloor(Floor vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyFloor(vo);
	 	
	 	
	 	result.extra =vo.fl_seq;
	 	return result;
	}
	/**
	 * 층별 맵파일 순번 수정
	 * @param Floor
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyFloorFileSeq(Floor vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyFloorFileSeq(vo);
	 	
	 	
	 	result.extra =vo.fl_seq;
	 	return result;
	}
	
	/**
	 * 층 삭제
	 * @param Floor
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteFloor(Floor vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteFloor(vo);
	 	
	 	result.extra =vo.fl_seq;
	 	return result;
	}
	/**
	 * 블록 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<Block> getFloorBlocks(Block vo) {
		
		ListResult<Block> result = new ListResult<>();
		
		List<Block> cpList = mapper.getFloorBlocks(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	
	/**
	 * 블록 상세
	 * @param ACPN001Vo
	 * @return 
	 */
	public Block getFloorBlock(Block vo) {
		
		ListResult<Block> result = new ListResult<>();
		
		Block ss = mapper.getFloorBlock(vo);
		//result.list = cpList;
	
		return ss;
	}
	
	/**
	 * 맵 파일 조회 일괄  등록
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regFloorBlockFromMapFile(Floor fvo) {
		
		SimpleResult result = new SimpleResult();
		//층별 block삭제
		Block vo2 = new Block();
		vo2.fl   = fvo.fl;
		//mapper.deleteAllFloorBlock(vo2); //우선 주석
		//경로, 파일명 추출
		System.out.println("bcn_cd>>"+fvo.bcn_cd);
		System.out.println("fvo.fl>>"+fvo.fl);
		Floor vo1 = mapper.getFloorMapFile(fvo);
		String file_path =vo1.file_path;
		String file_nm = vo1.file_nm;
		System.out.println("file_path>>"+file_path);
		System.out.println("file_nm>>"+file_nm);
		Path path =Paths.get(file_path, file_nm);
		Charset charset = Charset.forName("UTF-8");
        
        try{
            List<String> lines = Files.readAllLines(path, charset);
            int i=-1;
            String[] stra;
            String room_num="";
            String point_cord="";
            for(String line : lines){
                stra=line.split(";");
                Block vo = new Block();
                while(i<stra.length){
                	++i;
                	if(i<stra.length){
                		if(i==0){
                			vo.room_num = stra[i];
                		}else if(i==1){
                			//vo.data = stra[i];
                			vo.point_cord = stra[i];
                		}else if(i==2){
                			vo.spray = stra[i];
                		}else if(i==3){
                			vo.height = stra[i];
                		}else if(i==4){
                			vo.spray_oft_x = stra[i];
                		}else if(i==5){
                			vo.spray_oft_y = stra[i];
                		}else if(i==6){
                			vo.spray_sle = stra[i];
                		}else if(i==7){
                			vo.etc = stra[i];	
                		}
                	}
                }
                vo.bcn_cd   = fvo.bcn_cd;
                vo.fl   = fvo.fl;
                //vo.room_num   = room_num;
                //vo.point_cord = point_cord;
                //vo.data = point_cord;
                //System.out.println("vo.fl>>"+vo.fl);
                //System.out.println("room_num>>"+room_num);
                //System.out.println("point_cord>>"+point_cord);
                vo.insert_div ="BATCH";
                mapper.regFloorBlock(vo);
                i=-1;
            }
        } catch (IOException e){
            System.out.println(e);
        }
	 	//mapper.regFloorBlock(vo);
	 	
	 	//result.extra =vo.fl;
	 	return result;
	}
	
	/**
	 * 블록 등록
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regFloorBlock(Block vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regFloorBlock(vo);
	 	
	 	result.extra =vo.fb_seq;
	 	return result;
	}
	
	/**
	 * 블록 수정
	 * @param Block
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyFloorBlock(Block vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyFloorBlock(vo);
	 	
	 	result.extra =vo.fb_seq;
	 	return result;
	}

	/**
	 * 블록 삭제
	 * @param Block
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteFloorBlock(Block vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteFloorBlock(vo);
	 	
	 	result.extra =vo.fb_seq;
	 	return result;
	}
	
	/**
	 * 블록 전체삭제
	 * @param Block
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteAllFloorBlock(Block vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteAllFloorBlock(vo);
	 	
	 	result.extra =vo.fb_seq;
	 	return result;
	}
	/**
	 * 맵 데이터 파일 삭제
	 * 
	 * @param Poi
	 * @return
	 */
	public SimpleResult deleteMapFile(MapFile vo) {
		SimpleResult result = new SimpleResult();
		vo.file_div_cd="MAP";
		MapFile mapFile = mapper.getMapStyleFilePath(vo);
		String logDir = "C:\\gitNew2\\starfield-admin\\src\\main\\webapp\\resources\\js\\app\\map\\";
		String fileNm = "mapData.json";
		logDir = mapFile.file_path;
		fileNm = mapFile.file_nm;
		removeFile(logDir,fileNm);
		result.code =0;

		return result;
	}
	/**
	 * 맵 메타 데이터  생성
	 * 
	 * @param Poi
	 * @return
	 */
	public SimpleResult saveMapFile(MapFile vo) {
		SimpleResult result = new SimpleResult();
		vo.file_div_cd="MAP";
		mapper.makeMapData(vo);
		
		result.code =0;

		return result;
	}
	/**
	 * 맵 데이터 파일 생성
	 * 
	 * @param Poi
	 * @return
	 */
	public SimpleResult makeMapFile(MapFile vo) {
		SimpleResult result = new SimpleResult();
		vo.file_div_cd="MAP";
		//mapper.makeMapData(vo);
		MapFile mapFile = mapper.getMapStyleFilePath(vo);
		String logDir = "C:\\gitNew2\\starfield-admin\\src\\main\\webapp\\resources\\js\\app\\map\\";
		String fileNm = "mapData.json";
		logDir = mapFile.file_path;
		fileNm = mapFile.file_nm;
		List<MapFile> mapFileList = mapper.getMapFiles(vo);
		String row_text;
		for (MapFile mf : mapFileList){
			row_text = mf.row_text;
			System.out.println("문장>>"+row_text);
			writeFile(logDir,fileNm, row_text);
		}
		
		result.code =0;

		return result;
	}
	/**
	 * 맵 데이터 파일 삭제
	 * 
	 * @param Poi
	 * @return
	 */
	public SimpleResult deleteMapStyleFile(MapFile vo) {
		SimpleResult result = new SimpleResult();
		vo.file_div_cd="MAPSTYLE";
		MapFile mapFile = mapper.getMapStyleFilePath(vo);
		String logDir = "C:\\gitNew2\\starfield-admin\\src\\main\\webapp\\resources\\css\\map\\";
		String fileNm = "mapStyle.css";
		logDir = mapFile.file_path;
		fileNm = mapFile.file_nm;
		removeFile(logDir,fileNm);
		result.code =0;

		return result;
	}
	
	/**
	 * 맵 스타일 데이터 파일 생성
	 * 
	 * @param Poi
	 * @return
	 */
	public SimpleResult makeMapStyleFile(MapFile vo) {
		SimpleResult result = new SimpleResult();
		vo.file_div_cd="MAPSTYLE";
		//mapper.makeMapStyleData(vo);
		MapFile mapFile = mapper.getMapStyleFilePath(vo);
		String logDir = "C:\\gitNew2\\starfield-admin\\src\\main\\webapp\\resources\\css\\map\\";
		String fileNm = "mapStyle.css";
		logDir = mapFile.file_path;
		fileNm = mapFile.file_nm;
		//fileNm = mapFile.file_nm;
		List<MapFile> mapFileList = mapper.getMapStyleFiles(vo);
		String row_text;
		for (MapFile mf : mapFileList){
			row_text = mf.row_text;
			System.out.println("문장>>"+row_text);
			writeFile(logDir,fileNm, row_text);
		}
		
		result.code =0;

		return result;
	}
	/*
	 * p:전문
	 */
	public static void writeFile(String logDir, String fileNm, String p_logStr) {
		Date d = new Date();
		String now = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String now_time = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
		try {
			String rootDir = "D:\\";
			//rootDir = "/home/cpadm/";
			/*
			File dir = new File(rootDir);
			if (!dir.exists()) {
				// dir.mkdir();

				if (dir.mkdirs()) {
					// System.out.println("Directory is created!");
				} else {
					// System.out.println("Failed to create directory!");
				}
			}
			*/
			File dir = new File(logDir);
			if (!dir.exists()) {
				// dir.mkdir();

				if (dir.mkdirs()) {
					// System.out.println("Directory is created!");
				} else {
					// System.out.println("Failed to create directory!");
				}
			}
			String path = logDir  +fileNm; // 임시
			File file = new File(path);
			//file.
			//OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");			
			//out.append("\n" + p_logStr);
			//out.flush();
			//out.close();
			// true 지정시 파일의 기존 내용에 이어서 작성
			FileWriter fw = new FileWriter(file, true);
			String logStr = p_logStr;
			
			//// 파일안에 문자열 쓰기
			//fw.write("\n" + logStr.getBytes("UTF-8").toString());
			fw.write("\n" + logStr);
			fw.flush();

			// 객체 닫기
			//fw.close();

		} catch (Exception e) {
			System.out.println("파일로그남기기>>" + e.toString());
			// e.printStackTrace();
		}

	}
	
	/*
	 * p:전문
	 */
	public static void removeFile(String logDir,String fileNm) {
		try {
			String rootDir = "D:\\";
			String path = logDir  +fileNm; // 임시
			System.out.println("removeFile>>"+path);
			File file = new File(path);
			if(file.exists()){
			file.delete();
			}
			//file.deleteOnExit();

		} catch (Exception e) {
			System.out.println("파일로그남기기>>" + e.toString());
			// e.printStackTrace();
		}

	}
	
	/**
	 * 테넌트 POI 리스트
	 * @param ACPN001Vo
	 * @return 
	 */
	public ListResult<TenantPoi> getTenantPois(TenantPoi vo) {
		
		ListResult<TenantPoi> result = new ListResult<>();
		
		List<TenantPoi> cpList = mapper.getTenantPois(vo);
		result.list = cpList;

		int totCnt = cpList.size() > 0 ? cpList.get(0).tot_cnt : 0;
		
		Paging paging = new Paging(vo.offset, vo.limit, totCnt);
		result.paging = paging;
		result.tot_cnt = totCnt;
	
		return result;
	}
	
	/**
	 * 테넌트 POI 상세
	 * @param TenantPoi
	 * @return 
	 */
	public TenantPoi getTenantPoi(TenantPoi vo) {
		
		TenantPoi ss = mapper.getTenantPoi(vo);
		//result.list = cpList;
	
		return ss;
	}
	
	/**
	 * 테넌트 POI 등록
	 * @param StyleSet
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult regTenantPoi(TenantPoi vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.regTenantPoi(vo);
	 	
	 	result.extra =vo.tenant_poi_seq;
	 	return result;
	}
	
	/**
	 * 테넌트 POI 수정
	 * @param Block
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult modifyTenantPoi(TenantPoi vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.modifyTenantPoi(vo);
	 	
	 	result.extra =vo.tenant_poi_seq;
	 	return result;
	}

	/**
	 * 테넌트/편의시설 삭제
	 * @param Block
	 * @return 
	 */	
	@Transactional(rollbackFor = Exception.class)
	public SimpleResult deleteTenantPoi(TenantPoi vo) {
		
		SimpleResult result = new SimpleResult();
	 	mapper.deleteTenantPoi(vo);
	 	
	 	result.extra =vo.tenant_poi_seq;
	 	return result;
	}
}
