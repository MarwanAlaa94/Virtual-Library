<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	Welcome You are now authenticated.
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
