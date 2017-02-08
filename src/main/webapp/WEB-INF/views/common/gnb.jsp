<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<div id="gnb"
		data-auth="<c:forEach items="${authTypeArr}" var="element" varStatus="status">${element.auth_nm}<c:if test="${not status.last}"><c:out value=","/></c:if></c:forEach>"
	>
	
		<ul class="main-menu">
			<li>
				<a href="/${bcn_cd}/main/bannerGroups">메인 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/main/bannerGroups">상단 히어로 배너 관리</a></li>
					<li><a href="/${bcn_cd}/main/whatsNewGroups">STARFIELD NOW Group 관리</a></li>
				</ul>
			</li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a href="/${bcn_cd}/stores/operation">점포 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/stores/operation">스타필드 운영정보 관리</a></li>
					<li><a href="/${bcn_cd}/stores/tenants">매장 관리</a></li>
					<li><a href="/${bcn_cd}/stores/facilities">편의시설 관리</a></li>
				</ul>
			</li>
			<li><a href="/${bcn_cd}/eventCoupon/events">이벤트 관리</a></li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a href="/${bcn_cd}/coupon">쿠폰 관리</a>
				<ul class="sub-menu"> 
					<li><a href="/${bcn_cd}/coupon">쿠폰 목록 관리</a></li>
					<li>
						<a href="/${bcn_cd}/coupon/mbCoupon">신규쿠폰 등록</a> 
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/coupon/nmCoupon">직접회수형 쿠폰 등록</a></li>
							<li><a href="/${bcn_cd}/coupon/mbCoupon">자동회수형 쿠폰 등록</a></li>
							<li><a href="/${bcn_cd}/coupon/chCoupon">교환권 등록</a></li>
						</ul>
					</li>
					<li><a href="/${bcn_cd}/downCoupons">쿠폰 다운로드내역 조회</a></li>
					<li><a href="/${bcn_cd}/useCoupons">쿠폰 사용내역 조회</a></li>
					<li><a href="/${bcn_cd}/useWaitCoupons">쿠폰 사용내역(가승인) 조회</a></li>
					<li><a href="/${bcn_cd}/useCompareCoupons">쿠폰 영업정보 대사건 조회</a></li>
				</ul>
			</li>
			<li>
				<a href="/${bcn_cd}/starfieldStory/blog">스타필드 스토리 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/starfieldStory/blog">블로그 관리</a></li>
					<li><a href="/${bcn_cd}/starfieldStory/instagram">인스타그램 관리</a></li>
					<li><a href="/${bcn_cd}/starfieldStory/news">NEWS 관리</a></li>
					<li><a href="/${bcn_cd}/starfieldStory/theme">추천테마 관리</a></li>
				</ul>
			</li>
			<li>
				<a href="/${bcn_cd}/service/faq">고객센터 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/service/faq">FAQ 관리</a></li>
					<li><a href="/${bcn_cd}/service/notice">공지사항 관리</a></li>
					<li><a href="/${bcn_cd}/service/qna">1:1 이메일 문의 관리</a></li>
					
				</ul>
			</li>
			<li>
				<a href="/${bcn_cd}/account/actionLog">계정 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/account/actionLog">관리자 활동이력 조회</a></li>
					<li data-auth="system,starfield,store_master,handle_personal_info"><a href="/${bcn_cd}/account/admins">관리자 계정 관리</a></li>
				</ul>
			</li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a href="/${bcn_cd}/operation/holiday">운영관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/operation/holiday">스타필드 비정기운영일 관리</a></li>
					<li data-auth="system"><a href="/${bcn_cd}/operation/appVer">APP 버전 관리</a></li>
				</ul>
			</li>
			<li data-auth="system">
				<a href="/${bcn_cd}/category">매장 카테고리 관리</a>
			</li>
			<li data-auth="system">
				<a href="/${bcn_cd}/language">다국어 Page 관리</a>
			</li>
			<li>
				<a >ITO 관리</a>
				<ul class="sub-menu">
					<!-- <li><a href="/${bcn_cd}/ITO/appLog">APP 로그 관리</a></li> -->
					<li><a href="/${bcn_cd}/ITO/locInfoManage">APP 개인 위치정보 삭제접수 관리</a></li>
					<li><a href="/${bcn_cd}/ITO/locInfoManagePrc">사용자 위치정보 삭제 관리</a></li>
					<li><a href="/${bcn_cd}/ITO/locationUses">위치정보 이용 및 제공 내역</a>
					<li><a href="/${bcn_cd}/location/reqViews">위치정보 열람 요청 및 내역</a>
				</ul>
			</li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a >지도 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/tenant/mapping">운영정보 테넌트 매핑</a></li>
					<li><a href="/${bcn_cd}/lbsZone/mapping">LBS Zone 매핑</a></li>
					<li><a href="/${bcn_cd}/faci/mapping">LBS 편의시설 매핑</a></li>
					<li><a href="https://ssglbs.shinsegae-inc.com/admin/login.do" target="_blank">지도 데이터 업로드</a></li>
				</ul>
			</li> 
			<li data-auth="system,starfield,store_master,store_sub">
				<a href="/${bcn_cd}/operation/pushes">APP Push</a>
				<ul class="sub-menu">
					<li>
						<a href="/${bcn_cd}/operation/pushes">APP Push 관리</a>
					</li>
					<li>
						<a href="/${bcn_cd}/welcome/push">웰컴메시지 푸시 관리</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/welcome/push">웹컴메시지 목록</a></li>
							<li><a href="/${bcn_cd}/welcome/reqPush">웹컴메시지 등록</a></li>
						</ul> 
					</li>
					<li>
						<a href="/${bcn_cd}/scenario/push">시나리오 기반 쿠폰 푸시</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/scenario/push">시나리오 기반 푸시 목록</a></li>
							<li><a href="/${bcn_cd}/scenario/1">시나리오 기반 푸시 등록</a></li>
						</ul>
					</li>
					<li>
						<a href="/${bcn_cd}/outbound/push">아웃바운드 고객 푸시</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/outbound/push">아웃바운드 푸시 목록</a></li>
							<li><a href="/${bcn_cd}/outbound/2">아운바운드 푸시 등록</a></li>
						</ul>
					</li>
				</ul>
			</li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a href="/${bcn_cd}/banners">지도 배너 관리</a>
				<ul class="sub-menu">
					<li><a href="/${bcn_cd}/banners">지도 배너 목록</a></li>
					<li><a href="/${bcn_cd}/reqBanner">지도 배너 등록</a></li>
				</ul>
			</li>
			<li data-auth="system,starfield,store_master,store_sub">
				<a>앱 사용 통계</a>
				<ul class="sub-menu">
					<li><a >방문자 통계</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/stats/visitors">전체 방문자통계</a></li>
							<li><a href="/${bcn_cd}/stats/tenant/visitors">테넌트별 방문자 전체통계</a></li>
							<li><a href="/${bcn_cd}/stats/tenant/memb">테넌트별 방문자 회원기준통계</a></li>
						</ul>
					</li>
					<li><a href="/${bcn_cd}/stats/campaign/all">앱 캠페인 효과 분석</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/stats/campaign/all">전체 데이터</a></li>
							<li><a href="/${bcn_cd}/stats/campaign/memb">회원기준 통계</a></li>
						</ul>
					</li>
					<li><a href="/${bcn_cd}/stats/move">Heat Map</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/stats/move">이동경로분석</a></li>
							<%-- <li><a href="/${bcn_cd}/stats/campaign">전체 데이터</a></li>
							<li><a href="/${bcn_cd}/stats/campaign/memb">회원기준 통계</a></li> --%>
						</ul>
					</li>
					<li><a href="/${bcn_cd}/appStats/install">APP 통계</a>
						<ul class="menu-3depth">
							<li><a href="/${bcn_cd}/appStats/install">전체 누적 설치 통계</a></li>
							<li><a href="/${bcn_cd}/appTermStats/install">기간별 설치 통계</a></li>
						</ul>
					</li>
				</ul>
			</li>
		</ul>
	</div>

	<script src="/js/lib/gnb.js" type="text/javascript"></script>