<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Volunteer Call</title>
    <meta charset="UTF-8">

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(to right, #1b4332, #52b788);
            min-height: 100vh;
        }

        .container {
            width: 750px;
            background: #f4f7ef;
            margin: 40px auto;
            padding: 45px;
            border-radius: 25px;
        }

        h1 {
            text-align: center;
            color: #1b4332;
            font-size: 42px;
            margin-bottom: 30px;
        }

        label {
            font-weight: bold;
            color: #222;
            display: block;
            margin-bottom: 10px;
            font-size: 18px;
        }

        input,
        textarea {
            width: 100%;
            padding: 16px;
            margin-bottom: 25px;
            border: 1px solid #ccc;
            border-radius: 10px;
            font-size: 16px;
            font-family: Arial, sans-serif;
            box-sizing: border-box;
        }

        textarea {
            height: 140px;
            resize: vertical;
        }

        .hint {
            color: #1b4332;
            font-weight: bold;
            margin-top: -15px;
            margin-bottom: 25px;
        }

        .error {
            background: #ffebee;
            color: #d32f2f;
            border: 1px solid #ef9a9a;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-weight: bold;
        }

        button {
            width: 100%;
            padding: 16px;
            background: green;
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 20px;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background: #007000;
        }

        .cancel {
            display: block;
            text-align: center;
            margin-top: 25px;
            color: #1b4332;
            font-weight: bold;
            text-decoration: none;
            font-size: 17px;
        }
    </style>
</head>

<body>

<div class="container">

    <h1>Add Volunteer Call</h1>

    <% if ("hours".equals(request.getParameter("error"))) { %>
        <div class="error">
            ❗ Hours must be between 1 and 8. Please enter a valid number.
        </div>
    <% } %>

    <% if ("database".equals(request.getParameter("error"))) { %>
        <div class="error">
            ❗ Failed to save volunteer call. Please try again.
        </div>
    <% } %>

    <form action="AddActivityServlet" method="post">

        <label>Title</label>
        <input type="text"
               name="title"
               placeholder="Enter volunteer call title"
               required>

        <label>Description</label>
        <textarea name="description"
                  placeholder="Enter activity description"
                  required></textarea>

        <label>Date</label>
        <input type="date"
               name="activityDate"
               required>

        <label>Required Hours</label>
        <input type="number"
               name="hourOffered"
               id="hourOffered"
               placeholder="Enter required hours"
               min="1"
               max="8"
               step="1"
               required
               oninput="validateHours(this)">

        <div class="hint">(maximum 8 hours)</div>

        <div id="hourError" class="error" style="display:none;">
            ❗ Hours must be between 1 and 8. Please enter a valid number.
        </div>

        <button type="submit">Save Volunteer Call</button>

    </form>

    <a href="ListActivityServlet" class="cancel">Cancel</a>

</div>

<script>
function validateHours(input) {
    var value = Number(input.value);
    var errorBox = document.getElementById("hourError");

    if (value > 8) {
        input.value = 8;
        errorBox.style.display = "block";
    } else if (value < 1 && input.value !== "") {
        errorBox.style.display = "block";
    } else {
        errorBox.style.display = "none";
    }
}
</script>

</body>
</html>