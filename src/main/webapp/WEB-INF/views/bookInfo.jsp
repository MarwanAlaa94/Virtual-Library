<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class ="card">


   
 <div class ="favbut">
      <iframe name="hiddenFrame0" class="hide"></iframe>
      <form action="/done" method="post" target="hiddenFrame0">
          <input type="hidden" name="ISBN" value="${book.ISBN}">
         <button type="submit" class="btn btn-default btn-sm">
         <span class="glyphicon glyphicon-ok"></span> Done
         </button>
      </form>
   </div>
   
     <div class ="favbut">
      <iframe name="hiddenFrame1" class="hide"></iframe>
      <form action="/addToRead" method="post" target="hiddenFrame1">
       <input type="hidden" name="ISBN" value="${book.ISBN}">
         <button type="submit" class="btn btn-default btn-sm">
         <span class="glyphicon glyphicon-floppy-disk"></span>Save to read
         </button>
      </form>
   </div>
    <div class ="favbut">
      <iframe name="hiddenFrame2" class="hide"></iframe>
      <form action="/addFav" method="post" target="hiddenFrame2">
       <input type="hidden" name="ISBN" value="${book.ISBN}">
         <button type="submit" class="btn btn-default btn-sm">
         <span class="glyphicon glyphicon-star"></span> Add to favourites
         </button>
      </form>
   </div>
   <div style="text-align: center;">
      <img style="width: 200px;margin-left: 23%;" src="${book.imageLink}">
      <h1>${book.title}</h1>
      <h4>by ${book.author}</h4>
      <h4>Book rating : ${book.averageRating}</h4>
       <div>
      <iframe name="hiddenFrame0" class="hide"></iframe>
      <form action="/rate" method="post" target="hiddenFrame0">
        <input type="hidden" name="ISBN" value="${book.ISBN}">
        <input  class="inputRate" type="text" name="rate" value="">
         <button type="submit" class="btn btn-default btn-sm">
         <span class="glyphicon glyphicon-ok"></span> Rate
         </button>
      </form>
   </div>
   </div>
  
</div>
<div style = "display: inline-block; padding-top=20px;">
   <h2><u>Description</u></h2>
   <div class ="dis">
      ${book.description}
   </div>
</div>


<h1><u>Reviews</u></h1>

<div>
<c:forEach var="review" items="${book.reviews}">
<h3>${review.get(1)} :</h3>
<div class="review">
<p>${review.get(0)}</p>
</div>
</c:forEach>
</div>

<div>
      <iframe name="hiddenFrame0" class="hide"></iframe>
      <form action="/addReview" method="post" target="hiddenFrame0">
        <input type="hidden" name="ISBN" value="${book.ISBN}">
        <input style = "width: 80%; height: 50px; "class="review" type="text" name="review" value="">
         <button type="submit" class="btn btn-default btn-sm">
         <span class="glyphicon glyphicon-ok"></span> Add a review
         </button>
      </form>
   </div>
 
<%@ include file="common/footer.jspf"%>