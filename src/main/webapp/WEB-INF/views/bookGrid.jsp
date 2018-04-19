<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
<c:forEach var="category" items="${categoryList}">
    <h1><u>${category.key} Books</u><h1>
    <c:forEach items="${category.value}" var="book">
       <a style= "color: #f3efdb; "href="${book.googleMoreInfo}">
       <div class="innergrid">
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
    </c:forEach>
</c:forEach>

<%@ include file="common/footer.jspf"%>
