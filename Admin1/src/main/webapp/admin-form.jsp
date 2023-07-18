<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin Management Application</title>
		<link rel="stylesheet"
		href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
		crossorigin="anonymous">
	</head>
	<body>
		<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: tomato">
			<div>
				<a href="https://www.java.main" class="navbar-brand"> Admin
					Management App </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Admins</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${admin != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${admin == null}">
					<form action="insert" method="post">
				</c:if>

				<caption>
					<h2>
						<c:if test="${admin != null}">
                                    Edit Admin
                                </c:if>
						<c:if test="${admin == null}">
                                    Add New Admin
                                </c:if>
					</h2>
				</caption>

				<c:if test="${admin != null}">
					<input type="hidden" name="id"
						value="<c:out value='${admin.id}' />" />
				</c:if>

				<fieldset class="form-group">
					<label>Admin Name</label> <input type="text"
						value="<c:out value='${admin.name}' />" class="form-control"
						name="name" required="required">
				</fieldset>

				<fieldset class="form-group">
					<label>Admin Email</label> <input type="text"
						value="<c:out value='${admin.email}' />" class="form-control"
						name="email">
				</fieldset>

				<fieldset class="form-group">
					<label>Admin Password</label> <input type="password"
						value="<c:out value='${admin.password}' />" class="form-control"
						name="password">
				</fieldset>

				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>