<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
<c:forEach var="category" items="${categoryList}">
    <h1><u>${category.key} Books</u><h1>
    <c:forEach items="${category.value}" var="book">
       <div class="innergrid">
           <form id=${book.ISBN} action="/bookInfo" method="post">
        <input type="hidden" name="ISBN" value="${book.ISBN}">
        <a href="#" onclick="document.getElementById('${book.ISBN}').submit();">
          <div>
             <img src= ${book.imageLink}> 
          </div>
          <div class="innergrid-text">
             Title: ${book.title}</td>
          </div>
          <div class="innergrid-text">
            Author: ${book.author}</td>
          </div>
      </div>
      </a>
      </form>
    </c:forEach>
</c:forEach>

<%@ include file="common/footer.jspf"%>
