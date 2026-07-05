<%@page import="java.util.List"%>
<%@page import="com.esukarelawan.model.ActivityBean"%>
<%@page import="com.esukarelawan.model.OrganizerBean"%>
<%@page import="com.esukarelawan.model.ParticipationBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    OrganizerBean organizer = (OrganizerBean) request.getAttribute("organizer");
    List<ActivityBean> activityList = (List<ActivityBean>) request.getAttribute("activityList");
    List<ParticipationBean> participationList = (List<ParticipationBean>) request.getAttribute("participationList");

    Integer totalActivitiesObj = (Integer) request.getAttribute("totalActivities");
    Integer totalVolunteersObj = (Integer) request.getAttribute("totalVolunteers");
    Number totalHoursObj = (Number) request.getAttribute("totalHours");

    int totalActivities = totalActivitiesObj != null ? totalActivitiesObj : 0;
    int totalVolunteers = totalVolunteersObj != null ? totalVolunteersObj : 0;
    int totalHours = totalHoursObj != null ? totalHoursObj.intValue() : 0;

    int notificationCount = 0;
    if (participationList != null) {
        for (ParticipationBean p : participationList) {
            if ("Pending".equalsIgnoreCase(p.getStatus())) {
                notificationCount++;
            }
        }
    }

    String organizerName = "Organizer";
    if (organizer != null && organizer.getFullName() != null) {
        organizerName = organizer.getFullName();
    }

    String initials = "OR";
    if (organizerName != null && organizerName.trim().length() > 0) {
        String[] parts = organizerName.trim().split(" ");
        if (parts.length >= 2) {
            initials = parts[0].substring(0, 1).toUpperCase() + parts[1].substring(0, 1).toUpperCase();
        } else {
            initials = organizerName.substring(0, 1).toUpperCase();
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>E-Sukarelawan | Organizer Dashboard</title>

    <style>
        *{margin:0;padding:0;box-sizing:border-box;}
        body{font-family:Arial,sans-serif;background-color:#f8f9fa;color:#333;}
        .sidebar{position:fixed;left:0;top:0;width:260px;height:100vh;background:linear-gradient(135deg,#1e5631 0%,#2a6f3e 100%);padding:2rem 0;overflow-y:auto;z-index:1000;box-shadow:0 5px 15px rgba(0,0,0,0.1);}
        .sidebar-brand{padding:0 1.5rem 2rem;border-bottom:1px solid rgba(255,255,255,0.15);margin-bottom:2rem;}
        .logo{font-size:1.3rem;font-weight:700;color:#a4de02;text-transform:uppercase;letter-spacing:1px;}
        .sidebar-nav{list-style:none;padding:0;}
        .sidebar-nav a{display:flex;align-items:center;padding:1rem 1.5rem;color:rgba(255,255,255,0.75);text-decoration:none;border-left:3px solid transparent;transition:all .3s ease;font-size:.95rem;}
        .sidebar-nav a:hover,.sidebar-nav a.active{background-color:rgba(164,222,2,0.1);color:#a4de02;border-left-color:#a4de02;}
        .sidebar-nav-icon{width:20px;margin-right:1rem;text-align:center;}
        .main-content{margin-left:260px;min-height:100vh;background-color:#f8f9fa;}
        .top-navbar{background:linear-gradient(135deg,#1e5631 0%,#a4de02 100%);box-shadow:0 5px 15px rgba(0,0,0,0.1);position:sticky;top:0;z-index:100;padding:1rem 2rem;display:flex;align-items:center;justify-content:space-between;gap:2rem;}
        .navbar-brand{color:white;font-weight:700;font-size:1.2rem;}
        .search-box{background:white;border-radius:8px;padding:.5rem 1rem;display:flex;align-items:center;gap:.5rem;flex:1;max-width:300px;}
        .search-box input{border:none;background:transparent;outline:none;font-size:.95rem;width:100%;}
        .navbar-right{display:flex;align-items:center;gap:1.5rem;margin-left:auto;}
        .notification-icon{font-size:1.2rem;color:white;cursor:pointer;position:relative;}
        .notification-dropdown{display:none;position:absolute;right:0;top:38px;width:330px;background:white;color:#333;border-radius:10px;box-shadow:0 8px 25px rgba(0,0,0,.2);z-index:2000;overflow:hidden;}
        .notification-dropdown.show{display:block;}
        .notification-title{padding:1rem;font-weight:bold;color:#1e5631;border-bottom:1px solid #eee;background:#f8f9fa;}
        .notification-item{padding:.9rem 1rem;border-bottom:1px solid #f0f0f0;font-size:.9rem;line-height:1.4;}
        .notification-item:last-child{border-bottom:none;}
        .notification-item strong{color:#1e5631;}
        .notification-empty{padding:1rem;color:#777;font-style:italic;text-align:center;font-size:.9rem;}
        .notification-badge{background:#e74c3c;color:white;width:24px;height:24px;border-radius:50%;display:flex;align-items:center;justify-content:center;font-size:.8rem;font-weight:bold;position:absolute;top:-8px;right:-8px;}
        .user-profile{display:flex;align-items:center;gap:.8rem;background:rgba(255,255,255,0.15);padding:.5rem 1rem;border-radius:8px;cursor:pointer;}
        .user-avatar{width:36px;height:36px;border-radius:50%;background:rgba(255,255,255,0.3);display:flex;align-items:center;justify-content:center;color:white;font-weight:bold;}
        .user-info{color:white;font-size:.9rem;}
        .user-name{font-weight:bold;display:block;}
        .user-role{font-size:.8rem;opacity:.9;display:block;}
        .btn-logout{background:#1b4332;color:white;padding:.6rem 1rem;border-radius:8px;text-decoration:none;font-weight:600;transition:all .3s;display:inline-block;font-size:.95rem;}
        .btn-logout:hover{background:#2a5c3c;box-shadow:0 4px 10px rgba(27,67,50,.3);transform:translateY(-2px);}
        .container{padding:2rem;max-width:1400px;margin:0 auto;}
        .page-header{margin-bottom:2rem;}
        .page-title{font-size:1.8rem;font-weight:bold;color:#2c3e50;margin-bottom:.5rem;}
        .page-subtitle{color:#999;font-size:.95rem;}
        .stats-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:1.5rem;margin-bottom:2rem;}
        .stat-card{background:white;border-radius:12px;padding:1.5rem;border-left:5px solid;box-shadow:0 2px 8px rgba(0,0,0,0.08);transition:all .3s;}
        .stat-card:hover{transform:translateY(-5px);box-shadow:0 10px 25px rgba(0,0,0,0.15);}
        .stat-content{display:flex;gap:1.5rem;align-items:center;}
        .stat-icon{width:60px;height:60px;border-radius:10px;display:flex;align-items:center;justify-content:center;font-size:1.8rem;flex-shrink:0;}
        .stat-value{font-size:2.5rem;font-weight:700;color:#2c3e50;}
        .stat-label{color:#999;font-size:.9rem;}
        .stat-activities{border-left-color:#1e5631;}
        .stat-activities .stat-icon{background:rgba(30,86,49,.1);color:#1e5631;}
        .stat-hours{border-left-color:#a4de02;}
        .stat-hours .stat-icon{background:rgba(164,222,2,.1);color:#a4de02;}
        .stat-participants{border-left-color:#3498db;}
        .stat-participants .stat-icon{background:rgba(52,152,219,.1);color:#3498db;}
        .section{background:white;border-radius:12px;padding:1.5rem;box-shadow:0 2px 8px rgba(0,0,0,0.08);margin-bottom:1.5rem;animation:slideIn .5s ease-out;}
        .section-header{border-bottom:2px solid #f8f9fa;padding-bottom:1.5rem;margin-bottom:1.5rem;display:flex;justify-content:space-between;align-items:center;}
        .section-title{font-size:1.3rem;color:#1e5631;font-weight:700;display:flex;align-items:center;gap:.8rem;margin:0;}
        .section-title-icon{color:#a4de02;font-size:1.4rem;}
        .btn-primary{background:linear-gradient(135deg,#1e5631 0%,#2a6f3e 100%);color:white;border:none;padding:.75rem 1.5rem;border-radius:8px;font-weight:600;transition:all .3s;display:inline-flex;align-items:center;gap:.5rem;cursor:pointer;font-size:.95rem;text-decoration:none;}
        .btn-primary:hover{transform:translateY(-2px);box-shadow:0 8px 20px rgba(30,86,49,.3);}
        .btn-sm{display:inline-flex;align-items:center;justify-content:center;gap:6px;min-width:120px;height:42px;padding:0 16px;border:1px solid #dcdcdc;border-radius:6px;background:#fff;color:#333;font-size:14px;font-weight:500;text-decoration:none;cursor:pointer;transition:.25s;white-space:nowrap;}
        .btn-sm:hover{background:#f5f5f5;border-color:#bdbdbd;}
        .btn-icon{min-width:42px;width:42px;height:42px;padding:0;}
        .btn-delete{color:#d9534f;}
        .action-buttons{display:flex;align-items:center;gap:.6rem;flex-wrap:nowrap;}
        .action-form{margin:0;display:inline-flex;}
        .activity-link{color:#1e5631;text-decoration:none;font-weight:bold;}
        .activity-link:hover{color:#2a6f3e;text-decoration:underline;}
        table{width:100%;border-collapse:collapse;}
        thead{background:#f8f9fa;}
        th{padding:1rem;text-align:left;font-weight:600;color:#666;font-size:.9rem;border-bottom:2px solid #e0e0e0;}
        td{padding:1rem;border-bottom:1px solid #f0f0f0;}
        tbody tr:hover{background:#f8f9fa;}
        small{color:#999;font-size:.85rem;}
        .badge{display:inline-block;padding:.4rem .8rem;border-radius:20px;font-size:.8rem;font-weight:600;}
        .badge-completed{background:#e8f5e9;color:#2e7d32;}
        .badge-pending{background:#fff3e0;color:#e65100;}
        .empty{text-align:center;color:#777;padding:2rem;font-style:italic;}
        @keyframes slideIn{from{opacity:0;transform:translateY(20px);}to{opacity:1;transform:translateY(0);}}
        @media(max-width:768px){.sidebar{width:0;display:none;}.main-content{margin-left:0;}.top-navbar{padding:1rem;flex-direction:column;gap:1rem;}.search-box{max-width:100%;}.navbar-right{width:100%;margin-left:0;flex-wrap:wrap;}.container{padding:1rem;}.stats-grid{grid-template-columns:1fr;gap:1rem;}.section-header{flex-direction:column;align-items:flex-start;gap:1rem;}table{font-size:.85rem;}th,td{padding:.75rem;}}
    </style>
</head>

<body>

<aside class="sidebar">
    <div class="sidebar-brand">
        <div class="logo">E-Sukarelawan</div>
    </div>

    <ul class="sidebar-nav">
        <li><a href="ListActivityServlet" class="active"><span class="sidebar-nav-icon">📊</span> Dashboard</a></li>
        <li><a href="#activities"><span class="sidebar-nav-icon">✓</span> Volunteer Calls</a></li>
        <li><a href="#participation"><span class="sidebar-nav-icon">👥</span> Participation</a></li>
    </ul>
</aside>

<div class="main-content">
    <nav class="top-navbar">
        <div class="navbar-brand">Dashboard</div>

        <div class="search-box">
            <span>🔍</span>
            <input type="text" id="searchInput" placeholder="Search activities..." onkeyup="searchDashboard()">
        </div>

        <div class="navbar-right">
            <div class="notification-icon" onclick="toggleNotifications()">
                <span>🔔</span>
                <% if (notificationCount > 0) { %>
                    <span class="notification-badge"><%= notificationCount %></span>
                <% } %>

                <div class="notification-dropdown" id="notificationDropdown" onclick="event.stopPropagation();">
                    <div class="notification-title">Notifications</div>

                    <% if (participationList != null && notificationCount > 0) {
                        for (ParticipationBean p : participationList) {
                            if ("Pending".equalsIgnoreCase(p.getStatus())) {
                    %>
                        <div class="notification-item">
                            • <strong><%= p.getVolunteerName() %></strong> joined
                            <strong><%= p.getActivityTitle() %></strong>
                        </div>
                    <%      }
                        }
                    } else { %>
                        <div class="notification-empty">No new notifications.</div>
                    <% } %>
                </div>
            </div>

            <div class="user-profile">
                <div class="user-avatar"><%= initials %></div>
                <div class="user-info">
                    <span class="user-name"><%= organizerName %></span>
                    <span class="user-role">Organizer</span>
                </div>
            </div>

            <a href="LogoutServlet" class="btn-logout">🚪 Logout</a>
        </div>
    </nav>

    <div class="container">
        <div class="page-header">
            <h2 class="page-title">Welcome, <%= organizerName %>!</h2>
            <p class="page-subtitle">Here's an overview of your volunteer activities and participation.</p>
        </div>

        <div class="stats-grid">
            <div class="stat-card stat-activities">
                <div class="stat-content">
                    <div class="stat-icon">📅</div>
                    <div>
                        <div class="stat-value"><%= totalActivities %></div>
                        <div class="stat-label">Total Activities</div>
                    </div>
                </div>
            </div>

            <div class="stat-card stat-hours">
                <div class="stat-content">
                    <div class="stat-icon">⏳</div>
                    <div>
                        <div class="stat-value"><%= totalHours %></div>
                        <div class="stat-label">Community Hours</div>
                    </div>
                </div>
            </div>

            <div class="stat-card stat-participants">
                <div class="stat-content">
                    <div class="stat-icon">👫</div>
                    <div>
                        <div class="stat-value"><%= totalVolunteers %></div>
                        <div class="stat-label">Total Volunteers</div>
                    </div>
                </div>
            </div>
        </div>

        <div class="section" id="activities">
            <div class="section-header">
                <h5 class="section-title"><span class="section-title-icon">📢</span> Volunteer Calls Management</h5>
                <a href="addActivity.jsp" class="btn-primary">➕ Create Volunteer Call</a>
            </div>

            <div style="overflow-x:auto;">
                <table id="activityTable">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Description</th>
                            <th>Date</th>
                            <th>Hours</th>
                            <th>Organizer</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (activityList == null || activityList.isEmpty()) { %>
                            <tr><td colspan="6" class="empty">No volunteer calls found.</td></tr>
                        <% } else { for (ActivityBean activity : activityList) { %>
                            <tr>
                                <td>
                                    <%= activity.getTitle() %>
                                </td>
                                <td><small><%= activity.getDescription() %></small></td>
                                <td><%= activity.getActivityDate() %></td>
                                <td><%= (int) activity.getHourOffered() %></td>
                                <td><%= activity.getOrganizerName() %></td>
                                <td>
                                    <div class="action-buttons">
                                        <a href="AttendanceServlet?id=<%= activity.getActivityId() %>" class="btn-sm">✅ Attendance</a>
                                        <a href="EditActivityServlet?id=<%= activity.getActivityId() %>" class="btn-sm btn-icon" title="Edit">✏️</a>
                                        <a href="DeleteActivityServlet?id=<%= activity.getActivityId() %>" class="btn-sm btn-icon btn-delete" title="Delete" onclick="return confirm('Are you sure you want to delete this volunteer call?');">🗑️</a>
                                    </div>
                                </td>
                            </tr>
                        <% } } %>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="section" id="participation">
            <div class="section-header">
                <h5 class="section-title"><span class="section-title-icon">📋</span> Student Participation</h5>
            </div>

            <div style="overflow-x:auto;">
                <table id="participationTable">
                    <thead>
                        <tr>
                            <th>Activity</th>
                            <th>Total Participants</th>
                            <th>Status</th>
                            <th>Hours Earned</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if (participationList == null || participationList.isEmpty()) { %>
                            <tr><td colspan="5" class="empty">No participation records found.</td></tr>
                        <% } else { for (ParticipationBean p : participationList) {
                            String badgeClass = "badge-pending";
                            if ("Completed".equalsIgnoreCase(p.getStatus())) {
                                badgeClass = "badge-completed";
                            }
                        %>
                            <tr>
                                <td><%= p.getActivityTitle() %></td>
                                <td><%= p.getTotalParticipants() %></td>
                                <td><span class="badge <%= badgeClass %>"><%= p.getStatus() %></span></td>
                                <td><%= p.getHourEarned() %></td>
                                <td>
                                    <form action="ActivityParticipationDetailServlet" method="get" class="action-form">
                                        <input type="hidden" name="activityId" value="<%= p.getActivityId() %>">
                                        <button type="submit" class="btn-sm">👁️ View Details</button>
                                    </form>
                                </td>
                            </tr>
                        <% } } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>


<script>
function searchDashboard() {
    var input = document.getElementById("searchInput");
    var filter = input.value.toLowerCase();

    filterTable("activityTable", filter);
    filterTable("participationTable", filter);
}

function filterTable(tableId, filter) {
    var table = document.getElementById(tableId);

    if (!table) {
        return;
    }

    var rows = table.getElementsByTagName("tr");

    for (var i = 1; i < rows.length; i++) {
        var rowText = rows[i].textContent || rows[i].innerText;

        if (rowText.toLowerCase().indexOf(filter) > -1) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
}
</script>


<script>
function toggleNotifications() {
    var dropdown = document.getElementById("notificationDropdown");
    if (dropdown) {
        dropdown.classList.toggle("show");
    }
}

window.onclick = function(event) {
    if (!event.target.closest(".notification-icon")) {
        var dropdown = document.getElementById("notificationDropdown");
        if (dropdown) {
            dropdown.classList.remove("show");
        }
    }
};
</script>

</body>
</html>
