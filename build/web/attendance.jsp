<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.esukarelawan.model.ActivityBean"%>
<%@page import="com.esukarelawan.model.ParticipationBean"%>

<%
    ActivityBean activity = (ActivityBean) request.getAttribute("activity");
    List<ParticipationBean> participantList =
            (List<ParticipationBean>) request.getAttribute("participantList");

    if (activity == null) {
        response.sendRedirect("ListActivityServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Volunteer Attendance</title>
    <meta charset="UTF-8">

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #1b4332, #52b788);
            min-height: 100vh;
        }

        .container {
            width: 850px;
            background: #f4f7ef;
            margin: 40px auto;
            padding: 40px;
            border-radius: 25px;
        }

        h1 {
            text-align: center;
            color: #1b4332;
            font-size: 38px;
            margin-bottom: 25px;
        }

        .info-box {
            background: white;
            padding: 25px;
            border-radius: 15px;
            margin-bottom: 30px;
            border-left: 6px solid #1b4332;
        }

        .info-box p {
            font-size: 18px;
            margin: 10px 0;
        }

        .info-box strong {
            color: #1b4332;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 15px;
            overflow: hidden;
            margin-bottom: 30px;
        }

        th {
            background: #1b4332;
            color: white;
            padding: 15px;
            text-align: left;
            font-size: 16px;
        }

        td {
            padding: 15px;
            border-bottom: 1px solid #ddd;
            font-size: 16px;
        }

        tr:hover {
            background: #f1f8f4;
        }

        .checkbox {
            width: 22px;
            height: 22px;
            cursor: pointer;
        }

        .button-group {
            display: flex;
            gap: 15px;
        }

        button, .back-btn {
            flex: 1;
            padding: 15px;
            border: none;
            border-radius: 10px;
            font-size: 18px;
            font-weight: bold;
            text-align: center;
            cursor: pointer;
            text-decoration: none;
        }

        button {
            background: green;
            color: white;
        }

        button:hover {
            background: #007000;
        }

        .back-btn {
            background: #9e9e9e;
            color: white;
        }

        .back-btn:hover {
            background: #757575;
        }

        .empty {
            text-align: center;
            color: #777;
            font-style: italic;
            padding: 25px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Volunteer Attendance</h1>

    <div class="info-box">
        <p><strong>Volunteer Call:</strong> <%= activity.getTitle() %></p>
        <p><strong>Date:</strong> <%= activity.getActivityDate() %></p>
        <p><strong>Hours Offered:</strong> <%= (int) activity.getHourOffered() %> Hours</p>
    </div>

    <form action="SaveAttendanceServlet" method="post">

        <input type="hidden" name="activityId" value="<%= activity.getActivityId() %>">
        <input type="hidden" name="hourOffered" value="<%= activity.getHourOffered() %>">

        <table>
            <tr>
                <th>Student Name</th>
                <th>Attendance</th>
            </tr>

            <% if (participantList == null || participantList.isEmpty()) { %>

                <tr>
                    <td colspan="2" class="empty">No students have joined this volunteer call yet.</td>
                </tr>

            <% } else {
                for (ParticipationBean p : participantList) {
            %>

                <tr>
                    <td><%= p.getVolunteerName() %></td>
                    <td>
                        <input type="checkbox"
                               class="checkbox"
                               name="attended"
                               value="<%= p.getVolunteerId() %>"
                               <% if ("Completed".equalsIgnoreCase(p.getStatus())) { %> checked <% } %>>
                    </td>
                </tr>

            <% } } %>
        </table>

        <div class="button-group">
            <button type="submit">💾 Save Attendance</button>
            <a href="ListActivityServlet" class="back-btn">Back</a>
        </div>

    </form>

</div>

</body>
</html>