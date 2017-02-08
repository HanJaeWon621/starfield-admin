/*
 * HomeMapper.java	1.00 2016/04/04
 *
 * Copyright (c) 2016 PAPRIKA. All Rights Reserved.
 * 
*/
package kr.co.starfield.mapper;

import java.util.List;

import kr.co.starfield.model.Bcn;
import kr.co.starfield.model.Block;
import kr.co.starfield.model.BlockPoi;
import kr.co.starfield.model.Floor;
import kr.co.starfield.model.MapFile;
import kr.co.starfield.model.Poi;
import kr.co.starfield.model.TenantPoi;
import kr.co.starfield.model.Tnt;

/**
 * HomeMapper interface
 *
 * Copyright Copyright (c) 2016 Company Paprika
 *
 * @author xxx
 * @version 1.0,
 * @see
 * @date 2016.04.11
 */

public interface IndoorMapMapper {

	// 키코드 존재 여부
	public int getBcnExistChk(Bcn vo);

	// 키코드 존재 여부
	public int getBcnFloorExistChk(Bcn vo);

	// POI 리스트
	public List<Poi> getPois(Poi vo);

	// 테넌트 리스트
	public List<Tnt> getTenents(Tnt vo);

	// 맵 층 리스트
	// public List<Floor> getFloors(Floor vo);

	// 맵 층 디테일
	// public Floor getFloor(Floor vo);

	// 맵 층별 블록 리스트
	public List<Block> getBlocks(Floor vo);

	// 맵 층별 블록 poi 리스트
	public List<BlockPoi> getBlockPois(Block vo);

	// 지점 리스트
	public List<Bcn> getBcns(Bcn vo);

	// 지점 상세
	public Bcn getBcn(Bcn vo);

	// 지점 등록
	public void regBcn(Bcn vo);

	// 지점 수정
	public void modifyBcn(Bcn vo);

	// 지점 삭제
	public void deleteBcn(Bcn vo);

	// 층 리스트
	public List<Floor> getFloors(Floor vo);

	// 층 상세
	public Floor getFloor(Floor vo);

	// 층 등록
	public void regFloor(Floor vo);

	// 층 수정
	public void modifyFloor(Floor vo);

	// 층순번 수정
	public void modifyFloorFileSeq(Floor vo);

	// 층 별 맵 일괄 등록위한 조회
	public Floor getFloorMapFile(Floor vo);

	// 층 삭제
	public void deleteFloor(Floor vo);

	// 블록 리스트
	public List<Block> getFloorBlocks(Block vo);

	// 블록 상세
	public Block getFloorBlock(Block vo);

	// 블록 등록
	public void regFloorBlock(Block vo);

	// 블록 수정
	public void modifyFloorBlock(Block vo);

	// 층별 블록 전체 삭제
	public void deleteFloorBlock(Block vo);

	// 블록 전체 삭제
	public void deleteAllFloorBlock(Block vo);

	// 맵 파일 생성 메타 리스트
	public void makeMapData(MapFile vo);

	public void makeMapStyleData(MapFile vo);

	// 맵 파일 생성 메타 리스트
	public MapFile getMapStyleFilePath(MapFile vo);

	// 맵 파일 생성 메타 리스트
	public List<MapFile> getMapFiles(MapFile vo);

	// 맵 스타일 파일 생성 메타 리스트
	public List<MapFile> getMapStyleFiles(MapFile vo);

	// 테넌트 POI 리스트
	public List<TenantPoi> getTenantPois(TenantPoi vo);

	// 테넌트 POI 상세
	public TenantPoi getTenantPoi(TenantPoi vo);

	// 테넌트 POI 등록
	public void regTenantPoi(TenantPoi vo);

	// 테넌트 POI 수정
	public void modifyTenantPoi(TenantPoi vo);

	// 테넌트 POI 삭제
	public void deleteTenantPoi(TenantPoi vo);

}
