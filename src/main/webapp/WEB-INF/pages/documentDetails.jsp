<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Document Details">
    <div class="card-body"><h3 style="text-align: center">${document.title}</h3>
        <h5>Дата загрузки: ${document.date} ${document.time}</h5>
        <div style="text-align: center">${document.text}</div>
    </div>
</tags:master>

