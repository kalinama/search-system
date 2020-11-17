<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Search System">
    <div class="card-body">
        <div class="d-flex flex-row">
            <div class="p-2 " style="text-align: center;">
                <h4>Введите свой запрос:</h4>
                <form action="${pageContext.request.contextPath}/search" method="get" autocomplete="off"
                      id="searchForm">
                    <div class="input-append">
                        <input style="min-width: 900px; text-align: center" class="form-control" id="query" type="text"
                               name="query" required="required" value="${param.query}"/>
                        <button class="btn btn-block" type="submit"> Найти</button>
                    </div>
                </form>
            </div>

            <div class="mr-auto p-2" style="min-width: 100px"></div>
            <div class="p-2  border rounded border-dark ">
                <h4>Загрузите новые файлы:</h4>
                <form action="${pageContext.request.contextPath}/document/upload" method="post" autocomplete="off"
                      enctype="multipart/form-data" id="docForm">
                    <div class="form-group attachment">
                        <input type="file" name="documents" multiple/>
                    </div>
                    <button class="btn" type="submit" style="background: #ffe8c6; ">
                        Загрузить
                    </button>
                </form>
            </div>
        </div>

        <c:if test="${documents ne null}">
<span id="searchResult">
    <h4>Найдено ${documents.size()} документа(ов).</h4>
            <h5>  Время поиска: ${searchTime} с.</h5>
    <c:choose>
        <c:when test="${empty documents}">
            <h3 id="noresults">Не найдено результатов по запросу.</h3>
        </c:when>
        <c:otherwise>
            <table style="color: #5e253f" class="table table-striped table-hover">
                <tr>
                    <th>Документ</th>
                    <th>Релевантность</th>
                    <th></th>
                    <th>Дата загрузки</th>
                    <th>Время загрузки</th>
                </tr>
                <c:forEach var="document" items="${documents}">
                    <tr>
                        <td><a style="color:#5e253f;"
                               href="${pageContext.request.contextPath}/document/${document.document.id}">${document.document.title}</a></td>
                        <td>${document.rank}</td>
                        <td>${document.snippet}</td>
                        <td>${document.document.date}</td>
                        <td>${document.document.time}</td>
                    </tr>
                </c:forEach>
            </table>

        </c:otherwise>
    </c:choose>

    <h3>Метрики:</h3>
    <table style="color:#5e253f;" class="table table-hover">
        <tr>
            <td>Количество релевантных документов</td>
            <td>${documents.size()}</td>
        </tr>
        <tr>
            <td>Полнота</td>
            <td>1</td>
        </tr>
        <tr>
            <td>Точность</td>
            <td>1</td>
        </tr>
        <tr>
            <td>Аккуратность</td>
            <td>1</td>
        </tr>
        <tr>
            <td>Ошибка</td>
            <td>0</td>
        </tr>
        <tr>
            <td>F-мера</td>
            <td>1</td>
        </tr>
    </table>
</span>
        </c:if>
    </div>

</tags:master>

