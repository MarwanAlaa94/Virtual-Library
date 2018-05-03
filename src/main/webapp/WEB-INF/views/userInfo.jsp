<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class ="card">
<div style = "margin-left: 40%;">
  <img src="${user.pictureUrl}" alt="John" style="width:200px">
  <h1>${user.name}</h1>
  <h4>${user.email}</h4>
</div>
</div>

<div>
<c:forEach var="category" items="${UserInfo}">
         <h2>${category.key}</h2>
        <div class="outer">
        <c:forEach items="${category.value}" var="book">
        <div class="inner">
         <form id=${book.ISBN} action="/bookInfo" method="post">
        <input type="hidden" name="ISBN" value="${book.ISBN}">
        <a href="#" onclick="document.getElementById('${book.ISBN}').submit();">
           <div>
              <img src= ${book.imageLink}> 
           </div>
           <div>
               Title: ${book.title}</td>
           </div>
           <div>
               Author: ${book.author}</td>
           </div>
        </div>
        </a>
         </form>
       </c:forEach>
       </div>
</c:forEach>
</div>
<style>
  .hide { position:absolute; top:-1px; left:-1px; width:1px; height:1px; }
</style>


<%@ include file="common/footer.jspf"%>	