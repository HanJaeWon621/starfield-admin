<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ include file="../common/header.jsp" %>
	<%@ include file="../common/gnb.jsp" %>
	<div id="cate" class="contents-area">
		<div class="contents-wrap" id="cate-wrap">
			<script id="tmpl-cate" type="text/ractive">
			<h1 class="menu-title">매장 카테고리 관리</h1>
			<div class="column-wrap cate-second-open"><!--cate-second-open class 추가시, 2차 카테고리 목록 뜸-->
				<div id="cate-first-wrap" class="column first">
					<h2>1차 카테고리</h2>
					<div id="cate-first" class="list-table">
						<div class="t-row list-head">
							<div class="t-cell">No</div>
							<div class="t-cell">카테고리명</div>
							<div class="t-cell"></div>
							<div class="t-cell">노출여부</div>
							<div class="t-cell">삭제</div>
						</div>
						<ul class="list-body sort">
							{{#firstCategories}}
							<li class="t-row cursor-m t-row-h42" data-no="{{no}}" data-cate_seq="{{cate_seq}}">
								<div class="t-cell">{{no}}</div>
								<div class="t-cell ellipsis" on-click="changeFirstCate:{{cate_seq}}">{{cate_nm_ko}}</div>
								<div class="t-cell"><div class="basic-btn" on-click="view:'firstCategories',{{no-1}}">상세보기</div></div>
								<div class="t-cell cursor-d {{#if sts == '1'}} blue {{else}} red {{/if}}" on-click="open:'firstCategories',{{no-1}},{{#if sts == '1'}}'0'{{else}}'1'{{/if}}"><div class="basic-btn">{{#if sts == '1'}} 노출 {{else}} 미노출 {{/if}}</div></div>
								<div class="t-cell cursor-p" on-click="del:'firstCategories','#cate-first-wrap',{{no-1}},{{child_cnt}}">&#9747;</div>
							</li>
							{{/firstCategories}}
						</ul>
					</div>
					<div class="btn-wrap">
						<div class="basic-btn gold reg-cate-first-btn" on-click="add:'firstCategories'">1차 카테고리 등록</div>
					</div>
				</div>
				<div id="cate-second-wrap" class="column second">
					<h2>2차 카테고리</h2>
					<div id="cate-first" class="list-table">
						<div class="t-row list-head">
							<div class="t-cell">No</div>
							<div class="t-cell">카테고리명</div>
							<div class="t-cell"></div>
							<div class="t-cell">노출여부</div>
							<div class="t-cell">삭제</div>
						</div>
						<ul class="list-body sort">
							{{#secondCategories}}
							<li class="t-row cursor-m t-row-h42" data-no="{{no}}" data-cate_seq="{{cate_seq}}">
								<div class="t-cell">{{no}}</div>
								<div class="t-cell ellipsis">{{cate_nm_ko}}</div>
								<div class="t-cell"><div class="basic-btn" on-click="view:'secondCategories',{{no-1}}">상세보기</div></div>
								<div class="t-cell cursor-d {{#if sts == '1'}} blue {{else}} red {{/if}}" on-click="open:'secondCategories',{{no-1}},{{#if sts == '1'}}'0'{{else}}'1'{{/if}}"><div class="basic-btn">{{#if sts == '1'}} 노출 {{else}} 미노출 {{/if}}</div></div>
								<div class="t-cell cursor-p" on-click="del:'secondCategories','#cate-second-wrap',{{no-1}},{{tnt_cnt}}">&#9747;</div>
							</li>
							{{/secondCategories}}
						</ul>
					</div>
					<div class="btn-wrap">
						<div class="basic-btn gold reg-cate-first-btn" on-click="add:'secondCategories'">2차 카테고리 등록</div>
					</div>
				</div>
				<div class="btn-wrap">
					<div class="basic-btn gold" on-click="cancel">취소</div>
					<div class="basic-btn gold" on-click="save">저장</div>
				</div>
			</div>
		</script>
		</div>
	</div>
	<!--카테고리명 등록 팝업-->
	<div id="cate-name-reg-popup" class="popup-wrap">
		<script id="tmpl-cate-name-reg-popup" type="text/ractive">
		<div class="popup-container">
			<div class="popup">
				<div class="popup-header"><span class="btn-close" on-click="close">&#215;</span></div>
				<h4 class="popup-title red">카테고리{{#if popupType == 'view'}}&nbsp;상세{{elseif category.cate_seq}}&nbsp;수정 {{else}}&nbsp;등록 {{/if}}</h4>
				<div class="popup-contents">
					<table id="cate-name-reg" class="input-list">
						<tr>
							<th>국문 카테고리명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="" placeholder="국문 카테고리명을 입력하세요" lang="ko" value="{{category.cate_nm_ko}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>영문 카테고리명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="" placeholder="영문 카테고리명을 입력하세요" lang="en" value="{{category.cate_nm_en}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>중문 카테고리명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="" placeholder="중문 카테고리명을 입력하세요" lang="zh" value="{{category.cate_nm_cn}}">
								</div>
							</td>
						</tr>
						<tr>
							<th>일문 카테고리명</th>
							<td>
								<div class="text-input-wrap">
									<input class="text-input" type="text" value="" placeholder="일문 카테고리명을 입력하세요" lang="ja" value="{{category.cate_nm_jp}}">
								</div>
							</td>
						</tr>
					</table>
					<div class="btn-wrap">
						{{#if popupType == 'view'}}
						<div class="basic-btn gold" on-click="close">확인</div>
						<div class="basic-btn gold" on-click="goModify">수정</div>
						{{else}}
						<div class="basic-btn gold" on-click="save">확인</div>
						<div class="basic-btn gold" on-click="cancel">취소</div>
						{{/if}}
					</div>
				</div>
			</div>
		</div>
		</script>
	</div>
	<!--//카테고리명 등록 팝업-->
	<script src="/js/app/category/ACTGW001.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-sortable.js" type="text/javascript"></script>
<%@ include file="../common/loading-popup.jsp" %>
<%@ include file="../common/footer.jsp" %>