<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/user.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>

$(document).ready(function() {
    console.log("AJAX 요청 준비 완료");

    var el = $("#btn-check");
    el.click(function() {
        var email = $("#email").val();
        console.log("AJAX 요청 URL: ", "${pageContext.request.contextPath}/api/user/checkemail?email=" + email);

        if (email === "") {
            console.log("이메일 입력값 없음");
            return;
        }

        $.ajax({
            url: "${pageContext.request.contextPath}/api/user/checkemail?email=" + email,
            type: "get",
            dataType: "json",
            success: function(response) {
                console.log("AJAX 응답: ", response);

                if (response.result !== "success") {
                    console.error("응답 실패: ", response.message);
                    return;
                }

                console.log("response.data.exist 값: ", response.data.exist);
                if (response.data.exist) {
                    alert("이메일이 존재합니다. 다른 이메일을 사용해 주세요.");
                    $("#email").val("");
                    $("#email").focus();
                    return;
                }

                console.log("여기 걸림? 왜 출력이 안돼");
                $("#img-check").show();
                $("#btn-check").hide();
            },
            error: function(xhr, status, err) {
                console.error("AJAX 요청 실패: ", err);
            }
        });
    });
});

</script>
</head>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">
				<form:form modelAttribute="userVo"
					action="${pageContext.request.contextPath}/user/join" method="post"
					id="join-form">
					<input type='hidden' name="a" value="join">

					<label class="block-label" for="name"> <spring:message
							code="user.join.label.name" /></label>
					<form:input path="name" />
					<p style="padding: 5px 0; margin: 0; color: #f00">
						<form:errors path="name" />
					</p>

					<spring:message code="user.join.label.email.check"
						var="userJoinLabelEmailCheck" />
					<label class="block-label" for="email"><spring:message
							code="user.join.label.email" /></label>
					<form:input path="email" />
					<input id="btn-check" type="button"
						value="${userJoinLabelEmailCheck }" style="display:;">
					<img id="img-check"
						src="${pageContext.request.contextPath}/assets/images/check.png"
						style="vertical-align: bottom; width: 24px; display: none">
					<p style="color: #f00; text-align: left; padding: 0">
						<form:errors path="email" />
					</p>


					<label class="block-label"> <spring:message
							code="user.join.label.password" /></label>
					<form:password path="password" />
					<p style="padding: 5px 0; margin: 0; color: #f00">
						<form:errors path="password" />
					</p>

					<fieldset>
						<legend>
							<spring:message code="user.join.label.gender" />
						</legend>
						<label> <spring:message
								code="user.join.label.gender.female" />
						</label> <input type="radio" name="gender" value="female"
							checked="checked"> <label> <spring:message
								code="user.join.label.gender.male" />
						</label> <input type="radio" name="gender" value="male">
					</fieldset>

					<fieldset>
						<legend>
							<spring:message code="user.join.label.terms" />
						</legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label><spring:message
								code="user.join.label.terms.message" /></label>
					</fieldset>

					<input type="submit"
						value="<spring:message code='user.join.label.terms'/>">
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>