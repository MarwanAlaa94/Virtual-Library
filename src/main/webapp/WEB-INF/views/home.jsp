<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
     </br>
     <c:forEach items="${result}" var="book">
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

</div>

<%@ include file="common/footer.jspf"%>
