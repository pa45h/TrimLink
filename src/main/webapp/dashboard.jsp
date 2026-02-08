<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard | TrimLink</title>

    <style>
        * {
            box-sizing: border-box;
            font-family: "Segoe UI", Tahoma, Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: #0f1220;
            color: #e5e7eb;
        }

        .dashboard {
            max-width: 1100px;
            margin: 0 auto;
            padding: 24px;
        }

        /* Header */
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background: #15192d;
            padding: 16px 24px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.6);
            margin-bottom: 24px;
        }

        .header h2 {
            margin: 0;
            color: #f3f4f6;
            font-weight: 600;
        }

        .logout {
            text-decoration: none;
            background: #ff5f5f;
            color: #fff;
            padding: 8px 14px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
        }

        .logout:hover {
            background: #e04a4a;
        }

        /* Cards */
        .card {
            background: #15192d;
            padding: 24px;
            border-radius: 12px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.6);
            margin-bottom: 24px;
        }

        .card h3 {
            margin-top: 0;
            margin-bottom: 16px;
            color: #e5e7eb;
            font-weight: 600;
        }

        /* Form */
        .shorten-form {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .shorten-form input {
            flex: 1;
            padding: 10px 12px;
            border: 1px solid #2a2f4a;
            border-radius: 6px;
            font-size: 14px;
            background: #0f1220;
            color: #e5e7eb;
        }

        .shorten-form input::placeholder {
            color: #9ca3af;
        }

        .shorten-form input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.25);
        }

        .shorten-form button {
            padding: 10px 18px;
            border: none;
            border-radius: 6px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: #fff;
            font-weight: 600;
            cursor: pointer;
        }

        .shorten-form button:hover {
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        th {
            background: #1c2140;
            text-align: left;
            padding: 12px;
            font-size: 14px;
            color: #c7d2fe;
        }

        td {
            padding: 12px;
            font-size: 14px;
            border-top: 1px solid #2a2f4a;
            color: #e5e7eb;
            word-break: break-all;
        }

        td a {
            color: #93c5fd;
            text-decoration: none;
            font-weight: 500;
        }

        td a:hover {
            text-decoration: underline;
        }

        .delete-btn {
            background: #ff5f5f;
            border: none;
            color: #fff;
            padding: 6px 10px;
            border-radius: 6px;
            font-size: 13px;
            cursor: pointer;
        }

        .delete-btn:hover {
            background: #e04a4a;
        }

        .empty-text {
            color: #9ca3af;
            font-size: 14px;
        }

        .long-url {
            max-width: 220px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }


    </style>

</head>
<body>

<div class="dashboard">

    <div class="header">
        <h2>Welcome, ${sessionScope.username}</h2>
        <a href="logout" class="logout">Logout</a>
    </div>

    <div class="card">
        <h3>Shorten a URL</h3>
        <form method="post" action="shorten" class="shorten-form">
            <input type="url" name="originalUrl" placeholder="Enter long URL" required>
            <input type="text" name="customSlug" placeholder="Custom slug (optional)">
            <button type="submit">Shorten</button>
        </form>
    </div>

    <div class="card">
        <h3>Your Shortened URLs</h3>

        <c:if test="${empty urls}">
            <p class="empty-text">No URLs found.</p>
        </c:if>

        <c:if test="${not empty urls}">
            <table>
                <tr>
                    <th>Original URL</th>
                    <th>Short URL</th>
                    <th>Action</th>
                </tr>

                <c:forEach var="url" items="${urls}">
                    <tr>
                        <td class="long-url" title="${url.longUrl}">
                                ${url.longUrl}
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/url/${url.shortUrl}" target="_blank">
                                    ${url.shortUrl}
                            </a>
                        </td>
                        <td>
                            <form method="post" action="delete">
                                <input type="hidden" name="urlId" value="${url.urlId}">
                                <button type="submit" class="delete-btn">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </div>

</div>

</body>
</html>
