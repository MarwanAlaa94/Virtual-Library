<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="container">
<h2>Sciencefiction</h2>
<c:forEach items="${Sciencefiction}" var="book">
   <tr>   
       </br>  
       <td>title: ${book.title}</td>
        </br>
       <td>author: ${book.author}</td>
        </br>
       <td><img src= ${book.imageLink}</alt="Flowers in Chania">
       </td>
   </tr>
</c:forEach>
<h2>Mystery</h2>
<c:forEach items="${Mystery}" var="book">
   <tr>   
       </br>  
       <td>title: ${book.title}</td>
        </br>
       <td>author: ${book.author}</td>
        </br>
       <td><img src= ${book.imageLink}</alt="Flowers in Chania">
       </td>
   </tr>
</c:forEach>
</br>
</br>
</br>
</br>
</br>
<h2>Romance</h2>
<c:forEach items="${Romance}" var="book">
   <tr>   
       </br>  
       <td>title: ${book.title}</td>
        </br>
       <td>author: ${book.author}</td>
        </br>
       <td><img src= ${book.imageLink}</alt="Flowers in Chania">
       </td>
   </tr>
</c:forEach>
</br>
</br>
</br>
</br>
</br>
<h2>Horror</h2>
<c:forEach items="${Horror}" var="book">
   <tr>   
       </br>  
       <td>title: ${book.title}</td>
        </br>
       <td>author: ${book.author}</td>
        </br>
       <td><img src= ${book.imageLink}</alt="Flowers in Chania">
       </td>
   </tr>
</c:forEach>
</br>
<h2>Drama</h2>
<c:forEach items="${Drama}" var="book">
   <tr>   
       </br>  
       <td>title: ${book.title}</td>
        </br>
       <td>author: ${book.author}</td>
        </br>
       <td><img src= ${book.imageLink}</alt="Flowers in Chania">
       </td>
   </tr>
</c:forEach>

<%@ include file="common/footer.jspf"%>
