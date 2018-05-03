<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="search-block">
<form action="/search" method="POST">
    <p style="color:#f3efdb">Search by:</hp><br>
     <div>
     <select style"margin-bottom:14px; " name="key">
        <option value="Title">Title</option>
        <option value="Category">Category</option>
        <option value="Author">Author</option>
        <option value="isbn">ISBN</option>
     </select>
     </div>
    <br>
    <input type="text" name="value" value="">
    <br><br>
    <input type="submit" value="Search">
</form>
</div>

<div>
<c:forEach var="category" items="${categoryList}">
    <form id=${category.key} action="/bookGrid" method="post">
        <input type="hidden" name="category" value="${category.key}">
        <a href="#" onclick="document.getElementById('${category.key}').submit();">
         <h2>${category.key}</h2></a>
    </form>
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
<%@ include file="common/footer.jspf"%>
