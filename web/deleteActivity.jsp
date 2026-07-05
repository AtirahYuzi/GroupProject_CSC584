<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.esukarelawan.model.ActivityBean"%>

<%
    ActivityBean activity = (ActivityBean) request.getAttribute("activity");

    if (activity == null) {
        response.sendRedirect("ListActivityServlet");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Delete Volunteer Call</title>
    <meta charset="UTF-8">

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #1b4332, #52b788);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .container {
            width: 500px;
            background: #f4f7ef;
            padding: 40px;
            border-radius: 25px;
            text-align: center;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
        }

        h1 {
            color: #1b4332;
            font-size: 2rem;
            margin-bottom: 1.5rem;
        }

        p {
            font-size: 1.1rem;
            color: #444;
            margin-bottom: 2rem;
        }

        .program {
            font-size: 1.5rem;
            font-weight: bold;
            margin: 2rem 0;
            padding: 1.5rem;
            background: #ffe0e0;
            color: #d32f2f;
            border-radius: 10px;
            border-left: 5px solid #d32f2f;
        }

        .warning {
            color: #e74c3c;
            font-size: 0.95rem;
            font-style: italic;
        }

        .button-group {
            display: flex;
            gap: 1rem;
            justify-content: center;
            margin-top: 2rem;
        }

        button, a {
            padding: 12px 30px;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: all 0.3s;
        }

        .delete-btn {
            background-color: #d32f2f;
            color: white;
            flex: 1;
        }

        .delete-btn:hover {
            background-color: #b71c1c;
            box-shadow: 0 5px 15px rgba(211, 47, 47, 0.3);
            transform: translateY(-2px);
        }

        .cancel-btn {
            background-color: #9e9e9e;
            color: white;
            flex: 1;
        }

        .cancel-btn:hover {
            background-color: #757575;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
            transform: translateY(-2px);
        }

        @media (max-width: 768px) {
            .container {
                width: 90%;
                padding: 25px;
            }

            h1 {
                font-size: 1.5rem;
            }

            .button-group {
                flex-direction: column;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Delete Volunteer Call</h1>

    <p>Are you sure you want to delete this volunteer call?</p>

    <div class="program">
        <%= activity.getTitle() %>
    </div>

    <p class="warning">This action cannot be undone.</p>

    <form action="DeleteActivityServlet" method="post">
        <input type="hidden" name="activityId" value="<%= activity.getActivityId() %>">

        <div class="button-group">
            <button type="submit" class="delete-btn">🗑️ Delete</button>
            <a href="ListActivityServlet" class="cancel-btn">Cancel</a>
        </div>
    </form>

</div>

</body>
</html>