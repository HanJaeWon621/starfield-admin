<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
	<div id="location-mng" class="contents-area" data-bcn_cd="${bcn_cd}">
	<script id="tmpl-location-mng" type="text/ractive">
		<div class="contents-wrap">
			<h1 class="menu-title">APP 개인 위치정보 삭제접수 관리 - 목록</h1>
			<div class="search-box">
				<div class="select-wrap">
					<select id="location-date-selector" class="with-checkbox" {{#if !hashObject.date_search}}disabled{{/if}} value="{{hashObject.date_type}}">
						<option value="REQ_DEL_DTTM">삭제 요청일</option>
						<option value="ACT_DTTM">조치일</option>
					</select>
					<input type="checkbox" checked="{{hashObject.date_search}}"><!--check시 #location-date-selector와 date-picker에 disabled 추가-->
				</div>
				<div class="date-picker">
					<input type="text" name="datepicker" placeholder="시작일자" value="{{hashObject.stat_date}}" {{#if !hashObject.date_search}}disabled{{/if}}>
				</div>
				<span>~</span>
				<div class="date-picker">
					<input type="text" name="datepicker" placeholder="종료일자" value="{{hashObject.end_date}}" {{#if !hashObject.date_search}}disabled{{/if}}>
				</div>
				<span class="select-title">조치여부</span>
				<div class="select-wrap w80">
					<select id="location-measure-selector" value="{{hashObject.act_yn}}">
						<option value="">전체</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
				</div>
				<span class="select-title">조치결과</span>
				<div class="select-wrap w80">
					<select id="location-result-selector" value="{{hashObject.sts}}">
						<option value="-1">전체</option>
						<option value="0">접수</option>
						<option value="1">확인중</option>
						<option value="8">삭제불가</option>
						<option value="9">삭제완료</option>
					</select>
				</div>
				<div class="basic-btn gold h30" on-click="search">검색</div>
			</div>
			<div class="list-top">
				<ul class="inline-list">
					<li on-click="download">엑셀 다운</li><li on-click="create">추가</li>
				</ul>
			</div>
			<table id="location-mng-list" class="basic-list">
				<colgroup>
					<col width="80">
					<col width="*">
					<col width="*">
					<col width="150">
					<col width="150">
					<col width="150">
					<col width="150">
					<col width="150">
				</colgroup>
				<thead>
					<tr>
						<th {{#if(hashObject.sort_name == 'rnum')}}class="selected"{{/if}} on-click="sort:rnum">NO</th>
						<th {{#if(hashObject.sort_name == 'name')}}class="selected"{{/if}} on-click="sort:name">고객 이름</th>
						<th {{#if(hashObject.sort_name == 'phone_num')}}class="selected"{{/if}} on-click="sort:phone_num">고객 전화번호</th>
						<th {{#if(hashObject.sort_name == 'req_del_dttm')}}class="selected"{{/if}} on-click="sort:req_del_dttm">삭제 요청일</th>
						<th {{#if(hashObject.sort_name == 'sts')}}class="selected"{{/if}} on-click="sort:sts">조치여부</th>
						<th {{#if(hashObject.sort_name == 'act_yn')}}class="selected"{{/if}} on-click="sort:act_yn">조치결과</th>
						<th {{#if(hashObject.sort_name == 'act_dttm')}}class="selected"{{/if}} on-click="sort:act_dttm">조치일</th>
						<th {{#if(hashObject.sort_name == 'act_usr')}}class="selected"{{/if}} on-click="sort:act_usr">조치자ID</th>
					</tr>
				</thead>
				<tbody>
					{{#list}}
					<tr class="cursor-p">
						<td on-click="detail:{{plid_mng_seq}}">{{rnum}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{name}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{phone_num}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{req_del_dttm}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{#if sts == 0}}접수{{elseif sts == 1}}확인중{{elseif sts == 8}}삭제불가{{elseif sts == 9}}삭제완료{{/if}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{act_yn}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{#if act_dttm}}{{act_dttm}}{{else}}-{{/if}}</td>
						<td on-click="detail:{{plid_mng_seq}}">{{#if act_usr}}{{act_usr}}{{else}}-{{/if}}</td>
					</tr>
					{{/list}}
				</tbody>
			</table>
			{{#paging}}
			<div class="paging-wrap">
				<ul class="paging inline-list">
				<li class="prev {{#if paging.page_start == 1}}off{{/if}}" on-click="pageMove:{{paging.page_start - 1}}">PREV</li>
				{{#pages}}
				<li class="page {{#if cur_page == this}}selected{{/if}}" on-click="pageMove:{{this}}">{{this}}</li>
				{{/pages}}
				<li class="next {{#if paging.page_end == paging.total_page_cnt}}off{{/if}}" on-click="pageMove:{{paging.page_end + 1}}">NEXT</li>
				</ul>
			</div>
			{{/paging}}
		</div>
	</script>
	</div>
<script src="/js/app/ITO/APLIW001.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>
