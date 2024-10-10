<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Branch Manager Dashboard</title>
<link rel="stylesheet" href="../css/branchmanagerdash.css">
</head>
<body>

	<header>
		<h1>XYZ Bank - Branch Manager Dashboard</h1>
	</header>

	<!-- Navigation Bar -->
	<nav>
		<a href="/bank_mgr/dashboard">Dashboard</a> <a
			href="/bank_mgr/manageEmployees">Manage Employees</a> <a
			href="/bank_mgr/view-profile/${userDetails.getUsername()}">Profile</a>
		<a href="/logout">Logout</a>
	</nav>

	<div class="dashboard-container">
		<h2>Welcome, Branch Manager</h2>

		<!-- Overview Section -->
		<section class="overview">
			<h3>Branch Overview</h3>
			<div class="stats">
				<div class="stat">
					<h4>Total Customers</h4>
					<p>320</p>
				</div>
				<div class="stat">
					<h4>Total Employees</h4>
					<p>25</p>
				</div>
				<div class="stat">
					<h4>Branch Performance</h4>
					<p>85%</p>
				</div>
			</div>
		</section>

		<!-- Quick Actions Section -->
		<section class="quick-actions">
			<h3>Quick Actions</h3>
			<div class="actions">
				<button
					onclick="window.location.href = '/bank_mgr/manageEmployees';">Manage
					Customers</button>
				<button>View Approval List</button>
			</div>
		</section>
	</div>
	<%@include file="../message.jsp"%>

	<!-- Footer -->
	<footer>
		<p>© 2024 XYZ Bank. All rights reserved.</p>
	</footer>

</body>
</html>
