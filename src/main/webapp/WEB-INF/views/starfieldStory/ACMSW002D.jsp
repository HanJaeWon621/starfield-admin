<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>
<%@ include file="../common/gnb.jsp" %>
<div id="sns-reg" class="contents-area" data-bcn_cd="${bcn_cd}" data-token="${token}" data-insta_tag_seq="${insta_tag_seq}">
	<script id="tmpl-sns-reg" type="text/ractive">
	<div class="contents-wrap">
		<h1 class="menu-title">스타필드 스토리 관리 - 인스타그램 관리 - 이미지 관리</h1>
		<div class="list-top">
			<div class="basic-btn w155" on-click="back">인스타그램 태그 목록</div>
			<ul class="inline-list">
				<li on-click="add">이미지 추가</li><li on-click="delete">선택 삭제</li>
			</ul>
		</div>
		<table id="sns-info" class="input-list free-width">
			<tr>
				<th>태그명</th>
				<td class="ellipsis">{{instagram.insta_tag_nm}}</td>
			</tr>
			<tr>
				<th>노출 여부</th>
				<td>{{#if instagram.sts == 0}}미노출{{else}}노출{{/if}}</td>
			</tr>
		</table>
		
		<div id="insta-list" class="list-table">
			<div class="t-row list-head">
				<div class="t-cell">NO</div>
				<div class="t-cell">미리보기</div>
				<div class="t-cell">노출여부</div>
				<div class="t-cell">태그명</div>
				<div class="t-cell">USERNAME</div>
				<div class="t-cell">등록일</div>
				<div class="t-cell">선택</div>
			</div>
			<ul class="list-body sort">
				{{#instagram.instagramImageList}}
					<li class="t-row cursor-m" data-sort_ord="{{sort_ord}}">
						<div class="t-cell">{{sort_ord}}</div>
						<div class="t-cell">
							<div class="insta-preview">
								<img src="/images/bg_square.png" alt class="default">
								<img src="{{url_thumb}}" alt>
							</div>
						</div>
						<div class="t-cell cursor-d {{#if sts == 1}}blue{{else}}red{{/if}}" on-click="changeStatus:{{.}}"><!-- .red : 미노출 / .blue: 노출--><div class="basic-btn">{{#if sts == 1}}노출{{else}}미노출{{/if}}</div></div>
						<div class="t-cell">{{tag}}</div>
						<div class="t-cell">{{user_name}}</div>
						<div class="t-cell">{{reg_dttm}}</div>
						<div class="t-cell cursor-d"><input name="groupCheckBox" type="checkbox" value="{{sort_ord}}"></div>
					</li>
				{{/instagram.instagramImageList}}
			</ul>
		</div>
		
		<div class="btn-wrap">
			<div class="basic-btn gold" on-click="cancel">취소</div>
			<div class="basic-btn gold" on-click="save">저장</div>
		</div>
	</div>
	</script>
</div>

<!--인스타그램 이미지 추가 팝업-->
<div id="sns-reg-popup" class="popup-wrap">
	<script id="tmpl-sns-reg-popup" type="text/ractive">
	<div class="popup-container">
		<div class="popup">
			<div class="popup-header"><span class="btn-close" on-click="cancel">&#215;</span></div>
			<h4 class="popup-title red">인스타그램 이미지 추가</h4>
			<div class="popup-contents ta-l">
				<div class="search-box">
					<div class="search-input-wrap">
						<input class="search-input" type="text" placeholder="태그명을 입력하세요" value="{{instagram.searchKeyword}}">
						<div class="basic-btn search gold" on-click="search">검색</div>
					</div>
				</div>
				<div class="list-top">
					<div class="item-count">총 <span class="sns-total-num">{{instagram.imageList.length}}</span>개 중 <span class="sns-choose-num red">{{instagram.selectedCount}}개</span>가 선택되었습니다.</div>
					<ul class="inline-list">
						<li on-click="viewChange">{{#if instagram.viewSelected}}전체보기{{else}}선택 모아보기{{/if}}</li>
					</ul>
				</div>
				<div class="insta-list">
				{{#instagram.imageList}}
					{{#if instagram.viewSelected}}
						{{#if select}}
						<div class="insta-item {{#select}}selected{{/if}}" on-click="imgSelect:{{.}}">
							<img src="/images/bg_square.png" alt class="default">
							<img src="{{images.low_resolution.url}}" alt>
						</div>
						{{/if}}
					{{else}}
					<div class="insta-item {{#select}}selected{{/if}}" on-click="imgSelect:{{.}}">
						<img src="/images/bg_square.png" alt class="default">
						<img src="{{images.low_resolution.url}}" alt>
					</div>
					{{/if}}
				{{/instagram.imageList}}
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="cancel">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</div>
	</div>
	</script>
</div>
<!--//인스타그램 이미지 추가 팝업-->
<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<script src="/js/app/starfieldStory/ACMSW002D.js" type="text/javascript"></script>
<%@ include file="../common/footer.jsp" %>